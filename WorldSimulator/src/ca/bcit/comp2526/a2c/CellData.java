package ca.bcit.comp2526.a2c;

import java.awt.Graphics;

/**
 * Set species data in a cell.
 * 
 * @author Eric
 * @version 1.0
 */
public class CellData {
	private static final int TILE_SIZE = 100;
	/**
	 * set properties.
	 * 
	 * @param g
	 *            Graphics object
	 * @param type
	 *            species type
	 */
	public static void setProperties(Graphics g, Species type) {
		g.setColor(type.getColor());
		g.fillRect(0, 0, TILE_SIZE, TILE_SIZE);
	}
}
