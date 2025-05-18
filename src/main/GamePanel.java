package main;
import java.awt.*;
import javax.swing.*;

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

	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 10;

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
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

		if (keyH.up == true) {
			playerY -= playerSpeed;
		} else if (keyH.down == true) {
			playerY += playerSpeed;
		} else if (keyH.left == true) {
			playerX -= playerSpeed;
		} else if (keyH.right == true) {
			playerX += playerSpeed;
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(Color.white);

		g2.fillRect(playerX, playerY, tileSize, tileSize);

		g2.dispose();
	}
/// hello 
}
