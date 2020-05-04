package main.java.animation;

import java.awt.image.BufferedImage;

import main.java.manager.ResourceManager;

public class SpriteSheet {
	protected BufferedImage original;
	public int frame_size;
	public int frame_width;
	public int frame_height;

	/**
	 * Constructor
	 * 
	 * @param filename
	 *            name of the sprite sheet source
	 * @param width
	 *            sprite sheet width
	 * @param height
	 *            sprite sheet height
	 * @param frame_size
	 *            sprite sheet frame size
	 */
	public SpriteSheet(String filename, int width, int height, int frame_size) {
		this.frame_size = frame_size;
		frame_width = width;
		frame_height = height;
		loadSprite(filename);
	}

	/**
	 * This method loads and image and store it to use a sprite sheet reference
	 * 
	 * @param filename
	 *            name of the sprite sheet source image
	 */
	private void loadSprite(String filename) {
		ResourceManager manager = ResourceManager.getInstance();
		original = manager.grabBufferedImg(filename);
	}

	/**
	 * This method gets a sub image from sprite sheet
	 * 
	 * @param x
	 *            x offset of the image
	 * @param y
	 *            y offset of the image
	 * @return sub image of the sprite sheet source at index x, y
	 * 
	 */
	public BufferedImage getSprite(int x, int y) {
		BufferedImage tmp = null;
		try {
			tmp = original.getSubimage(x * frame_width, y * frame_height, frame_width, frame_height);
		} catch (Exception e) {
			System.out.println("Frame: " + x);
			System.out.println(e.getMessage());
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return tmp;
	}

	/**
	 * This method change the sprite sheet source
	 * 
	 * @param newSrc
	 *            new sprite sheet source name
	 */
	public void changeImageSrc(String newSrc) {
		loadSprite(newSrc);
	}
}
