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
 * <br> for example:
 * MainApp.main(new String[]{});
 * TODO: change-ben
 */
public class MainApp {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
		new MainApp().run();
		});
		}
	
	public void run() {
		GameWindow.show();
		// Hint: MainApp should not contain game logic or drawing code
		}
}