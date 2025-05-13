import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import java.awt.*;
import javax.swing.*;

public class Player extends JComponent {

	private int x = 0;
	private int y = 0;
	private final int R = 30;
	private BufferedImage image;
	private boolean spriteLoaded;
	private int dx = 10;
	private int dy = 10;

	public Player() {
		try {
			image = ImageIO.read(new File("src/Images/Player.png"));
			spriteLoaded = true;
		} catch (IOException e) {
			spriteLoaded = false;
		}

//		Timer animationTimer = new Timer(50, e -> updateListener());
//		animationTimer.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, x, y, null);
	}
}
