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
		loadMap("/mapLevel/Level0.txt");
	}

	public void loadMap(String m) {
		try {
			InputStream map = getClass().getResourceAsStream(m);
			BufferedReader br = new BufferedReader(new InputStreamReader(map));

			int col = 0;
			int row = 0;

			while (col < gp.getMaxScreenCol() && row < gp.getMaxScreenRow()) {
				String line = br.readLine();

				while (col < gp.getMaxScreenCol()) {
					String numbers[] = line.split("");

					int num = Integer.parseInt(numbers[col]);

					mapTile[col][row] = num;
					col++;
				}
				if (col == gp.getMaxScreenCol()) {
					col = 0;
					row++;

				}
			}
			br.close();
		} catch (Exception e) {

		}
	}

	public void getTileImage() {
		try {
			tiles[0] = new Tile(gp.getTileSize(), gp.getTileSize());
			tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/images/fence.png"));

			tiles[1] = new Tile(gp.getTileSize(), gp.getTileSize());
			tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/images/grass.png"));
			
			tiles[5] = new Tile(gp.getTileSize(), gp.getTileSize());
			tiles[5].image = ImageIO.read(getClass().getResourceAsStream("/images/sky.png"));
			
			tiles[2] = new Tile(gp.getTileSize(), gp.getTileSize());
			tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/images/cloud.png"));
			
			tiles[3] = new Tile(gp.getTileSize(), gp.getTileSize());
			tiles[3].image = ImageIO.read(getClass().getResourceAsStream("/images/desert.png"));
			
			tiles[4] = new Tile(gp.getTileSize(), gp.getTileSize());
			tiles[4].image = ImageIO.read(getClass().getResourceAsStream("/images/flower.png"));
			

		} catch (IOException e) {
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
			x += gp.getTileSize();

			if (col == gp.getMaxScreenCol()) {
				col = 0;
				x = 0;
				row++;
				y += gp.getTileSize();
			}
		}

	}
}
