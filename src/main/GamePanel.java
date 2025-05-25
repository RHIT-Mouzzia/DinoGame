package main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import GameObjects.Bullet;
import GameObjects.Cage;
import GameObjects.Effect;
import GameObjects.Entities;
import GameObjects.Meat;
import GameObjects.Player;
import GameObjects.Raptor;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

	private int originalTileSize = 16;
	private int scale = 3;
	private int tileSize = originalTileSize * scale;
	private int maxScreenCol = 16;
	private int maxScreenRow = 12;
	private int screenWidth = tileSize * maxScreenCol;
	private int screenHeight = tileSize * maxScreenRow;
	private int fps = 60;
	private final int timeLimit = 5;
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

	ArrayList<Entities> allObj = new ArrayList<Entities>();
	ArrayList<Entities> gameObj = new ArrayList<Entities>();
	ArrayList<Cage> fences = new ArrayList<Cage>();
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();

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

	public TileManager getTileManager() {
		return this.tileM;
	}

	public int getTileSize() {
		return this.tileSize;
	}

	public ArrayList<Entities> getEntities() {
		return this.gameObj;
	}

	public ArrayList<Bullet> getBullets() {
		return this.bullets;
	}

	public void addBullet(Bullet b) {
		this.bullets.add(b);
	}

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		changeMap(0);

	}

	public void changeMap(int newMap) {

		currentMap = newMap;

		tileM.loadMap(getCurrentMapPath());

		gameObj.clear();
		fences.clear();
		bullets.clear();
		allObj.clear();

		if (currentMap == 0) {
			startTime = System.nanoTime();
			gameOver = false;
		}

		allObj.add(new Player(this, 8 * tileSize, 8 * tileSize, tileSize, tileSize, 4, "down", keyH));

		if (currentMap == 1) {
			startTime = System.nanoTime();
			gameOver = false;
		
			int id = 1;

			for (int i = 1; i <= this.getMaxScreenRow(); i += 5) {
				fences.add(new Cage(this, i * this.gettileSize(), 3 * this.gettileSize(), 4 * this.gettileSize(),
						this.gettileSize(), id));
				id += 1;
			}

			for (int i = 0; i <= this.getMaxScreenCol(); i += 5) {
				gameObj.add(new Cage(this, i * this.gettileSize(), 0, this.gettileSize(), 4 * this.gettileSize()));
			}

			gameObj.add(new Meat(this, 7 * this.gettileSize(), 10 * this.gettileSize(), 2 * this.gettileSize(),
					2 * this.gettileSize()));
			gameObj.add(new Raptor(this, tileSize, tileSize, 1));
			gameObj.add(new Raptor(this, tileSize, tileSize, 2));
			gameObj.add(new Raptor(this, tileSize, tileSize, 3));
			gameObj.add(new Effect(this, 1 * tileSize, 10 * tileSize, tileSize, false));
			gameObj.add(new Effect(this, 2 * tileSize, 10 * tileSize, tileSize, true));
			gameObj.add(new Effect(this, 3 * tileSize, 10 * tileSize, tileSize, false));
			gameObj.add(new Effect(this, 4 * tileSize, 10 * tileSize, tileSize, true));

		} else if (currentMap == 2) {

		}
		allObj.addAll(gameObj);
		allObj.addAll(fences);
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
			bullets.clear();
			gameObj.clear();
			fences.clear();
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
			this.bullets.remove(object);
			this.allObj.remove(object);
//			this.flyers.remove(object);
			object.onRemove();
		}
		shouldRemove.clear();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		if (currentMap == 0) {
			tileM.draw(g2);

			ArrayList<Entities> drawList = new ArrayList<>(allObj);

			for (Entities e : drawList) {
				e.draw(g2);
			}

			g2.setFont(new Font("Arial", Font.BOLD, 40));
			g2.setColor(Color.WHITE);
			g2.drawString("Welcome to Among Chickens!", 100, 100);
			g2.setFont(new Font("SansSerif", Font.PLAIN, 24));
			g2.drawString("Press 0 for Start Menu", 100, 160);
			g2.drawString("Press 1 for Level 1", 100, 200);
			g2.drawString("Press 2 for Level 2", 100, 240);
			return;
		}

		tileM.draw(g2);

		ArrayList<Entities> drawList = new ArrayList<>(allObj);

		for (Entities e : drawList) {
			e.draw(g2);
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
				g2.setColor(Color.RED);
				g2.setFont(new Font("Arial", Font.BOLD, 45));
				String GameOver = "Game Over";
				FontMetrics fm = g2.getFontMetrics();
				int x = (screenWidth - fm.stringWidth(GameOver)) / 2;
				int y = (screenHeight - fm.getHeight()) / 2 + fm.getAscent();
				g2.drawString(GameOver, x, y);
				return;
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

		g2.dispose();
	}

}
