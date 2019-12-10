package com.dsd.game.userinterface.view;

import com.dsd.game.core.Game;
import com.dsd.game.userinterface.MenuScreen;
import com.dsd.game.userinterface.MouseEventInterface;
import com.dsd.game.userinterface.Screen;
import com.dsd.game.userinterface.model.buttons.SaveResolutionChangesButton;
import com.dsd.game.userinterface.model.labels.ResolutionLabel;
import java.awt.Graphics2D;

/**
 * This is the view that will be shown when the user wants to change their
 * resolution.
 * 
 * [Group Name: Data Structure Deadheads]
 * 
 * @author Joshua, Ronald, Rinty Last Updated: 12/10/2019
 */
public class ResolutionView extends Screen implements MouseEventInterface {

    //  Miscellaneous menu reference.
    private final MenuScreen menuScreen;

    public ResolutionView(Game _game, MenuScreen _menuScreen) {
        super(_game);
        this.menuScreen = _menuScreen;
        super.addInteractor(new ResolutionLabel(super.getGame(), this.menuScreen));
        super.addInteractor(new SaveResolutionChangesButton(super.getGame(), this.menuScreen));
    }

    @Override
    public void tick() {
        if (!this.menuScreen.isOnResolution() || !this.getGame().isMenu()) {
            return;
        }
        super.tick();
    }

    @Override
    public void render(Graphics2D _g2) {
        if (!this.menuScreen.isOnResolution() || !this.getGame().isMenu()) {
            return;
        }
        super.render(_g2);
    }

    @Override
    public void onMouseClick() {
        if (!this.menuScreen.isOnResolution() || !this.getGame().isMenu()) {
            return;
        }
    }

    @Override
    public void onMouseEnterHover() {
        if (!this.menuScreen.isOnResolution() || !this.getGame().isMenu()) {
            return;
        }
    }

    @Override
    public void onMouseExitHover() {
        if (!this.menuScreen.isOnResolution() || !this.getGame().isMenu()) {
            return;
        }
    }

}
