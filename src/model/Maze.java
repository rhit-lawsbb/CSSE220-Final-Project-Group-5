package model;

import java.awt.Color;
import java.awt.Graphics;

public class Maze {
	
	private int[][] layout = {
			{1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,1,0,0,1},
			{1,0,1,1,1,0,1,0,1,1},
			{1,0,0,0,1,0,0,0,0,1},
			{1,1,1,0,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,1,0,1},
			{1,0,1,1,1,1,1,1,0,1},
			{1,0,0,0,0,0,0,0,0,1},
			{1,0,1,1,1,1,1,1,0,1},
			{1,1,1,1,1,1,1,1,1,1},
			
	};
	
	public boolean isWall(int row, int col) {
		if (row < 0 || col < 0 || row >= layout.length || col >= layout[0].length) return true;
			return layout[row][col] == 1;
		
	}
	
	public void draw(Graphics g) {
		for (int i = 0; i < layout.length; i++) {
			for (int k = 0; k < layout[0].length; k++) {
				if (layout[i][k] == 1) {
					g.setColor(new Color(0,125,0));
				}else {
					g.setColor(Color.LIGHT_GRAY);
				}
				g.fillRect(k * 48, i *48, 48, 48);
			}
		}
	}
}
