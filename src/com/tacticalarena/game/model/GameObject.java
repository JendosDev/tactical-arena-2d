package com.tacticalarena.game.model;

/**
 * Abstract base class for all game objects.
 * Stores position and size information shared
 * by game entities such as the player and enemies.
 */
public abstract class GameObject {

    /**
     * X coordinate of the object.
     */
    protected int x;

    /**
     * Y coordinate of the object.
     */
    protected int y;

    /**
     * Width of the object.
     */
    protected int width;

    /**
     * Height of the object.
     */
    protected int height;

    /**
     * Creates a game object with the specified position and size.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @param width object width
     * @param height object height
     */
    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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

    // endregion
}