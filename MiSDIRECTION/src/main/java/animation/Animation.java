package main.java.animation;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Animation extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected int current_frame = 1;
	protected int last_frame = 1;
	protected int offset = 0;
	protected SpriteSheet sprite_sheet;
	protected int render_width;
	protected int render_height;
	private float opacity = 1f;

	/**
	 * This class consists of methods that invokes and define the animation. Note
	 * that the instance of the class itself does not perform animation, rather, it
	 * defines how an animation is perform.
	 * 
	 * Related class : SpriteSheet.java, AnimationProvider.java
	 * 
	 * Default constructor
	 * 
	 * @author ericw
	 * @param sprite_sheet
	 *            the sprite sheet to use for animation
	 */
	public Animation(SpriteSheet sprite_sheet) {
		this(sprite_sheet, sprite_sheet.frame_width, sprite_sheet.frame_height);
	}

	/**
	 * Constructor
	 * 
	 * @param sprite_sheet
	 *            the sprite sheet to use for animation
	 * @param render_width
	 *            resize src frame width to render width
	 * @param render_height
	 *            resize src frame height to render height
	 *
	 */
	public Animation(SpriteSheet sprite_sheet, int render_width, int render_height) {
		this.sprite_sheet = sprite_sheet;
		last_frame = sprite_sheet.frame_size;
		this.render_width = render_width;
		this.render_height = render_height;
		setPreferredSize(new Dimension(render_width, render_height));
		setOpaque(false);
	}

	/**
	 * 
	 * This setter sets the number of frames to loop
	 * 
	 * @param last_frame
	 *            index of last frame in sprite sheet
	 */
	public void setMaxFrameSize(int last_frame) {
		this.last_frame = last_frame;
	}

	/**
	 * This method is not yet implemented
	 */
	protected void setCurrentFrame() {
	}

	/**
	 * 
	 * This setter sets the column offset the the sprite sheet
	 * 
	 * @param offset
	 *            column of the sprite sheet
	 */
	public synchronized void setYOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * 
	 * This method changes the index from current frame to the next one, until the
	 * index number matches the last frame. A render() is invoker upon the frame
	 * number gets increment. If the index number reaches the last frame, it will be
	 * reset to 1, before returning false to notify the caller that the animation
	 * has ended.
	 * 
	 * @return boolean notify is the animation reaches the last frame or not
	 */
	public synchronized boolean loopFrame() {
		current_frame++;
		render();
		if (current_frame == last_frame) {
			current_frame = 1;
			return false;
		}
		return true;
	}

	/**
	 * 
	 * This method changes the index from current frame to the next one. If current
	 * frame is same as last frame, it method immediately returns false to notify
	 * the caller that the animation has reached the last frame, otherwise, the
	 * current frame gets increment, and the render() is called once. Note that if
	 * the last frame is reached, a reset frame index is necessary before invoking
	 * the animation again.
	 * 
	 * @return boolean notify is the animation reaches the last frame or not
	 */
	public synchronized boolean loopOnce() {
		if (current_frame != last_frame) {
			current_frame++;
			render();
			if (current_frame == last_frame) {
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * This method instantiate a new sprite sheet object using the source name from
	 * parameter, and change the current sprite sheet to the new one. This method
	 * also reset the current frame number to 1, and re-render
	 * 
	 * @param src
	 *            new sprite sheet source name
	 */
	public synchronized void changeSpriteSheetSrc(String src) {
		current_frame = 1;
		sprite_sheet.changeImageSrc(src);
		render();
	}

	/**
	 * This method replace the current sprite sheet with the one from parameter.
	 * This method also reset the current frame number to 1, and re-render
	 * 
	 * @param src
	 *            new sprite sheet source name
	 */
	public synchronized void changeSpriteSheetSrc(SpriteSheet sprite_sheet) {
		current_frame = 1;
		this.sprite_sheet = sprite_sheet;
		render();
	}

	/**
	 * This method updates and render the animation panel
	 */
	public synchronized void render() {
		revalidate();
		repaint();
	}

	/**
	 * paint component
	 */
	@Override
	protected synchronized void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		BufferedImage img = resize(sprite_sheet.getSprite(current_frame - 1, offset), render_width, render_height);
		g.drawImage(img, 0, 0, null);
	}

	/**
	 * This method resize a buffered image to a width and height.
	 * 
	 * @param img
	 *            image to resize
	 * @param height
	 *            new height
	 * @param width
	 *            new width
	 * @return resized buffered image
	 */
	private static BufferedImage resize(BufferedImage img, int height, int width) {
		Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return resized;
	}

	/**
	 * This method returns the current frame of the animation
	 * 
	 * @return Integer current frame number
	 */
	public BufferedImage getCurrentFrame() {
		return sprite_sheet.getSprite(current_frame - 1, offset);
	}

	/**
	 * This method changes the opacity of the animation panel
	 * 
	 * @param opacity
	 *            opacity level to change to
	 */
	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}
}
