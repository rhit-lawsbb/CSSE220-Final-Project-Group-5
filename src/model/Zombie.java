package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Zombie extends Entity{
	private Random rand;
	private int dx;
	private int dy;
	
	
	
	public Zombie(int row, int col, Maze maze) {
		super(row, col, maze);
		rand = new Random();
		
		chooseDirection();
		try {
			spriteRight = ImageIO.read(Player.class.getResource("Zombie1_flip.png"));
			spriteLeft = ImageIO.read(Player.class.getResource("Zombie1.png"));
			} catch (IOException | IllegalArgumentException ex) {
			spriteRight = spriteLeft = null; 
			}
	}
	// controls direction of Zombie
	private void chooseDirection() {
		dx = 0;
		dy = 0;
		
		int r = rand.nextInt(4);
		if (r == 0) {
			dy = -1;
		}else if (r == 1) {
			dy = 1;
		}else if(r == 2) {
			dx = -1;
			facingRight = false;
		}else if (r == 3) {
			dx = 1;
			facingRight = true;
		}
	}
	//checks for walls and decides to move Zombie 
	public void wander() {
		float nextX = x + dx * STEP;
		float nextY = y + dy * STEP;
		
		if (!canMoveTo(nextX, nextY)) {
			chooseDirection();
		
			nextX = x + dx * STEP;
			nextY = y + dy * STEP;
			
		}
		
		if (!canMoveTo(nextX, nextY)) {
				return;
		}
		x = nextX;
		y = nextY;
	}
	
	@Override
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
