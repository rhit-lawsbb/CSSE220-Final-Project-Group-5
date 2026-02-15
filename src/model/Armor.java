package model;

import java.awt.Color;
import java.awt.Graphics;

public class Armor {
	private int x, y;
	private boolean active = true;

	public Armor(int col, int row) {
		this.x = col * 48;
		this.y = row * 48;
	}

	public void draw(Graphics g) {
		if (!active) return;
		g.setColor(Color.RED);
		g.fillOval(x + 8, y + 8, 32, 32);
	}

	public int getX() { return x; }
	public int getY() { return y; }
	public boolean isActive() { return active; }
	public void setActive(boolean active) { this.active = active; }
}
