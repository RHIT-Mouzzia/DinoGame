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
	private boolean collided;

	public Player(GamePanel gp, int x, int y, int width,int height, int speed, String direction, KeyHandler keyH) {
		super(gp, x, y, width, height, speed, direction);
		setImage();
		this.keyH = keyH;
		this.collided = false;
	}

	public void setDefaultValues() {
	}

	@Override
	public void setImage() {
		this.setUp("/Images/up.png");
		this.setDown("/Images/down.png");
		this.setLeft("/Images/left.png");
		this.setRight("/Images/right.png");
	}

	@Override
	public void update() {
//		int nextX = getX();
//		int nextY = getY();
		
		
		if(offBottom()) {
			setY(gp.getHeight() - this.getHeight());
		}
		if(offLeft()) {
			setX(0);
		}
		if(offRight()) {
			setX(gp.getWidth() - this.getWidth());
		}
		
		//if(this.gp.getTileManager().fenceCollision(getSpeed(), getHeight()))
		
		if (keyH.up) {
			setDirection("up");
			this.setY(getY() - getSpeed());
			//nextY -= getSpeed();
		} else if (keyH.down) {
			setDirection("down");
			setY(getY() + getSpeed());
			//nextY += getSpeed();
		} else if (keyH.left) {
			setDirection("left");
			setX(getX() - getSpeed());
			//nextX -= getSpeed();
		} else if (keyH.right) {
			setDirection("right");
			setX(getX() + getSpeed());
			//nextX += getSpeed();
		}

//		if(!collided) {
//			setY(nextY);
//			setX(nextX);
//		}
	}

	@Override
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

		g2.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
	}

	@Override
	public void collidedWithBox(Entities e) {
		System.out.println("Touching other box");
		int diffX = this.getX() - e.getX();
		int diffY = this.getY() - e.getY();
		setX(e.getX() + diffX);
		setY(getY() + diffY);
		
	}


}