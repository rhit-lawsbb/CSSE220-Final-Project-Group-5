package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.Timer;

import model.GameModel;

public class GameComponent extends JComponent implements ActionListener, KeyListener{

	private Timer timer;
	private GameModel model;


	public GameComponent(GameModel model) {
		setPreferredSize(new Dimension(480, 480));
		this.model = model;
		
		setFocusable(true);
		addKeyListener(this);
		timer = new Timer(120, this);
		timer.start();
	}


	@Override
	protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	Graphics2D g2 = (Graphics2D) g;

	if (model != null) {
		model.draw(g);
	}else {
	// Minimal placeholder to test  it’s running
	g2.drawString("Final Project Starter: UI is running ✅", 20, 30);
	}

	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		model.handleKey(e);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		model.update();
		repaint();
	}
}
