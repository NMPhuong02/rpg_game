package Object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import rpg_sequel.GamePanel;
import rpg_sequel.Positioned;
import rpg_sequel.UtilityTool;

public class SuperObject implements Positioned{

    public UtilityTool uTool = new UtilityTool();
    public BufferedImage image;
    public int worldX, worldY;
    public String name;
    //Object Collision
    public boolean collision = false;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    public void draw(Graphics2D g2, GamePanel gp) {

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX > gp.player.worldX - gp.player.screenX - gp.tileSize && 
            worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
            worldY > gp.player.worldY - gp.player.screenY - gp.tileSize &&
            worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {    
                    
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
    
    @Override
    public Rectangle getSolidArea() {
        return solidArea;
    }

    @Override
    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }

    @Override
    public int getSolidAreaDefaultY() {
        return solidAreaDefaultY;
    }

    @Override
    public int getWorldX() {
        return worldX;
    }

    @Override
    public int getWorldY() {
        return worldY;
    }
}
