/* This work by Christopher Reedy, email address: Chris.Reedy@wwu.edu,
 * is licensed under the Creative Commons Attribution 4.0 International
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/4.0/.
 */

package cs345;

import cs345.model.ModelFactory;
import cs345.runner.TextRunner;

import java.io.InputStream;
import java.util.Properties;

/* Main class for the CS345Opolis game.
 *
 * This class performs the following actions:
 *   1) Fetch the properties file and parse it.
 *   2) construct an instance of the TextView class and call the run
 *      method on that instance.
 */
public class Main {

    public static String PROPERTIES_FILE = "cs345opolis.properties";

    /**
     * Get the external Properties file and parse it. Call TextView
     * to run the game.
     *
     * @param args command line arguments (unused)
     * @throws Exception allows Exceptions to pass out of main
     */
    public static void main(String[] args) throws Exception {
        InputStream propsFile = Main.class.getResourceAsStream(PROPERTIES_FILE);
        if (propsFile == null) {
            throw new AssertionError("Could not access properties file: " + PROPERTIES_FILE);
        }
        Properties defaults = new Properties();
        defaults.load(propsFile);
        TextRunner ui = new TextRunner(new ModelFactory(), defaults);
        ui.run();
    }
}
