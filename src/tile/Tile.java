package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import GameObjects.Bullet;
import GameObjects.Cage;
import GameObjects.Entities;
import main.GamePanel;


public class Tile {
	private int width;
	private int height;
	
	public Tile(int width, int height) {
		this.width = width;
		this.height = height;
		// TODO Auto-generated constructor stub
	}
	
	public BufferedImage image;
	public boolean collision  = false;
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
}