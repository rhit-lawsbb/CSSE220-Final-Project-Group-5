package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Zombie extends Entity{
	private Random rand;
	private Direction direction;

	public Zombie(int row, int col, Maze maze) {
		super(row, col, maze);
		rand = new Random();

		chooseDirection();
		try {
			setSpriteRight(ImageIO.read(Player.class.getResource("Zombie1_flip.png")));
			setSpriteLeft(ImageIO.read(Player.class.getResource("Zombie1.png")));
			} catch (IOException | IllegalArgumentException ex) {
			setSpriteRight(null);
			setSpriteLeft(null);
			}
	}

	// controls direction of Zombie
	private void chooseDirection() {
		Direction[] dirs = Direction.values();
		setDirection(dirs[rand.nextInt(dirs.length)]);
	}

	private void setDirection(Direction dir) {
		this.direction = dir;
		if (dir == Direction.LEFT) setFacingRight(false);
		else if (dir == Direction.RIGHT) setFacingRight(true);
	}

	//checks for walls and decides to move Zombie
	public void wander() {
		 if ((int) getX() % Maze.TILE_SIZE == 0 && (int) getY() % Maze.TILE_SIZE == 0) {

		        // 2. Decide if we want to change direction at this intersection
		        // We look for "open spots" (non-walls) around us
		        if (shouldChangeDirection()) {
		            chooseAvailableDirection();
		        }
		    }

		    // 3. Move in the current direction
		    double nextX = getX() + direction.getDx() * STEP;
		    double nextY = getY() + direction.getDy() * STEP;

		    if (canMoveTo(nextX, nextY)) {
		        setX(nextX);
		        setY(nextY);
		    } else {
		        // If we hit a wall unexpectedly, pick a new way immediately
		        chooseAvailableDirection();
		    }
	}

	private boolean shouldChangeDirection() {
	    // Look for side openings relative to current movement
	    // If moving horizontally, check if Up or Down is open
	    if (direction.getDx() != 0) {
	        if (canMoveTo(getX(), getY() - STEP) || canMoveTo(getX(), getY() + STEP)) return true;
	    }
	    // If moving vertically, check if Left or Right is open
	    if (direction.getDy() != 0) {
	        if (canMoveTo(getX() - STEP, getY()) || canMoveTo(getX() + STEP, getY())) return true;
	    }
	    return false;
	}

	private void chooseAvailableDirection() {
	    // Create a list of all directions that aren't blocked
	    ArrayList<Direction> validDirs = new ArrayList<>();

	    if (canMoveTo(getX(), getY() - STEP)) validDirs.add(Direction.UP);
	    if (canMoveTo(getX(), getY() + STEP)) validDirs.add(Direction.DOWN);
	    if (canMoveTo(getX() - STEP, getY())) validDirs.add(Direction.LEFT);
	    if (canMoveTo(getX() + STEP, getY())) validDirs.add(Direction.RIGHT);

	    if (validDirs.isEmpty()) return;

	    // Pick one of the valid directions at random
	    setDirection(validDirs.get(rand.nextInt(validDirs.size())));
	}
}
