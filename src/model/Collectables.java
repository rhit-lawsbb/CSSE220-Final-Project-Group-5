package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Collectables {
	private int x, y;
	private BufferedImage spriteHeads;
	private BufferedImage spriteTails;
	// Define image names as constants
	private static final String HEADS_IMAGE = "Gold_Collectable.png";
	private static final String TAILS_IMAGE = "Gold_Collectable_flipped.png";
	private static final int FLIP_SPEED_MS = 650; // Flips every 0.5 seconds
	public int getX() { return x; }
	public int getY() { return y; }
	public Collectables(int col, int row) {
		this.x = col * 48;
		this.y = row * 48;
		try {
			// Load both images upon creation
			this.spriteHeads = ImageIO.read(Collectables.class.getResource(HEADS_IMAGE));
			this.spriteTails = ImageIO.read(Collectables.class.getResource(TAILS_IMAGE));
		} catch (IOException | IllegalArgumentException ex) {
			// Handle error if images are missing (e.g. print error, use fallback shape)
			System.err.println("Error loading coin images: " + ex.getMessage());
			this.spriteHeads = this.spriteTails = null; 
		}
	}
	
	public void draw(Graphics g) {
		if (spriteHeads == null || spriteTails == null) return;

		// Use system time and modulo to flip every 500ms
		// This results in a value of 0 or 1, toggling every 0.5 seconds
		boolean showHeads = (System.currentTimeMillis() / FLIP_SPEED_MS) % 2 == 0;

		BufferedImage currentSprite = showHeads ? spriteHeads : spriteTails;
		
		g.drawImage(currentSprite, x, y, 48, 48, null);
	}
}
