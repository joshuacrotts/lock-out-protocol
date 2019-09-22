package com.dsd.game.userinterface.model;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * This class is a template for any type of UI element within menus, submenus,
 * etc. Buttons, labels, text, menu bars, or anything else can be classified as
 * an Interactor.
 *
 * @author Joshua
 */
public abstract class Interactor {

    private int x;
    private int y;
    private int velX;
    private int velY;
    private int width;
    private int height;

    private boolean interactable = true;

    public Interactor () {
    }

    public Interactor (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Interactor (int x, int y, boolean interactable) {
        this(x, y);
        this.interactable = interactable;
    }

    public Interactor (int x, int y, int width, int height) {
        this(x, y);
        this.width = width;
        this.height = height;
    }

    public Interactor (int x, int y, int width, int height, boolean interactable) {
        this(x, y, width, height);
        this.interactable = interactable;
    }

    public abstract void tick ();

    public abstract void render (Graphics2D paramGraphics2D);

    public Rectangle getBounds () {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

//============================ GETTERS =================================//
    public int getX () {
        return this.x;
    }

    public int getY () {
        return this.y;
    }

    public int getVelX () {
        return this.velX;
    }

    public int getVelY () {
        return this.velY;
    }

    public int getWidth () {
        return this.width;
    }

    public int getHeight () {
        return this.height;
    }

    public boolean isInteractable () {
        return this.interactable;
    }
//============================ SETTERS =================================//

    public void setX (int x) {
        this.x = x;
    }

    public void setY (int y) {
        this.y = y;
    }

    public void setVelX (int velX) {
        this.velX = velX;
    }

    public void setVelY (int velY) {
        this.velY = velY;
    }

    public void setWidth (int width) {
        this.width = width;
    }

    public void setHeight (int height) {
        this.height = height;
    }

    public void setInteractable (boolean interactable) {
        this.interactable = interactable;
    }
}
