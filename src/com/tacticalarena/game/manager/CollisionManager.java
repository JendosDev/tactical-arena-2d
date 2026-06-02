package com.tacticalarena.game.manager;

import com.tacticalarena.game.model.Enemy;

import com.tacticalarena.game.model.Player;

/**
 * Utility class responsible for collision detection
 * between game objects.
 */
public class CollisionManager {
    /**
     * Checks whether the player and enemy overlap.
     *
     * @param player player object
     * @param enemy enemy object
     * @return true if the objects collide, otherwise false
     */
    public static boolean isColliding(Player player, Enemy enemy) {
        return player.getX() < enemy.getX() + enemy.getWidth() &&
                player.getX() + player.getWidth() > enemy.getX() &&
                player.getY() < enemy.getY() + enemy.getHeight() &&
                player.getY() + player.getHeight() > enemy.getY();
    }
}