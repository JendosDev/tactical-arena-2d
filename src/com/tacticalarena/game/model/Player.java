package com.tacticalarena.game.model;

import com.tacticalarena.game.handler.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player {
    private BufferedImage currentImage;
    private BufferedImage
            upImage,
            downImage,
            rightImage,
            leftImage;
    private int x;
    private int y;
    private int width;
    private int height;
    private int speed;
    private int health;
    private int dirX = 0;
    private int dirY = -1;

    public Player(int x, int y) {
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

        this.x = x;
        this.y = y;
        this.width = 150;
        this.height = 150;
        this.speed = 5;
        this.health = 100;
    }

    public void update(KeyHandler keyHandler, int panelHeight, int panelWidth) {

        // speed set
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

        if (dirX == 0 && dirY == -1) currentImage = upImage;
        if (dirX == 0 && dirY == 1) currentImage = downImage;
        if (dirX == -1 && dirY == 0) currentImage = leftImage;
        if (dirX == 1 && dirY == 0) currentImage = rightImage;

        // clamp
        if (y < 0) y = 0;
        if (x < 0) x = 0;

        if (y + height >  panelHeight) y = panelHeight - height;
        if (x + width > panelWidth) x = panelWidth - width;
    }

    public void draw(Graphics g) {
        if (currentImage != null) {
            g.drawImage(currentImage, x, y, width, height, null);
        } else {
            g.setColor(Color.RED);
            g.fillRect(x, y, width, height);
        }
    }

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
        this.health = health;
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
