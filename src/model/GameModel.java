package model;

import java.awt.Graphics;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
//Adds every aspect into game
public class GameModel {
	private Maze maze;
	private Player player;
	private ArrayList<Zombie> zombies;
	private ArrayList<Collectables> items;
	private Random rand = new Random();
	
	
	public GameModel() {
		maze = new Maze();
		player = new Player(1,1,maze);
		
		zombies = new ArrayList<>();
		spawnZombies(3);
		
		items = new ArrayList<>();
		spawnCoins();
		
	}
	
	private void spawnZombies(int count) {
		for (int i = 0; i < count; i++) {
			int r, c;
			boolean validSpawn = false;
			while (!validSpawn) {
				r = rand.nextInt(10);
				c = rand.nextInt(10);
				
				// Ensure player exists and is far away
				if (!maze.isWall(r, c) && !isTileOccupied(r, c) && isFarFromPlayer(r, c)&& isFarFromOtherZombies(r, c)) {
					zombies.add(new Zombie(r, c, maze));
					validSpawn = true;
				}
			}
		}
	}
	
	private boolean isFarFromOtherZombies(int r, int c) {
	    for (Zombie z : zombies) {
	        // Convert zombie position to tile coordinates
	        int zRow = Math.round(z.y / 48);
	        int zCol = Math.round(z.x / 48);
	        
	        // Euclidean distance formula
	        double distance = Math.sqrt(Math.pow(zRow - r, 2) + Math.pow(zCol - c, 2));
	        
	        // If it's closer than 10, it's not a valid spawn
	        if (distance < 5) {
	            return false;
	        }
	    }
	    return true; // Safe to spawn
	}

	private boolean isFarFromPlayer(int r, int c) {
		int playerR = Math.round(player.y / 48);
		int playerC = Math.round(player.x / 48);
		double distance = Math.sqrt(Math.pow(playerR - r, 2) + Math.pow(playerC - c, 2));
		return distance >= 5;
	}

	private boolean isTileOccupied(int r, int c) {
		// Player check
		if (Math.round(player.y / 48) == r && Math.round(player.x / 48) == c) return true;
		
		// Existing zombies check
		for (Zombie z : zombies) {
			if (Math.round(z.y / 48) == r && Math.round(z.x / 48) == c) return true;
		}
		return false;
	}
	
	private void spawnCoins() {
		Random rand = new Random();
		int coinsPlaced = 0;
		
		// Loop until we successfully place n coins on valid tiles
		while (coinsPlaced < 4) {
			int r = rand.nextInt(10); // Maze row
			int c = rand.nextInt(10); // Maze column
			
			// 1. Check if the tile is a floor (not a wall)
			// 2. Check if the tile is already occupied by player or zombie
			if (!maze.isWall(r, c) && !isTileOccupied(r, c)) {
				// The Collectible constructor now handles its own images internally
				items.add(new Collectables(c, r)); 
				coinsPlaced++;
			}
		}
	}
	

	
	public void update() {
		player.update();
		
		for (Zombie z: zombies) {
			z.wander();
		}
	}
	
	public void handleKey(KeyEvent e) {
		player.handleKey(e);
	}
	
	public void draw(Graphics g) {
		maze.draw(g);
		player.draw(g);
		
		for (Collectables item : items) {
			item.draw(g);
		}
		
		for (Zombie z : zombies) {
			z.draw(g);
		}
	}

}
