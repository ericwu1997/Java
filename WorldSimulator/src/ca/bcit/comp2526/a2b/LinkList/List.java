package ca.bcit.comp2526.a2b.LinkList;

import ca.bcit.comp2526.a2b.Exception.CouldNotAddException;
import ca.bcit.comp2526.a2b.Exception.CouldNotRemoveException;

/**
 * List.
 * 
 * @author Eric
 * @version 1.0
 * @param <E>
 */
interface List<E> extends Iterable<E> {
	/**
	 * add to front
	 * 
	 * @param e
	 *            e
	 * @throws CouldNotAddException
	 *             e
	 */
	void addToFront(E e) throws CouldNotAddException;
	/**
	 * remove from front.
	 * 
	 * @return E
	 * @throws CouldNotRemoveException
	 *             e
	 */
	E removeFromFront() throws CouldNotRemoveException;
	/**
	 * add to end.
	 * 
	 * @param e
	 *            e
	 * @throws CouldNotAddException
	 *             e
	 */
	void addToEnd(E e) throws CouldNotAddException;
	/**
	 * remove from end.
	 * 
	 * @return E e
	 * @throws CouldNotRemoveException
	 *             e
	 */
	E removeFromEnd() throws CouldNotRemoveException;
	/**
	 * iterator.
	 * 
	 * @return E
	 */
	// Iterator<E> iterator();

}
