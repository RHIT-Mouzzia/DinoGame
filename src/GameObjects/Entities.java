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

	// static object
	public Entities(GamePanel gp, int x, int y, int width, int height, String direction) {
		this.gp = gp;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.direction = direction;
	}

	/*
	 * Return x position
	 */
	public int getX() {
		return x;
	}

	/*
	 * Set x position
	 */
	public void setX(int x) {
		this.x = x;
	}

	/*
	 * Return y position
	 */
	public int getY() {
		return y;
	}

	/*
	 * Set y position
	 */
	public void setY(int y) {
		this.y = y;
	}

	/*
	 * Return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/*
	 * Set the width
	 */
	public void setWidth(int w) {
		this.width = w;
	}

	/*
	 * Return the width
	 */
	public int getWidth() {
		return this.width;
	}

	/*
	 * Set the height
	 */
	public void setHeight(int h) {
		this.height = h;
	}

	/*
	 * Return the height
	 */
	public int getHeight() {
		return this.height;
	}

	/*
	 * Set the speed
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/*
	 * Return the direction
	 */
	public String getDirection() {
		return direction;
	}

	/*
	 * Set the direction
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	/*
	 * Get image for up direction
	 */
	public BufferedImage getUp() {
		return up;
	}

	/*
	 * Set image for up direction
	 */
	public void setUp(String up) {
		try {
			this.up = ImageIO.read(getClass().getResourceAsStream(up));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Get image for down direction
	 */
	public BufferedImage getDown() {
		return down;
	}

	/*
	 * Set image for down direction
	 */
	public void setDown(String down) {
		try {
			this.down = ImageIO.read(getClass().getResourceAsStream(down));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Get image for left direction
	 */
	public BufferedImage getLeft() {
		return left;
	}

	/*
	 * Set image for left direction
	 */
	public void setLeft(String left) {
		try {
			this.left = ImageIO.read(getClass().getResourceAsStream(left));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Get image for right direction
	 */
	public BufferedImage getRight() {
		return right;
	}

	/*
	 * Set image for right direction
	 */
	public void setRight(String right) {
		try {
			this.right = ImageIO.read(getClass().getResourceAsStream(right));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Get if object should be remove
	 */
	public boolean shouldRemove() {
		return this.shouldRemove;
	}

	/*
	 * Mark object to be removed
	 */
	public void markToRemove() {
		this.shouldRemove = true;
	}

	/*
	 * Get the object surrounding box
	 */
	public Rectangle2D.Double getBoundingBox() {
		return new Rectangle2D.Double(this.x, this.y, 0.8 * getWidth(), 0.8 * getHeight());
	}

	/*
	 * Check if objects bounding box are overlaped
	 */
	public boolean overlaps(Entities e) {
		return getBoundingBox().intersects(e.getBoundingBox());
	}

	/*
	 * Check if object is off screen
	 */
	public boolean isOffScreen() {
		boolean xLow = x < 0;
		boolean xHigh = x + width > gp.getWidth();
		boolean yLow = y < 0;
		boolean yHigh = y + height > gp.getHeight();
		return xLow || xHigh || yLow || yHigh;
	}

	/*
	 * Check if object is off bottom
	 */
	public boolean offBottom() {
		return y + height > gp.getHeight();
	}

	/*
	 * Check if object is off left
	 */
	public boolean offLeft() {
		return x < 0;
	}

	/*
	 * Check if object is off right
	 */
	public boolean offRight() {
		return x + this.width > gp.getWidth();
	}

	/*
	 * Basic update for every object
	 */
	public void update() {
		this.x += speed;
		this.y += speed;
	}

	/*
	 * Abstract method to draw object
	 */
	public abstract void draw(Graphics2D g2);

	/*
	 * Abstract method to set images
	 */
	public abstract void setImage();

	/*
	 * Abstract method for collision
	 */
	public abstract void collidedWithBox(Entities e);
}
