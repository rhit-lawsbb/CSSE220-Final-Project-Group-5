package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Entity {
	 protected int row, col;
	 protected Maze maze;
	 protected BufferedImage sprite;
	 
	 
	 public Entity(int row, int col, Maze maze) {
		 this.row = row;
		 this.col = col;
		 this.maze = maze;
	 }
	 
	 public void move(Direction dir) {
		 int newRow = row + dir.dy;
		 int newCol = col + dir.dx;
		 if (!maze.isWall(newRow, newCol)) {
			 row = newRow;
			 col = newCol;
		 }
	 }
	 
	 public void draw(Graphics g) {
		 if (sprite != null) {
			 g.drawImage(sprite, col * 48, row * 48, 48, 48, null);
		 }else {
			 g.setColor(Color.RED);
			 g.fillRect(col * 48, row * 48, 48, 48);
		 }
	 }
}
