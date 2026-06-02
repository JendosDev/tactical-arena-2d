package com.tacticalarena.game.model;

import com.tacticalarena.game.interfaces.Collidable;

import java.awt.*;

/**
 * Represents an enemy character in the game.
 * The enemy follows the player and can deal damage
 * when it gets close enough.
 */
public class Enemy extends GameObject implements Collidable {

    /**
     * Current enemy health.
     */
    private int health;

    /**
     * Enemy movement speed.
     */
    private int speed;

    /**
     * Amount of damage dealt to the player.
     */
    private int damage;

    /**
     * Creates a new enemy at the specified position.
     *
     * @param x initial X coordinate
     * @param y initial Y coordinate
     */
    public Enemy(int x, int y) {
        super(x, y, 90, 90);
        this.health = 1000;
        this.speed = 1;
        this.damage = 10;
    }

    /**
     * Updates enemy movement towards the player.
     *
     * @param player target player
     */
    public void update(Player player) {
        if (health <= 0) {
            health = 0;
        }

        int dx = player.getX() - x;
        int dy = player.getY() - y;

        if (Math.abs(dx) > 100) {
            if (dx > 0) x += speed;
            else x -= speed;
        }

        if (Math.abs(dy) > 100) {
            if (dy > 0) y += speed;
            else y -= speed;
        }
    }

    /**
     * Draws the enemy on the game panel.
     *
     * @param g graphics context
     */
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }

    /**
     * Checks whether the enemy has been defeated.
     *
     * @return true if health is zero or below
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    // endregion

    /**
     * Checks collision between this enemy and another rectangular object.
     *
     * @param x object's X coordinate
     * @param y object's Y coordinate
     * @param width object's width
     * @param height object's height
     * @return true if the objects overlap
     */
    @Override
    public boolean isColliding(int x, int y, int width, int height) {
        return this.x < x + width &&
                this.x + this.width > x &&
                this.y < y + height &&
                this.y + this.height > y;
    }
}