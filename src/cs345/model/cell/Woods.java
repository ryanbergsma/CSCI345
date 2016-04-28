/* This work by Christopher Reedy, email address: Chris.Reedy@wwu.edu,
 * is licensed under the Creative Commons Attribution 4.0 International
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/4.0/.
 */

package cs345.model.cell;

import cs345.model.Cell;

/**
 * Cell for woods, forest, trees.
 *
 * There is only one woods cell object that is shared by all woods cells in the
 * grid. Woods cells have CellType WOODS, are buildable and bulldozeable.
 *
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
public class Woods implements Cell {

    public static Cell WOODS = new Woods();

    private Woods() { }

    @Override public boolean isTree() {
        return true;
    }

    @Override public CellType getCellType() {
        return CellType.WOODS;
    }

    @Override public boolean isBuildable() {
        return true;
    }

    @Override public boolean isBulldozeable() {
        return true;
    }

    @Override public void bulldoze() {
        // Nothing to do here
    }
}
