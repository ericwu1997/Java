import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Peddle {

	private static int width = 10;
	private static int height = 10;
	private static int speed = 10;

	private Point position;
	private int padding = 15;
	private int direction = -1;

	public Peddle(Canvas canvas) {
		// TODO Auto-generated constructor stub
		position = new Point(canvas.getWidth() / 2 - width / 2,
				canvas.getHeight() - height - padding);
	}

	public void draw(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(position.x, position.y, width, height);
	}

	public void updatePostion(int x, int y) {
		position.x = x;
	}

	public void updateX(int direction) {
		this.direction = direction;
		position.x = position.x + direction * speed;
	}
	
	public int getDirection() {
		return direction;
	}

	public int getX() {
		return position.x;
	}

	public int getY() {
		return position.y;
	}

	public int getSpeed() {
		return speed;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getPadding() {
		return padding;
	}


	public Point getPosition() {
		return position;
	}

	public int getCenter() {
		return getPosition().x + getWidth() / 2;
	}
	
	public static void setWidth(int width) {
		Peddle.width = width;
	}
	
	public static void setHeight(int height) {
		Peddle.height = height;
	}
	
	public static void setSpeed(int speed) {
		Peddle.speed = speed;
	}
}
