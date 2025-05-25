package GameObjects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Flyer extends Entities {
	
	public Flyer(GamePanel gp,int x, int y, int size, int speed) {
		super(gp, x, y, size, size, speed, "left");
		setImage();
	}

	@Override
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		
		switch (getDirection()) {
		case "left":
			image = this.getLeft();
			break;
		case "right":
			image = this.getRight();
			break;
		}		
		g2.drawImage(image, getX(), getY(), getHeight(), getWidth(), null);

	}
	
	@Override
	public void update() {
		
		if(this.getY() == gp.getTileSize() * 10) gp.setGameOver(true);
		
		if(offLeft()) {
			setSpeed(getSpeed() + 1);
			setX(gp.getWidth() + this.getWidth());
			setY(this.getY() + gp.getTileSize());
			
		}
		setX(getX() - getSpeed());
	}

	@Override
	public void setImage() {
		// TODO Auto-generated method stub
		setLeft("/Images/pte.png");
		setRight("/Images/pte.png");
	}

	@Override
	public void collidedWithBox(Entities e) {
		// TODO Auto-generated method stub
		if (e instanceof Bullet) {
			this.markToRemove();
			e.markToRemove();
		}
	}

}
