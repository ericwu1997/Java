
package ca.bcit.comp2526.a2c;

import java.awt.Color;

/**
 * Empty.
 * 
 * @author Eric
 * @version 1.0
 */
public class Empty extends Species {
	/**
	 * constructor.
	 */
	Empty() {
		init();
	}

	/**
	 * initialize.
	 */
	public void init() {
		// System.out.println("Nothing!");
	}

	/**
	 * no behaviour.
	 * 
	 * @param adjacent
	 *            adjacent cell
	 */
	public void takeAction(Cell[] adjacent) {

	}

	@Override
	public void setCell(Cell destination) {
		// TODO Auto-generated method stub

	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return Color.WHITE;
	}
}