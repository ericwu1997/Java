package ca.bcit.comp2526.a2c;

/**
 * SpeciesGenerator.java.
 * 
 * @author Eric
 * @version 1.0
 *
 */
public class SpeciesGenerator {
	private static final int HERBIVORE_CHANCE = 80;
	private static final int FIFTYPERCENT = 50;
	private static final int EIGHTEEN = 18;
	private static final int OMNIVORE_CHANCE = 8;
	/**
	 * generate species.
	 * 
	 * @param cell
	 *            cell
	 * @param rand
	 *            rand
	 * @return Species species
	 */
	static Species nextSpecies(Cell cell, int rand) {
		if (rand <= EIGHTEEN) {
			if (rand <= OMNIVORE_CHANCE) {
				return new Omnivore(cell);
			} else {
				return new Carnivore(cell);
			}
		}
		if (rand >= FIFTYPERCENT) {
			if (rand >= HERBIVORE_CHANCE) {
				return new Herbivore(cell);
			} else {
				return new Plant(cell);
			}
		} else {
			return new Empty();
		}
	}
}
