package com.dsd.game.userinterface.view;

import com.dsd.game.core.Game;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.model.buttons.StandardButton;
import com.revivedstandards.controller.StandardFadeController;
import com.revivedstandards.util.StdOps;
import com.revivedstandards.view.Renderable;
import com.revivedstandards.view.Updatable;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

/**
 * This is the view that will be shown when the user wants to change their
 * player gender.
 *
 * @author Rinty
 */
public class PlayerView implements Renderable, Updatable {

    //  Miscellaneous reference variables.
    private final Game game;
    private final MenuScreen menuScreen;
    private final StandardButton parentButton;

    //  Variables that define the outline around the icons.
    private Rectangle iconOutline;
    private StandardFadeController fadeController;

    //  Backing buffered image.
    private final BufferedImage icon;
    private final String sex;

    //  Image dimension info.
    private final int STROKE_WIDTH = 10;
    private final int imageWidth;
    private final int imageHeight;
    private boolean mouseOver;

    public PlayerView (Game _game, MenuScreen _menuScreen, StandardButton _parentButton, String _sex) {
        this.game = _game;
        this.menuScreen = _menuScreen;
        this.parentButton = _parentButton;
        this.icon = StdOps.loadImage("src/resources/img/player/player_icon/" + _sex + "_icon.png");
        this.sex = _sex;
        this.imageWidth = _parentButton.getWidth();
        this.imageHeight = _parentButton.getHeight();
        this.setFadeController();
    }

    @Override
    public void tick () {
        if (!this.menuScreen.isOnPlayerGender()) {
            return;
        }
        this.iconOutline = new Rectangle(this.parentButton.getX(), this.parentButton.getY(), imageWidth, imageHeight);
    }

    @Override
    public void render (Graphics2D _g2) {
        if (!this.menuScreen.isOnPlayerGender()) {
            return;
        }

        if (this.mouseOver) {
            _g2.setColor(this.fadeController.combine());
        }
        else {
            _g2.setColor(Color.BLACK);
        }

        _g2.drawImage(this.icon, this.parentButton.getX(), this.parentButton.getY(), this.imageWidth, this.imageHeight, null);
        this.drawBorder(_g2);
    }

    private void drawBorder (Graphics2D _g2) {
        Stroke oldStroke = _g2.getStroke();

        _g2.setStroke(new BasicStroke(this.STROKE_WIDTH));
        _g2.draw(this.iconOutline);
        _g2.setStroke(oldStroke);
    }

    /**
     * The outline color around the playerview's icon is determined by which sex
     * the player is currently hovering over.
     */
    private void setFadeController () {
        switch (this.sex) {
            case "female":
                this.fadeController = new StandardFadeController(Color.red, Color.orange, 0.05f);
                break;
            case "male":
                this.fadeController = new StandardFadeController(Color.blue, Color.green, 0.05f);
                break;
        }
        this.iconOutline = new Rectangle(this.parentButton.getX(), this.parentButton.getY(), imageWidth, imageHeight);
    }

//=============================== SETTERS ==================================//
    public void setMouseOver (boolean _mouseOn) {
        this.mouseOver = _mouseOn;
    }
}
