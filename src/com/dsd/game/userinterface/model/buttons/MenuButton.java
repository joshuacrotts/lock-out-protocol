package com.dsd.game.userinterface.model.buttons;

import com.dsd.game.AccountStatus;
import com.dsd.game.Game;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MouseEventInterface;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.util.StdOps;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;

/**
 *
 * @author Joshua
 */
public abstract class MenuButton extends StandardButton implements MouseEventInterface {

    //  Miscellaneous reference variables.
    private final Game game;
    private final MenuScreen menuScreen;
    protected final Font font;
    protected BufferedImage onHoverButtonImg;
    protected BufferedImage buttonImg;
    protected BufferedImage activeImage;

    public MenuButton (int _x, int _y, int _width, int _height, String _text, Game _game, MenuScreen _menuScreen) {
        super(_x, _y, _width, _height);
        this.game = _game;
        this.menuScreen = _menuScreen;
        this.font = StdOps.initFont("src/resources/fonts/chargen.ttf", 24f);
        this.setText(_text);
        this.initializeButtonImages();

    }

    @Override
    public void render (Graphics2D _g2) {
        _g2.drawImage(activeImage, (int) (this.getX()),
                (int) (this.getY()),
                this.getWidth(), this.getHeight(), game);
    }

    private void initializeButtonImages () {
        this.buttonImg = StdOps.loadImage("src/resources/img/ui/buttonStock1.png");
        this.onHoverButtonImg = StdOps.loadImage("src/resources/img/ui/buttonStock1h.png");
        this.activeImage = this.buttonImg;
    }

    @Override
    public void onMouseClick () {
        StandardAudioController.play("src/resources/audio/sfx/menuselect.wav");
    }

    public void displayAccountStatus (AccountStatus _status) {
        if (_status == null) {
            return;
        }
        switch (_status) {
            case DOES_NOT_EXIST:
                JOptionPane.showMessageDialog(null, "Error: Your account does not exist.");
                break;
            case EXISTS:
                JOptionPane.showMessageDialog(null, "You already have an account!");
                break;
            case ACCOUNT_CREATED:
                JOptionPane.showMessageDialog(null, "Your account has been created. You may log in now.");
                break;
            case INCORRECT_PASS:
                JOptionPane.showMessageDialog(null, "Your password is incorrect.");
                break;
            case CORRECT:
                JOptionPane.showMessageDialog(null, "Logged in successfully!");
                break;
        }
    }

//================================== GETTERS ===================================//
    public Game getGame () {
        return this.game;
    }

    public MenuScreen getMenuScreen () {
        return this.menuScreen;
    }
}
