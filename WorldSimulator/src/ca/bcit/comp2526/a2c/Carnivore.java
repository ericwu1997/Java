package ca.bcit.comp2526.a2c;

import java.awt.Color;
/**
 * Herbivore.java.
 * 
 * @author Eric
 * @version 5.2
 */
public class Carnivore extends Species implements Animal {

	private static final int DEFAULT_HUNGER = 7;
	private int hunger;

	/**
	 * constructor.
	 * 
	 * @param location
	 *            cell locations
	 */
	Carnivore(Cell location) {
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
	Carnivore(Cell location, boolean status, int hunger) {
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
		int carnivoreCount = 0;
		if (!currentLocation.checkMoveStatus()) {

			if (hunger <= 0) {
				currentLocation.remove();
				return;
			}

			Shuffle.oneDarray(adjacent);

			for (Cell cell : adjacent) {
				if (cell != null) {
					if (cell.getSpecies() instanceof Empty) {
						emptyCount++;
					} else if (cell.getSpecies() instanceof Carnivore) {
						if (!cell.checkMoveStatus()) {
							carnivoreCount++;
						}
					} else if (cell.getSpecies() instanceof Herbivore
							|| cell.getSpecies() instanceof Omnivore) {
						foodCount++;
					}
				}
			}

			if (foodCount >= 2 && emptyCount >= 2 && carnivoreCount >= 1) {
				for (Cell cell : adjacent) {
					if (cell != null && cell.getSpecies() instanceof Empty) {
						giveBirth(cell);
						return;
					}
				}
			}

			for (Cell cell : adjacent) {
				if (cell != null) {
					if (cell.getSpecies() instanceof Omnivore
							|| cell.getSpecies() instanceof Herbivore) {
						if ((hunger < DEFAULT_HUNGER)) {
							hunger++;
						}
						move(cell);
						return;
					}
				}
			}
			hunger--;
			for (Cell cell : adjacent) {
				if (cell != null && (cell.getSpecies() instanceof Empty)) {
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
		destination.setSpecies(new Carnivore(destination, true, hunger));
	}

	/**
	 * move species to desiginated cell.
	 * 
	 * @param destination
	 *            desiginated cell
	 */
	@Override
	public void move(Cell destination) {
		setCell(destination);
		currentLocation.remove();
	}

	@Override
	public void giveBirth(Cell location) {
		// TODO Auto-generated method stub
		location.setSpecies(new Carnivore(location, true, DEFAULT_HUNGER));
	}

	@Override
	public Color getColor() {
		return Color.MAGENTA;
	}

}
