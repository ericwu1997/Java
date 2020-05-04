package main.java.composition;

import java.awt.Color;

import javax.swing.JLayeredPane;
import javax.swing.border.LineBorder;

import main.java.component.DisplayTile;
import main.java.game.tiles.Tile;
import main.java.manager.ResourceManager;
import main.java.property.Properties;

public class DisplayBoard extends JLayeredPane {
	private DisplayTile[][] board;
	private DisplayTile selected_tile;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public DisplayBoard() {
		setBounds(Properties.BOARD_FRAME_PADDING - Properties.TILE_FLIP_GAP,
				Properties.BOARD_FRAME_PADDING - Properties.TILE_FLIP_GAP, Properties.BOARD_WIDTH,
				Properties.BOARD_HEIGHT);
		setBorder(new LineBorder(Color.black, 10));
		init();
	}

	/**
	 * Initialize the tiles
	 */
	public void init() {
		board = new DisplayTile[Properties.GRID_DIMENSION][Properties.GRID_DIMENSION];
	}

	/**
	 * record the tiles
	 * 
	 * @param tile
	 *            tile
	 */
	public void setTile(Tile tile) {
		board[tile.getRow() - 1][tile.getCol() - 1] = new DisplayTile(tile);
	}

	/**
	 * render the tiles
	 */
	public void renderBoard() {
		for (int i = 0; i < Properties.GRID_DIMENSION; i++) {
			for (int j = 0; j < Properties.GRID_DIMENSION; j++) {
				add(board[i][j].getPanel());
			}
		}
	}

	/**
	 * This method flips all the tile one board
	 */
	public void flipAll() {
		Thread thread1 = new Thread() {
			public void run() {
				for (int i = 0; i < Properties.GRID_DIMENSION; i++) {
					for (int j = 0; j < Properties.GRID_DIMENSION; j++) {
						try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						board[i][j].flip();
					}
				}
			};
		};
		thread1.start();
		try {
			thread1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResourceManager.getInstance().clear();
	}

	/**
	 * This method returns a tile at x, y
	 * 
	 * @param x
	 *            x
	 * @param y
	 *            y
	 * @return tile at x y
	 */
	public DisplayTile getTile(int x, int y) {
		return board[x - 1][y - 1];
	}

	/**
	 * This method returns all tile
	 * 
	 * @return tile set
	 */
	public DisplayTile[][] getTileArray() {
		return board;
	}

	/**
	 * This method records the last selected tile on board
	 * 
	 * @param tile
	 *            tile last selected
	 */
	public void setLastSelectedTile(DisplayTile tile) {
		selected_tile = tile;
		selected_tile.select();
	}

	/**
	 * This method remove the selection of the last selected tile
	 */
	public void deselectLastTile() {
		if (selected_tile != null) {
			selected_tile.deselect();
		}
	}
}
