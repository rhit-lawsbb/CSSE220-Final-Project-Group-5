package model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Maze {
	
	private int[][][] layouts = {
			//Layout 1
			{
				{1,1,1,1,1,1,1,1,1,1},
				{1,0,0,0,0,0,1,0,0,1},
				{1,0,1,1,1,0,1,0,1,1},
				{1,0,0,0,1,0,0,0,0,1},
				{1,1,1,0,1,1,1,1,0,1},
				{1,0,0,0,0,0,0,1,0,1},
				{1,0,1,1,1,1,1,1,0,1},
				{1,0,0,0,0,0,0,0,0,1},
				{1,0,1,1,1,1,1,1,0,1},
				{1,1,1,1,1,1,1,1,1,1}
			},
			//Layout 2
			{
				{1,1,1,1,1,1,1,1,1,1},
				{1,0,0,0,1,0,0,0,0,1},
				{1,0,1,1,1,1,0,1,0,1},
				{1,0,1,0,0,0,0,1,1,1},
				{1,0,1,0,1,1,0,1,0,1},
				{1,0,0,0,0,1,0,0,0,1},
				{1,0,1,1,0,0,0,1,0,1},
				{1,0,0,1,1,1,0,1,0,1},
				{1,1,0,0,0,1,1,1,0,1},
				{1,1,1,1,1,1,1,1,1,1}
			},
			//Layout 3
			{
				{1,1,1,1,1,1,1,1,1,1},
				{1,0,0,0,0,0,0,1,0,1},
				{1,0,1,1,1,1,0,0,0,1},
				{1,0,1,0,0,1,1,1,1,1},
				{1,0,1,0,1,1,0,0,0,1},
				{1,0,1,0,0,0,0,1,1,1},
				{1,0,0,0,1,0,1,1,0,1},
				{1,1,0,1,1,0,0,1,0,1},
				{1,0,0,0,1,1,0,0,0,1},
				{1,1,1,1,1,1,1,1,1,1}
			},
			//Layout 4
			{
				{1,1,1,1,1,1,1,1,1,1},
				{1,0,0,0,0,0,0,0,0,1},
				{1,1,1,1,1,0,1,1,0,1},
				{1,0,1,0,0,0,1,0,0,1},
				{1,0,1,0,1,1,1,0,1,1},
				{1,0,0,0,0,0,1,0,0,1},
				{1,0,1,1,1,0,1,1,1,1},
				{1,0,1,0,1,0,0,1,0,1},
				{1,0,0,0,1,1,0,0,0,1},
				{1,1,1,1,1,1,1,1,1,1}
			},
			//Layout 5
			{
				{1,1,1,1,1,1,1,1,1,1},
				{1,0,1,1,1,0,0,1,0,1},
				{1,0,0,1,1,1,0,1,0,1},
				{1,1,0,0,0,0,0,1,0,1},
				{1,1,0,1,1,1,0,1,0,1},
				{1,0,0,1,0,1,0,0,0,1},
				{1,0,1,1,0,1,1,1,0,1},
				{1,0,1,1,0,0,0,0,0,1},
				{1,0,0,1,1,1,1,1,0,1},
				{1,1,1,1,1,1,1,1,1,1}
			}
		};
		
		private int[][] currentLayout;

		public Maze() {
			// Use Random to pick one layout from the 3D array
			Random rand = new Random();
			int index = rand.nextInt(layouts.length);
			this.currentLayout = layouts[index];
		}
		
		public boolean isWall(int row, int col) {
			if (row < 0 || col < 0 || row >= currentLayout.length || col >= currentLayout[0].length) return true;
			return currentLayout[row][col] == 1;
		}
		
		public void draw(Graphics g) {
			for (int i = 0; i < currentLayout.length; i++) {
				for (int k = 0; k < currentLayout[0].length; k++) {
					if (currentLayout[i][k] == 1) {
						g.setColor(new Color(0,125,0));
					} else {
						g.setColor(Color.LIGHT_GRAY);
					}
					g.fillRect(k * 48, i * 48, 48, 48);
				}
			}
		}
//	private int[][] layout = {
//			{1,1,1,1,1,1,1,1,1,1},
//			{1,0,0,0,0,0,1,0,0,1},
//			{1,0,1,1,1,0,1,0,1,1},
//			{1,0,0,0,1,0,0,0,0,1},
//			{1,1,1,0,1,1,1,1,0,1},
//			{1,0,0,0,0,0,0,1,0,1},
//			{1,0,1,1,1,1,1,1,0,1},
//			{1,0,0,0,0,0,0,0,0,1},
//			{1,0,1,1,1,1,1,1,0,1},
//			{1,1,1,1,1,1,1,1,1,1},
//			
//	};
//	
//	public boolean isWall(int row, int col) {
//		if (row < 0 || col < 0 || row >= layout.length || col >= layout[0].length) return true;
//			return layout[row][col] == 1;
//		
//	}
	
//	public void draw(Graphics g) {
//		for (int i = 0; i < layout.length; i++) {
//			for (int k = 0; k < layout[0].length; k++) {
//				if (layout[i][k] == 1) {
//					g.setColor(new Color(0,125,0));
//				}else {
//					g.setColor(Color.LIGHT_GRAY);
//				}
//				g.fillRect(k * 48, i *48, 48, 48);
//			}
//		}
//	}
}
