package GameObjects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Raptor extends Entities {
	private int cage;
	private int hunger;

	public Raptor(GamePanel gp, int width, int height, int cage) {
		super(gp, 0, 0, width, height, "down");
		this.cage = cage;
		setDefaultValues();
		this.setSpeed(2);
		setImage();
	}

	// Adding to raptor hunger
	public void getFeed() {
		this.hunger += 1;
	}

	/*
	 * Check if raptor is matured
	 */
	public boolean matured() {
		if (this.hunger == 5) {
			return true;
		}

		return false;
	}

	/*
	 * Return raptor's hunger
	 */
	public int getHunger() {
		return this.hunger;
	}

	/*
	 * Return raptor's cage
	 */
	public int getCage() {
		return this.cage;
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

		if (this.isOffScreen()) {
			this.setSpeed(-getSpeed());
		}

		// If raptor is matured, increase size
		if (matured()) {
			this.setWidth(2 * gp.getTileSize());
			this.setHeight(2 * gp.getTileSize());
			this.setSpeed(0);
		}

		super.update();

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

	/*
	 * Set the raptor position base on what cage it is spawn in
	 */
	public void setDefaultValues() {
		this.hunger = 0;
		if (this.cage == 3) {
			this.setX(13 * this.gp.getTileSize());
			this.setY(2 * this.gp.getTileSize());
		} else if (this.cage == 2) {
			this.setX(8 * this.gp.getTileSize());
			this.setY(2 * this.gp.getTileSize());
		} else {
			this.setX(3 * this.gp.getTileSize());
			this.setY(2 * this.gp.getTileSize());
		}
	}

	@Override
	public void collidedWithBox(Entities e) {
		this.setSpeed(-getSpeed());
	}

}
