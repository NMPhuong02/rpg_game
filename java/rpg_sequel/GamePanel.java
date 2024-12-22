package rpg_sequel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import Object.SuperObject;
import background.TilesManager;
import entity.Entity;
import entity.Player;
import pathfinding.Pathfinder;

public class GamePanel extends JPanel implements Runnable {
    
    // Screen settings
    final int originalTileSize = 16; // 16x16 tiles
    final int scale = 3; 
    public final int tileSize = originalTileSize * scale; // 48x48 tiles
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
    // World setting
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    // FPS
    int fps = 60;

    // System
    public CollisionChecker cCheck = new CollisionChecker(this);
    public TilesManager tilesM = new TilesManager(this);
    public AssetSetter aSetter = new AssetSetter(this); 
    public KeyHandler keyH = new KeyHandler();
    public UI ui= new UI(this);
    public Thread gameThread;
    public Pathfinder pFinder = new Pathfinder(this);
    // Entity and Object
    public Player player = new Player(this, keyH);
    public Entity npc[] = new Entity[10];   
    public SuperObject obj[] = new SuperObject[10];

    public GamePanel()  {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {

        aSetter.setObject();
        aSetter.setNPC();
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {

            currentTime = System.nanoTime();
            timer += currentTime - lastTime;
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1) {

                // Update camera position
                update();
                // Draw the screen with the update information
                repaint();
                
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000) {

                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {

        player.update();
        for(int i = 0; i < npc.length; i++){

            if(npc[i] != null){

                npc[i].update();
            }
        }
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        //Tiles
        tilesM.draw(g2);

        //Object
        for(int i = 0; i < obj.length; i++) {
            if(obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }

        //NPD
        for(int i = 0; i <npc.length; i++){
            if(npc[i] != null) {
                npc[i].draw(g2);
            }
        }

        //Player
        player.draw(g2);
        ui.draw(g2);
        g2.dispose();
    }
}
