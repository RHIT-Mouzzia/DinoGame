package GameObjects;

import java.awt.Graphics2D;

import main.GamePanel;

public class Meat extends Entities {

	public Meat(GamePanel gp, int x, int y, int width, int height) {
		super(gp, x, y, width, height, "down");
		setImage();
	}

	@Override
	public void setImage() {
		this.setDown("/Images/meat_crate.png");
	}

	@Override
	public void update() {

	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(getDown(), getX(), getY(), getWidth(), getHeight(), null);
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
