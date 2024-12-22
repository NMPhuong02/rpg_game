package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import rpg_sequel.KeyHandler;
import rpg_sequel.UtilityTool;
import rpg_sequel.GamePanel;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public int hasKey = 0;
    public int standCounter = 0;
    boolean moving = false;
    int pixelCounter = 0;

    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 1;
        solidArea.y = 1;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 46;
        solidArea.height = 46;

        setDefaultValue();
        getPlayerImage();
    }

    public void setDefaultValue() {

        worldX = gp.tileSize * 30;
        worldY = gp.tileSize * 7;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {

        up0 = setup("Walking sprites/l0_sprite_4");
        up1 = setup("Walking sprites/l0_sprite_5");
        up2 = setup("Walking sprites/l0_sprite_6");
        down0 = setup("Walking sprites/l0_sprite_1");
        down1 = setup("Walking sprites/l0_sprite_2");
        down2 = setup("Walking sprites/l0_sprite_3");
        left0 = setup("Walking sprites/l0_sprite_7");
        left1 = setup("Walking sprites/l0_sprite_8");
        left2 = setup("Walking sprites/l0_sprite_9");
        right0 = setup("Walking sprites/l0_sprite_10");
        right1 = setup("Walking sprites/l0_sprite_11");
        right2 = setup("Walking sprites/l0_sprite_12");
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

    public void update() {

        if (moving == false) {

            if(keyH.upPressed == true || keyH.downPressed == true || 
            keyH.leftPressed == true || keyH.rightPressed == true) {
            
                if(keyH.upPressed == true) {
                    direction = "up";
                }
                if (keyH.downPressed == true) {
                    direction = "down";
                }
                if (keyH.leftPressed == true) {
                    direction = "left";
                }
                if (keyH.rightPressed == true) {
                    direction = "right";
                }
                if (keyH.run == true) {
                    speed = 4;
                }
                else if (keyH.run == false) {
                    speed = 4;
                }

                moving = true;
                
                //Check tiles collision
                collisionOn = false;
                gp.cCheck.checkTile(this);
                
                //Check object collision
                int  ObjIndex = gp.cCheck.checkObject(this, true);
                touchTheForbiddenPear(ObjIndex);
            }
            else {

                standCounter++;
                
                if (standCounter == 20) {
                    
                    spritesNum = 1;
                    standCounter = 0;
                }
            }
        } 
        else {
            
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

            pixelCounter += speed;

            if (pixelCounter == 48) {
                moving = false;
                pixelCounter = 0;
            }
        }  
    }

    public void touchTheForbiddenPear(int i) {
        if (i != 999) {

            String objectName = gp.obj[i].name;
            
            switch(objectName) {
                case "chest":
                    gp.ui.showMessage("CONGRATULATION!!!!!");
                    break;
            }
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        
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