/* This work by Christopher Reedy, email address: Chris.Reedy@wwu.edu,
 * is licensed under the Creative Commons Attribution 4.0 International
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/4.0/.
 */

package cs345.runner;

import cs345.model.Cs345Opolis;
import cs345.model.MapGenerator;
import cs345.model.ModelFactory;

import java.io.PrintStream;
import java.util.*;

/**
 * Run a CS345Opolis game from the command line.
 *
 * This class runs a game. Input is taken from System.in and all output goes
 * to System.out. Command input and text output are handled by the TextView
 * class.
 *
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
public class TextRunner {

    public static final String GRID_WIDTH = "cs345opolis.grid.width";
    public static final String GRID_HEIGHT = "cs345opolis.grid.height";

    private Properties props;

    private final Cs345Opolis game;

    private boolean quit = false;

    private MapGenerator mapGen = null;
    boolean newGridOK = true;

    private Scanner input;
    private PrintStream output;

    private final TextView view;

    /**
     * Create a text runner on a model from the given factory.
     * @param factory a factory that creates a model object
     */
    public TextRunner(ModelFactory factory, Properties props) {
        this.props = props;
        this.game = factory.makeModel(this.props);
        this.input = new Scanner(System.in);
        this.output = System.out;
        this.view = new TextView(this, game, input, output);
    }

    /**
     * Run the text runner.
     *
     * The text runner will ask the user for input until input exhausts or a
     * quit command is received. The commands will be applied to the model
     * that constructed the runner.
     *
     * Command input will be taken from System.in and all output is sent to
     * System.out.
     */
    public void run() {
        initMap(true);
        view.welcomeMessage();
        output.print("> ");
        while (!quit && input.hasNextLine()) {
            String line = input.nextLine().trim();
            if (line.length() != 0) {
                String[] words = line.split("\\s+");
                try {
                    Command cmd = view.parseCommand(words);
                    cmd.run();
                } catch (CommandException ex) {
                    view.message(ex.getMessage());
                }
            }
            if (!quit) {
                output.print("> ");
            }
        }
    }

    /** Get the TextView object
     */
    TextView getView() {
        return view;
    }

    /** Get the Cs345Opolis game object
     */
    Cs345Opolis getGame() {
        return game;
    }

    /* Set quit to be true. */
    void setQuit() {
        quit = true;
    }

    /* Initialize a map. */
    void initMap(boolean newGenerator) {
        int width = Integer.parseInt(props.getProperty(GRID_WIDTH));
        int height = Integer.parseInt(props.getProperty(GRID_HEIGHT));
        if (newGenerator)
            mapGen = new MapGenerator(props);
        game.newMapGrid(width, height, mapGen);
    }

    /** Step the game the given number (num) of intervals.
     *
     * @param num number of intervals
     * @param interval size of an interval
     */
    void step(int num, int interval) {
        newGridOK = false; // step command invalidates new grid
        for (int step = 0; step < num * interval; step++) {
            game.step();
        }
    }

//    /* Print a command. For testing purposes. */
//    private void printCommand(String command, String[] words) {
//        message(String.format("Command %s: %s%n",
//                command, Arrays.toString(words)));
//    }
}
