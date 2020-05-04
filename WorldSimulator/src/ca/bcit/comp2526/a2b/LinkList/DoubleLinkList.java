package ca.bcit.comp2526.a2b.LinkList;

import java.util.Iterator;
import java.util.NoSuchElementException;

import ca.bcit.comp2526.a2b.Exception.CouldNotAddException;
import ca.bcit.comp2526.a2b.Exception.CouldNotRemoveException;

/**
 * DoubleLink.
 * 
 * @author Eric
 * @version 1.0
 * @param <E>
 */
public class DoubleLinkList<E> implements List<E> {
	/**
	 * head and tail.
	 */
	private Node<E> head;
	private Node<E> tail;

	/**
	 * Node.
	 * 
	 * @author Eric
	 */

	@Override
	public void addToFront(E data) throws CouldNotAddException {
		if (data == null) {
			throw new CouldNotAddException("Error!");
		}
		Node<E> temp = new Node<E>(data);
		if (head == null) {
			head = temp;
			tail = head;
			return;
		}
		temp.next = head;
		head.prev = temp;
		head = temp;
	}

	@Override
	public E removeFromFront() throws CouldNotRemoveException {
		if (head == null) {
			throw new CouldNotRemoveException("Error!");
		}
		E removedData = head.key;
		head = head.next;
		head.prev = null;
		return removedData;
	}

	@Override
	public void addToEnd(E data) throws CouldNotAddException {
		// TODO Auto-generated method stub
		if (data == null) {
			throw new CouldNotAddException("Error!");
		}
		Node<E> temp = new Node<E>(data);

		if (tail == null) {
			head = temp;
			tail = temp;
			return;
		}

		tail.next = temp;
		temp.prev = tail;
		tail = temp;
	}

	@Override
	public E removeFromEnd() throws CouldNotRemoveException {
		if (tail == null) {
			throw new CouldNotRemoveException("Error!");
		}

		E removedData;
		removedData = tail.key;
		tail = tail.prev;
		tail.next = null;
		return removedData;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		Iterator<E> iter = new ListIterator<E>(head) {

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return nextNode != null;
			}

			@Override
			public E next() {
				// TODO Auto-generated method stub
				if (!this.hasNext()) {
					throw new NoSuchElementException("end of the iteration");
				}

				prevNode = nextNode;
				nextNode = nextNode.next;

				return prevNode.key;
			}

		};

		return iter;
	}

}
