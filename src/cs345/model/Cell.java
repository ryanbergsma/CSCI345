/* This work by Christopher Reedy, email address: Chris.Reedy@wwu.edu,
 * is licensed under the Creative Commons Attribution 4.0 International
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/4.0/.
 */

package cs345.model;

import cs345.model.cell.CellType;

/**
 * Interface representing a Cell in the the grid map for a game.
 *
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
public interface Cell {

    /**
     * Return the CellType for this cell
     * @return the CellType
     */
    CellType getCellType();

    /**
     * Return true if this cell is some form of woods.
     *
     * Default implementation is that the cell is not woods.
     *
     * @return true if this is woods
     */
    default boolean isTree() {
        return false;
    }

    /**
     * Return true if this cell can be built on.
     *
     * Default implementation is that the cell is not buildable.
     *
     * @return true if the cell is buildable
     */
    default boolean isBuildable() {
        return false;
    }

    /**
     * Return true if this cell can be bulldozed.
     *
     * Default implementation is that the cell cannot be bulldozed.
     *
     * @return true if the cell can be bulldozed
     */
    default boolean isBulldozeable() {
        return false;
    }

    /**
     * Bulldoze the cell.
     *
     * This method provides an opportunity for any cleanup required when the
     * cell is being bulldozed. The default implementation throws an
     * UnsupportedOperationException on the assumption that the cell cannot
     * be bulldozed.
     */
    default void bulldoze() {
        throw new UnsupportedOperationException("Illegal bulldoze operation");
    }
}
