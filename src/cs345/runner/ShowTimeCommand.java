/* This work by Christopher Reedy, email address: Chris.Reedy@wwu.edu,
 * is licensed under the Creative Commons Attribution 4.0 International
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/4.0/.
 */

package cs345.runner;

import cs345.model.SimulatorTime;

/**
 * Display the time
 *
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
class ShowTimeCommand implements Command {

    private TextRunner runner;

    ShowTimeCommand(TextRunner runner) {
        this.runner = runner;
    }

    @Override
    public void run() throws CommandException {
        // Show the current time
        SimulatorTime.TimeData curTime =
                runner.getGame().getCurrentTime().getTimeData();
        runner.getView().message("%s", curTime);
   }
}