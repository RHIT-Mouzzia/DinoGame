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
	
	/*
	 * Return if this is buff or debuff
	 */
	public boolean getBuff() {
		return buff;
	}

	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		g2.drawImage(getUp(), getX(), getY(), getWidth(), getHeight(), null);
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
				p.setSpeed(p.getSpeed() + 1);//Add speed for buff
			}
			else {
				p.setSpeed(p.getSpeed() - 1);//Slow speed for debuff
			}
		}
		this.markToRemove();//Remove when collided

	}

}
