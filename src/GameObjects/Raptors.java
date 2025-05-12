package GameObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;

import game.GameComponent;
import game.GameObject;

public class Raptors extends GameObject implements ImageObserver {

	public Raptors(GameComponent gameComponent, double x, double y) {
		super(gameComponent, x, y, 12, 12, 30, 30, "Raptor");
		// TODO Auto-generated constructor stub
	} 

	@Override
	public void onRemove() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void update() {
		this.setDx(0);
		super.update();
		if ( isOffScreen()  ) {
			this.reverseDirection();
		}
	}

	@Override
	public void drawOn(Graphics2D g2) {
		// TODO Auto-generated method stub
		if (this.isSpriteLoaded()) {
			g2.drawImage(this.getImage(), (int)this.getX(), (int)this.getY(), 4*(int)this.getWidth(), 4*(int)this.getHeight(), this);
		} else {
			g2.setColor(Color.RED);
			g2.fillOval((int)this.getX(), (int)this.getY(), 4*(int)this.getWidth(), 4*(int)this.getHeight());
		}
	}

	@Override
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}

}
