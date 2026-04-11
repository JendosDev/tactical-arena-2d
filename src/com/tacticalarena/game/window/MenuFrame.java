package com.tacticalarena.game.window;

import com.tacticalarena.game.panel.MenuPanel;

import javax.swing.*;

/**
 * Class MenuFrame represents the main menu window of the application.
 * This window is displayed, when the application starts.
 */
public class MenuFrame extends JFrame {
    /**
     * Panel
     */
    private MenuPanel panel;

    /**
     * Constructor creates and configures the main menu window.
     */
    public MenuFrame() {
        setTitle("Tactical Arena – Menu");
        this.panel = new MenuPanel(this);

        add(panel);
        setContentPane(panel);

        panel.initUI();
        configureFrame();
    }

    /**
     * Configures basic frame properties
     */
    public void configureFrame() {
        // Set window size
        setSize(800, 600);

        // Exit application when window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Center the window on the screen
        setLocationRelativeTo(null);

        // Turns off resize option
        setResizable(false);

        // Make window visible
        setVisible(true);
    }

    // region get, set

    public MenuPanel getPanel() {
        return panel;
    }

    public void setPanel(MenuPanel panel) {
        this.panel = panel;
    }


    // endregion
}

