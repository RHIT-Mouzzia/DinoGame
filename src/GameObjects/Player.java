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

	public Player(GamePanel gp, int x, int y, int width,int height, int speed, String direction, KeyHandler keyH) {
		super(gp, x, y, width, height, speed, direction);
		setImage();
		this.keyH = keyH;
		this.food = false;
	}

	public void setDefaultValues() {
	}
	
	public void fireBullet() {
	    int bSize  = 16;
	    int bSpeed = 8;
	   
	    int bx = getX() + (getWidth()  - bSize)/2;
	    int by = getY() + (getHeight() - bSize)/2;
	    Bullet b = new Bullet(gp, bx, by, bSize, bSpeed, getDirection());
	    gp.addBullet(b);
		keyH.shoot = false;
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
		prevX = getX();
		prevY = getY();
		
		
		if(offBottom()) {
			setY(gp.getHeight() - this.getHeight());
		}
		if(offLeft()) {
			setX(0);
		}
		if(offRight()) {
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
		
		if(keyH.shoot) {
			fireBullet();
			
		}

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
		//Block by objects
		setX(prevX);
		setY(prevY);
		//collide with feeder
		if (e instanceof Cage) {
			Cage c = (Cage)e;
			this.collidedWithFeederFence(c);
		}
		else {
		//collide with food crate
		this.food = true;
		}
	}
	
	

	@Override
	public void collidedWithFeederFence(Cage f) {
		setX(prevX);
		setY(prevY);
		for (Entities e : gp.getEntities()) {
			if (e instanceof Raptor) {
				Raptor r = (Raptor)e;
				if (r.getCage() == f.getCage() && this.food == true) {
				r.getFeed();
				System.out.println("Player feeding raptor: " + r.getCage());
				System.out.println("Raptor " + r.getCage() + " is now: " + r.getHunger()); 
				}
			}
		}
		this.food = false;
		
	}

	@Override
	public void collidedWithBullets(Bullet b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRemove() {
		// TODO Auto-generated method stub
		
	}


}