package com.tacticalarena.game.panel;

import com.tacticalarena.game.handler.KeyHandler;
import com.tacticalarena.game.model.Bullet;
import com.tacticalarena.game.model.Enemy;
import com.tacticalarena.game.model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {
    private Player player;
    private Enemy enemy;
    private List<Bullet> bullets;
    private KeyHandler keyHandler;
    private boolean canShoot = true;
    private int shootCooldown = 0;
    private int damageCooldown = 0;

    public GamePanel() {
        this.keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        this.bullets = new ArrayList<>();

        setFocusable(true);
        requestFocus();

        // Initialize player
        player = new Player(100, 100);
        enemy = new Enemy(700, 100);

        setBackground(Color.BLACK);

        Timer timer = new Timer(16, e -> {
            update();
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }

        if (enemy != null) {
            enemy.draw(g);
        }
    }

    public void update() {

        updatePlayer();
        handleShooting();
        updateBullets();
        handleBulletCollisions();
        updateEnemy();
        handlePlayerDamage();
        updateCooldowns();
    }

    private void updatePlayer() {
        int oldX = player.getX();
        int oldY = player.getY();

        player.update(keyHandler, getHeight(), getWidth());

        if (enemy != null && isColliding(player, enemy)) {
            player.setX(oldX);
            player.setY(oldY);
        }
    }

    private boolean isColliding(Player player, Enemy enemy) {
        return player.getX() < enemy.getX() + enemy.getWidth() &&
                player.getX() + player.getWidth() > enemy.getX() &&
                player.getY() < enemy.getY() + enemy.getHeight() &&
                player.getY() + player.getHeight() > enemy.getY();
    }

    private void handleShooting() {
        if (keyHandler.shoot && shootCooldown == 0) {
            bullets.add(player.shoot());
            shootCooldown = 10;
        }
    }

    private void updateBullets() {
        for (Bullet bullet : bullets) {
            bullet.update();
        }
    }

    private void handleBulletCollisions() {
        if (enemy == null) return;

        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (
                    bullet.getX() > enemy.getX() &&
                    bullet.getX() < enemy.getX() + enemy.getWidth() &&
                    bullet.getY() > enemy.getY() &&
                    bullet.getY() < enemy.getY() + enemy.getHeight()
            ) {
                enemy.setHealth(enemy.getHealth() - 50);
                bullets.remove(i);
                i--;
            }
        }
    }

    private void updateEnemy() {
        if (enemy == null) return;

        if (enemy.isDead()) {
            enemy = null;
        } else {
            enemy.update(player);
        }
    }

    private void handlePlayerDamage() {
        if (enemy == null) return;

        if (
                player.getX() < enemy.getX() + enemy.getWidth() &&
                player.getX() + player.getWidth() > enemy.getX() &&
                player.getY() < enemy.getY() + enemy.getHeight() &&
                player.getY() + player.getHeight() > enemy.getY()

        ) {
            if (damageCooldown == 0) {
                player.setHealth(player.getHealth() - enemy.getDamage());
                damageCooldown = 30;
            }
        }
    }

    private void updateCooldowns() {
        if (shootCooldown > 0) shootCooldown--;
        if (damageCooldown > 0) damageCooldown--;
    }

    public void removeBullet() {
        bullets.removeIf(bullet ->
                bullet.getX() < 0 ||
                        bullet.getY() < 0 ||
                        bullet.getX() > getWidth() ||
                        bullet.getY() > getHeight()
        );
    }
}
