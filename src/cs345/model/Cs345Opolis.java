/* This work by Christopher Reedy, email address: Chris.Reedy@wwu.edu,
 * is licensed under the Creative Commons Attribution 4.0 International
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/4.0/.
 */

package cs345.model;

import cs345.model.cell.CellConstants;
import cs345.model.cell.Dirt;

import java.util.Properties;
import java.util.Random;

/**
 * Model for the CS345Opolis game.
 *
 * The model acts as a container for the components of the game. The Simulator
 * and Grid for the game are attributes of this class and can be retrieved
 * by calling the getSim and getGrid methods.
 *
 * This class is the holder for global properties of the game, such as
 * the total residential population and the random number generator used by
 * the game.
 *
 * Finally, this class is where global SimulationActions, are located. (See
 * PeriodInitAction and PeriodEndAction.
 *
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
public class Cs345Opolis {

    /* Names for initialization properties */
    public static final String GRID_WIDTH = "cs345opolis.grid.width";
    public static final String GRID_HEIGHT = "cs345opolis.grid.height";
    public static final String PRNG_SEED = "cs345opolis.randomseed";

    private Properties props;
    private Simulator sim;
    private Grid grid;

    // This really should be private, However, these are accessed from a
    // number of different places. So, these are now public.
    public int curResPop = 0; // Total residential population, start of period
    public int newResPop = 0; // Total residential population, accumulating

    private Random prng;

    /**
     * Factory function for constructing a new game.
     *
     * @param props the Properties object containing initialization values
     *              for the game
     * @return the game object.
     */
    public static Cs345Opolis newCity(Properties props) {
        return new Cs345Opolis(props);
    }

    /**
     * Construct a CS345Opolis object.
     *
     * The constructed Grid object is all DIRT. It is up to the object creator,
     * TextView to initialize the Grid.
     *
     * The simulator for the game is constructed and initialized with the
     * PeriodInitAction and PeriodEnd Action.
     *
     * The random number generator for the game is created. It either initialized
     * using the seed specified in the properties or, if the initialization
     * value is missing, uses the default initialization for the generator.
     */
    private Cs345Opolis(Properties props) {
        this.props = props;
        String prngSeed = props.getProperty(PRNG_SEED);
        if (prngSeed != null) {
            prng = new Random(Long.parseLong(prngSeed));
        } else {
            prng = new Random();
        }

        newGrid();
        newSimulator();
        // Simulator always initializes with stepOffset == 0
        sim.addAction(sim.getCurrentTime(), new PeriodInitAction());
        sim.addAction(sim.getCurrentTime().nextStep(SimulatorTime.STEPS_PER_PERIOD - 1),
                new PeriodEndAction());
    }

    /* Create a new grid object. The width and height are taken from the
     * properties object. The grid is initialized to all DIRT.
     */
    private void newGrid() {
        int width = Integer.parseInt(props.getProperty(GRID_WIDTH));
        int height = Integer.parseInt(props.getProperty(GRID_HEIGHT));
        grid = Grid.emptyGrid(width, height, Dirt.DIRT);
    }


    /**
     * Generate a new grid initialized by the given map generator.
     * @param width the grid width
     * @param height the grid height
     * @param generator the map generator to be used to create the map
     * @throws IllegalArgumentException if either the width or height is <= MIN_GRID_SIZE
     */
    public void newMapGrid(int width, int height, MapGenerator generator) {
        grid = new Grid(width, height);
        generator.generateMap(grid);
    }

    /* Create a new Simulator for the game. */
    private void newSimulator() {
        sim = new Simulator(props);
    }

    /**
     * Return the associated Grid object for this game.
     * @return the Grid object
     */
    public Grid getGrid() {
        return grid;
    }

    /**
     * Step the model a single step.
     */
    public void step() {
        sim.step();
    }

    /**
     * Add the given action to the simulation. The action will be scheduled
     * for its first execution at the given time.
     * @param time the time for first execution of the action
     * @param action the action to be executed
     */
     public void addAction(SimulatorTime time, SimulatorAction action) {
         sim.addAction(time, action);
     }

    /**
     * Remove the given action from the simulation.
     * @param action the action to be executed
     */
    public void removeAction(SimulatorAction action) {
        sim.removeAction(action);
    }

    /**
     * Get the current time for the simulation.
     * @return a SimulatorTime object with the current time.
     */
    public SimulatorTime getCurrentTime() {
        return sim.getCurrentTime();
    }

    /**
     * Return a random int in the range 0 <= return value < n.
     *
     * The value returned is from the random number generator that is
     * associated with the game.
     *
     * @param n the range for the value to be returned
     * @return the random integer.
     */
    public int prngNextInt(int n) {
        return prng.nextInt(n);
    }

    /**
     * Return true is the given rectangle in the grid is buildable.
     *
     * A given cell is buildable if the isBuildable method on the cell returns
     * true. A region is buildable if all the cells in the regions are
     * buildable.
     *
     * If the region is not valid (extends outside the grid), false is returned.
     *
     * @param rect the GridRectangle to check
     * @return true if all cells in the rectangle are buildable
     */
    public boolean isBuildable(GridRectangle rect) {
        if (!grid.validRegion(rect))
            return false;
        for (int col = rect.x; col < rect.x + rect.w; col++) {
            for (int row = rect.y; row < rect.y + rect.h; row++) {
                if (!grid.cellAt(col, row).isBuildable())
                    return false;
            }
        }
        return true;
    }

    /**
     * Return true is the given rectangle in the grid can be bulldozed..
     *
     * A given cell is bulldozeable if the isBulldozeable method on the cell returns
     * true. A region is bulldozeable if all the cells in the regions are
     * bulldozeable.
     *
     * If the region is not valid (extends outside the grid), false is returned.
     *
     * @param rect the GridRectangle to check
     * @return true if the region is bulldozeable, otherwise false
     */
    public boolean isBulldozeable(GridRectangle rect) {
        if (!grid.validRegion(rect))
            return false;
        for (int col = rect.x; col < rect.x + rect.w; col++) {
            for (int row = rect.y; row < rect.y + rect.h; row++) {
                if (!grid.cellAt(col, row).isBulldozeable())
                    return false;
            }
        }
        return true;
    }

    /**
     * Bulldoze the given region. If the region is not valid (extends outside)
     * the grid, an IndexOutOfBoundsException will be thrown.
     * @param rect the GridRectangle to bulldoze
     */
    public void bulldoze(GridRectangle rect) {
        for (int col = rect.x; col < rect.x + rect.w; col++) {
            for (int row = rect.y; row < rect.y + rect.h; row++) {
                Cell cell = grid.cellAt(col, row);
                cell.bulldoze();
                grid.setCellAt(col, row, CellConstants.DIRT);
            }
        }
    }

    /**
     * Perform initialization actions at the start of a period.
     *
     * This action is run at step 0 of each simulation period. It's purpose
     * is to do initialization for the beginning of a period.
     */
    private class PeriodInitAction implements SimulatorAction {
        @Override public int doAction() {
            newResPop = 0;

            // Reschedule to run at beginning of next period
            return Simulator.STEPS_PER_PERIOD;
        }
    }

    /**
     * Perform actions at the end of a period.
     *
     * This action is run at the last step of each simulation period. It's
     * purpose is to do cleanup/finalization for the period.
     */
    private class PeriodEndAction implements SimulatorAction {
        @Override public int doAction() {
            curResPop = newResPop;

            // Reschedule to run at end of next period
            return Simulator.STEPS_PER_PERIOD;
        }
    }
}
