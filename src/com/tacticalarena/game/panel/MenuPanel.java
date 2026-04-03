package com.tacticalarena.game.panel;

import com.tacticalarena.game.window.GameFrame;
import com.tacticalarena.game.window.MenuFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class MenuPanel represents the main menu panel of the application
 */
public class MenuPanel extends JPanel {
    /**
     * Frame
     */
    private MenuFrame menuFrame;

    /**
     * Constructor creates and configures the main menu panel.
     */
    public MenuPanel(MenuFrame menuFrame) {
        this.menuFrame = menuFrame;

        setPreferredSize(new Dimension(800, 600));
        initUI();
    }

    public void initUI() {
        setLayout(null);

        createTitle();
        createButtons();
    }

    public void createTitle() {
        JLabel titleLabel = new JLabel("Tactical Arena");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 34));
        titleLabel.setBounds(280, 20, 400, 50);
        add(titleLabel);
    }

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
        exitButton.addActionListener(e -> System.exit(0));
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(exitButton);
    }
}
