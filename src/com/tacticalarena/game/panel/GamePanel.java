package com.tacticalarena.game.panel;

import com.tacticalarena.game.handler.KeyHandler;
import com.tacticalarena.game.interfaces.Collidable;
import com.tacticalarena.game.manager.CollisionManager;
import com.tacticalarena.game.model.Bullet;
import com.tacticalarena.game.model.Enemy;
import com.tacticalarena.game.model.Player;
import com.tacticalarena.game.window.GameOverFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel {
    private Player player;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private KeyHandler keyHandler;
    private int shootCooldown = 0;
    private int damageCooldown = 0;
    private boolean gameOver = false;
    private int kills = 0;
    private static final int TILE_SIZE = 100;

    public GamePanel() {
        this.keyHandler = new KeyHandler();
        addKeyListener(keyHandler);
        this.bullets = new ArrayList<>();

        setFocusable(true);
        requestFocus();

        // Initialize player
        player = new Player(100, 100);
        enemies = new ArrayList<>();

        enemies.add(new Enemy(700, 100));

        enemies.add(new Enemy(900, 300));

        enemies.add(new Enemy(500, 500));

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

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (map[row][col] == 1) {
                    g.setColor(Color.GRAY);
                } else {
                    g.setColor(Color.DARK_GRAY);
                }
                g.fillRect(
                        col * TILE_SIZE,
                        row * TILE_SIZE,
                        TILE_SIZE,
                        TILE_SIZE
                );
            }
        }
        player.draw(g);
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }

        for (Enemy enemy : enemies) {
            enemy.draw(g);
        }

        if (gameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", getWidth() / 2 - 150, getHeight() / 2);
        }

        if (player.getHealth() > 50) {
            g.setColor(Color.GREEN);
        } else if (player.getHealth() > 20) {
            g.setColor(Color.ORANGE);
        } else {
            g.setColor(Color.RED);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Kills: " + kills, 20, 70);

        g.fillRect(20, 20, player.getHealth() * 2, 20);
    }

    public void update() {
        if (gameOver) return;

        updatePlayer();
        handleShooting();
        updateBullets();
        removeBullet();
        handleBulletCollisions();
        updateEnemies();
        handlePlayerDamage();
        updateCooldowns();

        if (player.isDead()) {
            new GameOverFrame();

            Window window = SwingUtilities.getWindowAncestor(this);
            window.dispose();
        }
    }

    private void updatePlayer() {
        int oldX = player.getX();
        int oldY = player.getY();
        player.update(keyHandler, getHeight(), getWidth());

        for (Enemy enemy : enemies) {
            if (CollisionManager.isColliding(player, enemy)) {

                if (damageCooldown == 0) {
                    player.setHealth(player.getHealth() - enemy.getDamage());
                    damageCooldown = 30;
                }

                player.setX(oldX);
                player.setY(oldY);

                break;
            }
        }

        if (
                isWall(player.getX(), player.getY()) ||
                isWall(player.getX() + player.getWidth(), player.getY()) ||
                isWall(player.getX(), player.getY() + player.getHeight()) ||
                isWall(player.getX() + player.getWidth(), player.getY() + player.getHeight())
        ) {
            player.setX(oldX);
            player.setY(oldY);
        }
    }

    private boolean isWall(int x, int y) {
        int col = x / TILE_SIZE;
        int row = y / TILE_SIZE;

        if (col < 0 || row < 0 || row >= map.length || col >= map[0].length) {
            return true;
        }

        return map[row][col] == 1;
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

        for (Enemy enemy : enemies) {

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

                    break;
                }
            }
        }
    }

    private void updateEnemies() {

        for (int i = 0; i < enemies.size(); i++) {

            Enemy enemy = enemies.get(i);

            if (enemy.isDead()) {
                kills++;
                enemies.remove(i);
                i--;
            } else {
                enemy.update(player);
            }
        }
    }

    private void handlePlayerDamage() {
        int range = 20;

        for (Enemy enemy : enemies) {

            if (
                    player.getX() < enemy.getX() + enemy.getWidth() + range &&
                            player.getX() + player.getWidth() > enemy.getX() - range &&
                            player.getY() < enemy.getY() + enemy.getHeight() + range &&
                            player.getY() + player.getHeight() > enemy.getY() - range
            ) {

                if (damageCooldown == 0) {

                    player.setHealth(
                            player.getHealth() - enemy.getDamage()
                    );

                    damageCooldown = 30;

                    int push = 10;

                    double dx = player.getX() - enemy.getX();
                    double dy = player.getY() - enemy.getY();

                    double length = Math.sqrt(dx * dx + dy * dy);

                    if (length != 0) {
                        dx /= length;
                        dy /= length;
                    }

                    player.setX(
                            (int) (player.getX() + dx * push)
                    );

                    player.setY(
                            (int) (player.getY() + dy * push)
                    );
                }
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

    private int[][] map = {
            {1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,1,1,0,0,0,1,1,0,0,1},
            {1,0,0,0,0,1,0,0,0,0,0,1},
            {1,0,0,1,0,1,0,1,0,0,0,1},
            {1,0,0,1,0,0,0,1,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,1,0,1},
            {1,0,1,0,1,0,1,0,0,1,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1}
    };
}
