package com.tacticalarena.game.window;

import com.tacticalarena.game.panel.GamePanel;

import javax.swing.*;

/**
 * Represents the main game window.
 * This window contains the GamePanel where the gameplay takes place.
 */
public class GameFrame extends JFrame {

    /**
     * Main game panel containing game logic and rendering.
     */
    private GamePanel gamePanel;

    /**
     * Creates and initializes the game window.
     */
    public GameFrame() {
        this.gamePanel = new GamePanel();

        setContentPane(this.gamePanel);
        setTitle("Tactical Arena – InGame");

        setSize(1280, 720);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}