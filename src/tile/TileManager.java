package tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import java.awt.*;
import main.GamePanel;

public class TileManager {
	
	GamePanel gp;
	Tile[] tiles;
	int mapTile[][];
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tiles = new Tile[10];
		mapTile = new int[gp.getMaxScreenCol()][gp.getMaxScreenRow()];
		
		getTileImage();
		loadMap();
	}
	
	public void loadMap() {
		try {
			InputStream map = getClass().getResourceAsStream("/mapLevel/MapDemo.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(map));
			
			int col = 0;
			int row = 0;

			while (col < gp.getMaxScreenCol() && row < gp.getMaxScreenRow()) {
				String line =  br.readLine();
				
				while (col < gp.getMaxScreenCol()) {
					String numbers[] = line.split("");
					
					int num  = Integer.parseInt(numbers[col]);
					
					mapTile[col][row] = num;
					col++;
				}
				if ( col == gp.getMaxScreenCol()) {
					col = 0;
					row++;
					
				}
			}
			br.close();
		} catch(Exception e) {
			
		}
	}
	
	public void getTileImage() {
		try {
			tiles[0] = new Tile(gp.gettileSize(), gp.gettileSize());
			tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/images/fence.png"));// fence
			
			tiles[1] = new Tile(gp.gettileSize(), gp.gettileSize());
			tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/images/grass.png")); 
//			tiles[1] = new Tile();
//			tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/images/feeddoor.png")); // feed door
//			
//			tiles[2] = new Tile();
//			tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/images/fence.png")); // fence
//			
//			tiles[3] = new Tile();
//			tiles[3].image = ImageIO.read(getClass().getResourceAsStream("/images/food.png")); // meat crate
//			
//			tiles[4] = new Tile();
//			tiles[4].image = ImageIO.read(getClass().getResourceAsStream("/images/drug.png")); // medicine crate
//			
//			tiles[5] = new Tile();
//			tiles[5].image = ImageIO.read(getClass().getResourceAsStream("/images/toys.png")); // toys crate
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;

		while (col < gp.getMaxScreenCol() && row < gp.getMaxScreenRow()) {
			
			int tileNum = mapTile[col][row];
			
			g2.drawImage(tiles[tileNum].image, x, y, tiles[tileNum].getWidth(), tiles[tileNum].getHeight(), null);
			col++;
			x += gp.gettileSize();

			if (col == gp.getMaxScreenCol()) {
				col = 0;
				x = 0;
				row++;
				y += gp.gettileSize();
			}
		}

	}
}
