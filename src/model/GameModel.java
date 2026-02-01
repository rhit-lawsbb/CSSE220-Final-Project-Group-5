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
	
	
	public GameModel() {
		maze = new Maze();
		player = new Player(1,1,maze);
		
		zombies = new ArrayList<>();
		zombies.add(new Zombie(7,6,maze));
		zombies.add(new Zombie(5,4,maze));
		
		items = new ArrayList<>();
		spawnCoins();
		
	}
	
	private void spawnCoins() {
		Random rand = new Random();
		int coinsPlaced = 0;
		
		// Loop until we successfully place n coins on valid tiles
		while (coinsPlaced < 5) {
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
	
	private boolean isTileOccupied(int r, int c) {
		if (Math.round(player.y / 48) == r && Math.round(player.x / 48) == c) return true;
		for (Zombie z : zombies) {
			if (Math.round(z.y / 48) == r && Math.round(z.x / 48) == c) return true;
		}
		// Also check if another coin is already in this tile
		for (Collectables item : items) {
			if (Math.round(item.getY() / 48) == r && Math.round(item.getX() / 48) == c) {
	            return true;
			// (Assuming Collectible has a way to check its row/col or bounds)
			// For simplicity, we just check walls and entities first
		}
	}
		return false;
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
