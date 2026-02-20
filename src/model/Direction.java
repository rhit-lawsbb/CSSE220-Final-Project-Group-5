package model;

/**
 * Enum: Direction 
 * @author Group 5
 * <br>Purpose: represents the possible directions of movement, being up, down, left, and right
 * these are used by player and zombie entities
 * provides vector components for x and y movement 
 * <br>Restrictions: only includes cardinal directions, not diagonals
 * <br>For Example:
 * <pre>
 * 		Direction d = Direction.RIGHT;
 * 		int dx = d.getDx(); returns 1
 * 		int dy = d.getDy(); returns 0
 * </pre>
 */

public enum Direction {
	UP, DOWN, LEFT, RIGHT;

	/**
	 * ensures: returns movement component in horizonal direction, changing based on direction
	 * @return -1 if left, 1 if right, 0 if up or down
	 */
	public int getDx() {
		if (this == LEFT) return -1;
		if (this == RIGHT) return 1;
		return 0;
	}

	/**
	 * ensures: returns movement component in vertical direction, changing based on direction
	 * @return -1 if up, 1 if down, 0 if left or right
	 */
	public int getDy() {
		if (this == UP) return -1;
		if (this == DOWN) return 1;
		return 0;
	}
}
