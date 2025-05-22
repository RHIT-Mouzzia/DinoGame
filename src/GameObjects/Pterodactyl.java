package GameObjects;
import main.GamePanel;
import java.awt.*;
import javax.imageio.*;
public class Pterodactyl extends Entities {
    private int tileSize;
	private int speed;
	private String direction;
    
    public Pterodactyl(GamePanel gp, int startCol, int startRow) {
        super(gp, startCol, startRow, gp.getTileSize(), gp.getTileSize() );
        this.gp = gp;
        this.tileSize = gp.getTileSize();
        this.speed = 4;
        this.direction = "right";
        loadSprites();
    }

    private void loadSprites() {
    }

    @Override
    public void update() {
        if (direction.equals("right")) {
            setX(getX() + speed);
            if (getX() + tileSize >= gp.getScreenWidth()) {
                direction = "left";
                setY(getY() + tileSize);
            }
        } else {
            setX(getX() - speed);
            if (getX() <= 0) {
                direction = "right";
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
		
	}

	@Override
	public void collidedWithBox(Entities e) {
		// TODO Auto-generated method stub
		
	}
}

