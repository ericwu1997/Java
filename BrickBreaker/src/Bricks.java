import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

public class Bricks {

	public final static Map<Point, Rectangle> brickList = new HashMap<Point, Rectangle>();

	private static double ratio = 1.50;
	private static int brickWidth = 100;
	private static int brickHeight = 40;
	private static int gap = 5;

	private int row;
	private int column;

	Bricks(Canvas canvas) {
		column = (canvas.getWidth() - gap) / (brickWidth + gap);
		row = (int) ((canvas.getHeight() / ratio) / (brickHeight + gap));

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				brickList.put(
						new Point(j * brickWidth + (j + 1) * gap,
								i * (brickHeight) + (i + 1) * gap),
						new Rectangle(j * brickWidth + (j + 1) * gap,
								i * (brickHeight) + (i + 1) * gap, brickWidth,
								brickHeight));
			}
		}
	}

	public void draw(Graphics g) {
		g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		g.setColor(Color.WHITE);
		brickList.forEach((position, brick) -> g.fillRect(position.x,
				position.y, brickWidth, brickHeight));
	}

	public static int getWidth() {
		return brickWidth;
	}

	public static int getHeight() {
		return brickHeight;
	}

	public static void setWidth(int width) {
		brickWidth = width;
	}
	public static void setHeigth(int height) {
		brickHeight = height;
	}
	public static void setGap(int gap) {
		Bricks.gap = gap;
	}
	public static void setBrickRatio(double ratio) {
		Bricks.ratio = ratio;
	}
}
