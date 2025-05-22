package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import GameObjects.Cage;
import GameObjects.Entities;
import main.GamePanel;


public class Tile extends Entities {
	public Tile(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}
	public BufferedImage image;
	public boolean collision  = false;
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void draw(Graphics2D g2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setDefaultValues() {
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
	
}