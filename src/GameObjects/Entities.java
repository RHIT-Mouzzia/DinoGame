package GameObjects;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Entities {
	public int x, y;
	public int speed;

	public String direction;
	private BufferedImage up, down, left, right;

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
