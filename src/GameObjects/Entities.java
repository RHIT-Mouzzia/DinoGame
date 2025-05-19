package GameObjects;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Entities {
	private int x, y;
	private int speed;
	private String direction;
	private BufferedImage up, down, left, right;

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
}
