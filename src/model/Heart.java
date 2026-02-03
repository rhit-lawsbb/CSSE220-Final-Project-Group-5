package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Heart {
	private int x, y;
	private BufferedImage sprite1;
	private BufferedImage sprite2;
	private boolean active = true;
	private static final int FLIP_SPEED_MS = 600;
	
	public Heart(int col, int row) {
		this.x = col * 48;
		this.y = row *48;
		
		try {
			// Ensure these files exist in your src/res folder
			this.sprite1 = ImageIO.read(getClass().getResource("Health_region.png"));
			this.sprite2 = ImageIO.read(getClass().getResource("flipped_Health_region.png"));
		} catch (IOException | IllegalArgumentException e) {
			System.err.println("Heart image error: " + e.getMessage());
			this.sprite1 = this.sprite2 = null;
		}
	}
	
	public void draw(Graphics g) {
		 if (!active || sprite1 == null || sprite2 == null) return;

		    boolean showFirst = (System.currentTimeMillis() / FLIP_SPEED_MS) % 2 == 0;
		    BufferedImage currentSprite = showFirst ? sprite1 : sprite2;

		    // FORCED SCALING: Use 48, 48 to ensure it fits the tile regardless of file size
		    g.drawImage(currentSprite, x, y, 48, 48, null);
		}
	
	public boolean isActive() { return active; }
	public void setActive(boolean active) { this.active = active; }

}
