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
	private boolean gameOver;

	private Random rand = new Random();
	private boolean isHeartRespawning = false;
	private int heartsSpawnedCount = 0;
	private static final int MAX_HEARTS = 4;

	// sets up the maze, player, enemies, and collectibles
	public GameModel() {
		maze = new Maze();
		player = new Player(1, 1, maze);

		zombies = new ArrayList<>();
		items = new ArrayList<>();
		gameOver = false;

		spawnZombies(2);
		spawnCoins(3);
		spawnNewHeart();

		collisionHandler = new CollisionHandler(player, zombies, items);
	}

	// places zombies at random valid spots, away from the player
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
		if (gameOver) return;

		player.update();
		for (Zombie z : zombies) {
			z.wander();
		}

		collisionHandler.checkCollisions();
		tryPickUpHeart();

		if (!player.isAlive()) {
			gameOver = true;
		}
	}

	// passes key input to player
	public void handleKey(KeyEvent e) {
		if (gameOver) {
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
		isHeartRespawning = false;
		heartsSpawnedCount = 0;

		spawnZombies(2);
		spawnCoins(3);
		spawnNewHeart();

		collisionHandler = new CollisionHandler(player, zombies, items);
	}

	// draws everything, plus a game over screen if the player died
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

		if (gameOver) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, 480, 480);
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", Font.BOLD, 48));
			g.drawString("GAME OVER", 95, 240);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString("Score: " + collisionHandler.getScore(), 185, 280);
			g.setFont(new Font("Arial", Font.PLAIN, 16));
			g.drawString("Press R to Restart", 175, 320);
		}
	}

	public int getScore() { return collisionHandler.getScore(); }

	public int getLives() { return player.getLives(); }

	public boolean isGameOver() { return gameOver; }
}
