package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Class: Collectables 
 * @author Group 5
 * <br>Purpose:
 * <br>Restrictions:
 * <br>For Example:
 * <pre>
 * 		
 * </pre>
 */

public class Collectables extends Pickup {
	private BufferedImage spriteTails;
	// Define image names as constants
	private static final String HEADS_IMAGE = "Gold_Collectable.png";
	private static final String TAILS_IMAGE = "Gold_Collectable_flipped.png";
	private int frameCount = 0; // Flips every 0.5 seconds

	public Collectables(int col, int row) {
		super(col, row);
		try {
			// Load both images upon creation
			setSprite(ImageIO.read(Collectables.class.getResource(HEADS_IMAGE)));
			this.spriteTails = ImageIO.read(Collectables.class.getResource(TAILS_IMAGE));
		} catch (IOException | IllegalArgumentException ex) {
			// Handle error if images are missing (e.g. print error, use fallback shape)
			System.out.println("Error loading coin images: " + ex.getMessage());
			setSprite(null);
			this.spriteTails = null;
		}
	}

	@Override
	public void draw(Graphics g) {
		if (getSprite() == null || spriteTails == null) return;

		// Use system time and modulo to flip every 500ms
		// This results in a value of 0 or 1, toggling every 0.5 seconds
		frameCount++;
		boolean showHeads = (frameCount / 5) % 2 == 0;

		BufferedImage currentSprite = showHeads ? getSprite() : spriteTails;

		g.drawImage(currentSprite, getX(), getY(), Maze.TILE_SIZE, Maze.TILE_SIZE, null);
	}
}
