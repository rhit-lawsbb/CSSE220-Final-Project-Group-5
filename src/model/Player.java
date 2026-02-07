package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends Entity{
	private int dx;
	private int dy;
	private int lives;
	private BufferedImage[] rightSprites;
	private BufferedImage[] leftSprites;

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

	public void handleKey(KeyEvent e) {
		int key = e.getKeyCode();

	    if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
	        dx = 0; dy = -1;
	    } else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
	        dx = 0; dy = 1;
	    } else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
	        dx = -1; dy = 0;
	        facingRight = false;
	    } else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
	        dx = 1; dy = 0;
	        facingRight = true;
	    }
	}

	public void update() {
		float nextX = x + dx * STEP;
		float nextY = y + dy * STEP;

		if (canMoveTo(nextX, nextY)) {
			x = nextX;
			y = nextY;
		}
	}

	public void loseLife() {
		if (lives > 0) {
			lives--;
			updateSprite();
		}
	}

	public void gainLife() {
		if (lives < 3) {
			lives++;
			updateSprite();
		}
	}

	private void updateSprite() {
		int index = 3 - lives;
		if (index < 0) index = 0;
		if (index > 2) index = 2;
		spriteRight = rightSprites[index];
		spriteLeft = leftSprites[index];
	}

	public int getLives() { return lives; }

	public boolean isAlive() { return lives > 0; }

	@Override
	 public void draw(Graphics g) {
		BufferedImage currentSprite = facingRight ? spriteRight : spriteLeft;
		 if (currentSprite != null) {
			 g.drawImage(currentSprite, Math.round(x), Math.round(y), 48, 48, null);
		 }else {
			 g.setColor(Color.RED);
			 g.fillRect(Math.round(x), Math.round(y), 48, 48);
		 }
	 }
}
