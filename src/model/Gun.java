package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Class: Gun 
 * @author Group 5
 * <br>Purpose: represents a pickup the player can use, is a gun
 * <br>Restrictions: only active when spawned
 * <br>For Example:
 * <pre>
 * 		Gun gun = new Gun(1,2);
 * </pre>
 */

public class Gun extends Pickup {

	/**
	 * creates gun at location
	 * @param col column location of spawn
	 * @param row row location of spawn
	 */
    public Gun(int col, int row) {
        super(col, row);
        try {
            setSprite(ImageIO.read(getClass().getResource("Gun.png")));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Gun image error: " + e.getMessage());
            setSprite(null);
        }
    }

    /**
     * ensures: moves gun to new locations
     * @param col new column location of spawn
     * @param row new row location of spawn
     */
    public void respawn(int col, int row) {
        setX(col * Maze.TILE_SIZE);
        setY(row * Maze.TILE_SIZE);
        setActive(true);
    }
}
