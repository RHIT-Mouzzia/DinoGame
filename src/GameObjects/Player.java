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
	private BufferedImage upRight, upLeft, downRight, downLeft, left1, left2, right1, right2;

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
			upRight = ImageIO.read(getClass().getResourceAsStream("/Images/up up.png"));
			upLeft = ImageIO.read(getClass().getResourceAsStream("/Images/up right.png"));
			downRight = ImageIO.read(getClass().getResourceAsStream("/Images/up right.png"));
			downLeft = ImageIO.read(getClass().getResourceAsStream("/Images/up right.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/Images/up right.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/Images/up right.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/Images/up right.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/Images/up right.png"));
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

		g2.setColor(Color.white);

		g2.fillRect(x, y, gp.getTileSize(), gp.getTileSize());
	}
}
