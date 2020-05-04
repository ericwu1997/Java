package main.java.component;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import main.java.animation.Animation;
import main.java.animation.SpriteSheet;

public class DisplayDisk {

	boolean status = false;
	public Color color;
	SpriteSheet sprite_sheet_one;
	SpriteSheet sprite_sheet_two;
	SpriteSheet sprite_sheet_using;
	Animation animation_pane;

	/**
	 * Constructor
	 * 
	 * @param srcOne
	 *            name of the first image source
	 * @param srcTwo
	 *            name of the second image source
	 * @param src_width
	 *            width of the source sprite sheet image per frame
	 * @param src_height
	 *            height of the source sprite sheet image per frame
	 * @param frame_size
	 *            frame size of the sprite sheet
	 * @param color
	 *            color of the disk
	 */
	public DisplayDisk(String srcOne, String srcTwo, int src_width, int src_height, int frame_size, Color color) {
		this(srcOne, srcTwo, src_width, src_height, src_width, src_height, frame_size, color);
	}

	/**
	 * Constructor
	 * 
	 * @param srcOne
	 *            name of the first image source
	 * @param srcTwo
	 *            name of the second image source
	 * @param src_width
	 *            width of the source sprite sheet image per frame
	 * @param src_height
	 *            height of the source sprite sheet image per frame
	 * @param frame_size
	 *            frame size of the sprite sheet
	 * @param render_width
	 *            render width
	 * @param render_height
	 *            render height
	 * @param color
	 *            color of the disk
	 */
	public DisplayDisk(String srcOne, String srcTwo, int src_width, int src_height, int render_width, int render_height,
			int frame_size, Color color) {
		this.color = color;
		sprite_sheet_one = new SpriteSheet(srcOne, src_width, src_height, frame_size);
		sprite_sheet_two = new SpriteSheet(srcTwo, src_width, src_height, frame_size);
		sprite_sheet_using = sprite_sheet_one;
		animation_pane = new Animation(sprite_sheet_using, render_width, render_height);
		deactivate();
	}

	/**
	 * This method visually flips the disk on side bar
	 */
	public void flip() {
		Timer timer = new Timer(0, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (animation_pane.loopOnce()) {
				} else {
					((Timer) e.getSource()).stop();
					animation_pane.changeSpriteSheetSrc((status ? sprite_sheet_one : sprite_sheet_two));
					status = !status;
				}
			}
		});
		timer.setDelay(15);
		timer.setRepeats(true);
		timer.start();
	}

	/**
	 * This method deactivate the disk (low opacity)
	 */
	public void deactivate() {
		animation_pane.setOpacity(0.5f);
		animation_pane.render();
	}

	/**
	 * This method activates the disk (normal opacity)
	 */
	public void activate() {
		animation_pane.setOpacity(1f);
		animation_pane.render();
	}

	/**
	 * This method returns the animation panel
	 * 
	 * @return animation panel
	 */
	public JPanel getPanel() {
		return animation_pane;
	}
}
