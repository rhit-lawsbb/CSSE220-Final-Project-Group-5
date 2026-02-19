package app;

import javax.swing.SwingUtilities;

import ui.GameWindow;

/**
 * Class: MainApp
 * @author Group 5
 * <br>Purpose: Top level class for CSSE220 Project containing main method 
 * Entry point for the final project.
 * Launches game window and starts the ui overlay
 * <br>Restrictions: No game or render logic is in this class,
 * only responsibility in this class is starting the app
 * <br>For example:
 * <pre>
 * MainApp.main(new String[]{});
 * </pre>
 */
public class MainApp {
	/**
	 * ensures: launches the app by calling to create the ui
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
		new MainApp().run();
		});
		}
	
	/**
	 * ensures: creates and shows the game window
	 */
	public void run() {
		GameWindow.show();
		// Hint: MainApp should not contain game logic or drawing code
		}
}