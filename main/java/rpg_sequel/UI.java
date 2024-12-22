package rpg_sequel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gp;
    Font roboto_25;
    BufferedImage heartImage;
    public boolean messageOn = false;
    public String message = "";
    public int  messageCounter = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
        roboto_25 = new Font("Roboto", Font.PLAIN, 25);
    }

    public void showMessage(String text) {

        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2) {

        g2.setFont(roboto_25);
        g2.setColor(Color.white);

        //Message
        if (messageOn == true) {

            g2.drawString(message, 30, gp.screenHeight/2);
            messageCounter++;

            if (messageCounter == 120) {
                messageCounter = 0;
                messageOn = false;
            }
        }
    }
}
