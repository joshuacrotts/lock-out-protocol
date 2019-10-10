/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game.userinterface.model;

import com.dsd.game.Game;
import com.dsd.game.commands.LetterInputListener;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MouseEventInterface;
import com.dsd.game.userinterface.view.TextFieldView;
import java.awt.Graphics2D;

/**
 * This class represents a simple text-field. If the user wants to enter text,
 * they click on the field to "activate" it, and then they can start entering
 * text. To disable it, click it once again.
 *
 * @author Joshua
 */
public class TextFieldModel extends Interactor implements MouseEventInterface {

    //  Reference variables
    private final Game game;
    private final MenuScreen menuScreen;
    private final TextFieldView view;

    //  Dimensions of text field.
    private final int FIELD_WIDTH = 600;
    private final int FIELD_HEIGHT = 30;
    private boolean fieldActive;

    private final LetterInputListener inputListener;
    private final StringBuilder string;

    public TextFieldModel (int _x, int _y, Game _game, MenuScreen _menuScreen) {
        super(_x, _y);
        this.game = _game;
        this.menuScreen = _menuScreen;
        this.fieldActive = false;
        this.setWidth(FIELD_WIDTH);
        this.setHeight(FIELD_HEIGHT);
        super.setX(this.getX() - this.getWidth() / 2);

        //  Instantiate the variables associated with the view of this model.
        this.view = new TextFieldView(this);
        this.string = new StringBuilder();
        this.inputListener = new LetterInputListener(_game, this);
    }

    @Override
    public void tick () {
    }

    @Override
    public void render (Graphics2D _g2) {
        this.view.render(_g2);
    }

    @Override
    public void onMouseClick () {
        this.fieldActive = !this.fieldActive;
    }

    @Override
    public void onMouseEnterHover () {
    }

    @Override
    public void onMouseExitHover () {
    }

    public void appendToString (char _c) {
        this.string.append(_c);
    }

    /**
     * If the last character inputted by the user was a backspace, we will
     * truncate the last char on the StringBuilder.
     */
    public void removeLastChar () {
        this.string.deleteCharAt(this.string.length() - 1);
    }

    public boolean isActive () {
        return this.fieldActive;
    }

    public boolean isEmpty () {
        return this.string.length() == 0;
    }

//================================= GETTERS ===================================//
    public Game getGame () {
        return this.game;
    }

    public MenuScreen getMenuScreen () {
        return this.menuScreen;
    }

    public String getString () {
        return this.string.toString();
    }
}
