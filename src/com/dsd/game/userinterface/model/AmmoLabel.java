/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game.userinterface.model;

import com.dsd.game.Game;
import com.dsd.game.objects.Player;
import com.dsd.game.userinterface.Screen;
import com.revivedstandards.main.StandardDraw;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This label will display the amount of ammo in the gun used by the player.
 *
 * @author Joshua
 */
public class AmmoLabel extends StandardLabel {

    private final Game game;
    private final Player player;

    //
    //  Position and sizing of health elements
    //
    private final int AMMO_X_OFFSET = 220;
    private final int AMMO_Y_OFFSET = 100;

    public AmmoLabel (Game _game, Player _player) {
        super((int) (Screen.GAME_HALF_WIDTH + Screen.GAME_HALF_WIDTH),
              (int) (Screen.GAME_HALF_HEIGHT + Screen.GAME_HALF_HEIGHT / 2),
                    "AMMO: ", "src/res/fonts/chargen.ttf", 32f);

        this.game = _game;
        this.player = _player;
    }

    @Override
    public void tick () {

    }

    @Override
    public void render (Graphics2D _g2) {

        //  Update positioning here because the timing is crucial to the rendering;
        //  delegating it to tick() will cause flickering problems.
        this.setX((int) (this.game.getCamera().getX() + Screen.GAME_HALF_WIDTH - AMMO_X_OFFSET));
        this.setY((int) ((this.game.getCamera().getY() + Screen.GAME_HALF_HEIGHT / 2) + AMMO_Y_OFFSET));

        this.drawAmmoText(_g2);
    }

    private void drawAmmoText (Graphics2D _g2) {
        StandardDraw.text("AMMO: " + this.getAmmoAmount(), this.getX(), this.getY(), this.getFont(), this.getFont().getSize(), Color.WHITE);
    }

    private String getAmmoAmount () {
        return this.getCurrentAmmo() + "/" + this.getTotalAmmo();
    }

    private int getCurrentAmmo () {
        return this.player.getInventory().getCurrentWeapon().getCurrentAmmo();
    }

    private int getTotalAmmo () {
        return this.player.getInventory().getCurrentWeapon().getTotalAmmo();
    }

}
