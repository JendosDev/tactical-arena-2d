package com.tacticalarena.game.window;

import javax.swing.*;

/**
 * Class GameFrame represents the main game window.
 */
public class GameFrame extends JFrame {
    public GameFrame() {
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
