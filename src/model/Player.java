package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

// the player character, handles movement, lives, and sprite changes
public class Player extends Entity{
	private int dx;
	private int dy;
	private int lives;
	// stores different sized sprites for each life stage
	private BufferedImage[] rightSprites;
	private BufferedImage[] leftSprites;
	
	
	private boolean hasGun = false;
	private List<Bullet> bullets = new java.util.ArrayList<>();

	// loads all 3 sprite sizes (player shrinks as they lose lives)
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
	
	

	// sets movement direction based on wasd or arrow keys
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

	// moves the player if the next position isn't blocked
	public void update() {
		float nextX = x + dx * STEP;
		float nextY = y + dy * STEP;

		if (canMoveTo(nextX, nextY)) {
			x = nextX;
			y = nextY;
		}
	}

	// removes a life and updates the sprite to look smaller
	public void loseLife() {
		if (lives > 0) {
			lives--;
			updateSprite();
		}
	}

	// adds a life back, max 3
	public void gainLife() {
		if (lives < 3) {
			lives++;
			updateSprite();
		}
	}

	// swaps to the correct sprite based on current lives
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
		 
		 for (Bullet b : bullets) {
		        b.draw(g);
		    }
	 }
	
	public boolean hasGun() {
		return hasGun; 
		}
	
	public void pickupGun() { 
		hasGun = true; 
		}
	
	public void shoot() {
	    if (!hasGun) return;
	    bullets.add(new Bullet(x, y, facingRight, maze));
	}
	
		public void updateBullets(List<Zombie> zombies, java.util.List<Collectables> coins, GameModel model) {
		    for (int i = 0; i < bullets.size(); i++) {
		        Bullet b = bullets.get(i);
		        b.update();
		        for (int j = 0; j < zombies.size(); j++) {
		            Zombie z = zombies.get(j);
		            if (Math.abs(b.getX() - z.getX()) < 30 && Math.abs(b.getY() - z.getY()) < 30) {
		                
		                // ADDED: Tell the model to start a respawn timer
		                model.startZombieRespawn();
		                
		                zombies.remove(j);
		                coins.add(new Collectables((int)(z.getX() / 48), (int)(z.getY() / 48)));
		                b.deactivate();
		                break;
		            }
		        }
		        if (!b.isActive()) {
		            bullets.remove(i);
		            i--;
		        }
		    }
		}
	}
