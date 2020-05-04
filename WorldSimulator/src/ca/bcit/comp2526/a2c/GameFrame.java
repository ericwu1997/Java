package ca.bcit.comp2526.a2c;

import java.awt.GridLayout;
import javax.swing.JFrame;

/**
 * GameFrame.java.
 * 
 * @author Eric
 * @version 1.0
 */
public class GameFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private World world;

	/**
	 * constructor.
	 * 
	 * @param w
	 *            world
	 */
	public GameFrame(final World w) {
		world = w;
		world.init();
	}

	/**
	 * initialize.
	 */
	public void init() {
		setTitle("Assignment 2a");
		setLayout(new GridLayout(world.getRowCount(), world.getColumnCount()));
		int i = 0;
		for (int row = 0; row < world.getRowCount(); row++) {
			for (int col = 0; col < world.getColumnCount(); col++) {
				add(world.getCellAt(row, col));
			}
		}

		addMouseListener(new TurnListener(this));
	}
	/**
	 * take turn.
	 */
	public void takeTurn() {
		world.takeTurn();
		repaint();
	}

	/**
	 * load world.
	 * 
	 * @param w
	 *            w
	 */
	public void loadRecrod(World w) {
		this.world = w;
		getContentPane().removeAll();
		init();
		repaint();
	}
	
	public World getWorld() {
		return world;
	}
}
