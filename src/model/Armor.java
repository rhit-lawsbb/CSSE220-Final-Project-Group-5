package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Class: Armor
 * @author Group 5
 * <br>Purpose: Represents armor pickup that can reduce incoming damage from zombie collision with the player. 
 * <br>Restrictions: No collision logic is contained in this class, as it is handled in CollisionHandler. 
 * Only handles the armor in visual and spatial ways.
 * <br>For example:
 * <pre>
 * 		Armor armor = new Armor(1,2);
 * </pre>
 */

public class Armor extends Pickup {

	/**
	 * ensures: creates an armor pick up on a specified location on the map
	 * and attempts to load the sprite.
	 * If the sprite load fails, the sprite is set to null.
	 * @param col column location on the map of the armor pickup
	 * @param row row location on the map of the armor pickup
	 */
	public Armor(int col, int row) {
		super(col, row);
		try {
			setSprite(ImageIO.read(getClass().getResource("armor.png")));
		} catch (IOException | IllegalArgumentException e) {
			setSprite(null);
		}
	}
}
