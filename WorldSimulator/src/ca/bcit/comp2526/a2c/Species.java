package ca.bcit.comp2526.a2c;

import java.awt.Color;
import java.io.Serializable;
/**
 * Species.java.
 * 
 * @author Eric
 * @version 1.0
 */
public abstract class Species implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * currentLocation.
	 */
	protected Cell currentLocation;

	/**
	 * store neighbour data.
	 */
	protected int neighBourList[] = new int[5];

	Species() {

	}

	Species(Cell location) {
		this.currentLocation = location;
		init();
	}

	Species(Cell location, boolean status) {
		this.currentLocation = location;
		currentLocation.updateMoveStatus(status);
	}

	/**
	 * initialize.
	 */
	public abstract void init();

	/**
	 * take action.
	 * 
	 * @param adjacent
	 *            adjacent cell
	 */
	public abstract void takeAction(Cell[] adjacent);

	/**
	 * get species' color.
	 * 
	 * @return Color color
	 */
	public abstract Color getColor();

	/**
	 * set cell.
	 * 
	 * @param destination
	 *            cell location
	 */
	public abstract void setCell(Cell destination);

}
