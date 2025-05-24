package GameObjects;

import java.awt.Graphics2D;

import main.GamePanel;

public class Effect extends Entities {
	private boolean buff;
	
	public Effect(GamePanel gp, int x, int y, int size, boolean buff) {
		super(gp, x, y, size, size, "up");
		this.buff = buff;
		setImage();
	}
	
	public boolean getBuff() {
		return buff;
	}

	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		g2.drawImage(getUp(), getX(), getY(), getWidth(), getHeight(), null);
	}

	@Override
	public void onRemove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setImage() {
		// TODO Auto-generated method stub
		setUp("/Images/boba.png");
	}

	@Override
	public void collidedWithBox(Entities e) {
		// TODO Auto-generated method stub
		if(e instanceof Player) {
			Player p = (Player)e;
			if(buff) {
				p.setSpeed(p.getSpeed() + 1);
			}
			else {
				p.setSpeed(p.getSpeed() - 1);
			}
		}
		this.markToRemove();

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
