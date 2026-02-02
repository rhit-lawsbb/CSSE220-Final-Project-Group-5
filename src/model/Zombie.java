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
		 if (Math.round(x) % 48 == 0 && Math.round(y) % 48 == 0) {
		        
		        // 2. Decide if we want to change direction at this intersection
		        // We look for "open spots" (non-walls) around us
		        if (shouldChangeDirection()) {
		            chooseAvailableDirection();
		        }
		    }

		    // 3. Move in the current direction
		    float nextX = x + dx * STEP;
		    float nextY = y + dy * STEP;

		    if (canMoveTo(nextX, nextY)) {
		        x = nextX;
		        y = nextY;
		    } else {
		        // If we hit a wall unexpectedly, pick a new way immediately
		        chooseAvailableDirection();
		    }
	}
	
	private boolean shouldChangeDirection() {
	    // Look for side openings relative to current movement
	    // If moving horizontally, check if Up or Down is open
	    if (dx != 0) {
	        if (canMoveTo(x, y - STEP) || canMoveTo(x, y + STEP)) return true;
	    }
	    // If moving vertically, check if Left or Right is open
	    if (dy != 0) {
	        if (canMoveTo(x - STEP, y) || canMoveTo(x + STEP, y)) return true;
	    }
	    return false;
	}
	
	private void chooseAvailableDirection() {
	    // Create a list of all directions that aren't blocked
	    java.util.ArrayList<Integer> validDirs = new java.util.ArrayList<>();
	    
	    if (canMoveTo(x, y - STEP)) validDirs.add(0); // Up
	    if (canMoveTo(x, y + STEP)) validDirs.add(1); // Down
	    if (canMoveTo(x - STEP, y)) validDirs.add(2); // Left
	    if (canMoveTo(x + STEP, y)) validDirs.add(3); // Right

	    if (validDirs.isEmpty()) return;

	    // Pick one of the valid directions at random
	    int choice = validDirs.get(rand.nextInt(validDirs.size()));
	    
	    dx = 0; dy = 0;
	    if (choice == 0) dy = -1;
	    else if (choice == 1) dy = 1;
	    else if (choice == 2) { dx = -1; facingRight = false; }
	    else if (choice == 3) { dx = 1; facingRight = true; }
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
