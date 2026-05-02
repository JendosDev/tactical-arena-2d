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
        player.update(keyHandler, getHeight(), getWidth());

        if (keyHandler.shoot && shootCooldown == 0) {
            bullets.add(player.shoot());
            shootCooldown = 10;
        }

        if (!keyHandler.shoot) {
            canShoot = true;
        }

        if (enemy != null) {
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

        for (Bullet bullet : bullets) {
            bullet.update();
        }

        if (enemy != null) {
            if (
                player.getX() < enemy.getX() + enemy.getWidth() &&
                player.getX() + player.getWidth() > enemy.getX() &&
                player.getY() < enemy.getY() + enemy.getHeight() &&
                player.getY() + player.getHeight() > enemy.getY()
            ) {
                player.setHealth(player.getHealth() - enemy.getDamage());
                shootCooldown = 30;
            }
            if (enemy.isDead()) {
                enemy = null;
            } else {
                enemy.update(player);
            }
        }

        if (shootCooldown > 0) {
            shootCooldown--;
        }
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
