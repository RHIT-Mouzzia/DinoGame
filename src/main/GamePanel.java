package main;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import GameObjects.Entities;
import GameObjects.Meat;
import GameObjects.Player;
import GameObjects.Raptor;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

	final int originalTileSize = 16;
	final int scale = 3;

	final int tileSize = originalTileSize * scale;
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int screenWidth = tileSize * maxScreenCol;
	final int screenHeight = tileSize * maxScreenRow;

	int fps = 60;

	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	Player player = new Player(this, keyH);
	Meat meat  = new Meat(this);
	TileManager tileM = new TileManager(this);
	
	ArrayList<Entities> gameObj = new ArrayList<Entities>();

	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 10;

	public int gettileSize() {
		return this.tileSize;
	}

	public int getOriginalTileSize() {
		return originalTileSize;
	}

	public int getScale() {
		return scale;
	}

	public int getMaxScreenCol() {
		return maxScreenCol;
	}

	public int getMaxScreenRow() {
		return maxScreenRow;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		gameObj.add(player);
		gameObj.add(meat);
		gameObj.add(new Raptor(this, 3));
		gameObj.add(new Raptor(this, 1));
	}

	public int getTileSize() {
		return this.tileSize;
	}

	public void startGamethread() {

		gameThread = new Thread(this);
		gameThread.start();

	}

	public void run() {

		double drawInterval = 1000000000.0 / fps;
		double delta = 0;
		long currentTime = System.nanoTime();

		long timer = 0;
		int drawCount = 0;
		long lastTime = System.nanoTime();

		while (gameThread != null) {

			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}

			if (timer >= 1000000000) {
				System.out.println("FPS:" + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	}

	public void update() {

		//player.update();
		for (Entities obj : gameObj) {
			obj.update();
		}

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		
		tileM.draw(g2);

		//player.draw(g2);
		for (Entities obj : gameObj) {
			obj.draw(g2);
		}

		g2.dispose();
	}
/// hello 
}
