package Object;

import javax.imageio.ImageIO;

import rpg_sequel.GamePanel;

public class Shine extends SuperObject{
    
    GamePanel gp;

    public Shine(GamePanel gp) {

        this.gp = gp;
        name = "shine";
        try {
            
            image = ImageIO.read(getClass().getResourceAsStream("/Object/shine.gif"));
            uTool.scaledImage(image, gp.tileSize, gp.tileSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
