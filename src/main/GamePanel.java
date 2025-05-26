package main;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
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
	private final static int timeLimit = 60;
	private long startTime;
	private boolean gameOver = false;
	private boolean gameWon = false;
	private int killCount;
	private int spawnCount;

	private String[] mapPaths = { "/mapLevel/Level0.txt", "/mapLevel/Level1.txt", "/mapLevel/Level2.txt" };

	private int currentMap = 0;

	/*
	 * Return map path to select level
	 */
	public String getCurrentMapPath() {
		return mapPaths[currentMap];
	}

	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	TileManager tileM = new TileManager(this);
	Player pl;

	ArrayList<Entities> allObj = new ArrayList<Entities>();
	ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	/*
	 * Return original tile size (size of a tile)
	 */
	public int getOriginalTileSize() {
		return originalTileSize;
	}

	/*
	 * Return scale for each tile
	 */
	public int getScale() {
		return SCALE;
	}

	/*
	 * Return max column of screen
	 */
	public int getMaxScreenCol() {
		return maxScreenCol;
	}

	/*
	 * Return max row of screen
	 */
	public int getMaxScreenRow() {
		return maxScreenRow;
	}

	/*
	 * Return the screen width
	 */
	public int getScreenWidth() {
		return screenWidth;
	}

	/*
	 * Return the screen height
	 */
	public int getScreenHeight() {
		return screenHeight;
	}

	/*
	 * Return tile manager
	 */
	public TileManager getTileManager() {
		return this.tileM;
	}

	/*
	 * Return tile size
	 */
	public int getTileSize() {
		return this.tileSize;
	}

	/*
	 * Return all objects in game panel
	 */
	public ArrayList<Entities> getEntities() {
		return this.allObj;
	}

	/*
	 * Return list of bullets
	 */
	public ArrayList<Bullet> getBullets() {
		return this.bullets;
	}

	/*
	 * Adding a bullet to bullets list
	 */
	public void addBullet(Bullet b) {
		this.bullets.add(b);
	}

	/*
	 * Return if the game is over
	 */
	public boolean getGameOver() {
		return this.gameOver;
	}

	/*
	 * Set if the game is over or not
	 */
	public void setGameOver(boolean over) {
		this.gameOver = over;
	}

	/*
	 * Add i to the total number of kill count
	 */
	public void addKillCount(int i) {
		killCount += i;
	}

	/*
	 * Return if the player won or not
	 */
	public boolean getGameWon() {
		return this.gameWon;
	}

	/*
	 * Constructor
	 */
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		changeMap(0);
		playSound();
	}

	/*
	 * Method to play the music
	 */
	private void playSound() {
		try {
			AudioInputStream a = AudioSystem.getAudioInputStream(new File("src/audio/music.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(a);
			clip.start();
		} catch (Exception e) {
			System.err.println("playing sound: " + e.getMessage());
		}
	}

	/*
	 * This method is used to change different lv input newMap: the number of which
	 * lv the player want to play from the map paths
	 */
	public void changeMap(int newMap) {

		currentMap = newMap;
		gameOver = false;
		gameWon = false;
		tileM.loadMap(getCurrentMapPath()); // load tiles base on which map is selected

		allObj.clear(); // clear everything when change map

		pl = new Player(this, 8 * tileSize, 8 * tileSize, tileSize, tileSize, 4, "down", keyH); // a player is added in
																								// the game

		if (currentMap == 0) {
			startTime = System.nanoTime(); // Start time
			allObj.add(new Raptor(this, tileSize * 2, tileSize * 2, 0));
			allObj.add(new Flyer(this, 1 * tileSize, 1 * tileSize, tileSize, 3));
			allObj.add(new Flyer(this, 1 * tileSize, 1 * tileSize, tileSize, 3));

		}

		// Adding objects for level 1
		if (currentMap == 1) {
			startTime = System.nanoTime();
			pl = new Player(this, 8 * tileSize, 8 * tileSize, tileSize, tileSize, 4, "down", keyH);
			int id = 1;

			// Create horizontal cages or feeder fence
			for (int i = 1; i <= this.getMaxScreenCol(); i += 1) {
				allObj.add(new Cage(this, i * tileSize, 3 * tileSize, tileSize, tileSize, id));
				if (i == 4 || i == 9 || i == 14) {
					id += 1;
					i += 1;
				}
			}

			// Create vertical cages
			for (int i = 0; i <= this.getMaxScreenCol(); i += 5) {
				for (int j = 0; j < 4; j += 1) {
					allObj.add(new Cage(this, i * tileSize, j * tileSize, tileSize, tileSize));
				}
			}

			// Add one box for meat crate
			allObj.add(new Meat(this, 7 * this.getTileSize(), 10 * this.getTileSize(), 2 * this.getTileSize(),
					2 * this.getTileSize()));

			// Create 3 raptors
			for (int i = 1; i <= 3; i++) {
				allObj.add(new Raptor(this, tileSize, tileSize, i));
			}

			// Create 4 power up
			boolean powerUp = true;
			for (int i = 1; i <= 4; i++) {
				allObj.add(new Effect(this, i * tileSize, 10 * tileSize, tileSize, powerUp));
				powerUp = !powerUp;
			}

			// Adding objects for level 2
		} else if (currentMap == 2) {
			killCount = 0;
			spawnCount = 0;
			pl = new Player(this, 8 * tileSize, 10 * tileSize, tileSize, tileSize, 3, "down", keyH);
			// Add cage to prevent player move up
			for (int i = 0; i < maxScreenCol; i++) {
				allObj.add(new Cage(this, i * tileSize, 9 * tileSize, tileSize, tileSize));
			}
			// Add fliers
			for (int i = 2; i <= 10; i += 2) {
				allObj.add(new Flyer(this, i * tileSize, 2 * tileSize, tileSize, 3));
				spawnCount += 1;
			}
		}

		// Add everything to a list
		allObj.add(pl);
		allObj.addAll(bullets);
	}

	/*
	 * This method make the games start running
	 */
	public void startGamethread() {

		startTime = System.nanoTime();
		gameThread = new Thread(this);
		gameThread.start();
	}

	/*
	 * A game loop called delta/accumulator method used to draw and update at every
	 * draw interval
	 */
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

	/*
	 * Updated everything happen in the game
	 */
	public void update() {
		// Load intro level
		if (keyH.map0) {
			changeMap(0);
			keyH.map0 = false;
		}

		// Load level 1
		if (keyH.map1) {
			changeMap(1);
		} else if (keyH.map2) { // Load level 2
			changeMap(2);
		}

		// Update every objects
		for (Entities e : allObj) {
			e.update();
		}

		// Update for collisions of each objects
		for (Entities e1 : allObj) {
			for (Entities e2 : allObj) {
				if (e1 != e2) {
					if (e1.overlaps(e2)) {
						e1.collidedWithBox(e2);
					}
				}
			}
		}

		// Removing objects
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

	/*
	 * Show ending base on player loose or win
	 */
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
		g2.drawString("Press 1 for level one and 2 for level two", x - 80, y + 40);
		g2.drawString("Press 0 for Start Menu", x, y + 70);
		return;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		int multiplier = 5;
		int totalLevel = 0;
		int finalScore = 0;
		int x = 5 * tileSize;
		int y = 6 * tileSize;

		tileM.draw(g2);

		ArrayList<Entities> drawList = new ArrayList<>(allObj);
		// Draw every objects
		for (Entities e : drawList) {
			e.draw(g2);
		}

		// Draw for intro level
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
			g2.drawString("Space_Bar: Shoot up:", 100, 430);
			g2.dispose();
			return;
		}

		// Draw for level 1
		if (currentMap == 1) {
			// Using time for scoring system
			long pastTime = System.nanoTime() - startTime;
			int pastSec = (int) (pastTime / 1_000_000_000L);
			int remaining = timeLimit - pastSec;
			if (remaining <= 0)
				remaining = 0; // Make remaining time 0 when game over

			g2.setColor(Color.WHITE);
			g2.setFont(new Font("Arial", Font.BOLD, 24));
			g2.drawString("Time: " + remaining, 50, 240 + 5 * tileSize);

			g2.setColor(Color.WHITE);
			g2.setFont(new Font("Arial", Font.PLAIN, 24));

			for (Entities e : drawList) {
				if (e instanceof Raptor) {
					Raptor r = (Raptor) e;
					totalLevel += r.getHunger();
				}
			}

			// Setting multiplier, scoring for this level
			if (totalLevel > 10)
				multiplier = 15;
			else if (totalLevel > 5)
				multiplier = 10;
			finalScore = totalLevel * multiplier;

			// Winning condition
			if (totalLevel >= 15) {
				gameWon = true;
				// Losing condition
			} else if (remaining == 0) {
				gameOver = true;
			}

			// Draw if the player is not lost or won
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
				// Draw if player is won or lost
			} else {
				playGameEnding(g2, gameWon);
				g2.drawString("Your final score: " + finalScore, x, y + 100);
				g2.dispose();
			}
		}

		// Draw for level 2
		// If one flyer get over the set position, player lose
		if (currentMap == 2) {
			for (Entities e : allObj) {
				if (e instanceof Flyer) {
					Flyer f = (Flyer) e;
					if (f.getY() >= 9 * tileSize)
						gameOver = true;
				}
			}

			// If player kill all fliers, player won
			if (killCount == spawnCount)
				gameWon = true;

			if (gameOver) {
				playGameEnding(g2, gameWon);
				g2.dispose();
			} else if (gameWon) {
				playGameEnding(g2, gameWon);
				g2.dispose();
			}
		}

		g2.dispose();
	}

}
