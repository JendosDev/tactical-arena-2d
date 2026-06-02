package com.tacticalarena.game.handler;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Handles keyboard input for player movement and shooting.
 * Stores the current state of pressed keys.
 */
public class KeyHandler extends KeyAdapter {

    /**
     * Movement key states.
     */
    public boolean up, down, left, right;

    /**
     * Shooting key state.
     */
    public boolean shoot;

    /**
     * Creates a new keyboard input handler.
     */
    public KeyHandler() {
    }

    /**
     * Sets key states when a key is pressed.
     *
     * @param e keyboard event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) up = true;
        if (e.getKeyCode() == KeyEvent.VK_S) down = true;
        if (e.getKeyCode() == KeyEvent.VK_A) left = true;
        if (e.getKeyCode() == KeyEvent.VK_D) right = true;
        if (e.getKeyCode() == KeyEvent.VK_SPACE) shoot = true;
    }

    /**
     * Resets key states when a key is released.
     *
     * @param e keyboard event
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) up = false;
        if (e.getKeyCode() == KeyEvent.VK_S) down = false;
        if (e.getKeyCode() == KeyEvent.VK_A) left = false;
        if (e.getKeyCode() == KeyEvent.VK_D) right = false;
        if (e.getKeyCode() == KeyEvent.VK_SPACE) shoot = false;
    }
}