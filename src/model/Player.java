package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * Class: Player 
 * @author Group 5
 * <br>Purpose: represents player, handles movement shooting and lives
 * <br>Restrictions: movement is limited based on walls
 * <br>For Example:
 * <pre>
 * 		Player p = new Player(row, col, maze);
 * </pre>
 */

public class Player extends Entity {
	private Direction direction = Direction.RIGHT;
	private int lives;
	private BufferedImage[] rightSprites;
	private BufferedImage[] leftSprites;
	private boolean hasGun = false;
	private int ammo = 0;
	private static final int MAX_AMMO = 8;
	private List<Bullet> bullets = new java.util.ArrayList<>();

	/**
	 * ensures: creates player at location
	 * @param row row tile location
	 * @param col col tile location
	 * @param maze maze
	 */
	public Player(int row, int col, Maze maze) {
		super(row, col, maze);
		lives = 3;
		rightSprites = new BufferedImage[3];
		leftSprites = new BufferedImage[3];

		try {
			rightSprites[0] = ImageIO.read(Player.class.getResource("Characters_1st_Life.png"));
			leftSprites[0] = ImageIO.read(Player.class.getResource("left_Characters_1st_Life.png"));
			rightSprites[1] = ImageIO.read(Player.class.getResource("Characters_2nd_Life.png"));
			leftSprites[1] = ImageIO.read(Player.class.getResource("left_Characters_2nd_Life.png"));
			rightSprites[2] = ImageIO.read(Player.class.getResource("Characters_last_Life.png"));
			leftSprites[2] = ImageIO.read(Player.class.getResource("left_Characters_last_Life.png"));
		} catch (IOException | IllegalArgumentException ex) {
			rightSprites[0] = leftSprites[0] = null;
		}
		updateSprite();
	}

	/**
	 * ensures: updates player move direction, uses key input
	 * @param e key event from user input
	 */
	public void handleKey(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
			direction = Direction.UP;
		} else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
			direction = Direction.DOWN;
		} else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
			direction = Direction.LEFT;
			setFacingRight(false);
		} else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
			direction = Direction.RIGHT;
			setFacingRight(true);
		}
	}

	/**
	 * ensures: moves player in direction if valid
	 */
	public void update() {
		double nextX = getX() + direction.getDx() * STEP;
		double nextY = getY() + direction.getDy() * STEP;
		if (canMoveTo(nextX, nextY)) {
			setX(nextX);
			setY(nextY);
		}
	}

	/**
	 * ensures: gives player gun, refills amo
	 */
	public void pickupGun() {
		hasGun = true;
		ammo = MAX_AMMO;
	}

	/**
	 * ensires: shoots bullet if valid ammo ammount
	 */
	public void shoot() {
		if (!hasGun || ammo <= 0) return;
		bullets.add(new Bullet(getX(), getY(), isFacingRight(), getMaze()));
		ammo--;
		if (ammo <= 0) hasGun = false;
	}

	/**
	 * ensures: removes inactive bullets and updates the bullets
	 */
	public void updateBullets() {
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.get(i);
			b.update();
			if (!b.isActive()) {
				bullets.remove(i);
				i--;
			}
		}
	}

	public List<Bullet> getBullets() { return bullets; }

	// test

	public void loseLife() { if (lives > 0) { lives--; updateSprite(); } }
	public void gainLife() { if (lives < 3) { lives++; updateSprite(); } }
	private void updateSprite() {
		int index = 3 - lives;
		if (index < 0) index = 0;
		if (index > 2) index = 2;
		setSpriteRight(rightSprites[index]);
		setSpriteLeft(leftSprites[index]);
	}
	public int getLives() { return lives; }
	public boolean isAlive() { return lives > 0; }
	public boolean hasGun() { return hasGun; }
	public int getAmmo() { return ammo; }

	@Override
	public void draw(Graphics g) {
		BufferedImage currentSprite = isFacingRight() ? getSpriteRight() : getSpriteLeft();
		if (currentSprite != null) g.drawImage(currentSprite, (int) getX(), (int) getY(), Maze.TILE_SIZE, Maze.TILE_SIZE, null);
		for (Bullet b : bullets) b.draw(g);
	}
}
