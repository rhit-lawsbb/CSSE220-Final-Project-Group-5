package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import model.GameModel;

public class HUD {
	private GameModel model;

	public HUD(GameModel model) {
		this.model = model;
	}

	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0, 150));
		g.fillRect(0, 0, 480, 30);

		g.setFont(new Font("Arial", Font.BOLD, 16));

		g.setColor(Color.RED);
		String livesText = "";
		for (int i = 0; i < model.getLives(); i++) {
			livesText += "\u2764 ";
		}
		g.drawString(livesText, 10, 21);

		g.setColor(Color.YELLOW);
		g.drawString("Score: " + model.getScore(), 380, 21);
	}
}
