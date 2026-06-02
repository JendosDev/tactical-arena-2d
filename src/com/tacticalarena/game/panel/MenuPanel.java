package com.tacticalarena.game.panel;

import com.tacticalarena.game.window.GameFrame;
import com.tacticalarena.game.window.MenuFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the main menu panel of the application.
 * Allows the player to start the game or exit the application.
 */
public class MenuPanel extends JPanel {

    /**
     * Reference to the menu window.
     */
    private MenuFrame menuFrame;

    /**
     * Creates and initializes the main menu panel.
     *
     * @param menuFrame parent menu window
     */
    public MenuPanel(MenuFrame menuFrame) {
        this.menuFrame = menuFrame;

        setPreferredSize(new Dimension(800, 600));
        initUI();
    }

    /**
     * Initializes all graphical user interface components.
     */
    public void initUI() {
        setLayout(null);

        createTitle();
        createButtons();
    }

    /**
     * Creates and displays the game title.
     */
    public void createTitle() {
        JLabel titleLabel = new JLabel("Tactical Arena");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 34));
        titleLabel.setBounds(280, 20, 400, 50);
        add(titleLabel);
    }

    /**
     * Creates menu buttons and assigns their actions.
     */
    public void createButtons() {
        JButton startButton = new JButton("Start Game");
        startButton.setBounds(300, 200, 200, 50);
        startButton.setFont(new Font("Arial", Font.BOLD, 20));
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        startButton.addActionListener(e -> {
            new GameFrame();
            menuFrame.dispose();
        });

        add(startButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(300, 450, 200, 50);
        exitButton.setFont(new Font("Arial", Font.BOLD, 20));
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        exitButton.addActionListener(e -> System.exit(0));

        add(exitButton);
    }
}