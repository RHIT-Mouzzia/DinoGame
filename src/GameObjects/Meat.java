package GameObjects;

import java.awt.Graphics2D;

import main.GamePanel;

public class Meat extends Entities {
	private GamePanel gp;
	
	public Meat(GamePanel gp) {
		this.gp = gp;
		setDefaultValues();
		getMeatImage();
		this.setDirection("down");
	}

	private void getMeatImage() {
		this.setDown("/Images/meat_crate.png");
	}

	private void setDefaultValues() {
		this.setX(7*this.gp.gettileSize());
		this.setY(10*this.gp.gettileSize());
	}
	
	@Override
	public void update() {
		
	}
	
	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(this.getDown(), getX(), getY(), 2*gp.gettileSize(), 2*gp.gettileSize(), null);
	}
	
}
