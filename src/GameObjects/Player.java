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
	public int x, y, speed;
	public String direction;
	private BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;

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
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/Images/up.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/Images/up.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/Images/down.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/Images/down.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/Images/left.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/Images/left.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/Images/right.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/Images/right.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

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
			image = up1;
			break;
		case "down":
			image = down1;
			break;
		case "left":
			image = left1;
			break;
		case "right":
			image = right1;
			break;
		}

		g2.drawImage(image, x, y, gp.gettileSize(), gp.gettileSize(), null);
	}
}