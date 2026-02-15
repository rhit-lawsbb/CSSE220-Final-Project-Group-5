package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

// test

public class Armor {
	private int x, y;
	private boolean active = true;
	private BufferedImage sprite;

	public Armor(int col, int row) {
		this.x = col * 48;
		this.y = row * 48;
		try {
			this.sprite = ImageIO.read(getClass().getResource("armor.png"));
		} catch (IOException | IllegalArgumentException e) {
			this.sprite = null;
		}
	}

	public void draw(Graphics g) {
		if (!active) return;
		if (sprite != null) {
			g.drawImage(sprite, x, y, 48, 48, null);
		}
	}

	public int getX() { return x; }
	public int getY() { return y; }
	public boolean isActive() { return active; }
	public void setActive(boolean active) { this.active = active; }
}
