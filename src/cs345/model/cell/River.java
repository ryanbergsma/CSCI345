/* This work by Christopher Reedy, email address: Chris.Reedy@wwu.edu,
 * is licensed under the Creative Commons Attribution 4.0 International
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/4.0/.
 */

package cs345.model.cell;

import cs345.model.Cell;

/**
 * Cell for water: rivers, lakes, etc..
 *
 * There is only one river cell object that is shared by all river cells in the
 * grid. Dirt cells have CellType RIVER, are not buildable and not bulldozeable.
 *
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
public class River implements Cell {

    public static River RIVER = new River();

    private River() { }

    @Override public CellType getCellType() {
        return CellType.RIVER;
    }
}
