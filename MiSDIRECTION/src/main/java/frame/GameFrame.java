package main.java.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import main.java.component.DisplayTile;
import main.java.component.DisplayToken;
import main.java.manager.ResourceManager;
import main.java.property.Properties;

public class GameFrame extends JFrame {

	private SideBarFrame sideBarFrame;
	private BoardFrame boardFrame;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameFrame() {
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon(ResourceManager.getInstance().grabImage("Cursor.png")).getImage(), new Point(10, 10),
				"custom cursor"));
		setIconImage(ResourceManager.getInstance().grabBufferedImg("Icon.png"));
		setTitle("MiSDIRECTION");

		music("bgMusic.wav");

		JPanel menu = new JPanel() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				super.paintComponent(g);
				g.drawImage(ResourceManager.getInstance().grabBufferedImg("menu.png"), 0, 0, null);
			}
		};
		menu.setLayout(null);
		JButton start = new JButton("Get Miserable");
		start.setPreferredSize(new Dimension(100, 50));
		start.setBounds(Properties.GAME_FRAME_WIDTH / 2 - 50, Properties.GAME_FRAME_HEIGHT / 2, 100, 50);
		menu.add(start);

		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				menu.setVisible(false);
				sideBarFrame.setVisible(true);
				boardFrame.setVisible(true);
			}
		});

		sideBarFrame = new SideBarFrame();
		boardFrame = new BoardFrame();

		sideBarFrame.setVisible(false);
		boardFrame.setVisible(false);

		add(menu, BorderLayout.CENTER);
		add(sideBarFrame, BorderLayout.EAST);
		add(boardFrame, BorderLayout.WEST);

		setSize(Properties.GAME_FRAME_WIDTH, Properties.GAME_FRAME_HEIGHT);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);

		JWindow window = new JWindow();
		window.getContentPane()
				.add(new JLabel("", ResourceManager.getInstance().grabGif("log_loading.gif"), SwingConstants.CENTER));
		window.setBounds(0, 0, 500, 500);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {

		}
		window.dispose();

		setVisible(true);
		ResourceManager.getInstance().clear();
	}

	public DisplayTile getTile(int x, int y) {
		return boardFrame.getTile(x, y);
	}

	public void flipAll() {
		boardFrame.flipAll();
	}

	public BoardFrame getBoardFrame() {
		return boardFrame;
	}

	public SideBarFrame getSideBarFrame() {
		return sideBarFrame;
	}

	public DisplayToken getToken(int i) {
		switch (i) {
		case 1:
			return boardFrame.getToken(0);
		case 2:
			return boardFrame.getToken(1);
		case 3:
			return boardFrame.getToken(2);
		case 4:
			return boardFrame.getToken(3);
		default:
			return null;
		}
	}

	public void music(String filename) {
		try (InputStream in = ResourceManager.getInstance().grabMusicInpuyStream(filename)) {
			InputStream bufferedIn = new BufferedInputStream(in);
			try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn)) {
				Clip clip = AudioSystem.getClip();
				clip.open(audioIn);
				clip.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
