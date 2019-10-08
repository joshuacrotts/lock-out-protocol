package com.dsd.game.userinterface.model;

import com.revivedstandards.util.StdOps;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * This class represents a standard button model. It can be used as a template
 * to make more complex buttons or UI elements. However, buttons should ONLY be
 * used for changing the state of something.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public abstract class StandardButton extends Interactor {

    //  State of button
    private boolean isImage = false;
    //  Information regarding button's contents
    private String text;
    private Color color;
    private String fileLocation;
    private BufferedImage image;

    public StandardButton () {

    }

    public StandardButton (int _x, int _y) {
        super(_x, _y);
    }

    public StandardButton (int _x, int _y, int _width, int _height) {
        super(_x, _y, _width, _height);
    }

    public StandardButton (int _x, int _y, int _width, int _height, Color _color) {
        super(_x, _y, _width, _height);
        this.color = _color;
    }

    public StandardButton (int _x, int _y, int _width, int _height, String _text, Color _color) {
        this(_x, _y, _width, _height);
        this.text = _text;
        this.color = _color;
    }

    public StandardButton (int _x, int _y, int _width, int _height, int _red, int _green, int _blue) {
        super(_x, _y, _width, _height);
        this.color = new Color(_red, _green, _blue);
    }

    public StandardButton (int _x, int _y, String fileLocation) {
        super(_x, _y);
        this.image = StdOps.loadImage(fileLocation);
        this.setWidth(this.image.getWidth());
        this.setHeight(this.image.getHeight());
        this.isImage = true;
    }

    public StandardButton (int _x, int _y, BufferedImage _image) {
        this(_x, _y, _image.getWidth(), _image.getHeight());
        this.image = _image;
        this.isImage = true;
    }

    @Override
    public void tick () {

    }

    @Override
    public void render (Graphics2D _g2) {
        if (this.isImage) {
            _g2.drawImage(this.image, this.getX(), this.getY(), null);
        }
        else {
            _g2.setColor(this.color);
            _g2.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        }
    }

    @Override
    public abstract void onMouseClick ();

    @Override
    public abstract void onMouseEnterHover ();

    @Override
    public abstract void onMouseExitHover ();

    @Override
    public String toString () {
        return "StandardButton Object: X: " + getX() + "\tY: " + getY() + "\tWidth: " + getWidth() + "\tHeight: " + getHeight() + "\tText: " + this.text + "\tColor: " + this.color;
    }

//========================= GETTERS =============================//
    public String getFileLocation () {
        return this.fileLocation;
    }

    public Color getColor () {
        return color;
    }

    public String getText () {
        return this.text;
    }

//========================= SETTERS =============================//
    public void setFileLocation (String _fileLocation) {
        this.fileLocation = _fileLocation;
    }

    public void setColor (Color _c) {
        this.color = _c;
    }

    public void setText (String _t) {
        this.text = _t;
    }
}
