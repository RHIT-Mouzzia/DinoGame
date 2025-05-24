package GameObjects;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import main.GamePanel;

public abstract class Entities {
	private int x, y;
	private int width, height;
	private int speed;
	private String direction;
	private BufferedImage up, down, left, right;
	private boolean shouldRemove;
	protected GamePanel gp;

	// moving object
	public Entities(GamePanel gp, int x, int y, int width, int height, int speed, String direction) {
		this.gp = gp;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.direction = direction;
	}

	// tile-based movement logic
	public Entities(GamePanel gp, int col, int row, int width, int height) {
		this.gp = gp;
		this.width = width;
		this.height = height;
		this.x = col * width;
		this.y = row * height;
	}

	// static object
	public Entities(GamePanel gp, int x, int y, int width, int height, String direction) {
		this.gp = gp;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.direction = direction;
	}

	// tile
	public Entities(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSpeed() {
		return speed;
	}

	public void setWidth(int w) {
		this.width = w;
	}

	public int getWidth() {
		return this.width;
	}

	public void setHeight(int h) {
		this.height = h;
	}

	public int getHeight() {
		return this.height;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public BufferedImage getUp() {
		return up;
	}

	public void setUp(String up) {
		try {
			this.up = ImageIO.read(getClass().getResourceAsStream(up));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getDown() {
		return down;
	}

	public void setDown(String down) {
		try {
			this.down = ImageIO.read(getClass().getResourceAsStream(down));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getLeft() {
		return left;
	}

	public void setLeft(String left) {
		try {
			this.left = ImageIO.read(getClass().getResourceAsStream(left));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getRight() {
		return right;
	}

	public void setRight(String right) {
		try {
			this.right = ImageIO.read(getClass().getResourceAsStream(right));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean shouldRemove() {
		return this.shouldRemove;
	}

	public void markToRemove() {
		this.shouldRemove = true;
	}

	public Rectangle2D.Double getBoundingBox() {
		return new Rectangle2D.Double(this.x, this.y, 0.8 * getWidth(), 0.8 * getHeight());
	}

	public boolean overlaps(Entities e) {
		return getBoundingBox().intersects(e.getBoundingBox());
	}

	public boolean isOffScreen() {
		boolean xLow = x < 0;
		boolean xHigh = x + width > gp.getWidth();
		boolean yLow = y < 0;
		boolean yHigh = y + height > gp.getHeight();
		return xLow || xHigh || yLow || yHigh;
	}

	public boolean offBottom() {
		return y + height > gp.getHeight();
	}

	public boolean offLeft() {
		return x < 0;
	}

	public boolean offRight() {
		return x + this.width > gp.getWidth();
	}

	public void update() {
		this.x += speed;
		this.y += speed;
	}

	public abstract void draw(Graphics2D g2);

	public abstract void onRemove();

	public abstract void setImage();

	public abstract void collidedWithBox(Entities e);

	public abstract void collidedWithFeederFence(Cage f);

	public abstract void collidedWithBullets(Bullet b);
}
