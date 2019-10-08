package com.dsd.game.userinterface.model;

import com.dsd.game.Game;
import com.dsd.game.objects.Player;
import com.dsd.game.objects.weapons.Gun;
import com.dsd.game.userinterface.Screen;
import com.revivedstandards.main.StandardDraw;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This label will display the amount of ammo in the gun used by the player.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class AmmoLabel extends StandardLabel {

    private final Game game;
    private final Player player;
    //  Position and sizing of health elements
    private final int AMMO_X_OFFSET = 230;
    private final int AMMO_Y_OFFSET = 80;
    private boolean hasGun = false;

    public AmmoLabel(Game _game, Player _player) {
        super((int) (Screen.gameHalfWidth + Screen.gameHalfWidth),
                (int) (Screen.gameHalfHeight + Screen.gameHalfHeight / 2),
                "AMMO: ", "src/resources/fonts/chargen.ttf", 32f);
        this.game = _game;
        this.player = _player;
    }

    @Override
    public void tick() {
        this.hasGun = this.player.getInventory().getCurrentWeapon() instanceof Gun;
    }

    @Override
    public void render(Graphics2D _g2) {
        if (this.hasGun) {
            /**
             * Update positioning here because the timing is crucial to the
             * rendering; delegating it to tick() will cause flickering
             * problems.
             */
            this.setX((int) (this.game.getCamera().getX() + Screen.gameHalfWidth - this.AMMO_X_OFFSET));
            this.setY((int) ((this.game.getCamera().getY() + Screen.gameHalfHeight) - this.AMMO_Y_OFFSET));
            this.drawAmmoText(_g2);
        }
    }

    /**
     * Draws the amount of ammo the current gun has to the screen.
     *
     * @param _g2
     */
    private void drawAmmoText(Graphics2D _g2) {
        StandardDraw.text("AMMO: " + this.getAmmoAmount(), this.getX(), this.getY(), this.getFont(), this.getFont().getSize(), Color.WHITE);
    }

//=============================  GETTERS  ============================//
    private String getAmmoAmount() {
        return this.getCurrentAmmo() + "/" + this.getTotalAmmo();
    }

    private int getCurrentAmmo() {
        return this.player.getInventory().getGun().getCurrentAmmo();
    }

    private int getTotalAmmo() {
        return this.player.getInventory().getGun().getTotalAmmo();
    }
}
