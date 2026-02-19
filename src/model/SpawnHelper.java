package model;

import java.util.ArrayList;

public class SpawnHelper {
	public static boolean isWall(int r, int c, Maze maze) {
		return maze.isWall(r, c);
	}

	public static boolean isExitAt(int r, int c, Maze maze) {
		return maze.isExit(r, c);
	}

	public static boolean isGunAt(int r, int c, Gun gun) {
		if (gun != null && gun.isActive()) {
			return (int)(gun.getY() / Maze.TILE_SIZE) == r && (int)(gun.getX() / Maze.TILE_SIZE) == c;
		}
		return false;
	}

	public static boolean isFarFrom(int r, int c, int targetR, int targetC, double minDist) {
		double distSquared = (r - targetR) * (r - targetR) + (c - targetC) * (c - targetC);
		return distSquared >= minDist * minDist;
	}

	public static boolean isFarFromOtherZombies(int r, int c, ArrayList<Zombie> zombies, double minDist) {
		for (Zombie z : zombies) {
			int zRow = (int)(z.getY() / Maze.TILE_SIZE);
			int zCol = (int)(z.getX() / Maze.TILE_SIZE);
			double distSquared = (zRow - r) * (zRow - r) + (zCol - c) * (zCol - c);
			if (distSquared < minDist * minDist) return false;
		}
		return true;
	}

	public static boolean isEntityAt(int r, int c, Player p, ArrayList<Zombie> zombies) {
		if (p != null && (int)(p.getY() / Maze.TILE_SIZE) == r && (int)(p.getX() / Maze.TILE_SIZE) == c) return true;
		if (zombies != null) {
			for (Zombie z : zombies) {
				if ((int)(z.getY() / Maze.TILE_SIZE) == r && (int)(z.getX() / Maze.TILE_SIZE) == c) return true;
			}
		}
		return false;
	}

	public static boolean isCoinAt(int r, int c, ArrayList<Collectables> items) {
		for (Collectables item : items) {
			if ((int)(item.getY() / Maze.TILE_SIZE) == r && (int)(item.getX() / Maze.TILE_SIZE) == c) return true;
		}
		return false;
	}
}
