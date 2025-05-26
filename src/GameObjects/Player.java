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
	private KeyHandler keyH;
	private int prevX, prevY;
	private boolean food;
	private BufferedImage u1, u2, u3, u4;
	private BufferedImage d1, d2, d3, d4;
	private BufferedImage r1, r2, r3, r4;
	private BufferedImage l1, l2, l3, l4;
	private int spriteNum = 1;
	private int spriteCounter = 1;
	private int stepDelay = 50;
	private int coolDown = 0;
	private int fireDelay = 20;

	public Player(GamePanel gp, int x, int y, int width, int height, int speed, String direction, KeyHandler keyH) {
		super(gp, x, y, width, height, speed, direction);
		setImage();
		this.keyH = keyH;
		this.food = false;
	}

	public void fireBullet() {
		int bSize = 16;
		int bSpeed = 8;

		int bx = getX() + (getWidth() - bSize) / 2;
		int by = getY() + (getHeight() - bSize) / 2;
		Bullet b = new Bullet(gp, bx, by, bSize, bSpeed, getDirection());
		gp.addBullet(b);
		keyH.shoot = false;
	}

	public String getMeat() {
		if (food == true)
			return "";
		return " not";
	}

	@Override
	public void setImage() {
		try {
			u1 = ImageIO.read(getClass().getResourceAsStream("/Images/up1.png"));
			u2 = ImageIO.read(getClass().getResourceAsStream("/Images/up2.png"));
			u3 = ImageIO.read(getClass().getResourceAsStream("/Images/up3.png"));
			u4 = ImageIO.read(getClass().getResourceAsStream("/Images/up4.png"));
			d1 = ImageIO.read(getClass().getResourceAsStream("/Images/down1.png"));
			d2 = ImageIO.read(getClass().getResourceAsStream("/Images/down2.png"));
			d3 = ImageIO.read(getClass().getResourceAsStream("/Images/down3.png"));
			d4 = ImageIO.read(getClass().getResourceAsStream("/Images/down4.png"));
			r1 = ImageIO.read(getClass().getResourceAsStream("/Images/right1.png"));
			r2 = ImageIO.read(getClass().getResourceAsStream("/Images/right2.png"));
			r3 = ImageIO.read(getClass().getResourceAsStream("/Images/right3.png"));
			r4 = ImageIO.read(getClass().getResourceAsStream("/Images/right4.png"));
			l1 = ImageIO.read(getClass().getResourceAsStream("/Images/left1.png"));
			l2 = ImageIO.read(getClass().getResourceAsStream("/Images/left2.png"));
			l3 = ImageIO.read(getClass().getResourceAsStream("/Images/left3.png"));
			l4 = ImageIO.read(getClass().getResourceAsStream("/Images/left4.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		prevX = getX();
		prevY = getY();

		if (offBottom()) {
			setY(gp.getHeight() - this.getHeight());
		}
		if (offLeft()) {
			setX(0);
		}
		if (offRight()) {
			setX(gp.getWidth() - this.getWidth());
		}

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

		if (coolDown > 0) {
			coolDown--;
		}

		if (keyH.shoot && gp.getCurrentMapPath().equals("/mapLevel/Level2.txt") && !gp.getGameOver() && coolDown == 0) {
			fireBullet();
			coolDown = fireDelay;
		}

		if (gp.getGameOver() || gp.getGameWon())
			this.setSpeed(0);
		spriteCounter++;
		if (spriteCounter > stepDelay) {
			spriteNum = spriteNum % 4 + 1;
			spriteCounter = 0;
		}

	}

	@Override
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		switch (getDirection()) {
		case "up":
			switch (spriteNum) {
			case 1:
				image = u1;
				break;
			case 2:
				image = u2;
				break;
			case 3:
				image = u3;
				break;
			case 4:
				image = u4;
				break;
			}
			break;
		case "down":
			switch (spriteNum) {
			case 1:
				image = d1;
				break;
			case 2:
				image = d2;
				break;
			case 3:
				image = d3;
				break;
			case 4:
				image = d4;
				break;
			}
			break;
		case "left":
			switch (spriteNum) {
			case 1:
				image = l1;
				break;
			case 2:
				image = l2;
				break;
			case 3:
				image = l3;
				break;
			case 4:
				image = l4;
				break;
			}
			break;
		case "right":
			switch (spriteNum) {
			case 1:
				image = r1;
				break;
			case 2:
				image = r2;
				break;
			case 3:
				image = r3;
				break;
			case 4:
				image = r4;
				break;
			}
			break;
		}
		g2.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
	}

	@Override
	public void collidedWithBox(Entities e) {
		//By using previous x,y position to not let player go through objects
		setX(prevX);
		setY(prevY);
		
		//Feed raptor if collided with feeder fence
		if (e instanceof Cage) {
			Cage c = (Cage) e;
			this.collidedWithFeederFence(c);
		} else if (e instanceof Meat) {//Collect food when collided with meat crate
			this.food = true;
		}

		//Collide with power ups
		if (e instanceof Effect) {
			Effect pw = (Effect) e;
			pw.collidedWithBox(this);
		}
	}

	/*
	 * Collision with feeder fence, feeding the raptor inside the cage
	 */
	public void collidedWithFeederFence(Cage f) {
		for (Entities e : gp.getEntities()) {
			if (e instanceof Raptor) {
				Raptor r = (Raptor) e;
				if (r.getCage() == f.getCage() && this.food == true) {
					r.getFeed();
				}
			}
		}
		this.food = false;

	}

}