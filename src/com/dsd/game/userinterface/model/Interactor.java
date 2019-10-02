package com.dsd.game.userinterface.model;

import com.dsd.game.userinterface.MouseEventInterface;
import com.revivedstandards.view.Renderable;
import com.revivedstandards.view.Updatable;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * This class is a template for any type of UI element within menus, submenus,
 * etc. Buttons, labels, text, menu bars, or anything else can be classified as
 * an Interactor.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public abstract class Interactor implements Renderable, Updatable, MouseEventInterface {

    private int x;
    private int y;
    private int velX;
    private int velY;
    private int width;
    private int height;

    private boolean interactable = true;

    public Interactor () {
    }

    public Interactor (int _x, int _y) {
        this.x = _x;
        this.y = _y;
    }

    public Interactor (int _x, int _y, boolean _interactable) {
        this(_x, _y);
        this.interactable = _interactable;
    }

    public Interactor (int _x, int _y, int _width, int _height) {
        this(_x, _y);
        this.width = _width;
        this.height = _height;
    }

    public Interactor (int x, int y, int width, int height, boolean interactable) {
        this(x, y, width, height);
        this.interactable = interactable;
    }

    @Override
    public abstract void tick ();

    @Override
    public abstract void render (Graphics2D _g2);

    @Override
    public abstract void onMouseClick ();

    @Override
    public abstract void onMouseEnterHover ();

    @Override
    public abstract void onMouseExitHover ();

<<<<<<< HEAD
    public Rectangle getBounds () {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

//============================ GETTERS =================================//
    public int getX () {
=======
//============================ GETTERS =================================//
    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }

    public int getX() {
>>>>>>> 6b258bb2223bdf058e59bdeddcdfe6b064a3bd15
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

    public void setX (int _x) {
        this.x = _x;
    }

    public void setY (int _y) {
        this.y = _y;
    }

    public void setVelX (int _velX) {
        this.velX = _velX;
    }

    public void setVelY (int _velY) {
        this.velY = _velY;
    }

    public void setWidth (int _width) {
        this.width = _width;
    }

    public void setHeight (int _height) {
        this.height = _height;
    }

    public void setInteractable (boolean _interactable) {
        this.interactable = _interactable;
    }
}
