package rpg_sequel;

import Object.Chest;

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
}
