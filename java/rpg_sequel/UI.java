package rpg_sequel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {

    GamePanel gp;
    Font roboto_25;
    Font roboto_70;
    Graphics2D g2;
    public boolean messageOn = false;
    public String message = "";
    public int messageCounter = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
        roboto_25 = new Font("Roboto", Font.PLAIN, 25);
        roboto_70 = new Font("Roboto", Font.PLAIN, 70);
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(roboto_25);
        g2.setColor(Color.white);

        if (gp.gameState == gp.playState) {
            // Add game UI elements (health bar, score, etc.) here
        }

        if (gp.gameState == gp.pauseState) {
            pauseScreen();
        }

        if (messageOn) {
            displayMessage();
        }
    }

    public void pauseScreen() {
        g2.setFont(roboto_70);
        String text = "PAUSED";
        int x = getXCenter(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }

    public int getXCenter(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }

    private void displayMessage() {
        g2.setFont(roboto_25);
        g2.setColor(Color.white);
        int x = gp.screenWidth / 2 - g2.getFontMetrics().stringWidth(message) / 2;
        int y = gp.screenHeight / 2;
        g2.drawString(message, x, y);
        
        messageCounter++;
        if (messageCounter > 100) {
            messageOn = false;
            messageCounter = 0;
        }
    }
}
