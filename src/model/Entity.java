package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Class: Entity 
 * @author Group 5
 * <br>Purpose: Abstract base class for all game entities that can move, including player and zombie.
 * Stores position, direction the sprites should face, the sprites, movement logic
 * <br>Restrictions: doesnt handle game logic or collision logic.
 * subclasses have to define behaviors to instantize entity
 * <br>For Example:
 * <pre>
 * 		Player p = new Player(row, col, maze);
 * 		Zombie z = new Zombie(row, col, maze);
 * </pre>
 */

public abstract class Entity {
	 private double x, y;
	 private Maze maze;
	 private BufferedImage spriteRight;
	 private BufferedImage spriteLeft;

	 public static final int STEP = 6;
	 private boolean facingRight = true;
	 
	 /**
	  * ensures: spawns the entity based on the tile location given, stores location
	  * @param row the row tile location on the maze where the entity spawns
	  * @param col the column tile location on the maze where the entity spawns
	  * @param maze the maze which the entity will spawn in
	  */
	 public Entity(int row, int col, Maze maze) {
		 this.x = col * Maze.TILE_SIZE;
		 this.y = row * Maze.TILE_SIZE;
		 this.maze = maze;
	 }

	 /**
	  * decides if an entity is allowed to move to a new location based on new coordinates
	  * checks to make sure there are no walls blocking the movement
	  * @param newX possible x-coordinate to move to
	  * @param newY possible y-coordinate to move to
	  * @return true if the movement is allowed, false if blocked by a wall
	  */
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

	 /**
	  * ensures: returns current x-coordinate of entity
	  * @return x position in pixels
	  */
	 public double getX() { return x; }
	 
	 /**
	  * ensures: returns current y-coordinate of entity
	  * @return y position in pixels
	  */
	 public double getY() { return y; }
	 
	 /**
	  * ensures: sets x-coordinate of entity
	  * @param x new x position in pixels
	  */
	 public void setX(double x) { this.x = x; }
	 
	 /**
	  * ensures: sets y-coordinate of entity
	  * @param y new y position in pixels
	  */
	 public void setY(double y) { this.y = y; }
	 
	 /**
	  * ensures: returns maze that the entity is in
	  * @return maze of the level
	  */
	 public Maze getMaze() { return maze; }
	 
	 /**
	  * ensures: sets right facing sprite when the entity is facing right
	  * @param img the right facing sprite image
	  */
	 public void setSpriteRight(BufferedImage img) { this.spriteRight = img; }
	 
	 /**
	  * ensures: sets left facing sprite when the entity is facing left
	  * @param img the left facing sprite image
	  */
	 public void setSpriteLeft(BufferedImage img) { this.spriteLeft = img; }
	 
	 /**
	  * ensures: returns right facing sprite
	  * @return the right facing sprite
	  */
	 public BufferedImage getSpriteRight() { return spriteRight; }
	 
	 /**
	  * ensures: returns left facing sprite
	  * @return the left facing sprite
	  */
	 public BufferedImage getSpriteLeft() { return spriteLeft; }
	 
	 /**
	  * sets true or false depending on if the entity is facing right or not
	  * @param facing if facing right, then it is true, if facing left it is false
	  */
	 public void setFacingRight(boolean facing) { this.facingRight = facing; }
	 
	 /**
	  * ensures: returns whether the entity is facing right or not
	  * @return if facing right, then its true, false if left
	  */
	 public boolean isFacingRight() { return facingRight; }

	 /**
	  * ensures: draws sprite of entity based on direction it is facing
	  * if a sprite is not set, then a rectange is drawn instead
	  * @param g graphics used for rendering
	  * <br>requires: g &ne; null
	  */
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
