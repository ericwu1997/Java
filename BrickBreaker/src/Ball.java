import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Ball {

	private static List<Ball> pool = new ArrayList<Ball>();
	private static Ball ballToRemove;

	// changable properties
	private static int life = 10;
	private static int size = 20;
	private static int speed = 3;
	private static int score = 0;
	private static int bonusRate = 1;
	private static int basePoints = 50;
	private static int bonus = 0;	
	
	private Point center;
	private Point position;
	private int xMagnitude = -1;
	private int yMagnitude = -1;
	private int canvasWidth;
	private int canvasHeight;
	private int radius = size / 2;

	private Point pointToRemove;
	private Rectangle peddleRect;
	private Rectangle ballRect;
	private Peddle peddle;

	Ball() {
		// TODO Auto-generated constructor stub
	}

	Ball(Canvas canvas, Peddle peddle, int x, int direction) {
		this.peddle = peddle;
		canvasWidth = canvas.getWidth();
		canvasHeight = canvas.getHeight();
		xMagnitude = direction;
		position = new Point(x,
				canvasHeight - size - peddle.getHeight() - peddle.getPadding());
		center = new Point(position.x + radius, position.y + radius);
		decreaseLife();
	}

	public void checkCollision() {

		if (center.x > canvasWidth - radius || center.x - radius < 0) {
			xMagnitude *= -1;
			return;
		}
		if (center.y - radius < 0) {
			yMagnitude *= -1;
			return;
		}
		if(center.y > canvasHeight) {
			ballToRemove = this;
			bonus -= bonusRate;
			return;
		}

		ballRect = new Rectangle(position.x, position.y, size, size);

		peddleRect = new Rectangle(peddle.getX(), peddle.getY(),
				peddle.getWidth(), peddle.getHeight());

		if (ballRect.intersects(peddleRect)) {
			if (yMagnitude == 1) {
				yMagnitude *= -1;
			}

			return;
		}

		Bricks.brickList.forEach((location, brick) -> {
			if (ballRect.intersects(brick)) {

				if (center.x + radius < brick.x
						|| center.x - radius > (brick.x + Bricks.getWidth())) {
					xMagnitude *= -1;
				} else {
					yMagnitude *= -1;
				}
				addScore();
				pointToRemove = new Point(location);
			}
		});
		
		Bricks.brickList.remove(pointToRemove);

	}

	public void draw(Graphics g) {
		pool.forEach(ball -> {
			g.setColor(Color.WHITE);
			g.fillOval(ball.position.x, ball.position.y, Ball.size, Ball.size);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		});

		g.drawString("x" + life, 10, 400);
		g.drawString("score : " + score, 500, 400);
	}

	public void moveBall() {
		pool.forEach(ball -> {
			ball.checkCollision();
			ball.position.x += (ball.xMagnitude * speed);
			ball.position.y += (ball.yMagnitude * speed);
			ball.center.x = ball.position.x + radius;
			ball.center.y = ball.position.y + radius;
		});
	}
	
	private void addScore() {
		score += basePoints * bonus;
	}

	// Add ball
	public void addBall(Canvas canvas, Peddle peddle, int x, int direction) {
		pool.add(new Ball(canvas, peddle, x, direction));
		bonus += bonusRate;
	}

	// Remove out of bound balls
	public void removeBall() {
		pool.remove(ballToRemove);
	}

	private void decreaseLife() {
		life--;
	}

	public int getLife() {
		return life;
	}

	// Create ball pool
	public static Ball createBallPool() {
		return new Ball();
	}

	public static void setSpeed(int speed) {
		Ball.speed = speed;
	}

	public static void setSize(int size) {
		Ball.size = size;
	}

	public static void setLife(int life) {
		Ball.life = life;
	}
	
	public static void setBonusRate(int rate) {
		Ball.bonusRate = rate;
	}
	
	public static void setBasePoint(int base) {
		Ball.basePoints = base;
	}
}
