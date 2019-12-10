package com.dsd.game.userinterface.model.labels;

import com.dsd.game.core.Game;
import com.dsd.game.controller.LanguageController;
import com.dsd.game.objects.Player;
import com.dsd.game.objects.weapons.Gun;
import com.dsd.game.userinterface.Screen;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This label will display the amount of ammo in the gun used by the player.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 * 
 * @updated 12/10/19
 */
public class AmmoLabel extends StandardLabel {

    //  Miscellaneous reference variables.
    private final Game game;
    private final Player player;

    //  Position and sizing of health elements
    private final int AMMO_X_OFFSET = 230;
    private final int AMMO_Y_OFFSET = 90;
    private boolean hasGun = false;

    //  View element factors.
    private static final float FONT_SIZE = 28f;

    public AmmoLabel(Game _game, Player _player) {
        super((int) (Screen.gameHalfWidth + Screen.gameHalfWidth),
                (int) (Screen.gameHalfHeight + Screen.gameHalfHeight / 2),
                LanguageController.translate("AMMO: "), "src/resources/fonts/chargen.ttf", FONT_SIZE);
        this.game = _game;
        this.player = _player;
    }

    @Override
    public void tick() {
        this.hasGun = this.player.getInventory().getCurrentWeapon() instanceof Gun;
        this.setX(Screen.gameHalfWidth + Screen.gameHalfWidth);
        this.setY(Screen.gameHalfHeight + Screen.gameHalfHeight / 2);
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
        _g2.setFont(this.getFont());
        _g2.setColor(Color.WHITE);
        _g2.drawString(this.getText() + this.getAmmoAmount(), this.getX(), this.getY());
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
