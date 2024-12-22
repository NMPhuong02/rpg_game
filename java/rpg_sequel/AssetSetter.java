package rpg_sequel;

import Object.Chest;
import entity.doubleganger;

public class AssetSetter {
    GamePanel gp;
    
    public AssetSetter (GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[1] = new Chest(gp);
        gp.obj[1].worldX = 40 * gp.tileSize;
        gp.obj[1].worldY = 12 * gp.tileSize;
    }

    public void setNPC() {

        gp.npc[0]= new doubleganger(gp);
        gp.npc[0].worldX = 30 * gp.tileSize;
        gp.npc[0].worldY = 6 * gp.tileSize;
    }
}
