package model;

import java.awt.Color;
import java.awt.Graphics;
public class Bullet {
    private float x, y;
    private int dx;
    private int dy;
    private boolean active = true;
    private Maze maze;
    private static final int SPEED = 12;
    private static final int SIZE = 10;
    public Bullet(float startX, float startY, boolean facingRight, Maze maze) {
        this.maze = maze;
        this.x = startX + 20; // spawn near player’s center
        this.y = startY + 20;
        if (facingRight) {
            dx = 1; dy = 0;
        } else {
            dx = -1; dy = 0;
        }
    }
    public void update() {
        if (!active) return;
        float nextX = x + dx * SPEED;
        float nextY = y + dy * SPEED;
        int tileCol = (int)(nextX / 48);
        int tileRow = (int)(nextY / 48);
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
        g.fillOval(Math.round(x), Math.round(y), SIZE, SIZE);
    }
    public boolean isActive() { return active; }
    public void deactivate() { active = false; }
    public float getX() { return x; }
    public float getY() { return y; }
}