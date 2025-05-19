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
	public String direction;

	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		setDefaultValues();
		getPlayerImage();
	}

	public void setDefaultValues() {
		x = 100;
		y = 100;
		speed = 4;
		direction = "down";
	}

	public void getPlayerImage() {
		this.setUp("/Images/up.png");
		this.setDown("/Images/down.png");
		this.setLeft("/Images/left.png");
		this.setRight("/Images/right.png");
	}

	public void update() {
		if (keyH.up) {	
			direction = "up";
			y -= speed;
		} else if (keyH.down) {
			direction = "down";
			y += speed;
		} else if (keyH.left) {
			direction = "left";
			x -= speed;
		} else if (keyH.right) {
			direction = "right";
			x += speed;
		}
	}

	public void draw(Graphics2D g2) {

		BufferedImage image = null;

		switch (direction) {
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

		g2.drawImage(image, x, y, gp.gettileSize(), gp.gettileSize(), null);
	}
}