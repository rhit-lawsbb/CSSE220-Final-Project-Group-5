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
	private Heart heart;
	
	private Random rand = new Random();
	private boolean isHeartRespawning = false;
	private int heartsSpawnedCount = 0;
	private static final int MAX_HEARTS = 4;

	public GameModel() {
		maze = new Maze();
		player = new Player(1, 1, maze); // Player starting at 1,1
		
		zombies = new ArrayList<>();
		items = new ArrayList<>();
		
		// Run all spawn sequences
		spawnZombies(2);
		spawnCoins(3);
		spawnNewHeart();
	}

	// Logic for Spawning Zombies
	private void spawnZombies(int count) {
		for (int i = 0; i < count; i++) {
			boolean valid = false;
			while (!valid) {
				int r = rand.nextInt(10);
				int c = rand.nextInt(10);
				
				if (!SpawnHelper.isWall(r, c, maze) && 
					!SpawnHelper.isEntityAt(r, c, player, zombies) &&
					SpawnHelper.isFarFrom(r, c, 1, 1, 4.0) &&
					SpawnHelper.isFarFromOtherZombies(r, c, zombies, 3.0)) {
					
					zombies.add(new Zombie(r, c, maze));
					valid = true;
				}
			}
		}
	}

	// Logic for Spawning Coins (Items)
	private void spawnCoins(int count) {
		for (int i = 0; i < count; i++) {
			boolean valid = false;
			while (!valid) {
				int r = rand.nextInt(10);
				int c = rand.nextInt(10);
				
				// Coins shouldn't be on walls, entities, or other coins
				if (!SpawnHelper.isWall(r, c, maze) && 
					!SpawnHelper.isEntityAt(r, c, player, zombies) && 
					!SpawnHelper.isCoinAt(r, c, items)) {
					
					items.add(new Collectables(c, r));
					valid = true;
				}
			}
		}
	}

	// Logic for Spawning the Heart
	private void spawnNewHeart() {
		// Stop if limit reached
		if (heartsSpawnedCount >= MAX_HEARTS) {
			heart = null;
			return;
		}

		boolean valid = false;
		while (!valid) {
			int r = rand.nextInt(10);
			int c = rand.nextInt(10);
			
			// Heart shouldn't be on walls, entities, coins, and must be far from start
			if (!SpawnHelper.isWall(r, c, maze) && 
				!SpawnHelper.isEntityAt(r, c, player, zombies) && 
				!SpawnHelper.isCoinAt(r, c, items) && 
				SpawnHelper.isFarFrom(r, c, 1, 1, 3.0)) {
				
				heart = new Heart(c, r);
				heartsSpawnedCount++;
				isHeartRespawning = false;
				valid = true;
			}
		}
	}

	public void tryPickUpHeart() {
		if (heart == null || !heart.isActive() || isHeartRespawning) return;

		heart.setActive(false);
		isHeartRespawning = true;
		
		// 10-second delay for next heart
		javax.swing.Timer respawnTimer = new javax.swing.Timer(5000, e -> {
			spawnNewHeart();
			((javax.swing.Timer)e.getSource()).stop();
		});
		respawnTimer.start();
	}

	public void update() {
		player.update();
		for (Zombie z : zombies) {
			z.wander();
		}
	}

	public void handleKey(KeyEvent e) {
		player.handleKey(e);
		// Pick up heart key
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			tryPickUpHeart();
		}
	}

	public void draw(Graphics g) {
		maze.draw(g);
		
		for (Collectables item : items) {
			item.draw(g);
		}
		
		if (heart != null && heart.isActive()) {
			heart.draw(g);
		}
		
		player.draw(g);
		
		for (Zombie z : zombies) {
			z.draw(g);
		}
	}

}
