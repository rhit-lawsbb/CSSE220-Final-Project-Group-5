package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

// main game class test
public class GameModel implements ZombieDeathListener {
	private Maze maze;
	private Player player;
	private ArrayList<Zombie> zombies;
	private ArrayList<Collectables> items;
	private Heart heart;
	private CollisionHandler collisionHandler;
	private Gun gun;

	private Armor armor;
	private boolean isGunRespawning = false;

	private boolean gameOver;
	private boolean gameWon;

	private static final int COINS_PER_LEVEL = 5;
	private int level = 1;
	private int zombieDamage = 1;
	private int coinsRequired = COINS_PER_LEVEL;

	private Random rand = new Random();
	private boolean isHeartRespawning = false;
	private int heartsSpawnedCount = 0;
	private static final int MAX_HEARTS = 4;

	private int zombieRespawnCounter = 0;
	private int zombiesWaiting = 0;
	private int heartRespawnCounter = 0;
	private int gunRespawnCounter = 0;

	private Clip backgroundMusic;

	// sets up the maze, player, enemies, and collectibles
	public GameModel() {
		level = 1;
		zombieDamage = 1;
		coinsRequired = COINS_PER_LEVEL;
		initLevel(2, 0);
		playBackgroundMusic();
	}

	private void playBackgroundMusic() {
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(
				getClass().getResource("pirate_theme.wav"));
			backgroundMusic = AudioSystem.getClip();
			backgroundMusic.open(audioIn);
			FloatControl volume = (FloatControl) backgroundMusic.getControl(
				FloatControl.Type.MASTER_GAIN);
			volume.setValue(-20.0f);
			backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			System.out.println("Music error: " + e.getMessage());
		}
	}

	private void initLevel(int zombieCount, int previousScore) {
		maze = new Maze();

		int[] playerSpawn = maze.getPlayerSpawn();
		player = new Player(playerSpawn[0], playerSpawn[1], maze);

		zombies = new ArrayList<>();
		items = new ArrayList<>();
		gameOver = false;
		gameWon = false;
		isHeartRespawning = false;
		heartsSpawnedCount = 0;
		zombieRespawnCounter = 0;
		zombiesWaiting = 0;
		heartRespawnCounter = 0;
		gunRespawnCounter = 0;

		// places zombies from tile legend positions
		ArrayList<int[]> zombieSpawns = maze.getZombieSpawns();
		for (int i = 0; i < zombieCount && i < zombieSpawns.size(); i++) {
			int[] pos = zombieSpawns.get(i);
			zombies.add(new Zombie(pos[0], pos[1], maze));
		}

		spawnCoins(8);
		spawnNewHeart();
		spawnGun();

		if (level >= 3) {
			spawnArmor();
		} else {
			armor = null;
		}

		collisionHandler = new CollisionHandler(player, zombies, items, zombieDamage, maze, this);
		collisionHandler.setGun(gun);
		collisionHandler.setArmor(armor);
		collisionHandler.setHeart(heart);
		collisionHandler.addScore(previousScore);
	}

	@Override
	public void onZombieDied() {
		startZombieRespawn();
	}

	public void startZombieRespawn() {
		zombiesWaiting++;
		if (zombieRespawnCounter <= 0) {
			zombieRespawnCounter = 58;
		}
	}

	// NEW: Spawns a single zombie at a safe distance
	private void spawnSingleZombie() {
		boolean valid = false;
		while (!valid) {
			int r = rand.nextInt(10);
			int c = rand.nextInt(10);
			if (!SpawnHelper.isWall(r, c, maze) &&
				SpawnHelper.isFarFrom(r, c, (int)(player.getY()/Maze.TILE_SIZE), (int)(player.getX()/Maze.TILE_SIZE), 3.0)) {
				zombies.add(new Zombie(r, c, maze));
				valid = true;
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
					!SpawnHelper.isExitAt(r, c, maze) &&
					SpawnHelper.isFarFrom(r, count, 1, 1, 5) &&
					!SpawnHelper.isEntityAt(r, c, player, zombies) &&
					!SpawnHelper.isCoinAt(r, c, items) &&
					!SpawnHelper.isGunAt(r, c, gun)) {

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
				!maze.isExit(r, c) &&
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

	private void spawnGun() {
		boolean valid = false;
		int attempts = 0;
		while (!valid && attempts < 100) {
			attempts++;
			int r = rand.nextInt(10);
			int c = rand.nextInt(10);
			if (!SpawnHelper.isWall(r, c, maze) && !maze.isExit(r, c) &&
				!SpawnHelper.isEntityAt(r, c, player, zombies) &&
				!SpawnHelper.isCoinAt(r, c, items)) {
				if (gun == null) gun = new Gun(c, r);
				else gun.respawn(c, r);
				valid = true;
			}
		}
	}

	private void spawnArmor() {
		boolean valid = false;
		int attempts = 0;
		while (!valid && attempts < 100) {
			attempts++;
			int r = rand.nextInt(10);
			int c = rand.nextInt(10);
			if (!SpawnHelper.isWall(r, c, maze) && !maze.isExit(r, c) &&
				!SpawnHelper.isEntityAt(r, c, player, zombies) &&
				!SpawnHelper.isCoinAt(r, c, items)) {
				armor = new Armor(c, r);
				valid = true;
			}
		}
	}

	 private void startGunRespawnTimer() {
	        isGunRespawning = true;
	        gunRespawnCounter = 42;
	    }

	 public void levels(){
		 level++;
		 if (level > 5) {
			 gameWon = true;
			 return;
		 }

		 int prevScore = collisionHandler.getScore();

		 int zombieCount;
		 if (level == 2) { zombieCount = 3; zombieDamage = 1; }
		 else if (level == 3) { zombieCount = 3; zombieDamage = 2; }
		 else if (level == 4) { zombieCount = 4; zombieDamage = 1; }
		 else if (level == 5) { zombieCount = 4; zombieDamage = 2; }
		 else { zombieCount = 2; zombieDamage = 1; }

		 coinsRequired = prevScore + COINS_PER_LEVEL;
		 initLevel(zombieCount, prevScore);
	 }

	// moves entities, checks collisions, checks game over bo
	public void update() {
		if (gameOver || gameWon) return;

		player.update();
		player.updateBullets();
		for (Zombie z : zombies) {
			z.wander();
		}

		collisionHandler.checkCollisions();

		if (zombieRespawnCounter > 0) {
			zombieRespawnCounter--;
			if (zombieRespawnCounter == 0 && zombiesWaiting > 0) {
				if (!gameOver && !gameWon) {
					spawnSingleZombie();
				}
				zombiesWaiting--;
				if (zombiesWaiting > 0) {
					zombieRespawnCounter = 58;
				}
			}
		}

		// picks up heart on contact and starts a timer to spawn the next one
		if (heart != null && !heart.isActive() && !isHeartRespawning) {
			isHeartRespawning = true;
			heartRespawnCounter = 42;
		}
		if (heartRespawnCounter > 0) {
			heartRespawnCounter--;
			if (heartRespawnCounter == 0) {
				spawnNewHeart();
				if (heart != null) {
					collisionHandler.setHeart(heart);
				}
			}
		}

		if (gunRespawnCounter > 0) {
			gunRespawnCounter--;
			if (gunRespawnCounter == 0) {
				boolean valid = false;
				while (!valid) {
					int r = rand.nextInt(10);
					int c = rand.nextInt(10);
					if (!SpawnHelper.isWall(r, c, maze) && !SpawnHelper.isExitAt(r, c, maze)) {
						gun.setActive(true);
						// Move gun to new spot or keep original (8,8)
						// gun.setPos(c, r);
						isGunRespawning = false;
						valid = true;
					}
				}
			}
		}

		int pr = (int)(player.getY()/Maze.TILE_SIZE);
		int pc = (int)(player.getX()/Maze.TILE_SIZE);
		if (maze.isExit(pr, pc) && collisionHandler.getScore() >= getCoinsRequired()) {
			levels();
		}

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
		level = 1;
		zombieDamage = 1;
		coinsRequired = COINS_PER_LEVEL;
		initLevel(2, 0);
	}

	// draws everything, plus a game over screen if the player died
	public void draw(Graphics g) {
		maze.draw(g);
		for (Collectables item : items) { item.draw(g); }
		if (heart != null && heart.isActive()) { heart.draw(g); }
		if (gun != null && gun.isActive()) { gun.draw(g); }
		if (armor != null && armor.isActive()) { armor.draw(g); }
		player.draw(g);
		for (Zombie z : zombies) { z.draw(g); }

		if (gameWon) {
			drawEndScreen(g, "YOU WIN!", Color.GREEN);
		} else if (gameOver) {
			drawEndScreen(g, "GAME OVER", Color.RED);
		}
	}

	private void drawEndScreen(Graphics g, String msg, Color color) {
		g.setColor(new Color(0, 0, 0, 150));
		g.fillRect(0, 0, Maze.TILE_SIZE * 10, Maze.TILE_SIZE * 10);
		g.setColor(color);
		g.setFont(new Font("Arial", Font.BOLD, 48));
		g.drawString(msg, msg.equals("YOU WIN!") ? 100 : 95, 240);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.drawString("Score: " + collisionHandler.getScore(), 185, 280);
		g.drawString("Press R to Restart", 165, 320);
	}

	public int getScore() { return collisionHandler.getScore(); }

	public int getLives() { return player.getLives(); }

	public Player getPlayer() { return player; }

	public CollisionHandler getCollisionHandler() {
		return collisionHandler;
	}

	public int getCoinsRequired() {
		return coinsRequired;
	}

	public int getLevel() {
		return level;
	}

	public boolean isGameOver() { return gameOver; }
}
