package model;

import java.util.ArrayList;

public class SpawnHelper {
	
	public static boolean isWall(int r, int c, Maze maze) {
		return maze.isWall(r, c);
	}
	
	public static boolean isFarFrom(int r, int c, int targetR, int targetC, double minDist) {
		double dist = Math.sqrt(Math.pow(r - targetR, 2) + Math.pow(c - targetC, 2));
		return dist >= minDist;
	}
	
	public static boolean isFarFromOtherZombies(int r, int c, ArrayList<Zombie> zombies, double minDist) {
		for (Zombie z : zombies) {
			int zRow = Math.round(z.y / 48);
			int zCol = Math.round(z.x / 48);
			double dist = Math.sqrt(Math.pow(zRow - r, 2) + Math.pow(zCol - c, 2));
			if (dist < minDist) return false;
		}
		return true;
	}
	
	public static boolean isEntityAt(int r, int c, Player p, ArrayList<Zombie> zombies) {
		if (p != null && Math.round(p.y / 48) == r && Math.round(p.x / 48) == c) return true;
		if (zombies != null) {
			for (Zombie z : zombies) {
				if (Math.round(z.y / 48) == r && Math.round(z.x / 48) == c) return true;
			}
		}
		return false;
	}
	
	public static boolean isCoinAt(int r, int c, ArrayList<Collectables> items) {
		for (Collectables item : items) {
			if (Math.round(item.getY() / 48) == r && Math.round(item.getX() / 48) == c) return true;
		}
		return false;
	}
	
	public static boolean isGunAt(int r, int c, Gun gun) {
	    if (gun != null && gun.isActive()) {
	        int gunRow = gun.getY() / 48;
	        int gunCol = gun.getX() / 48;
	        return (r == gunRow && c == gunCol);
	    }
	    return false;
	}
	
	public static boolean isExitAt(int r, int c, Maze maze) {
	    return maze.isExit(r, c);
	}

}
