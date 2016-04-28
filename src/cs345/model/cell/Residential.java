/* This work by Christopher Reedy, email address: Chris.Reedy@wwu.edu,
 * is licensed under the Creative Commons Attribution 4.0 International
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/4.0/.
 */

package cs345.model.cell;

import cs345.model.*;

/**
 * A cell representing a Residential zone.
 *
 * A residential zone is a 3x3 grid of cells. All cell in the zone reference
 * the same residential zone instance.
 *
 * A residential zone implements SimulatorAction. This allows the zone to
 * update its attributes every period.
 *
 * Residential zones have CellType RESIDENTIAL and are bulldozeable but not
 * buildable.
 *
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
public class Residential extends Zone implements SimulatorAction {

    private Cs345Opolis parent; // The associated game for this zone
    private GridLocation center; // The center location of the zone
    private int population; // The population of the zone

    /**
     * Construct a new residential zone.
     * @param parent The parent game
     * @param loc the GridLocation of the center of the zone
     */
    public Residential(Cs345Opolis parent, GridLocation loc) {
        this.parent = parent;
        this.center = loc;
        this.population = 0;
        placeZoneInGrid(this);
        scheduleUpdate();
    }

    @Override public CellType getCellType() {
        return CellType.RESIDENTIAL;
    }

    @Override public boolean isBulldozeable() {
            return true;
        }

    /**
     * Return the population for this zone. The population is changed by
     * the doAction method which is called once every period.
     * @return the population
     */
    public int getPopulation() {
        return population;
    }

    /* Place the given cell is all cells of the zones grid. */
    private void placeZoneInGrid(Cell cell) {
        Grid grid = parent.getGrid();
        for (int col = center.x - 1; col <= center.x + 1; col++) {
            for (int row = center.y - 1; row <= center.y + 1; row ++) {
                grid.setCellAt(col, row, cell);
            }
        }
    }

    /**
     * Bulldoze the zone.
     *
     * When any cell of the zone is bulldozed, the entire zone is bulldozed.
     */
    @Override
    public void bulldoze() {
        unscheduleUpdate();
        placeZoneInGrid(CellConstants.DIRT);
    }

    /* Schedule the cell with the simulator. */
    private void scheduleUpdate() {
        parent.addAction(parent.getCurrentTime().nextStep(1), this);
    }

    /* Unschedule the cell when the zone is bulldozed. */
    private void unscheduleUpdate() {
        parent.removeAction(this);
    }

    /**
     * Do the periodic update for the zone.
     *
     * The periodic update adjust the population toward the desired population.
     *
     * The action will be rescheduled for the next PERIOD.
     * @return Simulator.STEPS_PER_PERIOD
     */
    @Override
    public int doAction() {
        // Do we want to adjust the population
        if (parent.prngNextInt(8) == 0) {
            // 1 in every 8 cycles (random) adjust population
            // Get a random number 20 .. 60. If we're less than that, add
            // people. If we're more than that, subtract.
            int desired = parent.prngNextInt(40) + 20;
            if (population < desired) {
                population += parent.prngNextInt(3) + 1;
            } else if (population > desired) {
                population = Math.max(0, population - (parent.prngNextInt(3) + 1));
            }
        }

        // Update total population in parent
        parent.newResPop += population;

        // Reschedule for next week
        return SimulatorTime.WEEK * Simulator.STEPS_PER_PERIOD;
    }
}
