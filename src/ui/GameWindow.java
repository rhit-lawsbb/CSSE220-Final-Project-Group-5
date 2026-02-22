package ui;

import javax.swing.JFrame;

import model.GameModel;

/**
 * Class: GameWindow 
 * @author Group 5
 * <br>Purpose: creates game window and shows it
 * <br>Restrictions: depends on GameModel/GameComponent
 * <br>For Example:
 * <pre>
 * 		GameWindow.show();
 * </pre>
 */

public class GameWindow{

	/**
	 * ensures: makes game model and frame, displays game window
	 */
	public static void show() {
		// Minimal model instance (empty for now, by design)
		GameModel model = new GameModel();


		JFrame frame = new JFrame("CSSE220 Final Project");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		frame.add(new GameComponent(model));


		frame.setSize(496,519);
		frame.setLocationRelativeTo(null); // center on screen (nice UX, still minimal)
		frame.setVisible(true);
		}

}
