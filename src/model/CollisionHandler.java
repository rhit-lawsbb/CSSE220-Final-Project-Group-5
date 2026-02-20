package model;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * Class: CollisionHandler 
 * @author Group 5
 * <br>Purpose: Manages all collisions between player, zombie, pickup, bullets, and processes them
 * in charge of updating score, life count, damage done
 * keeps track of pickups and removing zombies
 * <br>Restrictions: Only in charge of collisions, doesnt have control of movement or rendering
 * Needs to be called every frame to detect new collisions
 * <br>For Example:
 * <pre>
 * 		CollisionHandler handler = new CollisionHandler(player, zombies, items, damage, maze, listener);
 * 		handler.checkCollisions();
 * </pre>
 */

public class CollisionHandler {
	private Player player;
	private ArrayList<Zombie> zombies;
	private ArrayList<Collectables> items;
	private int score;
	private int hitCooldown;
	private int damage;
	private Gun gun;
	private Armor armor;
	private Heart heart;
	private Maze maze;
	private ZombieDeathListener zombieDeathListener;
	private Clip coinSound;
	private Clip heartSound;
	private Clip damageSound;
	private Clip armorSound;
	private Clip gunPickupSound;

	/**
	 * ensures: starts the collision tracking for player, zombies, pickups, collectables
	 * starts playing sound effects and sets starting score
	 * @param player player in game who collides with other things
	 * @param zombies list of zombies in the game at once
	 * @param items list of collectable items
	 * @param damage amount of damage delt by zombies
	 * @param maze the maze of the level, needed for the tiles
	 * @param listener listener which is needed for when zombies die
	 * <br>requires: player &ne; null, zombies &ne; null, items &ne; null, maze &ne; null
	 */
	public CollisionHandler(Player player, ArrayList<Zombie> zombies, ArrayList<Collectables> items,
							int damage, Maze maze, ZombieDeathListener listener) {
		this.player = player;
		this.zombies = zombies;
		this.items = items;
		this.score = 0;
		this.hitCooldown = 0;
		this.damage = damage;
		this.maze = maze;
		this.zombieDeathListener = listener;
		this.coinSound = loadSound("coin_pickup.wav");
		this.heartSound = loadSound("health_pickup.wav");
		this.damageSound = loadSound("damage.wav");
		this.armorSound = loadSound("armor_pickup.wav");
		this.gunPickupSound = loadSound("gun_pickup.wav");
	}

	private Clip loadSound(String filename) {
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(
				getClass().getResource(filename));
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			return clip;
		} catch (Exception e) {
			System.out.println("Sound load error: " + e.getMessage());
			return null;
		}
	}

	private void playSound(Clip clip) {
		if (clip == null) return;
		clip.setFramePosition(0);
		clip.start();
	}

	/**
	 * ensures: checks all types of collision and processes them all
	 * this includes zombie, gun, armor, heart, coins and bullet
	 */
	public void checkCollisions() {
		checkCoinCollisions();
		checkZombieCollisions();
		checkGunCollision();
		checkArmorCollision();
		checkHeartCollision();
		checkBulletCollisions();
	}

	
	private boolean overlaps(double x1, double y1, double x2, double y2, int threshold) {
		double diffX = x1 - x2;
		double diffY = y1 - y2;
		if (diffX < 0) diffX = -diffX;
		if (diffY < 0) diffY = -diffY;
		return diffX < threshold && diffY < threshold;
	}

	// picks up coins the player is touching and adds to score
	private void checkCoinCollisions() {
		for (int i = items.size() - 1; i >= 0; i--) {
			Collectables coin = items.get(i);
			if (overlaps(player.getX(), player.getY(), coin.getX(), coin.getY(), 36)) {
				items.remove(i);
				score++;
				playSound(coinSound);
			}
		}
	}

	// damages player if a zombie is touching them, with cooldown between hits
	private void checkZombieCollisions() {
		if (hitCooldown > 0) {
			hitCooldown--;
			return;
		}
		for (Zombie z : zombies) {
			if (overlaps(player.getX(), player.getY(), z.getX(), z.getY(), 36)) {
				for (int i = 0; i < damage; i++) {
					player.loseLife();
				}
				playSound(damageSound);
				hitCooldown = 15;
				break;
			}
		}
	}

	private void checkGunCollision() {
		if (gun == null || !gun.isActive()) return;
		if (overlaps(player.getX(), player.getY(), gun.getX(), gun.getY(), Maze.TILE_SIZE / 2)) {
			player.pickupGun();
			gun.setActive(false);
			playSound(gunPickupSound);
		}
	}

	private void checkArmorCollision() {
		if (armor == null || !armor.isActive()) return;
		if (overlaps(player.getX(), player.getY(), armor.getX(), armor.getY(), 36)) {
			armor.setActive(false);
			damage = Math.max(1, damage / 2);
			playSound(armorSound);
		}
	}

	// gives the player a life if they walk over a heart
	private void checkHeartCollision() {
		if (heart == null || !heart.isActive()) return;
		if (overlaps(player.getX(), player.getY(), heart.getX(), heart.getY(), 36)) {
			player.gainLife();
			heart.setActive(false);
			playSound(heartSound);
		}
	}

	private void checkBulletCollisions() {
		List<Bullet> bullets = player.getBullets();
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			if (!b.isActive()) continue;
			for (int j = 0; j < zombies.size(); j++) {
				Zombie z = zombies.get(j);
				if (overlaps(b.getX(), b.getY(), z.getX(), z.getY(), 30)) {
					zombieDeathListener.onZombieDied();
					int dropRow = (int)(z.getY() / Maze.TILE_SIZE);
					int dropCol = (int)(z.getX() / Maze.TILE_SIZE);
					// FIX: No coin drop in walls or on exit
					if (!maze.isWall(dropRow, dropCol) && !maze.isExit(dropRow, dropCol)) {
						items.add(new Collectables(dropCol, dropRow));
					}
					zombies.remove(j);
					b.deactivate();
					break;
				}
			}
		}
	}

	/**
	 * ensures: sets reference point of the gun used for collision checking
	 * @param gun the gun to track
	 */
	public void setGun(Gun gun) { this.gun = gun; }
	
	/**
	 * ensures: sets reference point of the armor used for collision checking
	 * @param armor the armor to track
	 */
	public void setArmor(Armor armor) { this.armor = armor; }
	
	/**
	 * ensures: sets reference point of the heart used for collision checking
	 * @param heart the heart to track
	 */
	public void setHeart(Heart heart) { this.heart = heart; }

	/**
	 * ensures: returns the score value of the player
	 * @return player's score
	 */
	public int getScore() { return score; }
	
	/**
	 * ensures: increase the score value by a certain amount
	 * @param points number of points to increase the score by
	 */
	public void addScore(int points) { score += points; }
	
	/**
	 * ensures: sets the damage amount by the zombies to a certain value
	 * @param damage damage value it is set to
	 */
	public void setDamage(int damage) { this.damage = damage; }
}
