package Object;

import javax.imageio.ImageIO;

import rpg_sequel.GamePanel;

public class Chest extends SuperObject{

    GamePanel gp;
    
    public Chest(GamePanel gp) {

        this.gp = gp;
        name = "chest";
        try {
            
            image = ImageIO.read(getClass().getResourceAsStream("/Object/chest.png"));
            uTool.scaledImage(image, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
