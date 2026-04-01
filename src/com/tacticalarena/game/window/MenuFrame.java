package com.tacticalarena.game.window;

import javax.swing.*;

/**
 * Class MenuFrame represents the main menu window of the application.
 * This window is displayed, when the application starts.
 */
public class MenuFrame {
    /**
     * Frame
     */
    private JFrame frame;

    /**
     * Constructor creates and configures the main menu window.
     */
    public MenuFrame() {
        this.frame = new JFrame("Tactical Arena – Menu");
        configureFrame();
    }

    /**
     * Configures basic frame properties
     */
    public void configureFrame() {
        // Set window size
        frame.setSize(800, 600);

        // Exit application when window is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Center the window on the screen
        frame.setLocationRelativeTo(null);

        // Turns off resize option
        frame.setResizable(false);

        // Make window visible
        frame.setVisible(true);
    }

    // region get, set

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    // endregion
}

