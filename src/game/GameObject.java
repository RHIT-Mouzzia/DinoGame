package game;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class GameObject {
	
	private double x, y;
	private double yVelocity;
	private double xVelocity;
	private boolean shouldRemove;
	private double width;
	private double height;
	private BufferedImage image;
	private boolean spriteLoaded;
	protected GameComponent gameComponent;
	
	
	public GameObject(GameComponent gameComponent, double x, double y, double dx, double dy, double width, double height) {
		this.x = x;
		this.y = y;
		this.xVelocity = dx;
		this.yVelocity = dy;
		this.gameComponent = gameComponent;
		this.width = width;
		this.height = height;
	}
	
	public GameObject(GameComponent gameComponent, double x, double y, double dx, double dy, double width, double height, String image) {
		this.x = x;
		this.y = y;
		this.xVelocity = dx;
		this.yVelocity = dy;
		this.gameComponent = gameComponent;
		this.width = width;
		this.height = height;
		try {
		// lambda calling your new method
			this.image = ImageIO.read(new File("src/images/" + image + ".png"));
			this.spriteLoaded = true;
		}
		catch(IOException e){
			this.spriteLoaded = false;
		}
	}
	
	public abstract void onRemove();
	public abstract void drawOn(Graphics2D g2);
	
	public double getX() {
		return this.x;
	}
	public double getY() {
		return this.y;
	}
	public double getDx() {
		return this.xVelocity;
	}
	public double getDy() {
		return this.yVelocity;
	}
	public void setDx(double speed) {
		this.xVelocity = speed;
	}
	public void setDy(double speed) {
		this.yVelocity = speed;
	}
	public double getWidth() {
		return this.width;
	}
	public double getHeight() {
		return this.height;
	}
	public BufferedImage getImage() {
		return this.image;
	}
	public boolean isSpriteLoaded() {
		return this.spriteLoaded;
	}


	// updatePosition, fall, ... can all be consolidated into an update method.
	public void update() {
		this.x += this.xVelocity;
		this.y += this.yVelocity;
	}
	
	public void reverseDirection() {
		this.xVelocity = -this.xVelocity;
		this.yVelocity = -this.yVelocity;
	}
	
	// fall, willExplode, ...
	public boolean shouldRemove() {
		return this.shouldRemove;
	}
	
	public void markToRemove() {
		this.shouldRemove = true;
	}
	
	public Rectangle2D.Double getBoundingBox() {
		return new Rectangle2D.Double(this.x, this.y, getWidth(), getHeight() );
	}

	public boolean overlaps(GameObject other) {
		return getBoundingBox().intersects(other.getBoundingBox());
	}
	
	public boolean isOffScreen() {
		boolean xLow = x <0;
		boolean xHigh = x + width > gameComponent.getWidth();
		boolean yLow = y <0;
		boolean yHigh = y + height > gameComponent.getHeight();
		return xLow || xHigh|| yLow|| yHigh;
	}
	
	public boolean offBottom() {
		return y > gameComponent.getHeight();
	}
	
	
}
