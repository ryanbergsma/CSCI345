/* This work by Christopher Reedy, email address: Chris.Reedy@wwu.edu,
 * is licensed under the Creative Commons Attribution 4.0 International
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/4.0/.
 */

package cs345.runner;

import cs345.model.Cs345Opolis;
import cs345.model.GridRectangle;

/**
 * A bulldoze Command, includes the region to be bulldozed.
 *
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
class BulldozeCommand implements Command {

    private TextRunner runner;
    private GridRectangle rect;

    /** Construct a new bulldoze command.
     *
     * @param runner the TextRunner associated with this command.
     * @param rect the rectangle to bulldoze
     */
    BulldozeCommand(TextRunner runner, GridRectangle rect) {
        this.runner = runner;
        this.rect = rect;
    }

    @Override
    public void run() throws CommandException {
        Cs345Opolis game = runner.getGame();
        if (!game.isBulldozeable(rect)) {
            throw new CommandException("Cannot bulldoze %s", rect);
        }
        game.bulldoze(rect);
    }
}