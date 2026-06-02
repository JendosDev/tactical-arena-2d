package com.tacticalarena.game.interfaces;

/**
 * Interface for game objects that can participate
 * in collision detection.
 */
public interface Collidable {

    /**
     * Checks collision between this object and another
     * rectangular object.
     *
     * @param x x coordinate of the other object
     * @param y y coordinate of the other object
     * @param width width of the other object
     * @param height height of the other object
     * @return true if the objects overlap, otherwise false
     */
    boolean isColliding(int x, int y, int width, int height);
}
