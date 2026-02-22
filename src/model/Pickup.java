package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Class: Pickup 
 * @author Group 5
 * <br>Purpose: represents item that can be collected by player
 * <br>Restrictions: abstract class, cant be instantiated
 * <br>For Example:
 * <pre>
 * 		Gun gun = new Gun(1,2);
 * 		Heart heart = new Heart(1,2);
 * </pre>
 */

public abstract class Pickup {
	private int x, y;
	private boolean active;
	private BufferedImage sprite;

	/**
	 * ensures: create pickup at location, make it active
	 * @param col column location
	 * @param row row location
	 */
	public Pickup(int col, int row) {
		this.x = col * Maze.TILE_SIZE;
		this.y = row * Maze.TILE_SIZE;
		this.active = true;
	}

	/**
	 * ensures: draws pickup if active
	 * @param g graphics component
	 */
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
