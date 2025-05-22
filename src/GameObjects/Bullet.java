package GameObjects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Bullet extends Entities {
	
	 public Bullet(GamePanel gp, int x, int y, int size, int speed, String direction) {
	        super(gp, x, y, size, size, speed, direction);
	 }
	 
	 @Override
	    public void update() {
	        // move straight in the given direction
		 	setImage();
	        switch (getDirection()) {
	            case "up":    setY(getY() - getSpeed()); break;
	            case "down":  setY(getY() + getSpeed()); break;
	            case "left":  setX(getX() - getSpeed()); break;
	            case "right": setX(getX() + getSpeed()); break;
	        }
	        
	        if(isOffScreen()) {
	        	markToRemove();
	        	System.out.println("Bullet removed");
	        }
	       
	    }

	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
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
		case "nothing":
			image = null;
			break;
		}
		g2.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
	}

	@Override
	public void setDefaultValues() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setImage() {
		// TODO Auto-generated method stub
		setUp("/Images/bullet.png");
		setDown("/Images/bullet.png");
		setLeft("/Images/bullet.png");
		setRight("/Images/bullet.png");
	}

	@Override
	public void collidedWithBox(Entities e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void collidedWithFeederFence(Cage f) {
		// TODO Auto-generated method stub

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
