package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class Maze {
	 private BufferedImage floorImage;
	 private BufferedImage cornerImage;
	 private BufferedImage sideImage;
	 
	 private BufferedImage exitImage;
	 
	private int[][][] layouts = {
			//Layout 1
			{
				{1,1,1,1,1,1,1,1,1,1},
				{1,0,0,0,0,0,1,0,0,2},
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
				{1,0,0,0,0,1,1,1,0,1},
				{1,1,1,1,2,1,1,1,1,1}
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
				{2,0,0,0,1,1,0,0,0,1},
				{1,1,1,1,1,1,1,1,1,1}
			},
			//Layout 4
			{
				{1,1,1,1,1,1,1,1,1,1},
				{1,0,0,0,0,0,0,0,0,1},
				{1,1,1,1,1,0,1,1,0,1},
				{1,0,1,0,0,0,1,0,0,1},
				{1,0,1,0,1,1,1,0,1,1},
				{1,0,0,0,0,0,1,0,0,2},
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
				{1,1,1,1,1,1,1,1,2,1}
			}
		};
		
		private int[][] currentLayout;

		public Maze() {
			// Use Random to pick one layout from the 3D array
			Random rand = new Random();
			int index = rand.nextInt(layouts.length);
			this.currentLayout = layouts[index];
			
			try {
				floorImage = ImageIO.read(getClass().getResource("Pirate_Ship_Deck.png"));
				cornerImage = ImageIO.read(getClass().getResource("Wood_corner2.png"));
				sideImage = ImageIO.read(getClass().getResource("Ship_wall1.png"));
				
				BufferedImage doorBase = ImageIO.read(getClass().getResource("Plank_water.png"));
				BufferedImage doorTop = ImageIO.read(getClass().getResource("Plank.png"));
				exitImage = new BufferedImage(48, 48, BufferedImage.TYPE_INT_ARGB);
				Graphics2D gExit = exitImage.createGraphics();
				gExit.drawImage(doorBase, 0, 0, 48, 48, null);
				gExit.drawImage(doorTop, 0, 0, 48, 48, null);
				gExit.dispose();
				
			} catch (IOException | IllegalArgumentException e) {
				floorImage = null; // Fallback to original color if missing
				exitImage = null;
			}
		}
		
		public boolean isWall(int row, int col) {
			if (row < 0 || col < 0 || row >= currentLayout.length || col >= currentLayout[0].length) return true;
			return currentLayout[row][col] == 1;
		}
		
		public boolean isExit(int row, int col) {
			if (row < 0 || col < 0 || row >= currentLayout.length || col >= currentLayout[0].length) return false;
			return currentLayout[row][col] == 2;
		}
		
		public void draw(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			int size = currentLayout.length;

			for (int r = 0; r < size; r++) {
				for (int c = 0; c < size; c++) {
					int x = c * 48;
					int y = r * 48;

					if (floorImage != null) g2d.drawImage(floorImage, x, y, 48, 48, null);
					
					if (isExit(r, c)) {
						// AUTO-ROTATE EXIT based on which wall it is on
						double angle = 0;
						if (c == size - 1) angle = 90;  // Right wall
						else if (r == size - 1) angle = 180; // Bottom wall
						else if (c == 0) angle = 270; // Left wall
						
						drawRotated(g2d, exitImage, x, y, angle);
					} 

					else if (isWall(r, c)) {
						// 1. PERMANENT OUTER FRAME LOGIC
						if (r == 0 && c == 0) drawRotated(g2d, cornerImage, x, y, 0); 
						else if (r == 0 && c == size - 1) drawRotated(g2d, cornerImage, x, y, 90);
						else if (r == size - 1 && c == size - 1) drawRotated(g2d, cornerImage, x, y, 180);
						else if (r == size - 1 && c == 0) drawRotated(g2d, cornerImage, x, y, 270);
						else if (r == 0) drawRotated(g2d, sideImage, x, y, 0); // Top wall
						else if (c == size - 1) drawRotated(g2d, sideImage, x, y, 90); // Right wall
						else if (r == size - 1) drawRotated(g2d, sideImage, x, y, 180); // Bottom wall
						else if (c == 0) drawRotated(g2d, sideImage, x, y, 270); // Left wall
						
						// 2. INTERNAL DYNAMIC LOGIC
						else {
							drawInternalWall(g2d, r, c, x, y);
						}
					}
				}
			}
		}

		private void drawInternalWall(Graphics2D g2d, int r, int c, int x, int y) {
			boolean up = isWall(r - 1, c);
			boolean down = isWall(r + 1, c);
			boolean left = isWall(r, c - 1);
			boolean right = isWall(r, c + 1);

			// Internal Corner logic
			if (right && down && !up && !left) drawRotated(g2d, cornerImage, x, y, 0);
			else if (left && down && !up && !right) drawRotated(g2d, cornerImage, x, y, 90);
			else if (left && up && !down && !right) drawRotated(g2d, cornerImage, x, y, 180);
			else if (right && up && !down && !left) drawRotated(g2d, cornerImage, x, y, 270);
			// Internal Straight logic
			else if (left || right) drawRotated(g2d, sideImage, x, y, 0);
			else if (up || down) drawRotated(g2d, sideImage, x, y, 90);
			else {
				g2d.setColor(new Color(0, 125, 0));
				g2d.fillRect(x, y, 48, 48);
			}
		}

		private void drawRotated(Graphics2D g2d, BufferedImage img, int x, int y, double angle) {
			if (img == null) return;
			AffineTransform old = g2d.getTransform();
			g2d.rotate(Math.toRadians(angle), x + 24, y + 24);
			g2d.drawImage(img, x, y, 48, 48, null);
			g2d.setTransform(old);
		}
	}




		// This is the old code in case new breaks
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

