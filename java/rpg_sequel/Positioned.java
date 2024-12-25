package rpg_sequel;

import java.awt.Rectangle;

public interface  Positioned {

    Rectangle getSolidArea();
    int getSolidAreaDefaultX();
    int getSolidAreaDefaultY();
    int getWorldX();
    int getWorldY();
}
