
import java.awt.Canvas;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Mini game - Brick Breaker. Basic control - left and right - A and D
 * 
 * @author Eric_Wu
 * @version 1.0
 * 
 */
public class BrickBreaker extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String TITLE = "Break Tiles";
	private static final int WIDTH = 640;
	private static final int HEIGHT = 480;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BrickBreaker game = new BrickBreaker();

		// All this setting are for customization
		// If not specified, default will be used instead
		Ball.setSpeed(5);
		Ball.setSize(10);
		Ball.setLife(5);
		Ball.setBasePoint(50);
		Ball.setBonusRate(1);
		
		Bricks.setWidth(80);
		Bricks.setHeigth(20);
		Bricks.setBrickRatio(1.75);
		Bricks.setGap(10);
		
		Peddle.setHeight(10);
		Peddle.setWidth(100);
		Peddle.setSpeed(10);

		JFrame frame = new JFrame();
		GameLoop gameLoop = new GameLoop(game);
		

		frame.add(game);
		frame.setTitle(TITLE);
		frame.setSize(WIDTH, HEIGHT);
		frame.addKeyListener(new KeyInput());

		frame.setResizable(false);
		frame.setFocusable(true);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.err.println("Exiting game");
				gameLoop.stop();
			}
		});

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.requestFocus();
		gameLoop.start();
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT - 25;
	}

}
