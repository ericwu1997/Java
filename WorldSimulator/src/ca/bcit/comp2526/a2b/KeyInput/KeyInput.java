package ca.bcit.comp2526.a2b.KeyInput;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * KeyInput.
 * 
 * @author Eric
 * @version 1.0
 */
public class KeyInput extends KeyAdapter {

	/**
	 * key options.
	 */
	private static final int NUM_KEYS = 256;
	private static final boolean[] KEYS = new boolean[NUM_KEYS];
	private static final boolean[] LASTKEYS = new boolean[NUM_KEYS];

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		KEYS[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		KEYS[e.getKeyCode()] = false;
	}

	/**
	 * isDown.
	 * 
	 * @param keyCode
	 *            key
	 * @return boolean
	 */
	public static boolean isDown(int keyCode) {
		return KEYS[keyCode];
	}

	/**
	 * update.
	 */
	public static void update() {
		for (int i = 0; i < NUM_KEYS; i++) {
			LASTKEYS[i] = KEYS[i];
		}
	}

	/**
	 * was pressed.
	 * 
	 * @param keyCode
	 *            key
	 * @return boolean
	 */
	public static boolean wasPressed(int keyCode) {
		return isDown(keyCode) && !LASTKEYS[keyCode];
	}

	/**
	 * was released.
	 * 
	 * @param keyCode
	 *            key
	 * @return boolean
	 */
	public static boolean wasRelease(int keyCode) {
		return !isDown(keyCode) && LASTKEYS[keyCode];
	}
}
