package model;

/**
 * Class: Direction 
 * @author Group 5
 * <br>Purpose:
 * <br>Restrictions:
 * <br>For Example:
 * <pre>
 * 		
 * </pre>
 */

public enum Direction {
	UP, DOWN, LEFT, RIGHT;

	public int getDx() {
		if (this == LEFT) return -1;
		if (this == RIGHT) return 1;
		return 0;
	}

	public int getDy() {
		if (this == UP) return -1;
		if (this == DOWN) return 1;
		return 0;
	}
}
