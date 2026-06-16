package com.tacticalarena.game.window;

import javax.swing.*;
import java.awt.*;

/**
 * Window displayed after the player wins the game.
 */
public class VictoryFrame extends JFrame {

    public VictoryFrame() {
        setTitle("Victory");

        JLabel label = new JLabel("YOU WIN!", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 40));

        add(label);

        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}