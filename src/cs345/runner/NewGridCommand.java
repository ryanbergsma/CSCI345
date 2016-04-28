/* This work by Christopher Reedy, email address: Chris.Reedy@wwu.edu,
 * is licensed under the Creative Commons Attribution 4.0 International
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/4.0/.
 */

package cs345.runner;

/**
 * A newgrid Command
 *
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
class NewGridCommand implements Command {

    private TextRunner runner;

    NewGridCommand(TextRunner runner) {
        this.runner = runner;
    }

    @Override
    public void run() throws CommandException {
        if (runner.newGridOK) {
            runner.initMap(false);
        } else {
            throw new CommandException("New grid not allowed");
        }
    }
}