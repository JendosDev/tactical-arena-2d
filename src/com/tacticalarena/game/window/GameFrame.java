package com.tacticalarena.game.window;

import com.tacticalarena.game.panel.GamePanel;

import javax.swing.*;
import java.awt.*;

/**
 * Class GameFrame represents the main game window.
 */
public class GameFrame extends JFrame {
    /**
     * Panel
     */
    private GamePanel gamePanel;

    public GameFrame() {
        this.gamePanel = new GamePanel();
        setContentPane(this.gamePanel);
        setTitle("Tactical Arena – InGame");

        // Set window size
        setSize(800, 600);

        // Center window on screen
        setLocationRelativeTo(null);

        // Disable resizing
        setResizable(false);

        // Close application on exit
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Make window visible
        setVisible(true);
    }
}
