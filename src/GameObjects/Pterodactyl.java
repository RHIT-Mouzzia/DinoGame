package GameObjects;

import main.GamePanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;

public class Pterodactyl extends Entities {
	private int speed = 4;
	private String direction = "right";
	private int spriteNum = 1;
	private int spriteCounter = 0;

	public Pterodactyl(GamePanel gp, int col, int row) {
		super(gp, col, row, gp.getTileSize(), gp.getTileSize());
		setSpeed(4); // adjust as needed
		setDirection("right"); // start flying right
	}

	@Override
	public void setImage() {
		this.setRight("/Images/Raptor.png");
		this.setLeft("/Images/Raptor.png");
	}

	@Override
	public void update() {
		if (direction.equals("right")) {
			setX(getX() + speed);
			if (getX() + getWidth() >= gp.getScreenWidth()) {
				direction = "left";
				setY(getY() + getHeight()); // move down one tile
			}
		} else {
			setX(getX() - speed);
			if (getX() <= 0) {
				direction = "right";
				setY(getY() + getHeight()); // move down one tile
			}
		}
		// clamp to bottom
		if (getY() + getHeight() > gp.getScreenHeight()) {
			setY(gp.getScreenHeight() - getHeight());
		}
	}

	@Override
    public void draw(Graphics2D g2) {
        BufferedImage image= null;
        switch(getDirection()){
        case "right":
        	this.getRight();
        	break;
        case "left":
        	this.getLeft();
        	break;
        }
        g2.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
    }

	@Override
	public void setDefaultValues() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRemove() {
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
}
