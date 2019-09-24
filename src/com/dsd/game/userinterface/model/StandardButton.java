package com.dsd.game.userinterface.model;

import com.revivedstandards.util.StdOps;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class represents a standard button model. It can be used as a template
 * to make more complex buttons or UI elements. However, buttons should ONLY be
 * used for changing the state of something.
 *
 * @author Joshua
 */
public class StandardButton extends Interactor implements MouseListener, KeyListener, MouseMotionListener {

    //
    //  State of button
    //
    private boolean pressed = false;
    private boolean mouseOver = false;
    private boolean isImage = false;

    //
    //  Information regarding button's contents
    //
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

        try {
            this.image = ImageIO.read(new File(fileLocation));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

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
        if (this.mouseOver) {
            this.color = Color.green;
            if (this.pressed) {
                this.color = Color.BLUE;
            }
            else {
                this.color = Color.GREEN;
            }
        }
        else {
            this.color = Color.red;
        }
    }

    @Override
    public void render (Graphics2D _g2) {
        if (this.isImage) {
            _g2.drawImage(this.image, this.getX(), this.getY(), null);
        }
        else {
            _g2.setColor(this.color);
            _g2.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());

            _g2.setColor(Color.WHITE);

            if (this.text != null) {
                _g2.drawString(this.text, this.getX() + this.getHeight() / 2, this.getY() + this.getHeight() / 2);
            }
        }
    }

    @Override
    public void mousePressed (MouseEvent _e) {
        if (isInteractable() && !this.pressed) {
            System.out.println("Mouse clicked button.");

            this.pressed = StdOps.mouseOver(_e.getX(), _e.getY(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
        }
    }

    @Override
    public void mouseReleased (MouseEvent _e) {
        if (isInteractable() && this.pressed) {
            System.out.println("Mouse released button.");

            if (StdOps.mouseOver(_e.getX(), _e.getY(), this.getX(), this.getY(), this.getWidth(), this.getHeight())) {
                this.pressed = false;
            }
        }
    }

    @Override
    public void mouseMoved (MouseEvent _e) {
        if (isInteractable()) {
            this.mouseOver = StdOps.mouseOver(_e.getX(), _e.getY(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
        }
    }

    @Override
    public void mouseClicked (MouseEvent _e) {
    }

    @Override
    public void mouseEntered (MouseEvent _e) {
    }

    @Override
    public void mouseExited (MouseEvent _e) {
    }

    @Override
    public void mouseDragged (MouseEvent _e) {
    }

    @Override
    public void keyTyped (KeyEvent _e) {
    }

    @Override
    public void keyPressed (KeyEvent _e) {
    }

    @Override
    public void keyReleased (KeyEvent _e) {
    }

    @Override
    public String toString () {
        return "StandardButton Object: X: " + getX() + "\tY: " + getY() + "\tWidth: " + getWidth() + "\tHeight: " + getHeight() + "\tText: " + this.text + "\tColor: " + this.color;
    }

//========================= GETTERS =============================//

    public String getFileLocation () {
        return this.fileLocation;
    }

    public boolean isPressed () {
        return pressed;
    }

    public boolean isMouseOver () {
        return mouseOver;
    }

    public Color getColor () {
        return color;
    }

//========================= SETTERS =============================//

    public void setFileLocation (String _fileLocation) {
        this.fileLocation = _fileLocation;
    }
}
