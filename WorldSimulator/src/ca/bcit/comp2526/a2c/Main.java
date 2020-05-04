package ca.bcit.comp2526.a2c;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import ca.bcit.comp2526.a2b.Serialization.Serialize;

/**
 * Driver class.
 * 
 * @author Eric
 * @version 1.0
 */
public final class Main {

	private static final Toolkit TOOLKIT;
	private static final int WORLD_SIZE = 25;
	private static final float SCREEN_SIZE = 0.80f;
	private static final float MAX_DIMENSION = 100.0f;

	static {
		TOOLKIT = Toolkit.getDefaultToolkit();
	}

	/**
	 * constructor.
	 */
	private Main() {
	}

	/**
	 * program entry point.
	 * 
	 * @param argv
	 *            cmd args
	 */
	public static void main(final String[] argv) {
		final GameFrame frame;

		RandomGenerator.reset();
		frame = new GameFrame(new World(WORLD_SIZE, WORLD_SIZE));
		position(frame);
		frame.init();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setFocusable(true);
		frame.addKeyListener(new KeyAdapter() {
			private Serialize<World> serialize = new Serialize<World>(
					World.class);
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_S) {
					Serialize.init(frame.getWorld(),
							"./src/ca/bcit/comp2526/a2b/Serialization/record.ser");
					System.out.println("Saved");
				}
				if (e.getKeyCode() == KeyEvent.VK_L) {
					frame.loadRecrod(serialize.revert(
							"./src/ca/bcit/comp2526/a2b/Serialization/record.ser"));
					System.out.println("Loaded");
				}
			}
		});
		frame.setVisible(true);
	}

	/**
	 * set frame size.
	 * 
	 * @param frame
	 *            frame
	 */
	private static void position(final GameFrame frame) {
		final Dimension size;

		size = calculateScreenArea(SCREEN_SIZE, SCREEN_SIZE);
		frame.setSize(size);
		frame.setLocation(centreOnScreen(size));
	}

	/**
	 * set window location.
	 * 
	 * @param size
	 *            size
	 * @return Point location of the screen
	 */
	public static Point centreOnScreen(final Dimension size) {
		final Dimension screenSize;

		if (size == null) {
			throw new IllegalArgumentException("Size cannot be null");
		}

		screenSize = TOOLKIT.getScreenSize();

		return (new Point((screenSize.width - size.width) / 2,
				(screenSize.height - size.height) / 2));
	}

	/**
	 * get screen size.
	 * 
	 * @param widthPercent
	 *            width
	 * @param heightPercent
	 *            height
	 * @return Dimension screen size
	 */
	public static Dimension calculateScreenArea(final float widthPercent,
			final float heightPercent) {
		final Dimension screenSize;
		final Dimension area;
		final int width;
		final int height;
		final int size;

		if ((widthPercent <= 0.0f) || (widthPercent > MAX_DIMENSION)) {
			throw new IllegalArgumentException("widthPercent cannot be "
					+ "<= 0 or > 100 - got: " + widthPercent);
		}

		if ((heightPercent <= 0.0f) || (heightPercent > MAX_DIMENSION)) {
			throw new IllegalArgumentException("heightPercent cannot be "
					+ "<= 0 or > 100 - got: " + heightPercent);
		}

		screenSize = TOOLKIT.getScreenSize();
		width = (int) (screenSize.width * widthPercent);
		height = (int) (screenSize.height * heightPercent);
		size = Math.min(width, height);
		area = new Dimension(size, size);

		return (area);
	}
}
