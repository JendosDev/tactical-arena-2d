package com.tacticalarena.game.model;

import java.awt.*;

/**
 * Represents a projectile fired by the player.
 * Bullets travel in a specified direction and can damage enemies.
 */
public class Bullet {

    /**
     * Current bullet position.
     */
    private int x;
    private int y;

    /**
     * Direction vector of the bullet.
     */
    private int dx, dy;

    /**
     * Bullet movement speed.
     */
    private int speed;

    /**
     * Creates a bullet with the specified position and direction.
     *
     * @param x starting X coordinate
     * @param y starting Y coordinate
     * @param dx horizontal direction (-1, 0, 1)
     * @param dy vertical direction (-1, 0, 1)
     */
    public Bullet(int x, int y, int dx, int dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;

        this.speed = 10;
    }

    /**
     * Default constructor.
     */
    public Bullet() {
    }

    /**
     * Draws the bullet on the game panel.
     *
     * @param g graphics context
     */
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 15, 25);
    }

    /**
     * Updates the bullet position according to its direction and speed.
     */
    public void update() {
        x += dx * speed;
        y += dy * speed;
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    // endregion
}