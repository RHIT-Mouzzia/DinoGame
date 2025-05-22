package GameObjects;

import java.awt.Graphics2D;

import main.GamePanel;

public class Bullet extends Entities {
	
	 public Bullet(GamePanel gp, int x, int y, int size, int speed, String direction) {
	        super(gp, x, y, size, size, speed, direction);
	 }
	 
	 @Override
	    public void update() {
	        // move straight in the given direction
	        switch (getDirection()) {
	            case "up":    setY(getY() - getSpeed()); break;
	            case "down":  setY(getY() + getSpeed()); break;
	            case "left":  setX(getX() - getSpeed()); break;
	            case "right": setX(getX() + getSpeed()); break;
	        }
	    }

	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setDefaultValues() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setImage() {
		// TODO Auto-generated method stub

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
