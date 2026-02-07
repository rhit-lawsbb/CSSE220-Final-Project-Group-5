package model;

import java.util.ArrayList;

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

	public void checkCollisions() {
		checkCoinCollisions();
		checkZombieCollisions();
	}

	private boolean overlaps(float x1, float y1, float x2, float y2) {
		return Math.abs(x1 - x2) < 36 && Math.abs(y1 - y2) < 36;
	}

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

	public boolean isPlayerNearHeart(Heart heart) {
		if (heart == null || !heart.isActive()) return false;
		return overlaps(player.getX(), player.getY(), heart.getX(), heart.getY());
	}

	public int getScore() { return score; }
}
