package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

// test

public class Armor extends Pickup {

	public Armor(int col, int row) {
		super(col, row);
		try {
			setSprite(ImageIO.read(getClass().getResource("armor.png")));
		} catch (IOException | IllegalArgumentException e) {
			setSprite(null);
		}
	}
}
