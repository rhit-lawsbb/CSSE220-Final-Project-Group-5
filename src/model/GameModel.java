package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

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

	public void tryPickUpHeart() {
		if (heart == null || !heart.isActive() || isHeartRespawning) return;
		if (!collisionHandler.isPlayerNearHeart(heart)) return;

		player.gainLife();
		heart.setActive(false);
		isHeartRespawning = true;

		javax.swing.Timer respawnTimer = new javax.swing.Timer(5000, e -> {
			spawnNewHeart();
			((javax.swing.Timer)e.getSource()).stop();
		});
		respawnTimer.start();
	}

	public void update() {
		if (gameOver) return;

		player.update();
		for (Zombie z : zombies) {
			z.wander();
		}

		collisionHandler.checkCollisions();

		if (!player.isAlive()) {
			gameOver = true;
		}
	}

	public void handleKey(KeyEvent e) {
		if (gameOver) return;

		player.handleKey(e);
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

		if (gameOver) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, 480, 480);
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", Font.BOLD, 48));
			g.drawString("GAME OVER", 95, 240);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.PLAIN, 20));
			g.drawString("Score: " + collisionHandler.getScore(), 185, 280);
		}
	}

	public int getScore() { return collisionHandler.getScore(); }

	public int getLives() { return player.getLives(); }

	public boolean isGameOver() { return gameOver; }
}
