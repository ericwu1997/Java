package ca.bcit.comp2526.a2c;

import java.awt.Color;
/**
 * Herbivore.java.
 * 
 * @author Eric
 * @version 5.2
 */
public class Herbivore extends Species implements Animal {

	private static final int DEFAULT_HUNGER = 10;
	private int hunger;

	/**
	 * constructor.
	 * 
	 * @param location
	 *            cell locations
	 */
	Herbivore(Cell location) {
		super(location);
	}

	/**
	 * constructor.
	 * 
	 * @param location
	 *            cell destination
	 * @param status
	 *            move status
	 * @param hunger
	 *            hunger rate
	 */
	Herbivore(Cell location, boolean status, int hunger) {
		super(location, status);
		this.hunger = hunger;
	}

	@Override
	public void init() {
		hunger = DEFAULT_HUNGER;
	}

	@Override
	public void takeAction(Cell[] adjacent) {
		int foodCount = 0;
		int emptyCount = 0;
		int herbivoreCount = 0;
		if (!currentLocation.checkMoveStatus()) {

			if (hunger <= 0) {
				currentLocation.remove();
				return;
			}

			for (Cell cell : adjacent) {
				if (cell != null) {
					if (cell.getSpecies() instanceof Empty) {
						emptyCount++;
					} else if (cell.getSpecies() instanceof Herbivore) {
						if (!cell.checkMoveStatus()) {
							herbivoreCount++;
						}
					} else if (cell.getSpecies() instanceof Plant) {
						foodCount++;
					}
				}
			}

			Shuffle.oneDarray(adjacent);

			if (foodCount >= 2 && emptyCount >= 1 && herbivoreCount >= 2) {
				for (Cell cell : adjacent) {
					if (cell != null && cell.getSpecies() instanceof Empty) {
						giveBirth(cell);
						return;
					}
				}
			}

			for (Cell cell : adjacent) {
				if (cell != null) {
					if (cell.getSpecies() instanceof Plant) {
						if ((hunger < DEFAULT_HUNGER)) {
							hunger++;
						}
						move(cell);
						return;
					}
				}
			}

			for (Cell cell : adjacent) {
				if (cell != null && (cell.getSpecies() instanceof Empty)) {
					hunger--;
					move(cell);
					return;
				}
			}

		}

	}
	/**
	 * change speicies data of the desiginated cell.
	 * 
	 * @param destination
	 *            destination cell
	 */
	public void setCell(Cell destination) {
		destination.setSpecies(new Herbivore(destination, true, hunger));
	}

	/**
	 * move species to desiginated cell.
	 * 
	 * @param destination
	 *            desiginated cell
	 */
	public void move(Cell destination) {
		setCell(destination);
		currentLocation.remove();
	}

	@Override
	public Color getColor() {
		return Color.yellow;
	}

	@Override
	public void giveBirth(Cell location) {
		// TODO Auto-generated method stub
		location.setSpecies(new Herbivore(location, true, DEFAULT_HUNGER));
	}
}
