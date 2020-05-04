package ca.bcit.comp2526.a2b.Exception;

/**
 * CouldNotRemoveException.
 * 
 * @author Eric
 * @version 1.0
 */
public class CouldNotRemoveException extends NullPointerException {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	/**
	 * Could not add exception.
	 * 
	 * @param s
	 *            s
	 */
	public CouldNotRemoveException(String s) {
		super(s);
	}

}
