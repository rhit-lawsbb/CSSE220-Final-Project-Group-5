package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Class: Gun 
 * @author Group 5
 * <br>Purpose:
 * <br>Restrictions:
 * <br>For Example:
 * <pre>
 * 		
 * </pre>
 */

public class Gun extends Pickup {

    public Gun(int col, int row) {
        super(col, row);
        try {
            setSprite(ImageIO.read(getClass().getResource("Gun.png")));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Gun image error: " + e.getMessage());
            setSprite(null);
        }
    }

    public void respawn(int col, int row) {
        setX(col * Maze.TILE_SIZE);
        setY(row * Maze.TILE_SIZE);
        setActive(true);
    }
}
