import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

public class GameLoop implements Runnable {

	private static Thread thread;

	private Canvas canvas;
	private Peddle peddle;
	private Bricks bricks;
	private Ball ballPool;

	private double unprocessed = 0.0;
	private double target = 60.0;
	private long lastTime;
	private long now;
	private long timer;
	private int fps = 0;
	private int tps = 0;

	private boolean canRender = false;
	private boolean running = false;

	GameLoop(Canvas canvas) {
		this.canvas = canvas;

		peddle = new Peddle(canvas);

		bricks = new Bricks(canvas);

		ballPool = Ball.createBallPool();
	}

	public void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this, "Game Loop thread");
		thread.start();
	}

	public void stop() {
		if (!running)
			return;
		running = false;
	}

	public static void pause() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void tick() {

		// Peddle move left when press LEFT OR A
		if (KeyInput.isDown(KeyEvent.VK_LEFT) && peddle.getX() > 0)
			peddle.updateX(-1);
		if (KeyInput.isDown(KeyEvent.VK_A) && peddle.getX() > 0)
			peddle.updateX(-1);

		// Peddle move right when press RIGHT OR D
		if (KeyInput.isDown(KeyEvent.VK_RIGHT) && peddle.getX() < rightBorder())
			peddle.updateX(1);
		if (KeyInput.isDown(KeyEvent.VK_D) && peddle.getX() < rightBorder())
			peddle.updateX(1);

		// When SPACE is pressed AND life greater than 0, a Ball is spawned
		if (KeyInput.wasPressed(KeyEvent.VK_SPACE)) {
			if (ballPool.getLife() > 0) {
				ballPool.addBall(canvas, peddle, peddle.getCenter(), peddle.getDirection());
			}
		}

		ballPool.moveBall();

		ballPool.removeBall();
	}

	public void render() {

		BufferStrategy bs = canvas.getBufferStrategy();

		if (bs == null) {
			canvas.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		// Background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		// Peddle
		peddle.draw(g);

		// Brickes
		bricks.draw(g);

		// Ball
		ballPool.draw(g);

		g.dispose();
		bs.show();

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		double nsPerTick = 1000000000 / target;
		lastTime = System.nanoTime();
		timer = System.currentTimeMillis();

		while (running) {
			now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;

			if (unprocessed >= 1.0) {
				tick();
				KeyInput.update();
				unprocessed--;
				tps++;
				canRender = true;
			} else
				canRender = false;

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (canRender) {
				render();
				fps++;
			}

			if (System.currentTimeMillis() - 1000 > timer) {
				System.out.printf("FPS: %d | TPS: %d\n", fps, tps);
				timer += 1000;
				fps = 0;
				tps = 0;
			}
		}
		System.exit(0);
	}

	public void setTarget(double target) {
		this.target = target;
	}

	private int rightBorder() {
		return canvas.getWidth() - peddle.getWidth();
	}

}
