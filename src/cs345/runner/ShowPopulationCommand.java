/* This work by Christopher Reedy, email address: Chris.Reedy@wwu.edu,
 * is licensed under the Creative Commons Attribution 4.0 International
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/4.0/.
 */

package cs345.runner;

/**
 * Display the total population
 *
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
class ShowPopulationCommand implements Command {

    private TextRunner runner;

    ShowPopulationCommand(TextRunner runner) {
        this.runner = runner;
    }

    @Override
    public void run() throws CommandException {
        // Show the current population
        runner.getView().message("%d", runner.getGame().curResPop);
    }
}