package ca.bcit.comp2526.a2b.Serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Serialize.
 * 
 * @author Eric_Wu
 * @version 1.0
 * @param <E>
 */
public class Serialize<E> {
	/**
	 * prototype.
	 */
	private Class<E> genClass;
	/**
	 * constructor.
	 * 
	 * @param aClass
	 *            class
	 */
	public Serialize(Class<E> aClass) {
		genClass = aClass;
		System.out.println("Init Serialization");
	}
	/**
	 * Init.
	 * 
	 * @author Eric
	 * @version 1.0
	 */
	private static class InitSerialize {
		/**
		 * constructor.
		 * 
		 * @param o
		 *            o
		 * @param filename
		 *            filename
		 */
		InitSerialize(Object o, String filename) {
			System.out.println("Start Serialization");
			try {
				FileOutputStream fileOut = new FileOutputStream(filename);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(o);
				out.close();
				out.flush();
				fileOut.close();
				System.out.println("Serialized data is saved in " + filename);
			} catch (IOException i) {
				i.printStackTrace();
			}
		}
	}
	/**
	 * DeSerialize.
	 * 
	 * @author Eric
	 * @version 1.0
	 */
	private class DeSerialize {
		private Object temp;
		/**
		 * constructor.
		 * 
		 * @param filename
		 *            filename
		 */
		DeSerialize(String filename) {
			System.out.println("Start Deserialization");
			try {
				FileInputStream fileIn = new FileInputStream(filename);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				temp = in.readObject();
				in.close();
				fileIn.close();
			} catch (IOException i) {
				System.out.println("Error");
				i.printStackTrace();
				temp = null;
			} catch (ClassNotFoundException c) {
				System.out.println("Error");
				c.printStackTrace();
				temp = null;
			}
		}
		/**
		 * get object.
		 * 
		 * @return E e
		 */
		public E getObject() {
			if (!genClass.isInstance(temp)) {
				return null;
			} else {
				return genClass.cast(temp);
			}
		}
	}
	/**
	 * init.
	 * 
	 * @param o
	 *            object to be serialize.
	 * @param filename
	 *            filename for serialized binary code.
	 */
	public static void init(Object o, String filename) {
		new InitSerialize(o, filename);
	}
	/**
	 * revert.
	 * 
	 * @param filename
	 *            filename of the serialized binary code
	 * @return E return object type
	 */
	public E revert(String filename) {
		return new DeSerialize(filename).getObject();
	}
}
