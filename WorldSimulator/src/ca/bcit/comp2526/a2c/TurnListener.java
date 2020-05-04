package ca.bcit.comp2526.a2c;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * TurnListener.java.
 * 
 * @author Eric
 * @version 1.0
 *
 */
public class TurnListener extends MouseAdapter {

	private GameFrame g;
	/**
	 * constructor.
	 * 
	 * @param gameFrame
	 *            game frame
	 */
	public TurnListener(GameFrame gameFrame) {
		g = gameFrame;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		g.takeTurn();
	}

}
