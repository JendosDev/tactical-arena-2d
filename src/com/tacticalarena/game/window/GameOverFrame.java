package com.tacticalarena.game.window;

import javax.swing.*;
import java.awt.*;

public class GameOverFrame extends JFrame {

    public GameOverFrame() {
        setTitle("Game Over");

        JLabel label = new JLabel("GAME OVER", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 40));

        add(label);

        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}