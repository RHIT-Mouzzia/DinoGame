package GameObjects;

import java.awt.image.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import GameObjects.Player;
import main.GamePanel;
import main.KeyHandler;



public class Player extends Entities {
	private GamePanel gp;
	private KeyHandler keyH;

	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		setDefaultValues();
		getPlayerImage();
	}

	public void setDefaultValues() {
		this.setX(8*this.gp.gettileSize());
		this.setY(8*this.gp.gettileSize());
		this.setSpeed(4);
		this.setDirection("down");
	}

	public void getPlayerImage() {
		this.setUp("/Images/up.png");
		this.setDown("/Images/down.png");
		this.setLeft("/Images/left.png");
		this.setRight("/Images/right.png");
	}

	public void update() {
		if (keyH.up) {
			setDirection("up");
			this.setY(getY() - getSpeed());
		} else if (keyH.down) {
			setDirection("down");
			setY(getY() + getSpeed());
		} else if (keyH.left) {
			setDirection("left");
			setX(getX() - getSpeed());
		} else if (keyH.right) {
			setDirection("right");
			setX(getX() + getSpeed());
		}
	}

	public void draw(Graphics2D g2) {

		BufferedImage image = null;

		switch (getDirection()) {
		case "up":
			image = this.getUp();
			break;
		case "down":
			image = this.getDown();
			break;
		case "left":
			image = this.getLeft();
			break;
		case "right":
			image = this.getRight();
			break;
		}

		g2.drawImage(image, getX(), getY(), gp.gettileSize(), gp.gettileSize(), null);
	}
}