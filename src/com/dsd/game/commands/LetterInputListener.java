package com.dsd.game.commands;

import com.dsd.game.Game;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class will be used for user input when asking for their username and
 * password. We'll write a custom textfield UI element instead of using the
 * lousy Java Swing ones.
 *
 * @author joshuacrotts
 */
public class LetterInputListener implements KeyListener {

    private final Game game;
    private char character;

    //  This stringbuilder is just for testing. In reality, we would be using
    //  a custom text-entry field with a StringBuilder object, and constantly
    //  concatenating to it.
    private StringBuilder textElement;

    public LetterInputListener(Game _game) {
        this.game = _game;
        this.game.addKeyListener(this);
        this.textElement = new StringBuilder();
    }

    @Override
    public void keyTyped(KeyEvent _e) {
        //  Retrieve the char last typed
        this.character = _e.getKeyChar();

        //  If it's the backspace key, delete the last inserted character into
        //  the stringbuilder. Otherwise, just append it.
        if (_e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            textElement.deleteCharAt(textElement.length() - 1);
        } else {
            textElement.append(this.character);
        }
    }

    @Override
    public void keyPressed(KeyEvent _e) {

    }

    @Override
    public void keyReleased(KeyEvent _e) {
    }

    public char getLastKeyTyped() {
        return this.character;
    }

    public String getTextElement() {
        return this.textElement.toString();
    }
}