package rpg_sequel;

import Object.Chest;
import entity.doubleganger;

public class AssetSetter {
    GamePanel gp;
    
    public AssetSetter (GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        if (gp.obj != null && gp.obj.length > 1) {
            gp.obj[1] = new Chest(gp);
            gp.obj[1].worldX = 40 * gp.tileSize;
            gp.obj[1].worldY = 13 * gp.tileSize;
        } else {
            System.out.println("Error: gp.obj array not initialized or too small.");
        }
    }

    public void setNPC() {
        if (gp.npc != null && gp.npc.length > 0) {
            gp.npc[0] = new doubleganger(gp);
            gp.npc[0].worldX = 30 * gp.tileSize;
            gp.npc[0].worldY = 14 * gp.tileSize;
        } else {
            System.out.println("Error: gp.npc array not initialized or too small.");
        }
    }
}
