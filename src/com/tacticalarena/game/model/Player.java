package com.tacticalarena.game.model;

import com.tacticalarena.game.handler.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents the player controlled by the user.
 * Handles movement, shooting, health management
 * and rendering of the player sprite.
 */
public class Player extends GameObject {
    /**
     * Currently displayed player sprite.
     */
    private BufferedImage currentImage;
    private BufferedImage
            upImage,
            downImage,
            rightImage,
            leftImage;

    /**
     * Player movement speed.
     */
    private int speed;

    /**
     * Current player health.
     */
    private int health;
    private int dirX = 0;
    private int dirY = -1;

    /**
     * Creates a new player at the specified position
     * and loads all player sprites.
     *
     * @param x initial X coordinate
     * @param y initial Y coordinate
     */
    public Player(int x, int y) {
        super(x, y, 80, 80);

        try {
            upImage = javax.imageio.ImageIO.read(
                    getClass().getResourceAsStream("/player-image-up-1.png")
            );
            downImage = javax.imageio.ImageIO.read(
                    getClass().getResourceAsStream("/player-image-down-1.png")
            );
            leftImage = javax.imageio.ImageIO.read(
                    getClass().getResourceAsStream("/player-image-left-1.png")
            );
            rightImage = javax.imageio.ImageIO.read(
                    getClass().getResourceAsStream("/player-image-right-1.png")
            );

            currentImage = downImage;
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }

        this.speed = 5;
        this.health = 100;
    }


    /**
     * Updates player movement, sprite direction
     * and keeps the player inside the game area.
     *
     * @param keyHandler keyboard input handler
     * @param panelHeight game panel height
     * @param panelWidth game panel width
     */
    public void update(KeyHandler keyHandler, int panelHeight, int panelWidth) {
        playerMovement(keyHandler);
        loadPlayer();

        if (y < 0) y = 0;
        if (x < 0) x = 0;

        if (y + height > panelHeight) y = panelHeight - height;
        if (x + width > panelWidth) x = panelWidth - width;
    }

    /**
     * Processes keyboard input and updates
     * player position and facing direction.
     *
     * @param keyHandler keyboard input handler
     */
    private void playerMovement(KeyHandler keyHandler) {
        if (keyHandler.up) {
            y -= speed;
            dirX = 0;
            dirY = -1;
        } else if (keyHandler.down) {
            y += speed;
            dirX = 0;
            dirY = 1;
        } else if (keyHandler.left) {
            x -= speed;
            dirX = -1;
            dirY = 0;
        } else if (keyHandler.right) {
            x += speed;
            dirX = 1;
            dirY = 0;
        }
    }

    /**
     * Updates the displayed player sprite
     * according to the current direction.
     */
    private void loadPlayer() {
        if (dirX == 0 && dirY == -1) currentImage = upImage;
        if (dirX == 0 && dirY == 1) currentImage = downImage;
        if (dirX == -1 && dirY == 0) currentImage = leftImage;
        if (dirX == 1 && dirY == 0) currentImage = rightImage;
    }

    /**
     * Draws the player on the game panel.
     *
     * @param g graphics context
     */
    public void draw(Graphics g) {
        if (currentImage != null) {
            g.drawImage(currentImage, x, y, width, height, null);
        } else {
            g.setColor(Color.RED);
            g.fillRect(x, y, width, height);
        }
    }

    /**
     * Creates a bullet travelling in the
     * direction the player is facing.
     *
     * @return newly created bullet
     */
    public Bullet shoot() {
        int bulletX = x;
        int bulletY = y;

        if (dirX == 1) {
            bulletX = x + width;
            bulletY = y + height / 2;
        } else if (dirX == -1) {
            bulletX = x;
            bulletY = y + height / 2;
        } else if (dirY == -1) {
            bulletX = x + width / 2;
            bulletY = y;
        } else if (dirY == 1) {
            bulletX = x + width / 2;
            bulletY = y + height;
        }

        return new Bullet(bulletX, bulletY, dirX, dirY);
    }

    /**
     * Checks whether the player has been defeated.
     *
     * @return true if player health is zero or lower
     */
    public boolean isDead() {
        return health <= 0;
    }

    // region get, set

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = Math.max(0, health);
    }

    public int getDirX() {
        return dirX;
    }

    public void setDirX(int dirX) {
        this.dirX = dirX;
    }

    public int getDirY() {
        return dirY;
    }

    public void setDirY(int dirY) {
        this.dirY = dirY;
    }

    // endregion
}
