package com.dsd.game.userinterface.view;

import com.dsd.game.core.Game;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MouseEventInterface;
import com.dsd.game.userinterface.Screen;
import com.dsd.game.userinterface.model.buttons.SaveLanguageChangesButton;
import com.dsd.game.userinterface.model.labels.LanguageLabel;
import java.awt.Graphics2D;

/**
 * This is the view that will be shown when the user wants to change their
 * language.
 *
 * [Group Name: Data Structure Deadheads]
 * 
 * @author Joshua, Ronald, Rinty Last Updated: 12/10/2019
 */
public class LanguageChangeView extends Screen implements MouseEventInterface {

    //  Miscellaneous menu screen reference;
    private final MenuScreen menuScreen;

    public LanguageChangeView(Game _game, MenuScreen _menuScreen) {
        super(_game);
        this.menuScreen = _menuScreen;
        super.addInteractor(new LanguageLabel(super.getGame(), this.menuScreen));
        super.addInteractor(new SaveLanguageChangesButton(super.getGame(), this.menuScreen));
    }

    @Override
    public void tick() {
        if (!this.menuScreen.isOnLanguages() || !this.getGame().isMenu()) {
            return;
        }
        super.tick();
    }

    @Override
    public void render(Graphics2D _g2) {
        if (!this.menuScreen.isOnLanguages() || !this.getGame().isMenu()) {
            return;
        }
        super.render(_g2);
    }

    @Override
    public void onMouseClick() {
        if (!this.menuScreen.isOnLanguages() || !this.getGame().isMenu()) {
            return;
        }
    }

    @Override
    public void onMouseEnterHover() {
        if (!this.menuScreen.isOnLanguages() || !this.getGame().isMenu()) {
            return;
        }
    }

    @Override
    public void onMouseExitHover() {
        if (!this.menuScreen.isOnLanguages() || !this.getGame().isMenu()) {
            return;
        }
    }

}
