package com.tacticalarena.game.window;

import com.tacticalarena.game.panel.MenuPanel;

import javax.swing.*;

/**
 * Represents the main menu window of the application.
 * This window is displayed when the game starts.
 */
public class MenuFrame extends JFrame {

    /**
     * Main menu panel.
     */
    private MenuPanel panel;

    /**
     * Creates and initializes the main menu window.
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
     * Configures basic window properties.
     */
    public void configureFrame() {
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    // region get, set

    /**
     * Returns the menu panel.
     *
     * @return menu panel instance
     */
    public MenuPanel getPanel() {
        return panel;
    }

    /**
     * Sets the menu panel.
     *
     * @param panel new menu panel
     */
    public void setPanel(MenuPanel panel) {
        this.panel = panel;
    }

    // endregion
}