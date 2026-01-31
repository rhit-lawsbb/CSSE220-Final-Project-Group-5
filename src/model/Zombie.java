package model;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Zombie extends Entity{
	private Random rand;
	
	
	
	public Zombie(int row, int col, Maze maze) {
		super(row, col, maze);
		
		try {
			sprite = ImageIO.read(Player.class.getResource("pngkey.com-gaming-characters-png-1790042.png"));
			} catch (IOException | IllegalArgumentException ex) {
			sprite = null; 
			}
	}
	
	public void wander() {
		Direction[] d = Direction.values();
		move(d[rand.nextInt(d.length)]);
	}
	
	@Override
	 public void draw(Graphics g) {
		 if (sprite != null) {
			 g.drawImage(sprite, col * 48, row * 48, 48, 48, null);
		 }else {
			 g.setColor(Color.RED);
			 g.fillRect(col * 48, row * 48, 48, 48);
		 }
	 }

}
