package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.GameModel;
import model.Maze;

public class HUD {
    private GameModel model;
    private BufferedImage heartSprite;

    public HUD(GameModel model) {
        this.model = model;
        try {
            this.heartSprite = ImageIO.read(getClass().getResource("Health_region.png"));
        } catch (IOException | IllegalArgumentException e) {
            this.heartSprite = null;
        }
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, Maze.TILE_SIZE * 10, 50);

        g.setFont(new Font("Arial", Font.BOLD, 14));

        if (heartSprite != null) {
            for (int i = 0; i < model.getLives(); i++) {
                g.drawImage(heartSprite, 10 + (i * 35), 5, 30, 30, null);
            }
        }

        g.setColor(Color.WHITE);
        g.drawString("Lvl " + model.getLevel(), 120, 20);

        g.setColor(Color.YELLOW);
        g.drawString("Gold: " + model.getScore() + "/" + model.getCoinsRequired(), 120, 40);

        if (model.getPlayer().hasGun()) {
            g.setColor(Color.WHITE);
            g.drawString("Ammo: " + model.getPlayer().getAmmo(), 300, 20);
        }
    }
}
