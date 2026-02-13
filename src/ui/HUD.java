package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.GameModel;

public class HUD {
    private GameModel model;
    private BufferedImage heartSprite;

    public HUD(GameModel model) {
        this.model = model;
        try {
            this.heartSprite = ImageIO.read(getClass().getResource("Health_region.png"));
        } catch (IOException | IllegalArgumentException e) {
            this.heartSprite = null;
        }
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, 480, 45); 

        if (heartSprite != null) {
            for (int i = 0; i < model.getLives(); i++) {
                g.drawImage(heartSprite, 10 + (i * 45), 7, 35, 35, null);
            }
        }
        
        
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(Color.YELLOW);
        g.drawString("Score: " + model.getScore(), 400, 30);
        
        if (model.getPlayer().hasGun()) {
            g.setColor(Color.WHITE);
            g.drawString("Ammo: " + model.getPlayer().getAmmo(), 220, 30);
        }
     // 4. Draw Goal Progress (Gold: Current/Required)
        g.setColor(Color.YELLOW);
        // This combines your score with the required constant from the model
        String goalText = "Gold: " + model.getScore() + " / " + model.getCoinsRequired();
        g.drawString(goalText, 310, 30);
    }
}
