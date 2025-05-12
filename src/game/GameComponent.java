package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;

import GameObjects.*;

public class GameComponent extends JComponent {
	final int originalTileSize = 16;
	final int scale = 3;

	final int tileSize = originalTileSize * scale;
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int screenWidth = tileSize * maxScreenCol;
	final int screenHeight = tileSize * maxScreenRow;
	
	private Player player;
	private ArrayList<Raptors> utah = new ArrayList<Raptors>();
	
	public GameComponent() {
		setPreferredSize(new Dimension(screenWidth, screenHeight));
		setDoubleBuffered(true);
		
		this.player = new Player(this);
		utah.add(new Raptors(this, 60, 60));
		utah.add(new Raptors(this, 180, 60));
		utah.add(new Raptors(this, 400, 60));
		utah.add(new Raptors(this, 580, 60));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		this.player.drawOn(g2);
		
		for (Raptors raptor : this.utah) {
			raptor.drawOn(g2);
		}
	}
	
	public void drawScreen() {
		this.repaint();
	}
	
	public void upDateState() {
		this.upDatePlayer();
	}
	
	public void upDatePlayer() {
		this.player.update();
		
		for (Raptors raptor : utah) {
			raptor.update();
		}
	}

}
