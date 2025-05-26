package GameObjects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Flyer extends Entities {
	private int spriteNum = 1;
	private int spriteCounter = 1;
	private int flapDelay = 25;
	private BufferedImage left1, left2, left3;
	private BufferedImage right1, right2, right3;

	public Flyer(GamePanel gp, int x, int y, int size, int speed) {
		super(gp, x, y, size, size, speed, "left");
		setImage();
	}

	@Override
	public void draw(Graphics2D g2) {
		spriteCounter++;
		if (spriteCounter > flapDelay) {
	        spriteNum = spriteNum % 4 + 1;
	        spriteCounter = 0;
	    }

		BufferedImage image = null;
		switch (getDirection()) {
		case "left":
			switch (spriteNum) {
			case 1:
				image = left1;
				break;
			case 2:
				image = left2;
				break;
			case 3:
				image = left3;
				break;
			case 4:
				image = left2;
				break;
			}
			break;
		case "right":
			switch (spriteNum) {
			case 1:
				image = right1;
				break;
			case 2:
				image = right2;
				break;
			case 3: 
				image = right3;
				break;
			case 4: 
				image = right2;
				break;
			}
			break;
		}
		g2.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
	}

	@Override
	public void update() {

		if (this.getY() == gp.getTileSize() * 10)
			gp.setGameOver(true);

		if (offLeft()) {
			setSpeed(getSpeed() + 1);
			flapDelay -= 2;
			setX(gp.getWidth() + this.getWidth());
			setY(this.getY() + gp.getTileSize());

		}
		setX(getX() - getSpeed());
	}

	@Override
	public void onRemove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setImage() {
		try {
			right1 = ImageIO.read(getClass().getResourceAsStream("/Images/chicken_left1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/Images/chicken_left3.png"));
			right3 = ImageIO.read(getClass().getResourceAsStream("/Images/chicken_left2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/Images/chicken_right1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/Images/chicken_right3.png"));
			left3 = ImageIO.read(getClass().getResourceAsStream("/Images/chicken_right2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void collidedWithBox(Entities e) {
		// TODO Auto-generated method stub
		if (e instanceof Bullet) {
			this.markToRemove();
			e.markToRemove();
		}
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
