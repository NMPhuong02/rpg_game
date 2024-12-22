package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import rpg_sequel.GamePanel;
import rpg_sequel.UtilityTool;

public class Entity {

    GamePanel gp;
    public int worldX, worldY;
    public int speed;
    public BufferedImage up0, up1, up2, down0, down1, down2, left0, left1, left2, right0, right1, right2;
    public String direction;
    public int spritesCounter = 0;
    public int spritesNum = 1;
    public Rectangle solidArea = new Rectangle(0, 0, 46, 46);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    public boolean onPath = true;
    public int actionLockCounter = 0;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {

    }
    public void checkCollision() {
        collisionOn = false;
        gp.cCheck.checkTile(this);
    }

    public void update() {

        setAction();
        checkCollision();
        if(collisionOn == false) {
                    
            switch (direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }

        spritesCounter++;
        if(spritesCounter > 12) {
            if(spritesNum == 1) {
                spritesNum = 2;
            }
            else if(spritesNum == 2) {
                spritesNum = 3;
            }
            else if(spritesNum == 3) {
                spritesNum = 1;
            }
            spritesCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX > gp.player.worldX - gp.player.screenX - gp.tileSize && 
            worldX < gp.player.worldX + gp.player.screenX + gp.tileSize &&
            worldY > gp.player.worldY - gp.player.screenY - gp.tileSize &&
            worldY < gp.player.worldY + gp.player.screenY + gp.tileSize) {        

                switch (direction) {

                    case "up":
                        if(spritesNum == 1) {
                            image = up0;
                        }
                        if(spritesNum == 2) {
                            image = up1;
                        }
                        if(spritesNum == 3) {
                            image = up2;
                        }
                        break;
                    
                    case "down":
                        if(spritesNum == 1) {
                            image = down0;
                        }
                        if(spritesNum == 2) {
                            image = down1;
                        }
                        if(spritesNum == 3) {
                            image = down2;
                        }
                        break;
                    
                    case "left":
                        if(spritesNum == 1) {
                            image = left0;
                        }
                        if(spritesNum == 2) {
                            image = left1;
                        }
                        if(spritesNum == 3) {
                            image = left2;
                        }
                        break;
                    
                    case "right":
                        if(spritesNum == 1) {
                            image = right0;
                        }
                        if(spritesNum == 2) {
                            image = right1;
                        }
                        if(spritesNum == 3) {
                            image = right2;
                        }
                        break;
                }

            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    public BufferedImage setup(String name) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        
        try {
            
            image = ImageIO.read(getClass().getResource("/Player/" + name + ".png"));
            image = uTool.scaledImage(image, gp.tileSize, gp.tileSize);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public void searchPath(int goalCol, int goalRow){
        
        int startCol = (worldX + solidArea.x)/gp.tileSize;
        int startRow = (worldY + solidArea.y)/gp.tileSize;

        gp.pFinder.setNode(startCol, startRow, goalCol, goalRow);

        if(gp.pFinder.search() == true) {

            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x +solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            }
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            }
            else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize) {

                if(enLeftX > nextX) {
                    direction = "left";
                }
                if(enLeftX < nextX) {
                    direction = "right";
                }
            }
            else if(enTopY > nextY && enLeftX > nextX) {
                direction = "up";
                checkCollision();
                if(collisionOn == true) {
                    direction = "left";
                }
            }
            else if(enTopY > nextY && enLeftX < nextX) {
                direction = "up";
                checkCollision();
                if(collisionOn == true) {
                    direction = "right";
                }
            }
            else if(enTopY < nextY && enLeftX > nextX) {
                direction = "down";
                checkCollision();
                if(collisionOn == true) {
                    direction = "left";
                }
            }
            else if(enTopY < nextY && enLeftX < nextX) {
                direction = "down";
                checkCollision();
                if(collisionOn == true) {
                    direction = "right";
                }
            }

            int nextCol = gp.pFinder.pathList.get(0).col;
            int nextRow = gp.pFinder.pathList.get(0).row;
            if(nextCol == goalCol && nextRow == goalRow) {
                onPath = false;
            }
        }
    }
}