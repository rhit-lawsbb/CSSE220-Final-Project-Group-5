package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.Timer;

import model.GameModel;
import model.Maze;
//Draws everything
public class GameComponent extends JComponent implements ActionListener{

	private Timer timer;
	private GameModel model;
	private HUD hud;


	public GameComponent(GameModel model) {
		setPreferredSize(new Dimension(Maze.TILE_SIZE * 10, Maze.TILE_SIZE * 10));
		this.model = model;
		this.hud = new HUD(model);

		setFocusable(true);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				model.handleKey(e);
			}
		});
		timer = new Timer(120, this);
		timer.start();
	}


	@Override
	protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2 = (Graphics2D) g;

	if (model != null) {
		model.draw(g);
		hud.draw(g);
	}else {
	// Minimal placeholder to test  it's running
	g2.drawString("Final Project Starter: UI is running", 20, 30);
	}

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		model.update();
		repaint();
	}
}
