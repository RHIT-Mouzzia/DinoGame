package GameObjects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Raptor extends Entities {
	private GamePanel gp;
	private int cage;

	public Raptor(GamePanel gp, int cage) {
		this.gp = gp;
		this.cage = cage;
		this.setDirection("down");
		setDefaultValues();
		setImage();
	}
	
	@Override
	public void setImage() {
		this.setUp("/Images/Raptor.png");
		this.setDown("/Images/Raptor.png");
		this.setLeft("/Images/Raptor.png");
		this.setRight("/Images/Raptor.png");
	}

	@Override
	public void update() {

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

		g2.drawImage(image, getX(), getY(), gp.gettileSize(), gp.gettileSize(), null);
	}

	@Override
	public void setDefaultValues() {
		if(this.cage == 3) {
			this.setX(13*this.gp.gettileSize());
			this.setY(2*this.gp.gettileSize());
		}
		else if(this.cage == 2){
			this.setX(8*this.gp.gettileSize());
			this.setY(2*this.gp.gettileSize());
		}
		else {
			this.setX(3*this.gp.gettileSize());
			this.setY(2*this.gp.gettileSize());
		}
	}
	

}
