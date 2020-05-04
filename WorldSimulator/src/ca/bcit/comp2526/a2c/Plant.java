package ca.bcit.comp2526.a2c;

import java.awt.Color;

/**
 * Plant.java.
 * 
 * @author Eric
 * @version 1.0
 */
public class Plant extends Species {

	/**
	 * constructor.
	 * 
	 * @param location
	 *            cell location
	 */
	public Plant(Cell location) {
		super(location);
	}
	
	/**
	 * constructor.
	 * 
	 * @param location
	 *            cell location
	 * @param status
	 *            move status
	 */
	public Plant(Cell location, boolean status) {
		super(location, status);
	}

	@Override
	public void init() {
		// System.out.println("Plants!");
	}

	@Override
	public void takeAction(Cell[] adjacent) {
		int plantsCount = 0;
		int emptyCount = 0;
		if (!currentLocation.checkMoveStatus()) {
			for (Cell cell : adjacent) {
				if (cell != null) {
					if (cell.getSpecies() instanceof Plant) {
						plantsCount++;
					}
					if (cell.getSpecies() instanceof Empty) {
						emptyCount++;
					}
				}
			}

			Shuffle.oneDarray(adjacent);

			if (plantsCount >= 2 && emptyCount >= (2 + 1)) {
				for (Cell cell : adjacent) {
					if (cell != null && cell.getSpecies() instanceof Empty) {
						setCell(cell);
						return;
					}
				}
			}
		}

	}

	/**
	 * set species data of desiginated cell.
	 * 
	 * @param destination
	 *            desiginated cell
	 */
	public void setCell(Cell destination) {
		destination.setSpecies(new Plant(destination, true));
	}

	@Override
	public Color getColor() {
		return Color.green;
	}

}
