package GameObjects;

import java.awt.Graphics2D;

import main.GamePanel;

public class Event extends Entities {
	
	public Event(GamePanel gp, int x, int y, int size) {
		super(gp, x, y, size, size, "up");
	}

	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRemove() {
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

}
