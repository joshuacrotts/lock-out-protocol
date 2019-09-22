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

    public StandardButton (int x, int y) {
        super(x, y);
    }

    public StandardButton (int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public StandardButton (int x, int y, int width, int height, Color color) {
        super(x, y, width, height);
        this.color = color;
    }

    public StandardButton (int x, int y, int width, int height, String text, Color color) {
        this(x, y, width, height);
        this.text = text;
        this.color = color;
    }

    public StandardButton (int x, int y, int width, int height, int red, int green, int blue) {
        super(x, y, width, height);
        this.color = new Color(red, green, blue);
    }

    public StandardButton (int x, int y, String fileLocation) {
        super(x, y);

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

    public StandardButton (int x, int y, BufferedImage image) {
        this(x, y, image.getWidth(), image.getHeight());

        this.image = image;
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
    public void render (Graphics2D g2) {
        if (this.isImage) {
            g2.drawImage(this.image, getX(), getY(), null);
        }
        else {
            g2.setColor(this.color);
            g2.fillRect(getX(), getY(), getWidth(), getHeight());

            g2.setColor(Color.WHITE);

            if (this.text != null) {
                g2.drawString(this.text, getX() + getHeight() / 2, getY() + getHeight() / 2);
            }
        }
    }

    @Override
    public void mousePressed (MouseEvent e) {
        if (isInteractable() && !this.pressed) {
            System.out.println("Mouse clicked button.");

            this.pressed = StdOps.mouseOver(e.getX(), e.getY(), getX(), getY(), getWidth(), getHeight());
        }
    }

    @Override
    public void mouseReleased (MouseEvent e) {
        if (isInteractable() && this.pressed) {
            System.out.println("Mouse released button.");

            if (StdOps.mouseOver(e.getX(), e.getY(), getX(), getY(), getWidth(), getHeight())) {
                this.pressed = false;
            }
        }
    }

    @Override
    public void mouseMoved (MouseEvent e) {
        if (isInteractable()) {
            this.mouseOver = StdOps.mouseOver(e.getX(), e.getY(), getX(), getY(), getWidth(), getHeight());
        }
    }

    @Override
    public void mouseClicked (MouseEvent e) {
    }

    @Override
    public void mouseEntered (MouseEvent e) {
    }

    @Override
    public void mouseExited (MouseEvent e) {
    }

    @Override
    public void mouseDragged (MouseEvent e) {
    }

    @Override
    public void keyTyped (KeyEvent e) {
    }

    @Override
    public void keyPressed (KeyEvent e) {
    }

    @Override
    public void keyReleased (KeyEvent e) {
    }

    public String getFileLocation () {
        return this.fileLocation;
    }

    public void setFileLocation (String fileLocation) {
        this.fileLocation = fileLocation;
    }

    @Override
    public String toString () {
        return "StandardButton Object: X: " + getX() + "\tY: " + getY() + "\tWidth: " + getWidth() + "\tHeight: " + getHeight() + "\tText: " + this.text + "\tColor: " + this.color;
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

}
