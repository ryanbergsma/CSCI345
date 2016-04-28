/* This work by Christopher Reedy, email address: Chris.Reedy@wwu.edu,
 * is licensed under the Creative Commons Attribution 4.0 International
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/4.0/.
 */

package cs345.model.cell;

import cs345.model.Cell;

/**
 * Cell for dirt.
 *
 * There is only one dirt cell object that is shared by all dirt cells in the
 * grid. Dirt cells have CellType DIRT, are buildable and bulldozeable.
 *
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
public class Dirt implements Cell {

    /** The constant Dirt object. */
    public static Cell DIRT = new Dirt();

    /* Constructor is only used above. */
    private Dirt() { }

    @Override public CellType getCellType() {
        return CellType.DIRT;
    }

    @Override public boolean isBuildable() {
        return true;
    }

    @Override public boolean isBulldozeable() {
        return true;
    }

    @Override public void bulldoze() {
        // Nothing to do
    }
}
