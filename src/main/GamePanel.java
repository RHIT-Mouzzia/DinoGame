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
	private boolean gameWon = false;
	private int killCount;
	private int spawnCount;

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
	
	public void addKillCount(int i) {
		killCount += i;
	}
	
	public boolean getGameWon() {
		return this.gameWon;
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
		gameWon = false;
		tileM.loadMap(getCurrentMapPath());

		allObj.clear();
		pl = new Player(this, 8 * tileSize, 8 * tileSize, tileSize, tileSize, 4, "down", keyH);

		if (currentMap == 0) {
			startTime = System.nanoTime();
		}

		if (currentMap == 1) {
			startTime = System.nanoTime();
			pl = new Player(this, 8 * tileSize, 8 * tileSize, tileSize, tileSize, 4, "down", keyH);
			int id = 1;

			for (int i = 1; i <= this.getMaxScreenCol(); i += 1) {
				allObj.add(new Cage(this, i * tileSize, 3 * tileSize, tileSize, tileSize, id));
				if (i == 4 || i == 9 || i == 14) {
					id += 1;
					i += 1;
				}
			}

			for (int i = 0; i <= this.getMaxScreenCol(); i += 5) {
				for (int j = 0; j < 4; j += 1) {
					allObj.add(new Cage(this, i * tileSize, j * tileSize, tileSize, tileSize));
				}
			}

			allObj.add(new Meat(this, 7 * this.getTileSize(), 10 * this.getTileSize(), 2 * this.getTileSize(),
					2 * this.getTileSize()));

			for (int i = 1; i <= 3; i++) {
				allObj.add(new Raptor(this, tileSize, tileSize, i));
			}

			boolean powerUp = true;
			for (int i = 1; i <= 4; i++) {
				allObj.add(new Effect(this, i * tileSize, 10 * tileSize, tileSize, powerUp));
				powerUp = !powerUp;
			}

		} else if (currentMap == 2) {
			killCount = 0;
			spawnCount = 0;
			pl = new Player(this, 8 * tileSize, 10 * tileSize, tileSize, tileSize, 3, "down", keyH);
			for (int i = 0; i < maxScreenCol; i++) {
				allObj.add(new Cage(this, i * tileSize, 9 * tileSize, tileSize, tileSize));
			}
			for (int i = 2; i <= 10; i += 2) {
				allObj.add(new Flyer(this, i * tileSize, 2 * tileSize, tileSize, 3));
				spawnCount += 1;
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
		}
		shouldRemove.clear();
	}

	public void playGameEnding(Graphics2D g2, boolean win) {
		String game = "";
		g2.setFont(new Font("Arial", Font.BOLD, 45));
		if (win) {
			g2.setColor(Color.GREEN);
			game = "YOU WIN!";
		} else {
			g2.setColor(Color.RED);
			game = "Game Over";
		}

		int x = 5 * tileSize;
		int y = 6 * tileSize;
		g2.drawString(game, x, y);
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Arial", Font.PLAIN, 24));
		g2.drawString("Use the Keybad to select level or press 0 for Start Menu", x - 170, y + 40);
		return;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		int multiplier = 5;
		int totalLevel = 0;
		int finalScore = 0;
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
			g2.drawString("Controls:", 100, 280);
			g2.drawString("W and UP_key: Move Up:", 100, 310);
			g2.drawString("S and Left_key: Move Up:", 100, 340);
			g2.drawString("D and Rigth_key: Move Up:", 100, 370);
			g2.drawString("A and Left_key: Move Up:", 100, 400);
			g2.dispose();
			return;
		}

		if (currentMap == 1) {
			long pastTime = System.nanoTime() - startTime;
			int pastSec = (int) (pastTime / 1_000_000_000L);
			int remaining = timeLimit - pastSec;
			if (remaining <= 0) remaining = 0;
			
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("Arial", Font.BOLD, 24));
			g2.drawString("Time: " + remaining, 50, 240 + 5 * tileSize);
			
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("Arial", Font.PLAIN, 24));
			
			for(Entities e : drawList) {
				if ( e instanceof Raptor) {
					Raptor r = (Raptor)e;
					totalLevel += r.getHunger();
				}
			}
			
			if(totalLevel > 10) multiplier = 15;
			else if(totalLevel > 5) multiplier = 10;
			
			finalScore = totalLevel * multiplier;
			if (totalLevel >= 15) {
				gameWon = true;
				
			} else if (remaining == 0) {
				gameOver = true;
			}

			if (!gameWon && !gameOver) {
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
		} else {
			playGameEnding(g2, gameWon);
			g2.drawString("Your final score: " + finalScore, 10 * tileSize, 10 * tileSize);
			g2.dispose();
		}
		}

		if (currentMap == 2) {
			for (Entities e : allObj) {
				if (e instanceof Flyer) {
					Flyer f = (Flyer) e;
					if (f.getY() >= 9 * tileSize)
						gameOver = true;
				}
			}
			
			if(killCount >= spawnCount) gameWon = true;
			
			if (gameOver) {
				playGameEnding(g2, gameWon);
				g2.dispose();
			}
			else if (gameWon) {
				playGameEnding(g2, gameWon);
				g2.dispose();
			}
		}

		g2.dispose();
	}

}
