package entity;

import java.awt.Graphics2D;
import rpg_sequel.KeyHandler;
import rpg_sequel.GamePanel;

public class Player extends Entity {

    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {

        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

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

                moving = true;
                
                checkCollision();
                
                //Check object collision
                int ObjIndex = gp.cCheck.checkObject(this, true);
                touchTheForbiddenPear(ObjIndex);

                //Check npc collision
                int npcIndex = gp.cCheck.checkEntity(this, gp.npc);
                interactNPC(npcIndex);
            }
            else {

                standCounter++;
                
                if (standCounter == 20) {
                    
                    spritesNum = 1;
                    standCounter = 0;
                }
            }
        } 
        if (moving == true) {
            movement();
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

    public void interactNPC(int i) {

        if (i != 999) {
            System.out.println("Touch");
        }
    }

    public void draw(Graphics2D g2) {

        entity_animation(g2);
    }
}