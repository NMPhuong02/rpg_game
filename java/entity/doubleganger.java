package entity;

import java.util.Random;

import rpg_sequel.GamePanel;

public class doubleganger extends Entity{

    public doubleganger(GamePanel gp) {

        super(gp);
        direction = "down";
        speed = 3;

        getImage();
    }

        public void getImage() {

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

    public void setAction() {
        if(onPath == true) {
            // int goalCol = 13;
            // int goalRow = 40;
            int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize; 
            searchPath(goalCol, goalRow);
        }
        else {
            actionLockCounter ++;
            if (actionLockCounter == 120) {
                
                Random random = new Random();
                int i = random.nextInt(100) + 1;
                if (i <= 25){
                    direction = "up";
                }
                if (i > 25 && i <= 50) {
                    direction = "down";
                }
                if (i > 50 && i <= 75) {
                    direction = "left";
                }
                if (i > 75 && i <= 100) {
                    direction = "right";
                }
            }
            actionLockCounter = 0;
        }
    }
}
