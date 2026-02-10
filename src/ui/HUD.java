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
    private BufferedImage sprite1;
    private BufferedImage sprite2;
    private static final int FLIP_SPEED_MS = 1500;

    public HUD(GameModel model) {
        this.model = model;
        
        try {
            // Load the same sprites used in the Heart class
            this.sprite1 = ImageIO.read(getClass().getResource("Health_region.png"));
            this.sprite2 = ImageIO.read(getClass().getResource("flipped_Health_region.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("HUD Heart image error: " + e.getMessage());
        }
    }

    public void draw(Graphics g) {
        // 1. Draw the background bar
    	 g.setColor(new Color(0, 0, 0, 150));
    	    g.fillRect(0, 0, 480, 45); 

    	    if (sprite1 != null && sprite2 != null) {
    	        boolean showFirst = (System.currentTimeMillis() / FLIP_SPEED_MS) % 2 == 0;
    	        BufferedImage currentSprite = showFirst ? sprite1 : sprite2;

    	        for (int i = 0; i < model.getLives(); i++) {
    	            // Increased size to 40x40 (Width, Height)
    	            // Increased spacing to 45 (i * 45) to prevent overlap
    	            g.drawImage(currentSprite, 10 + (i * 45), 7, 40, 40, null);
    	        }
    	    }

    	    // 2. Adjusted Score position to match the new bar height
    	    g.setFont(new Font("Arial", Font.BOLD, 16));
    	    g.setColor(Color.YELLOW);
    	    g.drawString("Score: " + model.getScore(), 380, 35);
    	}
}
