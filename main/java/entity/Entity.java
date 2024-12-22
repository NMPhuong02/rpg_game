package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import rpg_sequel.GamePanel;

public class Entity {

    GamePanel gp;
    public int worldX, worldY;
    public int speed;
    public BufferedImage up0, up1, up2, down0, down1, down2, left0, left1, left2, right0, right1, right2;
    public String direction;
    public int spritesCounter = 0;
    public int spritesNum = 1;
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }
}