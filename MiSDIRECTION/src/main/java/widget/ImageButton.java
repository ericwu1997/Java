package main.java.widget;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;

import main.java.manager.ResourceManager;

public class ImageButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected BufferedImage current_image;
	protected BufferedImage unclicked_image;
	protected BufferedImage clicked_image;
	private boolean disable = false;

	public ImageButton(String unclicked_image_src, String clicked_image_src) {
		setPreferredSize(new Dimension(100, 50));
		addMouseListener(new ImageButtonListener());
		unclicked_image = ResourceManager.getInstance().grabBufferedImg(unclicked_image_src);
		clicked_image = ResourceManager.getInstance().grabBufferedImg(clicked_image_src);
		current_image = unclicked_image;
		setOpaque(false);
		setContentAreaFilled(false);
		setBorderPainted(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(current_image, 0, 0, null);

	}

	protected void active() {
		current_image = clicked_image;
		render();
	}

	protected void deactive() {
		current_image = unclicked_image;
		render();
	}

	private void render() {
		revalidate();
		repaint();
	}

	public void attachListener(ActionListener action) {
		addActionListener(action);
	}

	public void disable() {
		active();
		disable = true;
	}

	public void enable() {
		deactive();
		disable = false;
	}
	
	public boolean isDisable() {
		return disable;
	}
}
