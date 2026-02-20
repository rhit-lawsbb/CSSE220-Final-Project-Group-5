package model;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Class: Bullet
 * @author Group 5
 * <br>Purpose: Represents a projectile fired from the player.
 * handles the bullets movement and when it collides a wall
 * <br>Restrictions: Once fired, the bullet is deactivated when collision with wall is detected.
 * <br>For example:
 * <pre>
 * 		Bullet bullet = new Bullet(x,y,true,maze);
 * </pre>
 */
public class Bullet {
    private double x, y;
    private Direction direction;
    private boolean active = true;
    private Maze maze;
    private static final int SPEED = 12;
    private static final int SIZE = 10;
    
    /**
     * ensures: creates a bullet at a specified coordinate location on the map, 
     * decides direction based on facingRight boolean param,
     * declares bullet as active
     * @param startX starting x coordinate of the bullet
     * @param startY starting y coordinate of the bullet
     * @param facingRight true if the bullet should be moving right, false if left
     * @param maze the maze of the level, needed to detect walls
     */
    public Bullet(double startX, double startY, boolean facingRight, Maze maze) {
        this.maze = maze;
        this.x = startX + 20; // spawn near player's center
        this.y = startY + 20;
        if (facingRight) {
            direction = Direction.RIGHT;
        } else {
            direction = Direction.LEFT;
        }
    }
    
    /**
     * ensures: moves the bullet in the specified direction.
     * if bullet hits a wall, then it becomes inactive
     * if inactive, no bullet movement occurs.
     */
    public void update() {
        if (!active) return;
        double nextX = x + direction.getDx() * SPEED;
        double nextY = y + direction.getDy() * SPEED;
        int tileCol = (int)(nextX / Maze.TILE_SIZE);
        int tileRow = (int)(nextY / Maze.TILE_SIZE);
        if (maze.isWall(tileRow, tileCol)) {
            active = false;
            return;
        }
        x = nextX;
        y = nextY;
    }
    /**
     * ensures: if bullet is active, draws bullet on screen
     * if inactive, nothing is drawn
     * @param g graphics used for rendering
     * <br>requires: g &ne; null
     */
    public void draw(Graphics g) {
        if (!active) return;
        g.setColor(Color.YELLOW);
        g.fillOval((int) x, (int) y, SIZE, SIZE);
    }
    /**
     * ensures: returns if the bullet is active or not
     * @return true if bullet is active, false if inactive
     */
    public boolean isActive() { return active; }
    
    /**
     * ensures: sets bullet to inactive
     * once inactive, the bullet wont be drawn or move
     */
    public void deactivate() { active = false; }
    
    /**
     * returns the x coordinate of the bullet
     * @return x coordinate of bullet
     */
    public double getX() { return x; }
    
    /**
     * returns the y coordinate of the bullet
     * @return y coordinate of the bullet
     */
    public double getY() { return y; }
}
