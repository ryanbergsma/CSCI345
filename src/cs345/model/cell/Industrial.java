/* This work by Christopher Reedy, email address: Chris.Reedy@wwu.edu,
 * is licensed under the Creative Commons Attribution 4.0 International
 * License. To view a copy of this license, visit
 * http://creativecommons.org/licenses/by/4.0/.
 */

package cs345.model.cell;

import cs345.model.*;

/**
 * Beginning of a class for Industrial zones
 *
 * @author Chris Reedy (Chris.Reedy@wwu.edu)
 */
public class Industrial implements Cell {

    @Override public CellType getCellType() {
        return CellType.INDUSTRIAL;
    }
}

