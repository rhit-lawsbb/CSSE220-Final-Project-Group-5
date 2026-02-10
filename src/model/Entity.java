package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Entity {
	 protected float x, y;
	 protected Maze maze;
	 protected BufferedImage spriteRight;
	 protected BufferedImage spriteLeft;
	 
	 protected static final int STEP = 6;
	 protected boolean facingRight = true;
	 
	 //Base for all Zombie and Player
	 public Entity(int row, int col, Maze maze) {
		 this.x = col * 48;
		 this.y = row * 48;
		 this.maze = maze;
	 }
	 
	 
	 protected boolean canMoveTo(float newX, float newY) {
		 
		 
		 int leftTile = (int) (newX / 46);
		 int rightTile = (int) ((newX + 48 -3) / 48);
		 int topTile = (int) (newY / 46);
		 int bottomTile = (int) ((newY + 48 -3) / 48);
		 
		 if (maze.isWall(topTile, leftTile)) return false;
		 if (maze.isWall(topTile, rightTile)) return false;
		 if (maze.isWall(bottomTile, leftTile)) return false;
		 if (maze.isWall(bottomTile, rightTile)) return false;
		 
		 return true;
	 }
	 
	 public float getX() { return x; }
	 public float getY() { return y; }

	 public void draw(Graphics g) {
		 BufferedImage currentSprite = facingRight ? spriteRight : spriteLeft;
		 if (currentSprite != null) {
			 g.drawImage(currentSprite, Math.round(x), Math.round(y), 48, 48, null);
		 }else {
			 g.setColor(Color.RED);
			 g.fillRect(Math.round(x), Math.round(y), 48, 48);
		 }
	 }
}
