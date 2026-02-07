package model;

import java.util.ArrayList;

// handles all collision detection between the player and other objects
public class CollisionHandler {
	private Player player;
	private ArrayList<Zombie> zombies;
	private ArrayList<Collectables> items;
	private int score;
	private int hitCooldown;

	public CollisionHandler(Player player, ArrayList<Zombie> zombies, ArrayList<Collectables> items) {
		this.player = player;
		this.zombies = zombies;
		this.items = items;
		this.score = 0;
		this.hitCooldown = 0;
	}

	// runs all collision checks each frame
	public void checkCollisions() {
		checkCoinCollisions();
		checkZombieCollisions();
	}

	// checks if two objects are close enough to be touching
	private boolean overlaps(float x1, float y1, float x2, float y2) {
		return Math.abs(x1 - x2) < 36 && Math.abs(y1 - y2) < 36;
	}

	// picks up coins the player is touching and adds to score
	private void checkCoinCollisions() {
		ArrayList<Collectables> collected = new ArrayList<>();
		for (Collectables coin : items) {
			if (overlaps(player.getX(), player.getY(), coin.getX(), coin.getY())) {
				collected.add(coin);
				score++;
			}
		}
		items.removeAll(collected);
	}

	// damages player if a zombie is touching them, with cooldown between hits
	private void checkZombieCollisions() {
		if (hitCooldown > 0) {
			hitCooldown--;
			return;
		}
		for (Zombie z : zombies) {
			if (overlaps(player.getX(), player.getY(), z.getX(), z.getY())) {
				player.loseLife();
				hitCooldown = 15;
				break;
			}
		}
	}

	// gives the player a life if they walk over a heart
	public boolean checkHeartCollision(Heart heart) {
		if (heart == null || !heart.isActive()) return false;
		if (overlaps(player.getX(), player.getY(), heart.getX(), heart.getY())) {
			player.gainLife();
			heart.setActive(false);
			return true;
		}
		return false;
	}

	public int getScore() { return score; }
}
