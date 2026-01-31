package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.imageio.ImageIO;



public class Player extends Entity{

	public Player(int row, int col, Maze maze) {
		super(row, col, maze);
		
		try {
			
			sprite = ImageIO.read(Player.class.getResource("pngkit_game-character-png_1066630.png"));
			} catch (IOException | IllegalArgumentException ex) {
			sprite = null; 
			}
			}
	
	public void handleKey(KeyEvent e) {
		if (e.getKeyCode() ==KeyEvent.VK_W || e.getKeyCode() ==KeyEvent.VK_UP) {
			move(Direction.UP);
		}else if (e.getKeyCode() ==KeyEvent.VK_S || e.getKeyCode() ==KeyEvent.VK_DOWN) {
			move(Direction.DOWN);
		}else if (e.getKeyCode() ==KeyEvent.VK_A || e.getKeyCode() ==KeyEvent.VK_LEFT) {
			move(Direction.LEFT);
		}else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() ==KeyEvent.VK_RIGHT) {
			move(Direction.RIGHT);
	}
	}
	
	@Override
	 public void draw(Graphics g) {
		 if (sprite != null) {
			 g.drawImage(sprite, col * 48, row * 48, 48, 48, null);
		 }else {
			 g.setColor(Color.RED);
			 g.fillRect(col * 48, row * 48, 48, 48);
		 }
	 }
}

	

