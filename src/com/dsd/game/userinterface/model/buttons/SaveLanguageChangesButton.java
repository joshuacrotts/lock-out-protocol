package com.dsd.game.userinterface.model.buttons;

import com.dsd.game.core.Game;
import com.dsd.game.objects.ResolutionEnum;
import com.dsd.game.commands.DecreaseLanguageCommand;
import com.dsd.game.commands.IncreaseLanguageCommand;
import com.dsd.game.controller.LanguageController;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MouseEventInterface;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.swing.JOptionPane;

/**
 * This class can be used in a multitude of ways; for now, it will be used for
 * verifying changes in the sub-options menu.
 *
 * @author Joshua
 */
public class SaveLanguageChangesButton extends MenuButton implements MouseEventInterface {

    //  Miscellaneous reference variables.
    private final MenuScreen menuScreen;

    //  Commands for increasing/decreasing the pointer set by the language enum.
    private final IncreaseLanguageCommand incLangCommand;
    private final DecreaseLanguageCommand decLangCommand;

    //  Button offsets and positioning.
    private static final int BUTTON_X_OFFSET = 0;
    private static final int BUTTON_Y_OFFSET = 120;
    private static final int TEXT_X_OFFSET = 60;
    private static final int TEXT_Y_OFFSET = 45;
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 82;

    public SaveLanguageChangesButton (Game _game, MenuScreen _menuScreen) {
        super(BUTTON_X_OFFSET, _game.getGameHeight() - BUTTON_Y_OFFSET,
                BUTTON_WIDTH, BUTTON_HEIGHT, LanguageController.translate("SAVE CHANGES"), _game, _menuScreen);

        this.menuScreen = _menuScreen;
        this.incLangCommand = new IncreaseLanguageCommand(this.getGame());
        this.decLangCommand = new DecreaseLanguageCommand(this.getGame());
    }

    @Override
    public void tick () {
        this.setX(BUTTON_X_OFFSET);
        this.setY(this.getGame().getGameHeight() - BUTTON_Y_OFFSET);
    }

    @Override
    public void render (Graphics2D _g2) {
        if (!this.getGame().isMenu() || !this.getMenuScreen().isOnLanguages()) {
            return;
        }

        super.render(_g2);
        _g2.setFont(this.font);
        _g2.setColor(Color.WHITE);
        _g2.drawString(this.getText(), this.getX() + TEXT_X_OFFSET, this.getY() + TEXT_Y_OFFSET);
    }

    @Override
    public void onMouseClick () {
        if (!this.getGame().isMenu() || !this.getMenuScreen().isOnLanguages()) {
            return;
        }

        super.onMouseClick();

        //  Once the user presses the save changes button, it will update the game's resolution.
        Dimension changedDimension = ResolutionEnum.getDimension();
        this.getGame().changeResolution((int) changedDimension.getWidth(), (int) changedDimension.getHeight());
        this.getGame().saveToSettings();

        this.displaySaveChangesMsg();
    }

    @Override
    public void onMouseEnterHover () {
        if (!this.getGame().isMenu() || !this.getMenuScreen().isOnLanguages()) {
            return;
        }
        this.activeImage = this.onHoverButtonImg;
    }

    @Override
    public void onMouseExitHover () {
        if (!this.getGame().isMenu() || !this.getMenuScreen().isOnLanguages()) {
            return;
        }
        this.activeImage = this.buttonImg;
    }

    /**
     * Displays a message to the user letting them know their changes will
     * appear upon reloading the game.
     */
    private void displaySaveChangesMsg () {
        JOptionPane.showMessageDialog(null, LanguageController.translate("Your changes will appear upon reloading the game."));
    }

}
