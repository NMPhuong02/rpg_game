package rpg_sequel;

import entity.Entity;

public class CollisionChecker {
    
    GamePanel gp;
    
    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void updateTilesCollision(Entity entity, int col1, int row1, int col2, int row2) {
        int tileNum1, tileNum2; 
        tileNum1 = gp.tilesM.mapTilesNum[col1][row1];
        tileNum2 = gp.tilesM.mapTilesNum[col2][row2];
        if(gp.tilesM.tile[tileNum1].collision == true || gp.tilesM.tile[tileNum2].collision == true) {
            entity.collisionOn = true;
        }
    }

    public void checkTile(Entity entity){

        if (gp.tilesM != null) {
            
            int entityLeftWorldX = entity.worldX + entity.solidArea.x;
            int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
            int entityTopWorldY = entity.worldY + entity.solidArea.y;
            int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
            
            int entityLeftCol = entityLeftWorldX/gp.tileSize;
            int entityRightCol = entityRightWorldX/gp.tileSize;
            int entityTopRow = entityTopWorldY/gp.tileSize;
            int entityBottomRow = entityBottomWorldY/gp.tileSize;
            
            switch(entity.direction) {
                case "up":
                    entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                    updateTilesCollision(entity, entityLeftCol, entityTopRow, entityRightCol, entityTopRow);
                    break;
                
                case "down":
                    entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                    updateTilesCollision(entity, entityLeftCol, entityBottomRow, entityRightCol, entityBottomRow);
                    break;
                
                case "left":
                    entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                    updateTilesCollision(entity, entityLeftCol, entityTopRow, entityLeftCol, entityBottomRow);
                    break;
                
                case "right":
                    entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                    updateTilesCollision(entity, entityRightCol, entityTopRow, entityRightCol, entityBottomRow);
                    break;
                }
        }
    }

    public void takePosition(Entity entity, Positioned[] objects, int i) {

        //Entity's solid position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;
        //Object's solid position
        objects[i].getSolidArea().x = objects[i].getWorldX() + objects[i].getSolidArea().x;
        objects[i].getSolidArea().y = objects[i].getWorldY() + objects[i].getSolidArea().y;
    }

    public void resetPosition(Entity entity, Positioned[] objects, int i) {

        //Entity's solid position
        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        //Object's solid position
        objects[i].getSolidArea().x = objects[i].getSolidAreaDefaultX();
        objects[i].getSolidArea().y = objects[i].getSolidAreaDefaultY();
    }

    //Update Collision status
    public void updateCollision(Entity entity, int i, int index,  boolean player) {
        if (entity.solidArea.intersects(gp.obj[i].getSolidArea())) {
            if(gp.obj[i].collision == true) {
                entity.collisionOn = true;
            }
            if (player == true) {
                index = i;
            }
            System.out.println("DONE!");
        }
    }
    public void updateCollision(Entity entity, int i, int index, Positioned[] object) {
        if (entity.solidArea.intersects(object[i].getSolidArea())) {
            entity.collisionOn = true;
            index = i;
        }
    }

    //Collision based for object and entity
    public int checkCollision(Entity entity, Positioned[] object, boolean isObject, boolean player) {
        
        int index = 999;

        for (int i = 0; i< object.length; i++) {
            
            if (object[i] != null) {
                
                takePosition(entity, object, i);

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }

                if (isObject == true) {

                    updateCollision(entity, i, index, player);
                }
                else {

                    updateCollision(entity, i, index, object);
                }

                resetPosition(entity, object, i);
            }
        }
        return index;
    }

    //Object collision
    public int checkObject(Entity entity, boolean player) {

        Positioned[] object = gp.obj;
        int index = checkCollision(entity, object, true, player);
        return index;
    }

    //NPC collision
    public int checkEntity(Entity entity, Entity[] target) {

        Positioned[] object = target;
        int index = checkCollision(entity, object, false, false);
        return index;
    }
}
