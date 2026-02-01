package model;

import java.awt.Graphics;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameModel {
	private Maze maze;
	private Player player;
	private ArrayList<Zombie> zombies;
	
	
	public GameModel() {
		maze = new Maze();
		player = new Player(1,1,maze);
		
		zombies = new ArrayList<>();
		zombies.add(new Zombie(7,6,maze));
		zombies.add(new Zombie(5,4,maze));
		
	}
	
	public void update() {
		player.update();
		for (Zombie z: zombies) {
			z.wander();
		}
	}
	
	public void handleKey(KeyEvent e) {
		player.handleKey(e);
	}
	
	public void draw(Graphics g) {
		maze.draw(g);
		player.draw(g);
		
		for (Zombie z : zombies) {
			z.draw(g);
		}
	}

}
