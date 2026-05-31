package com.tacticalarena.game.manager;

import com.tacticalarena.game.interfaces.Collidable;
import com.tacticalarena.game.model.Enemy;
import com.tacticalarena.game.model.Player;

public class CollisionManager {
    public static boolean isColliding(Player player, Enemy enemy) {
        return player.getX() < enemy.getX() + enemy.getWidth() &&
                player.getX() + player.getWidth() > enemy.getX() &&
                player.getY() < enemy.getY() + enemy.getHeight() &&
                player.getY() + player.getHeight() > enemy.getY();
    }
}