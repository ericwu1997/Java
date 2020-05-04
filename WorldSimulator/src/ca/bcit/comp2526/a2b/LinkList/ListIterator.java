package ca.bcit.comp2526.a2b.LinkList;

import java.util.Iterator;

/**
 * ListIterator.
 * 
 * @author Eric
 * @version 1.0
 * @param <E>
 */
class ListIterator<E> implements Iterator<E> {

	protected Node<E> nextNode;
	/**
	 * previous node.
	 */
	protected Node<E> prevNode;

	/**
	 * Constructor.
	 * 
	 * @param head head
	 */
	ListIterator(Node<E> head) {
		nextNode = head;
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E next() {
		// TODO Auto-generated method stub
		return null;
	}
}
