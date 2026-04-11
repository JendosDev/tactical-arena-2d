package com.tacticalarena.game.model;

import java.awt.*;

public class Enemy {
    private int x;
    private int y;
    private int width;
    private int height;
    private int health;
    private int speed;
    private int damage;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 100;
        this.height = 100;
        this.health = 2000;
        this.speed = 5;
        this.damage = 10;
    }

    public void update(Player player) {
        if (health <= 0) {
            health = 0;
        }

        int dx = player.getX() - x;
        int dy = player.getY() - y;

        if (Math.abs(dx) > 50) {
            if (dx > 0) x += speed;
            else x -= speed;
        }
        if (Math.abs(dy) > 50) {
            if (dy > 0) y += speed;
            else y -= speed;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
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
}
