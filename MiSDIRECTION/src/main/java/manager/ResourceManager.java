package main.java.manager;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;

import main.java.property.Properties;

public class ResourceManager {
	private static ResourceManager img_manager_instance = null;
	Map<String, BufferedImage> manager = new HashMap<String, BufferedImage>();

	private ResourceManager() {

	}

	public void clear() {
		manager.clear();
	}

	public AudioInputStream grabAudioInputStream(String name) {
		AudioInputStream stream = null;
		try {
			stream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/main/resources/" + name));
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stream;
	}

	public InputStream grabMusicInpuyStream(String name) {
		return getClass().getResourceAsStream("/main/resources/" + name);
	}

	public Image grabImage(String name) {
		Image image = null;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/main/resources/" + name));
		} catch (IOException e) {
			System.out.println("Error reading : " + name);
			e.printStackTrace();
		}
		return image;
	}

	public ImageIcon grabGif(String name) {
		ImageIcon image = null;
		image = new ImageIcon(getClass().getResource("/main/resources/" + name));
		return image;
	}

	public BufferedImage grabBufferedImg(String name) {
		BufferedImage image = null;
		if (manager.containsKey(name)) {
			image = manager.get(name);
		} else {
			try {
				image = ImageIO.read(getClass().getResourceAsStream("/main/resources/" + name));
				manager.put(name, image);
			} catch (IOException e) {
				System.out.println("Error reading : " + name);
				e.printStackTrace();
			}
		}
		return image;
	}

	public static ResourceManager getInstance() {
		if (img_manager_instance == null)
			img_manager_instance = new ResourceManager();
		return img_manager_instance;
	}
}
