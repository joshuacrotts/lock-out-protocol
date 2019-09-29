package com.dsd.game.userinterface;

import com.dsd.game.Game;
import com.dsd.game.objects.Player;
import com.dsd.game.userinterface.model.AmmoLabel;
import com.dsd.game.userinterface.model.HealthLabel;
import java.awt.Graphics2D;

/**
 *
 * @author Joshua
 */
public class HUDScreen extends Screen {

    private final Player player;

    public HUDScreen(Game _game, Player _player) {
        super(_game);

        this.player = _player;

        this.createUIElements();
    }

    @Override
    public void tick() {
        super.tick();
        this.player.getInventory().getView().tick();
    }

    @Override
    public void render(Graphics2D _g2) {
        super.render(_g2);
        this.player.getInventory().getView().render(_g2);
    }

    private void createUIElements() {
        this.addInteractor(new HealthLabel(super.getGame(), this.player));
        this.addInteractor(new AmmoLabel(super.getGame(), this.player));
    }

}
