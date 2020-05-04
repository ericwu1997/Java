package ca.bcit.comp2526.a2b.Exception;

/**
 * LinkListException.
 * 
 * @author Eric
 * @version 1.0
 */
public class CouldNotAddException extends NullPointerException {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	/**
	 * Could not add exception.
	 * @param s s
	 */
	public CouldNotAddException(String s) {
		super(s);
	}

}
