package com.dsd.game.commands;

import com.dsd.game.Game;
import com.dsd.game.userinterface.model.TextFieldModel;
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
    private final TextFieldModel textElement;
    private char character;

    public LetterInputListener (Game _game, TextFieldModel _textElement) {
        this.game = _game;
        this.game.addKeyListener(this);
        this.textElement = _textElement;
    }

    @Override
    public void keyTyped (KeyEvent _e) {
        if (!this.textElement.isActive()) {
            return;
        }

        //  Retrieve the char last typed
        this.character = _e.getKeyChar();

        //  If it's the backspace key, delete the last inserted character into
        //  the stringbuilder. Otherwise, just append it.
        if (this.character == KeyEvent.VK_BACK_SPACE) {
            if (!this.textElement.isEmpty()) {
                textElement.removeLastChar();
            }
        }
        else {
            textElement.appendToString(this.character);
        }
    }

    @Override
    public void keyPressed (KeyEvent _e) {
    }

    @Override
    public void keyReleased (KeyEvent _e) {
    }

    public char getLastKeyTyped () {
        return this.character;
    }

    public String getTextElement () {
        return this.textElement.toString();
    }
}
