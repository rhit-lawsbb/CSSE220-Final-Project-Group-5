package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

// main game class
public class GameModel {
	private Maze maze;
	private Player player;
	private ArrayList<Zombie> zombies;
	private ArrayList<Collectables> items;
	private Heart heart;
	private CollisionHandler collisionHandler;
	private Gun gun;
	
	private boolean gameOver;
	private boolean gameWon;
	private static final int COINS_REQUIRED = 5;

	private Random rand = new Random();
	private boolean isHeartRespawning = false;
	private int heartsSpawnedCount = 0;
	private static final int MAX_HEARTS = 4;

	// sets up the maze, player, enemies, and collectibles
	public GameModel() {
		maze = new Maze();
		player = new Player(1, 1, maze);
		gun = new Gun(8,8);

		zombies = new ArrayList<>();
		items = new ArrayList<>();
		gameOver = false;
		gameWon =  false;

		spawnZombies(2);
		spawnCoins(8);
		spawnNewHeart();

		collisionHandler = new CollisionHandler(player, zombies, items);
	}
	
	public void startZombieRespawn() {
		javax.swing.Timer respawnTimer = new javax.swing.Timer(7000, e -> {
			if (!gameOver && !gameWon) {
				spawnSingleZombie();
			}
			((javax.swing.Timer)e.getSource()).stop();
		});
		respawnTimer.setRepeats(false);
		respawnTimer.start();
	}

	// NEW: Spawns a single zombie at a safe distance
	private void spawnSingleZombie() {
		boolean valid = false;
		while (!valid) {
			int r = rand.nextInt(10);
			int c = rand.nextInt(10);
			if (!SpawnHelper.isWall(r, c, maze) &&
				SpawnHelper.isFarFrom(r, c, Math.round(player.getY()/48), Math.round(player.getX()/48), 3.0)) {
				zombies.add(new Zombie(r, c, maze));
				valid = true;
			}
		}
	}

	// places zombies at random valid spots, away from the player
	private void spawnZombies(int count) {
		for (int i = 0; i < count; i++) {
			spawnSingleZombie();
		}
	}

	// places coins at random empty spots
	private void spawnCoins(int count) {
		for (int i = 0; i < count; i++) {
			boolean valid = false;
			while (!valid) {
				int r = rand.nextInt(10);
				int c = rand.nextInt(10);

				if (!SpawnHelper.isWall(r, c, maze) &&
					!SpawnHelper.isEntityAt(r, c, player, zombies) &&
					!SpawnHelper.isCoinAt(r, c, items)) {

					items.add(new Collectables(c, r));
					valid = true;
				}
			}
		}
	}

	// spawns a heart pickup, stops after max limit is reached
	private void spawnNewHeart() {
		if (heartsSpawnedCount >= MAX_HEARTS) {
			heart = null;
			return;
		}

		boolean valid = false;
		while (!valid) {
			int r = rand.nextInt(10);
			int c = rand.nextInt(10);

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

	// picks up heart on contact and starts a timer to spawn the next one
	private void tryPickUpHeart() {
		if (heart == null || !heart.isActive() || isHeartRespawning) return;
		if (!collisionHandler.checkHeartCollision(heart)) return;

		isHeartRespawning = true;

		javax.swing.Timer respawnTimer = new javax.swing.Timer(5000, e -> {
			spawnNewHeart();
			((javax.swing.Timer)e.getSource()).stop();
		});
		respawnTimer.start();
	}

	// moves entities, checks collisions, checks game over
	public void update() {
		if (gameOver || gameWon) return;

		player.update();
		
		if (gun != null && gun.isActive() && gun.collidesWith(player)) {
		    player.pickupGun();
		    gun.setActive(false);
		}
		player.updateBullets(zombies, items, this);
		for (Zombie z : zombies) {
			z.wander();
		}
		
		int pr = Math.round(player.getY()/48);
		int pc = Math.round(player.getX()/48);
		if (maze.isExit(pr, pc) && collisionHandler.getScore() >= COINS_REQUIRED) {
			gameWon = true;
		}

		collisionHandler.checkCollisions();
		tryPickUpHeart();

		if (!player.isAlive()) {
			gameOver = true;
		}
	}

	// passes key input to player
	public void handleKey(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_F) {
		    player.shoot();
		}
		if (gameOver || gameWon) {
			if (e.getKeyCode() == KeyEvent.VK_R) {
				restart();
			}
			return;
		}

		player.handleKey(e);
	}
	


	// resets everything back to a fresh game
	private void restart() {
		maze = new Maze();
		player = new Player(1, 1, maze);
		zombies = new ArrayList<>();
		items = new ArrayList<>();
		gameOver = false;
		gameWon = false;
		isHeartRespawning = false;
		heartsSpawnedCount = 0;
		gun = new Gun(8,8);

		spawnZombies(2);
		spawnCoins(8);
		spawnNewHeart();

		collisionHandler = new CollisionHandler(player, zombies, items);
	}

	// draws everything, plus a game over screen if the player died
	public void draw(Graphics g) {
		maze.draw(g);
		for (Collectables item : items) { item.draw(g); }
		if (heart != null && heart.isActive()) { heart.draw(g); }
		if (gun.isActive()) { gun.draw(g); }
		player.draw(g);
		for (Zombie z : zombies) { z.draw(g); }

		// HUD
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 16));
		g.drawString("Gold: " + collisionHandler.getScore() + "/" + COINS_REQUIRED, 10, 25);

		if (gameWon) {
			drawEndScreen(g, "YOU ESCAPED!", Color.GREEN);
		} else if (gameOver) {
			drawEndScreen(g, "GAME OVER", Color.RED);
		}
	}
	
	private void drawEndScreen(Graphics g, String msg, Color color) {
		g.setColor(new Color(0, 0, 0, 150));
		g.fillRect(0, 0, 480, 480);
		g.setColor(color);
		g.setFont(new Font("Arial", Font.BOLD, 48));
		g.drawString(msg, msg.contains("ESCAPED") ? 65 : 95, 240);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.drawString("Score: " + collisionHandler.getScore(), 185, 280);
		g.drawString("Press R to Restart", 165, 320);
	}

	public int getScore() { return collisionHandler.getScore(); }

	public int getLives() { return player.getLives(); }

	public boolean isGameOver() { return gameOver; }

}
