package ca.bcit.comp2526.a2c;

import java.awt.Color;

import javax.swing.BorderFactory;

/**
 * just to make tile look better.
 * 
 * @author Eric
 * @version 1.0
 */
public class PerfectTile {

	/**
	 * add cell border.
	 * 
	 * @param cell
	 *            cell
	 */
	static void addCellBorder(Cell cell) {
		if (cell.frameRightBorder() && cell.frameBottomBorder()) {
			cell.setBorder(
					BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		} else if (cell.frameRightBorder()) {
			cell.setBorder(
					BorderFactory.createMatteBorder(1, 1, 0, 1, Color.BLACK));
		} else if (cell.frameBottomBorder()) {
			cell.setBorder(
					BorderFactory.createMatteBorder(1, 1, 1, 0, Color.BLACK));
		} else {
			cell.setBorder(
					BorderFactory.createMatteBorder(1, 1, 0, 0, Color.BLACK));
		}

	}

}
