
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	private static final int NUM_KEYS = 256;
	private static final boolean[] keys = new boolean[NUM_KEYS];
	private static final boolean[] lastKeys = new boolean[NUM_KEYS];
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		keys[e.getKeyCode()] = true;
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		keys[e.getKeyCode()] = false;
	}
	
	public static boolean isDown(int keyCode) {
		return keys[keyCode];
	}
	
	public static void update() {
		for(int i = 0; i < NUM_KEYS; i++)
			lastKeys[i] = keys[i];
	}
	
	public static boolean wasPressed(int keyCode) {
		return 	isDown(keyCode) && !lastKeys[keyCode];
	}
	
	public static boolean wasRelease(int keyCode) {
		return 	!isDown(keyCode) && lastKeys[keyCode];
	}
}
