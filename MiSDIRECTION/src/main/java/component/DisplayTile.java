package main.java.component;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import main.java.animation.Animation;
import main.java.animation.AnimationProvider;
import main.java.animation.SpriteSheet;
import main.java.game.tiles.Tile;
import main.java.property.Properties;

public class DisplayTile {
	private Animation animation_pane;
	boolean flipped = false;
	boolean flippable = true;
	String color;
	int current_direction;
	Point position;

	/**
	 * Constructor
	 * 
	 * @param tile
	 *            tile object from system
	 */
	public DisplayTile(Tile tile) {
		current_direction = tile.getDirection() - 1;
		position = new Point();
		position.setLocation((tile.getCol() - 1) * Properties.TILE_WIDTH, (tile.getRow() - 1) * Properties.TILE_HEIGHT);
		animation_pane = new Animation(
				new SpriteSheet(getTileImageName(tile), Properties.TILE_SRC_WIDTH, Properties.TILE_SRC_HEIGHT, 9),
				(int) (Properties.TILE_SRC_WIDTH * Properties.TILE_SCALE_RATIO),
				(int) (Properties.TILE_SRC_HEIGHT * Properties.TILE_SCALE_RATIO));
		animation_pane.setBounds(position.x, position.y, Properties.TILE_WIDTH + 2 * Properties.TILE_FLIP_GAP,
				Properties.TILE_HEIGHT + 2 * Properties.TILE_FLIP_GAP);
	}

	/**
	 * This method returns the name of the image to be used to render the system
	 * tile.
	 * 
	 * @param tile
	 * @return tile source image name
	 */
	private String getTileImageName(Tile tile) {
		color = "";
		String direction;
		switch (tile.getPlayerNumber()) {
		case 1:
			color = "Red_";
			break;
		case 2:
			color = "Green_";
			break;
		case 3:
			color = "Blue_";
			break;
		case 4:
			color = "Yellow_";
			break;
		default:
			color = "";
			break;
		}
		switch (tile.getDirection()) {
		case 1:
			direction = "Up_";
			break;
		case 2:
			direction = "Up_Right_";
			break;
		case 3:
			direction = "Right_";
			break;
		case 4:
			direction = "Down_Right_";
			break;
		case 5:
			direction = "Down_";
			break;
		case 6:
			direction = "Down_Left_";
			break;
		case 7:
			direction = "Left_";
			break;
		case 8:
			direction = "Up_Left_";
			break;
		default:
			direction = "";
			break;
		}
		return color + direction + tile.getTypeString() + ".png";
	}

	/**
	 * This method returns the animation panel
	 * 
	 * @return animation panel
	 */
	public JPanel getPanel() {
		return animation_pane;
	}

	/**
	 * This method makes the tile not flip able
	 * 
	 * @return this tile
	 */
	public DisplayTile disableFlip() {
		flippable = false;
		return this;
	}

	/**
	 * This method rotates the tile on the board
	 * 
	 * @param tile
	 *            system tile
	 * @param clockwise
	 *            direction of the rotation
	 */
	public void rotate(Tile tile, boolean clockwise) {
		if (flipped) {
			animation_pane.changeSpriteSheetSrc(color + tile.getTypeString() + "_Rotation_Map.png");
			if (clockwise) {
				animation_pane.setYOffset(current_direction);
				current_direction = (current_direction + 2) % 8;
			} else {
				animation_pane.setYOffset(current_direction + 8);
				current_direction = (current_direction + 6) % 8;
			}
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					AnimationProvider.anime(animation_pane, 20, false);
				}
			});
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method flips the tile on the board
	 */
	public void flip() {
		flip(0);
	}

	/**
	 * This method flips the tile on the board
	 * 
	 * @param delay
	 *            wait time before flip
	 */
	public void flip(int delay) {
		if (flippable) {
			if (!flipped) {
				flipped = true;
				// TODO Auto-generated method stub
				Timer timer = new Timer(delay, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						AnimationProvider.anime(animation_pane, Properties.TILE_FLIP_RATE, false);
					}
				});
				timer.start();
			}
		}
	}

	/**
	 * This method returns the current frame number
	 * 
	 * @return current frame number
	 */
	public BufferedImage getCurrentFrame() {
		return animation_pane.getCurrentFrame();
	}

	/**
	 * This method highlights the tile
	 */
	public void select() {
		animation_pane.setBorder(
				BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.RED, 3)));
	}

	/**
	 * This method un highlight the tile
	 */
	public void deselect() {
		animation_pane.setBorder(null);
	}
}
