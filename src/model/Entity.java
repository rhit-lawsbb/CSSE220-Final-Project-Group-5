package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Class: Entity 
 * @author Group 5
 * <br>Purpose:
 * <br>Restrictions:
 * <br>For Example:
 * <pre>
 * 		
 * </pre>
 */

public abstract class Entity {
	 private double x, y;
	 private Maze maze;
	 private BufferedImage spriteRight;
	 private BufferedImage spriteLeft;

	 public static final int STEP = 6;
	 private boolean facingRight = true;

	 //Base for all Zombie and Player
	 public Entity(int row, int col, Maze maze) {
		 this.x = col * Maze.TILE_SIZE;
		 this.y = row * Maze.TILE_SIZE;
		 this.maze = maze;
	 }


	 public boolean canMoveTo(double newX, double newY) {


		 int leftTile = (int) (newX / 46);
		 int rightTile = (int) ((newX + Maze.TILE_SIZE -3) / Maze.TILE_SIZE);
		 int topTile = (int) (newY / 46);
		 int bottomTile = (int) ((newY + Maze.TILE_SIZE -3) / Maze.TILE_SIZE);

		 if (maze.isWall(topTile, leftTile)) return false;
		 if (maze.isWall(topTile, rightTile)) return false;
		 if (maze.isWall(bottomTile, leftTile)) return false;
		 if (maze.isWall(bottomTile, rightTile)) return false;

		 return true;
	 }

	 public double getX() { return x; }
	 public double getY() { return y; }
	 public void setX(double x) { this.x = x; }
	 public void setY(double y) { this.y = y; }
	 public Maze getMaze() { return maze; }
	 public void setSpriteRight(BufferedImage img) { this.spriteRight = img; }
	 public void setSpriteLeft(BufferedImage img) { this.spriteLeft = img; }
	 public BufferedImage getSpriteRight() { return spriteRight; }
	 public BufferedImage getSpriteLeft() { return spriteLeft; }
	 public void setFacingRight(boolean facing) { this.facingRight = facing; }
	 public boolean isFacingRight() { return facingRight; }

	 public void draw(Graphics g) {
		 BufferedImage currentSprite = facingRight ? spriteRight : spriteLeft;
		 if (currentSprite != null) {
			 g.drawImage(currentSprite, (int) x, (int) y, Maze.TILE_SIZE, Maze.TILE_SIZE, null);
		 }else {
			 g.setColor(Color.RED);
			 g.fillRect((int) x, (int) y, Maze.TILE_SIZE, Maze.TILE_SIZE);
		 }
	 }
}
