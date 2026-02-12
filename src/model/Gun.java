package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Gun {
	private int x, y;
    private boolean active = true;
    private BufferedImage sprite;
    private static final int TILE_SIZE = 48;
    public Gun(int col, int row) {
        this.x = col * TILE_SIZE;
        this.y = row * TILE_SIZE;
        try {
            sprite = ImageIO.read(getClass().getResource("Gun.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Gun image error: " + e.getMessage());
            sprite = null;
        }
    }
    public void draw(Graphics g) {
        if (!active || sprite == null) return;
        g.drawImage(sprite, x, y, TILE_SIZE, TILE_SIZE, null);
    }
    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isActive() {
    	return active; 
    	}
    
    public void setActive(boolean active) { 
    	this.active = active; 
    	}
    
    public boolean collidesWith(Player player) {
        return Math.abs(player.getX() - x) < TILE_SIZE / 2 &&
               Math.abs(player.getY() - y) < TILE_SIZE / 2;
    }
}
