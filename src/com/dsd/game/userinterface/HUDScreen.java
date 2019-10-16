package com.dsd.game.userinterface;

import com.dsd.game.Game;
import com.dsd.game.objects.Player;
import com.dsd.game.userinterface.model.labels.AmmoLabel;
import com.dsd.game.userinterface.model.labels.CoinLabel;
import com.dsd.game.userinterface.model.labels.HealthLabel;
import com.revivedstandards.handlers.StandardCollisionHandler;
import java.awt.Graphics2D;

/**
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class HUDScreen extends Screen {

    private final Player player;
    private final StandardCollisionHandler globalHandler;

    public HUDScreen (Game _game, Player _player, StandardCollisionHandler _sch) {
        super(_game);
        this.player = _player;
        this.globalHandler = _sch;
        this.createUIElements();
    }

    @Override
    public void tick () {
        if (this.getGame().isMenu() || this.getGame().isPaused()) {
            return;
        }
        super.tick();
        this.player.getInventory().getView().tick();
    }

    @Override
    public void render (Graphics2D _g2) {
        if (this.getGame().isMenu() || this.getGame().isPaused()) {
            return;
        }
        super.render(_g2);
        this.player.getInventory().getView().render(_g2);
    }

    /**
     * Creates the interactor elements for this HUD Screen.
     */
    private void createUIElements () {
        this.addInteractor(new HealthLabel(super.getGame(), this.player));
        this.addInteractor(new AmmoLabel(super.getGame(), this.player));
        this.addInteractor(new CoinLabel(super.getGame(), this.player));
        this.addInteractor(new Minimap(super.getGame(), this.globalHandler));
    }
}
