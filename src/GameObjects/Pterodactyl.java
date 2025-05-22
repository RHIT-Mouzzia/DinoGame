package GameObjects;
import main.GamePanel;
import java.awt.*;
import javax.imageio.*;
public class Pterodactyl extends Entities {
	private int tileSize = gp.getTileSize();
    
    public Pterodactyl(GamePanel gp, int startCol, int startRow) {
        super(gp, startCol, startRow, gp.getTileSize(), gp.getTileSize() );
        this.setSpeed(4);
        this.setDirection("right");
        setImage();
    }

    @Override
    public void update() {
        if (this.getDirection().equals("right")) {
            setX(getX() + this.getSpeed());
            if (getX() + tileSize >= gp.getScreenWidth()) {
                this.setDirection("left");
                setY(getY() + tileSize);
            }
        } else {
            setX(getX() - this.getSpeed());
            if (getX() <= 0) {
                this.setDirection("right");
                setY(getY() + tileSize);
            }
        }
        if (getY() + tileSize > gp.getScreenHeight()) {
            setY(gp.getScreenHeight() - tileSize);
        }
    }

    @Override
    public void draw(Graphics2D g2) {

    }

	@Override
	public void setDefaultValues() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setImage() {
		// TODO Auto-generated method stub
		this.setRight("");
		this.setLeft("");
	}

	@Override
	public void collidedWithBox(Entities e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collidedWithFeederFence(Cage f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void collidedWithBullets(Bullet b) {
		// TODO Auto-generated method stub
		b.markToRemove();
		this.markToRemove();
	}

	@Override
	public void onRemove() {
		// TODO Auto-generated method stub
		
	}
}

