package entity;

import rpg_sequel.GamePanel;

public class doubleganger extends Entity{

    public doubleganger(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 2;
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
            int goalCol = 13;
            int goalRow = 40;
            searchPath(goalCol, goalRow);
        }
        else {
            
        }
    }
}
