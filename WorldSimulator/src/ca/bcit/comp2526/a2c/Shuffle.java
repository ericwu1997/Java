package ca.bcit.comp2526.a2c;

/**
 * Shuffle.java.
 * 
 * @author Eric
 * @version 1.0
 */
public class Shuffle {

	/**
	 * shuffle one d array.
	 * 
	 * @param array
	 *            array
	 * @return array after shuffle.
	 */
	public static Object[] oneDarray(Object[] array) {
		for (int i = 0; i < array.length; i++) {
			try {
				int a = RandomGenerator.nextNumber(array.length);
				int b = RandomGenerator.nextNumber(array.length);
				Object temp = array[a];
				array[a] = array[b];
				array[b] = temp;
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println(e);
			}
		}
		return array;
	}

}
