package com.dsd.game.userinterface.model.buttons;

import com.dsd.game.core.AccountStatus;
import com.dsd.game.core.Game;
import com.dsd.game.controller.LanguageController;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MouseEventInterface;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.model.StandardAudioType;
import com.revivedstandards.util.StdOps;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;

/**
 * This class is the basic representation of a button displayed on the main menu
 * or any of its sub-options. It contains the logic for changing between a
 * button and if the user is hovering over it, etc.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 12/7/19
 */
public abstract class MenuButton extends StandardButton implements MouseEventInterface {

    //  Miscellaneous reference variables.
    private final Game game;
    private final MenuScreen menuScreen;

    protected static final int BUTTON_WIDTH = 300;
    protected static final int BUTTON_HEIGHT = 82;

    //  Images for when the mouse is over the button, not over, and the current
    //  image in the current frame.
    protected final Font font;
    protected BufferedImage onHoverButtonImg;
    protected BufferedImage buttonImg;
    protected BufferedImage activeImage;

    public MenuButton(int _x, int _y, String _text, Game _game, MenuScreen _menuScreen) {
        super(_x, _y, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.game = _game;
        this.menuScreen = _menuScreen;
        if (LanguageController.lang.equals("en-en")) {
            this.font = StdOps.initFont("src/resources/fonts/chargen.ttf", 24f);
        } else {
            this.font = new Font("Arial Unicode MS", Font.BOLD, 16);
        }
        this.setText(_text);
        this.initializeButtonImages();
    }

    @Override
    public void render(Graphics2D _g2) {
        _g2.drawImage(activeImage, (int) (this.getX()),
                (int) (this.getY()),
                this.getWidth(), this.getHeight(), game);
    }

    @Override
    public void onMouseClick() {
        StandardAudioController.play("src/resources/audio/sfx/menuselect.wav", StandardAudioType.SFX);
    }

    /**
     * Displays a JOptionPane dialog box with whatever AccountStatus code was
     * thrown.
     *
     * @param _status
     */
    public void displayAccountStatus(AccountStatus _status) {
        if (_status == null) {
            return;
        }
        switch (_status) {
            case DOES_NOT_EXIST:
                JOptionPane.showMessageDialog(null, LanguageController.translate("Error: Your account does not exist."));
                break;
            case EXISTS:
                JOptionPane.showMessageDialog(null, LanguageController.translate("You already have an account!"));
                break;
            case ACCOUNT_CREATED:
                JOptionPane.showMessageDialog(null, LanguageController.translate("Your account has been created. You may log in now."));
                break;
            case INVALID_EMAIL:
                JOptionPane.showMessageDialog(null, LanguageController.translate("Your email does not meet the criteria."));
                break;
            case INVALID_PASS:
                JOptionPane.showMessageDialog(null, LanguageController.translate("Your password does not meet the criteria."));
                break;
            case INCORRECT_PASS:
                JOptionPane.showMessageDialog(null, LanguageController.translate("Your password is incorrect."));
                break;
            case CORRECT:
                JOptionPane.showMessageDialog(null, LanguageController.translate("Logged in successfully!"));
                break;
            default:
                throw new IllegalArgumentException("Invalid ACCOUNT_STATUS!");
        }
    }

    /**
     * Initializes the static images for button hovers/presses.
     */
    private void initializeButtonImages() {
        this.buttonImg = StdOps.loadImage("src/resources/img/ui/buttonStock1.png");
        this.onHoverButtonImg = StdOps.loadImage("src/resources/img/ui/buttonStock1h.png");
        this.activeImage = this.buttonImg;
    }

//================================== GETTERS ===================================//
    public Game getGame() {
        return this.game;
    }

    public MenuScreen getMenuScreen() {
        return this.menuScreen;
    }
}
