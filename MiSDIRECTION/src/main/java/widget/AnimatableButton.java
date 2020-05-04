package main.java.widget;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

import main.java.animation.SpriteSheet;

abstract public class AnimatableButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int current_frame = 1;
	protected int last_frame = 1;
	protected int offset = 0;
	protected SpriteSheet sprite_sheet;
	protected SpriteSheet sprite_sheet_src_one;
	protected SpriteSheet sprite_sheet_src_two;
	protected int render_width;
	protected int render_height;
	protected boolean status = false;
	protected boolean running = false;
	protected boolean activated = false;

	float opacity = 0.5f;

	public AnimatableButton(SpriteSheet srcOne, SpriteSheet srcTwo, AnimatableButtonListener listener) {
		this(srcOne, srcTwo, srcOne.frame_width, srcTwo.frame_height, listener);
	}

	public AnimatableButton(SpriteSheet srcOne, SpriteSheet srcTwo, int render_width, int render_height,
			AnimatableButtonListener listener) {
		this.render_width = render_width;
		this.render_height = render_height;
		last_frame = srcOne.frame_size;
		sprite_sheet = srcOne;
		sprite_sheet_src_one = srcOne;
		sprite_sheet_src_two = srcTwo;
		setPreferredSize(new Dimension(render_width, render_height));
		addMouseListener(listener);
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
	}
	
	public void activate() {
		activated = true;
		opacity = 1f;
	}
	
	public void deactivate() {
		activated = false;
		opacity = 0.5f;
	}

	protected synchronized boolean loopOnce() {
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

	private synchronized void render() {
		revalidate();
		repaint();
	}

	protected synchronized void nextSpriteSheet() {
		current_frame = 1;
		status = !status;
		if (status) {
			sprite_sheet = sprite_sheet_src_two;
		} else {
			sprite_sheet = sprite_sheet_src_one;
		}
	}

	protected synchronized boolean checkRunningStatus() {
		return running;
	}

	protected synchronized void setRunningStatus(boolean status) {
		running = status;
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
		BufferedImage img = resize(sprite_sheet.getSprite(current_frame - 1, offset), render_width, render_height);
		g.drawImage(img, 0, 0, null);
	}

	private static BufferedImage resize(BufferedImage img, int height, int width) {
		Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return resized;
	}

	public void setOpacity(float opacity) {
		this.opacity = opacity;
		repaint();
	}

	abstract protected void action();
}
