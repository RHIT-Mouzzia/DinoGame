package main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import GameObjects.Bullet;
import GameObjects.Cage;
import GameObjects.Effect;
import GameObjects.Entities;
import GameObjects.Flyer;
import GameObjects.Meat;
import GameObjects.Player;
import GameObjects.Raptor;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

	private final static int originalTileSize = 16;
	private final static int SCALE = 3;
	private final int tileSize = originalTileSize * SCALE;
	private final static int maxScreenCol = 16;
	private final static int maxScreenRow = 12;
	private final int screenWidth = tileSize * maxScreenCol;
	private final int screenHeight = tileSize * maxScreenRow;
	private final static int fps = 60;
	private final static int timeLimit = 5;
	private long startTime;
	private boolean gameOver = false;

	private String[] mapPaths = { "/mapLevel/Level0.txt", "/mapLevel/Level1.txt", "/mapLevel/Level2.txt" };

	private int currentMap = 0;

	public String getCurrentMapPath() {
		return mapPaths[currentMap];
	}

	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	TileManager tileM = new TileManager(this);
	Player pl;

	ArrayList<Entities> allObj = new ArrayList<Entities>();
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	public int getOriginalTileSize() {
		return originalTileSize;
	}

	public int getScale() {
		return SCALE;
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

	public TileManager getTileManager() {
		return this.tileM;
	}

	public int getTileSize() {
		return this.tileSize;
	}

	public ArrayList<Entities> getEntities() {
		return this.allObj;
	}

	public ArrayList<Bullet> getBullets() {
		return this.bullets;
	}

	public void addBullet(Bullet b) {
		this.bullets.add(b);
	}

	public boolean getGameOver() {
		return this.gameOver;
	}

	public void setGameOver(boolean over) {
		this.gameOver = over;
	}

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		changeMap(0);
	}

	public void changeMap(int newMap) {

		currentMap = newMap;
		gameOver = false;
		tileM.loadMap(getCurrentMapPath());

		allObj.clear();

		if (currentMap == 0) {
			startTime = System.nanoTime();
		}
		
		pl = new Player(this, 8 * tileSize, 8 * tileSize, tileSize, tileSize, 4, "down", keyH);

		if (currentMap == 1) {
			startTime = System.nanoTime();

			int id = 1;

			for (int i = 1; i <= this.getMaxScreenRow(); i += 5) {
				allObj.add(new Cage(this, i * this.getTileSize(), 3 * this.getTileSize(), 4 * this.getTileSize(),
						this.getTileSize(), id));
				id += 1;
			}

			for (int i = 0; i <= this.getMaxScreenCol(); i += 5) {
				allObj.add(new Cage(this, i * this.getTileSize(), 0, this.getTileSize(), 4 * this.getTileSize()));
			}

			allObj.add(new Meat(this, 7 * this.getTileSize(), 10 * this.getTileSize(), 2 * this.getTileSize(),
					2 * this.getTileSize()));
			
			for(int i = 1; i <= 3; i ++) {
				allObj.add(new Raptor(this, tileSize, tileSize, i));
			}
			
			boolean powerUp = true;
			for(int i = 1; i <= 4; i ++) {
				allObj.add(new Effect(this, i * tileSize, 10 * tileSize, tileSize, powerUp));
				powerUp = !powerUp;
			}
			

		} else if (currentMap == 2) {
			for (int i = 2; i <= 10; i +=2) {
				allObj.add(new Flyer(this, i * tileSize, 2 * tileSize, tileSize, 3));
			}
		}
		
		allObj.add(pl);
		allObj.addAll(bullets);
	}

	public void startGamethread() {

		startTime = System.nanoTime();
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

		if (keyH.map0) {
			currentMap = 0;
			allObj.clear();
			keyH.map0 = false;
			return;
		}

		if (keyH.map1) {
			changeMap(1);
		} else if (keyH.map2) {
			changeMap(2);
		}

		for (Entities e : allObj) {
			e.update();
		}

		for (Entities e1 : allObj) {
			for (Entities e2 : allObj) {
				if (e1 != e2) {
					if (e1.overlaps(e2)) {
						e1.collidedWithBox(e2);
					}
				}
			}
		}

		List<Entities> shouldRemove = new ArrayList<>();

		for (Entities object : allObj) {
			if (object.shouldRemove()) {
				shouldRemove.add(object);
			}
		}

		if (!bullets.isEmpty()) {
			allObj.addAll(bullets);
			bullets.clear();
		}

		for (Entities object : shouldRemove) {
			this.allObj.remove(object);
			object.onRemove();
		}
		shouldRemove.clear();
	}

	public void playGameOver(Graphics2D g2) {
		g2.setColor(Color.RED);
		g2.setFont(new Font("Arial", Font.BOLD, 45));
		String GameOver = "Game Over";
		FontMetrics fm = g2.getFontMetrics();
		int x = (screenWidth - fm.stringWidth(GameOver)) / 2;
		int y = (screenHeight - fm.getHeight()) / 2 + fm.getAscent();
		g2.drawString(GameOver, x, y);
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Arial", Font.PLAIN, 24));
		g2.drawString("Use the Keybad to select level or press 0 for Start Menu", x - 170, y + 40);
		return;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		tileM.draw(g2);

		ArrayList<Entities> drawList = new ArrayList<>(allObj);

		for (Entities e : drawList) {
			e.draw(g2);
		}

		if (currentMap == 0) {

			g2.setFont(new Font("Arial", Font.BOLD, 40));
			g2.setColor(Color.WHITE);
			g2.drawString("Welcome to Among Chickens!", 100, 100);
			g2.setFont(new Font("SansSerif", Font.PLAIN, 24));
			g2.drawString("Press 0 for Start Menu", 100, 160);
			g2.drawString("Press 1 for Level 1", 100, 200);
			g2.drawString("Press 2 for Level 2", 100, 240);
			g2.dispose();
			return;
		}

		if (currentMap == 1) {
			long pastTime = System.nanoTime() - startTime;
			int pastSec = (int) (pastTime / 1_000_000_000L);
			int remaining = timeLimit - pastSec;
			if (remaining <= 0) {
				gameOver = true;
				remaining = 0;
			}

			g2.setColor(Color.WHITE);
			g2.setFont(new Font("Arial", Font.BOLD, 24));
			g2.drawString("Time: " + remaining, 50, 240 + 5 * tileSize);

			if (gameOver) {
				playGameOver(g2);
				g2.dispose();
			}

			g2.setColor(Color.WHITE);
			g2.setFont(new Font("Arial", Font.PLAIN, 24));

			for (Entities e : drawList) {
				if (e instanceof Raptor) {
					Raptor r = (Raptor) e;
					int lv = r.getHunger();
					if (lv >= 5) {
						lv = 5;
					}

					g2.drawString("Raptor " + r.getCage() + " lv: " + lv + "/5", 50, 240 + r.getCage() * tileSize);
				}
				if (e instanceof Player) {
					Player p = (Player) e;
					g2.drawString("Player is" + p.getMeat() + " carrying meat", 50, 240 + 4 * tileSize);
				}
			}

		}

		if (currentMap == 2) {
			for (Entities e : allObj) {
				if (e instanceof Flyer) {
					Flyer f = (Flyer) e;
					if (f.getY() >= 10 * tileSize)
						gameOver = true;
				}
			}

			if (gameOver) {
				playGameOver(g2);
				g2.dispose();
			}
		}

		g2.dispose();
	}

}
