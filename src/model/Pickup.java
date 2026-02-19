package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Pickup {
	private int x, y;
	private boolean active;
	private BufferedImage sprite;

	public Pickup(int col, int row) {
		this.x = col * Maze.TILE_SIZE;
		this.y = row * Maze.TILE_SIZE;
		this.active = true;
	}

	public void draw(Graphics g) {
		if (!active || sprite == null) return;
		g.drawImage(sprite, x, y, Maze.TILE_SIZE, Maze.TILE_SIZE, null);
	}

	public int getX() { return x; }
	public int getY() { return y; }
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public boolean isActive() { return active; }
	public void setActive(boolean active) { this.active = active; }
	public BufferedImage getSprite() { return sprite; }
	public void setSprite(BufferedImage sprite) { this.sprite = sprite; }
}
