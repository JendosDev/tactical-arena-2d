package com.tacticalarena.game.panel;

import com.tacticalarena.game.handler.KeyHandler;
import com.tacticalarena.game.interfaces.Collidable;
import com.tacticalarena.game.manager.CollisionManager;
import com.tacticalarena.game.model.Bullet;
import com.tacticalarena.game.model.Enemy;
import com.tacticalarena.game.model.Player;
import com.tacticalarena.game.window.GameOverFrame;
import com.tacticalarena.game.window.VictoryFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Main game panel of the Tactical Arena application.
 * Handles rendering, player input, collisions,
 * enemy behavior and overall game logic.
 */
public class GamePanel extends JPanel {
    private Player player;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private KeyHandler keyHandler;
    private int shootCooldown = 0;
    private int damageCooldown = 0;
    private boolean gameOver = false;
    private int kills = 0;
    private boolean victory = false;
    /**
     * Size of one map tile in pixels.
     */
    private static final int TILE_SIZE = 100;

    /**
     * Arena map.
     * 0 = floor
     * 1 = wall
     */
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

    /**
     * Creates and initializes the game panel,
     * player, enemies and the game loop.
     */
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

        enemies.add(new Enemy(500, 500));

        setBackground(Color.BLACK);

        Timer timer = new Timer(16, e -> {
            update();
            repaint();
        });
        timer.start();
    }

    /**
     * Draws all game objects and user interface elements.
     *
     * @param g graphics context
     */
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

    /**
     * Updates the entire game state.
     */
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

        if (enemies.isEmpty() && !victory) {

            victory = true;

            new VictoryFrame();

            Window window = SwingUtilities.getWindowAncestor(this);

            window.dispose();

        }

        if (player.isDead() && !gameOver) {
            gameOver = true;

            new GameOverFrame();

            Window window = SwingUtilities.getWindowAncestor(this);
            window.dispose();
        }
    }

    /**
     * Updates player movement and handles collisions
     * with walls and enemies.
     */
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

    /**
     * Checks whether the specified position
     * contains a wall tile.
     * @param x x coordinate
     * @param y y coordinate
     * @return true if the position contains a wall
     */
    private boolean isWall(int x, int y) {
        int col = x / TILE_SIZE;
        int row = y / TILE_SIZE;

        if (col < 0 || row < 0 || row >= map.length || col >= map[0].length) {
            return true;
        }

        return map[row][col] == 1;
    }

    /**
     * Handles player shooting and bullet creation.
     */
    private void handleShooting() {
        if (keyHandler.shoot && shootCooldown == 0) {
            bullets.add(player.shoot());
            shootCooldown = 10;
        }
    }

    /**
     * Updates all active bullets.
     */
    private void updateBullets() {
        for (Bullet bullet : bullets) {
            bullet.update();
        }
    }

    /**
     * Detects bullet collisions with enemies
     * and applies damage.
     */
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

    /**
     * Updates all enemies and removes defeated enemies.
     */
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

    /**
     * Applies damage to the player when an enemy
     * is within attack range.
     */
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

                    int push = 0;

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

    /**
     * Updates active cooldown timers.
     */
    private void updateCooldowns() {
        if (shootCooldown > 0) shootCooldown--;
        if (damageCooldown > 0) damageCooldown--;
    }

    /**
     * Removes bullets that leave the visible game area.
     */
    public void removeBullet() {
        bullets.removeIf(bullet ->
                bullet.getX() < 0 ||
                        bullet.getY() < 0 ||
                        bullet.getX() > getWidth() ||
                        bullet.getY() > getHeight()
        );
    }
}
