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

	public Player(int row, int col, Maze maze) {
		super(row, col, maze);
		
		try {
			
			spriteRight = ImageIO.read(Player.class.getResource("pngkit_game-character-png_1066630.png"));
			spriteLeft = ImageIO.read(Player.class.getResource("left_character.png"));
			} catch (IOException | IllegalArgumentException ex) {
			spriteRight = spriteLeft = null; 
			}
			}
	//read keys input for movement
	public void handleKey(KeyEvent e) {
		
		dx = 0;
		dy = 0;
		if (e.getKeyCode() ==KeyEvent.VK_W || e.getKeyCode() ==KeyEvent.VK_UP) {
			dy = -1;
		}else if (e.getKeyCode() ==KeyEvent.VK_S || e.getKeyCode() ==KeyEvent.VK_DOWN) {
				dy = 1;
		}else if (e.getKeyCode() ==KeyEvent.VK_A || e.getKeyCode() ==KeyEvent.VK_LEFT) {
			dx = -1;
			facingRight = false;
		}else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() ==KeyEvent.VK_RIGHT) {
			dx = 1;
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

	

