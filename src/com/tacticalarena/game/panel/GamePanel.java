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

    public GamePanel() {
        this.keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        this.bullets = new ArrayList<>();

        setFocusable(true);
        requestFocus();

        // Initialize player
        player = new Player(100, 100);
        enemy = new Enemy(300, 200);

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

        if (keyHandler.shoot && canShoot) {
            bullets.add(player.shoot());
            canShoot = false;
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

            if (enemy.isDead()) {
                enemy = null;
            } else {
                enemy.update(player);
            }
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
