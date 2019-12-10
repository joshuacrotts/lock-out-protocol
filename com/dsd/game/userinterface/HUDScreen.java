package com.dsd.game.userinterface;

import com.dsd.game.core.Game;
import com.dsd.game.handlers.PowerupTextHandler;
import com.dsd.game.objects.Player;
import com.dsd.game.userinterface.model.Interactor;
import com.dsd.game.userinterface.model.labels.AmmoLabel;
import com.dsd.game.userinterface.model.labels.CoinLabel;
import com.dsd.game.userinterface.model.labels.HealthLabel;
import com.dsd.game.userinterface.model.labels.TimeLabel;
import com.revivedstandards.handlers.StandardCollisionHandler;
import java.awt.Graphics2D;

/**
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class HUDScreen extends Screen {

    //  Miscellaneous reference variables.
    private final Player player;
    private final StandardCollisionHandler globalHandler;
    private final PowerupTextHandler powerupTextHandler;

    public HUDScreen(Game _game, Player _player, StandardCollisionHandler _sch) {
        super(_game);
        this.player = _player;
        this.globalHandler = _sch;
        this.powerupTextHandler = new PowerupTextHandler(_game);
        this.createUIElements();
    }

    @Override
    public void tick() {
        if (this.getGame().isMenu() || this.getGame().isPaused() || this.getGame().isHelp()) {
            return;
        }
        super.tick();
        this.player.getInventory().getView().tick();
        this.powerupTextHandler.tick();
    }

    @Override
    public void render(Graphics2D _g2) {
        if (this.getGame().isMenu() || this.getGame().isPaused() || this.getGame().isHelp()) {
            return;
        }
        super.render(_g2);
        this.player.getInventory().getView().render(_g2);
        this.powerupTextHandler.render(_g2);
    }

    @Override
    public void addInteractor(Interactor _inter) {
        super.addInteractor(_inter);
    }

    /**
     * Creates the interactor elements for this HUD Screen.
     */
    private void createUIElements() {
        super.addInteractor(new HealthLabel(super.getGame(), this.player));
        super.addInteractor(new AmmoLabel(super.getGame(), this.player));
        super.addInteractor(new CoinLabel(super.getGame(), this.player));
        super.addInteractor(new Minimap(super.getGame(), this.globalHandler));
        super.addInteractor(new TimeLabel(super.getGame()));
    }

//============================= GETTERS =====================================//
    public PowerupTextHandler getPowerupTextHandler() {
        return this.powerupTextHandler;
    }

}
