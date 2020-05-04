package main.java.component;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import main.java.animation.Animation;
import main.java.animation.AnimationProvider;
import main.java.animation.SpriteSheet;
import main.java.property.Properties;

public class DisplayToken {
	private Animation animation_pane;
	private String tokenName;
	private int registered_step_animation = 0;
	private int step_animation_completed = 0;
	Point position;

	/**
	 * Constructor
	 * 
	 * @param x
	 *            x
	 * @param y
	 *            y
	 * @param color
	 *            color of the token
	 */
	public DisplayToken(int x, int y, Color color) {
		this(new Point(x, y), color);
	}

	/**
	 * Constructor
	 * 
	 * @param point
	 *            coordinate of the token
	 * @param color
	 *            color of the token
	 */
	public DisplayToken(Point point, Color color) {
		position = new Point();
		position.setLocation((point.x - 1) * Properties.TILE_WIDTH + Properties.BOARD_FRAME_PADDING - 2,
				(point.y - 1) * Properties.TILE_HEIGHT + Properties.BOARD_FRAME_PADDING - Properties.MAX_JUMP_HEIGHT);
		if (color == Color.RED) {
			tokenName = "Red_token";
			animation_pane = new Animation(
					new SpriteSheet("Red_token.png", Properties.TOKEN_SRC_WIDTH, Properties.TOKEN_SRC_HEIGHT,
							Properties.TOKEN_FRAME_NUM),
					(int) (Properties.TOKEN_SRC_WIDTH * Properties.TOKEN_SCALE_RATIO),
					(int) (Properties.TOKEN_HEIGHT * Properties.TOKEN_SCALE_RATIO));
		} else if (color == Color.BLUE) {
			tokenName = "Blue_token";
			animation_pane = new Animation(
					new SpriteSheet("Blue_token.png", Properties.TOKEN_SRC_WIDTH, Properties.TOKEN_SRC_HEIGHT,
							Properties.TOKEN_FRAME_NUM),
					(int) (Properties.TOKEN_SRC_WIDTH * Properties.TOKEN_SCALE_RATIO),
					(int) (Properties.TOKEN_HEIGHT * Properties.TOKEN_SCALE_RATIO));
		} else if (color == Color.GREEN) {
			tokenName = "Green_token";
			animation_pane = new Animation(
					new SpriteSheet("Green_token.png", Properties.TOKEN_SRC_WIDTH, Properties.TOKEN_SRC_HEIGHT,
							Properties.TOKEN_FRAME_NUM),
					(int) (Properties.TOKEN_SRC_WIDTH * Properties.TOKEN_SCALE_RATIO),
					(int) (Properties.TOKEN_HEIGHT * Properties.TOKEN_SCALE_RATIO));
		} else {
			tokenName = "Yellow_token";
			animation_pane = new Animation(
					new SpriteSheet("Yellow_token.png", Properties.TOKEN_SRC_WIDTH, Properties.TOKEN_SRC_HEIGHT,
							Properties.TOKEN_FRAME_NUM),
					(int) (Properties.TOKEN_SRC_WIDTH * Properties.TOKEN_SCALE_RATIO),
					(int) (Properties.TOKEN_HEIGHT * Properties.TOKEN_SCALE_RATIO));
		}
		render();
	}

	/**
	 * This method returns the aniamtion panel
	 * 
	 * @return
	 */
	public Animation getPanel() {
		return animation_pane;
	}

	/**
	 * This method visually advance the token a step on board
	 * 
	 * @param direction
	 *            direction of the step
	 */
	public synchronized void takeStep(int direction) {
		takeStep(0, direction);
	}

	/**
	 * This method visually advance the token a step on board
	 * 
	 * @param delay
	 *            wait time before step taken
	 * @param direction
	 *            direction of the step
	 */
	public synchronized void takeStep(int delay, int direction) {
		int xOffset = determineXDirection(direction) * 5;
		int yOffset = determineYDirection(direction) * 5;
		final int iteration = Properties.TILE_WIDTH;

		Timer t = new Timer(registered_step_animation * 500 + delay, new ActionListener() {
			int i = iteration / 5;

			public void actionPerformed(ActionEvent e) {
				if (i == iteration / 5) {
					AnimationProvider.anime(animation_pane, 45);
				}
				if (i != 0) {
					position.x += xOffset;
					position.y += yOffset;
					render();
					i--;
				} else {
					((Timer) e.getSource()).stop();
					step_animation_completed++;
					if (step_animation_completed == registered_step_animation) {
						step_animation_completed = 0;
						registered_step_animation = 0;
					}
					render();
				}
			}
		});
		registered_step_animation++;
		t.setRepeats(true);
		t.setDelay((int) (10));
		t.start();
	}

	/**
	 * This method visually teleport the token to a specific location on board
	 * 
	 * @param x
	 *            x
	 * @param y
	 *            y
	 */
	public void teleport(int x, int y) {
		int delay = (registered_step_animation - step_animation_completed) * 500 + 200;
		List<Timer> timer_list = new ArrayList<Timer>();

		timer_list.add(new Timer(0 + delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				animation_pane.setMaxFrameSize(13);
				animation_pane.changeSpriteSheetSrc(tokenName + "_tp.png");
				AnimationProvider.anime(animation_pane, 10);
			}
		}));

		timer_list.add(new Timer(600 + delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				position.setLocation((y - 1) * Properties.TILE_WIDTH + Properties.BOARD_FRAME_PADDING - 2,
						(x - 1) * Properties.TILE_HEIGHT + Properties.BOARD_FRAME_PADDING - Properties.MAX_JUMP_HEIGHT);
				render();
			}
		}));
		timer_list.add(new Timer(2000 + delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				animation_pane.setMaxFrameSize(7);
				animation_pane.changeSpriteSheetSrc(tokenName + ".png");
			}
		}));
		for (Timer timer : timer_list) {
			timer.setRepeats(false);
			timer.start();
		}
	}

	/**
	 * This methods renders the token on board
	 */
	private void render() {
		animation_pane.setBounds(position.x, position.y, Properties.TOKEN_WIDTH + Properties.TILE_FLIP_GAP,
				Properties.TOKEN_HEIGHT);
	}

	/**
	 * This method determines the sign of x movement
	 * 
	 * @param direction
	 *            direction
	 * @return sign
	 */
	private int determineXDirection(int direction) {
		switch (direction) {
		case 2:
		case 3:
		case 4:
			return 1;
		case 1:
		case 5:
			return 0;
		case 6:
		case 7:
		case 8:
		default:
			return -1;
		}
	};

	/**
	 * This method determines the sign of y movement
	 * 
	 * @param direction
	 *            direction
	 * @return sign
	 */
	private int determineYDirection(int direction) {
		switch (direction) {
		case 1:
		case 2:
		case 8:
			return -1;
		case 3:
		case 7:
			return 0;
		case 4:
		case 5:
		case 6:
		default:
			return 1;
		}
	}
}
