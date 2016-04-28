/* This work by Christopher Reedy, email address: Chris.Reedy@wwu.edu,
 * is licensed under the Creative Commons Attribution 4.0 International
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/4.0/.
 */

package cs345.runner;

import cs345.model.Cs345Opolis;
import cs345.model.GridLocation;
import cs345.model.GridRectangle;
import cs345.model.cell.Residential;

/**
 * A zone Command, includes the type of zone to be created.
 *
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
class ZoneCommand implements Command {

    private TextRunner runner;
    private String zoneType;
    private GridLocation loc;

    ZoneCommand(TextRunner runner, String zoneType, GridLocation loc) {
        this.runner = runner;
        this.zoneType = zoneType;
        this.loc = loc;
    }

    @Override
    public void run() throws CommandException {
        int col = loc.x;
        int row = loc.y;

        // Validate location
        GridRectangle zoneRect = new GridRectangle(col - 1, row - 1, 3, 3);
        if (!runner.getGame().isBuildable(zoneRect)) {
            throw new CommandException("Cannot build at %s", loc);
        }

        Cs345Opolis game = runner.getGame();
        switch (zoneType) {
            case "residential":
                new Residential(game, loc);
                break;
            default:
                throw new AssertionError("Unknown zone type " + zoneType);
        }
    }
}