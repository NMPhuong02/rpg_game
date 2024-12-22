package background;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import rpg_sequel.GamePanel;
import rpg_sequel.UtilityTool;

public class TilesManager {

    GamePanel gp;
    public Tiles[] tile;
    public int mapTilesNum[][];
    boolean drawPath = true;

    public TilesManager(GamePanel gp) {

        this.gp = gp;
        tile = new Tiles[10];
        mapTilesNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/Map/world01.txt");
    }

    public void getTileImage() {

        setup(0, "grass00", false);
        setup(1, "wall", true);
        setup(2, "water01", true);
        setup(3, "earth", false);
        setup(4, "tree", true);
        setup(5, "road00", false);
    }

    public void setup(int index, String url, boolean collision) {

        UtilityTool uTool = new UtilityTool();
        try {
            
            tile[index] = new Tiles();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/Tiles/" + url + ".png"));
            tile[index].image = uTool.scaledImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {
        try {
            
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {

                String line = br.readLine();
                
                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTilesNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldRow) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTilesNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX > gp.player.worldX - gp.player.screenX - gp.tileSize && 
                worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
                worldY > gp.player.worldY - gp.player.screenY - gp.tileSize &&
                worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {       

                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
            }
                
            worldCol++;
            
            if(worldCol ==  gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
        if(drawPath == true) {
            g2.setColor(new Color(255,0,0,70));

            for(int i = 0; i < gp.pFinder.pathList.size(); i++) {
                int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
                int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
            }
        }
    }
}
