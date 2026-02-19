package model;

import java.awt.Color;
import java.awt.Graphics;
public class Bullet {
    private double x, y;
    private Direction direction;
    private boolean active = true;
    private Maze maze;
    private static final int SPEED = 12;
    private static final int SIZE = 10;
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
    public void draw(Graphics g) {
        if (!active) return;
        g.setColor(Color.YELLOW);
        g.fillOval((int) x, (int) y, SIZE, SIZE);
    }
    public boolean isActive() { return active; }
    public void deactivate() { active = false; }
    public double getX() { return x; }
    public double getY() { return y; }
}
