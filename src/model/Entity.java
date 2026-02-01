package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Entity {
	 protected float x, y;
	 protected Maze maze;
	 protected BufferedImage sprite;
	 protected static final int STEP = 6;
	 
	 
	 public Entity(int row, int col, Maze maze) {
		 this.x = col * 48;
		 this.y = row * 48;
		 this.maze = maze;
	 }
	 
	 protected boolean canMoveTo(float newX, float newY) {
		 int leftTile = (int) (newX/ 48);
		 int rightTile = (int) ((newX + 48 -1) / 48);
		 int topTile = (int) (newY / 48);
		 int bottomTile = (int) ((newY + 48 -1) / 48);
		 
		 if (maze.isWall(topTile, leftTile)) return false;
		 if (maze.isWall(topTile, rightTile)) return false;
		 if (maze.isWall(bottomTile, leftTile)) return false;
		 if (maze.isWall(bottomTile, rightTile)) return false;
		 
		 return true;
	 }
	 
	 public void draw(Graphics g) {
		 if (sprite != null) {
			 g.drawImage(sprite, Math.round(x), Math.round(y), 48, 48, null);
		 }else {
			 g.setColor(Color.RED);
			 g.fillRect(Math.round(x), Math.round(y), 48, 48);
		 }
	 }
}
