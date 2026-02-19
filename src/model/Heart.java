package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Heart extends Pickup {
	private BufferedImage sprite2;
	private int frameCount = 0;

	public Heart(int col, int row) {
		super(col, row);

		try {
			// Ensure these files exist in your src/res folder
			setSprite(ImageIO.read(getClass().getResource("Health_region.png")));
			this.sprite2 = ImageIO.read(getClass().getResource("flipped_Health_region.png"));
		} catch (IOException | IllegalArgumentException e) {
			System.out.println("Heart image error: " + e.getMessage());
			setSprite(null);
			this.sprite2 = null;
		}
	}

	@Override
	public void draw(Graphics g) {
		 if (!isActive() || getSprite() == null || sprite2 == null) return;

		    frameCount++;
		    boolean showFirst = (frameCount / 5) % 2 == 0;
		    BufferedImage currentSprite = showFirst ? getSprite() : sprite2;

		    // FORCED SCALING: Use 48, 48 to ensure it fits the tile regardless of file size
		    g.drawImage(currentSprite, getX(), getY(), Maze.TILE_SIZE, Maze.TILE_SIZE, null);
		}
}
