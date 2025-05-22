package GameObjects;

import java.awt.Graphics2D;

import main.GamePanel;

public class Cage extends Entities {
	private int cage;

	//Feeder
	public Cage(GamePanel gp, int x, int y, int width, int height, int id) {
		super(gp, x, y, width, height, "down");
		this.cage = id;
		setImage();
	}

	//Fence
	public Cage(GamePanel gp, int x, int y, int width, int height) {
		super(gp, x, y, width, height, "down");
		setImage();
	}
	
	public int getCage() {
		return this.cage;
	}
	
	@Override
	public void setImage() {
		this.setDown("/Images/fence.png");
	}

	@Override
	public void setDefaultValues() {
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


}
