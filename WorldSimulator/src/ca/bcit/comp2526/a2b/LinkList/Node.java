package ca.bcit.comp2526.a2b.LinkList;

/**
 * Node.
 * 
 * @author Eric.
 * @version 1.0
 * @param <E>
 */
class Node<E> {
	protected E key;
	/**
	 * next.
	 */
	protected Node<E> next;
	/**
	 * prev.
	 */
	protected Node<E> prev;
	/**
	 * Node.
	 * 
	 * @param data
	 *            data
	 */
	Node(E data) {
		this.key = data;
	}
}
