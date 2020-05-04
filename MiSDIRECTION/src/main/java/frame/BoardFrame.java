package main.java.frame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import main.java.component.DisplayTile;
import main.java.component.DisplayToken;
import main.java.composition.DisplayBoard;
import main.java.composition.DisplayPlayers;
import main.java.manager.ResourceManager;
import main.java.property.Properties;

public class BoardFrame extends JPanel {
	DisplayPlayers players;
	DisplayBoard board;
	
	BufferedImage background;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	BoardFrame() {
		setPreferredSize(new Dimension(Properties.BOARD_WIDTH + 2 * Properties.BOARD_FRAME_PADDING,
				Properties.BOARD_HEIGHT + 2 * Properties.BOARD_FRAME_PADDING));
		
		background = ResourceManager.getInstance().grabBufferedImg("woodBoard.png");
		
		players = new DisplayPlayers();
		board = new DisplayBoard();
		add(players);
		add(board);
		setLayout(null);
	}

	private void add(DisplayPlayers players) {
		for (DisplayToken token : players.getPlayersArray()) {
			if (token != null) {
				add(token.getPanel());
			}
		}
	}

	public DisplayToken getToken(int i) {
		return players.getToken(i);
	}

	public void flipAll() {
		board.flipAll();
	}

	public DisplayTile getTile(int x, int y) {
		return board.getTile(x, y);
	}

	public DisplayBoard getBoard() {
		return board;
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
	}
}
