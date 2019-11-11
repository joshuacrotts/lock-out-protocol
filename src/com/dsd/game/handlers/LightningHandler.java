package com.dsd.game.handlers;

import com.dsd.game.LightningFlash;
import com.revivedstandards.controller.StandardAudioController;
import com.revivedstandards.model.StandardAudioType;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * This class is very similar to a StandardHandler with the exception that it
 * renders and updates lightning flashes.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class LightningHandler {

    private final ArrayList<LightningFlash> lightningFlashes;
    private final int LIGHTNING_SFX = 3;

    public LightningHandler () {
        this.lightningFlashes = new ArrayList<>();
    }

    public void tick () {
        for (int i = 0 ; i < this.lightningFlashes.size() ; i++) {
            this.lightningFlashes.get(i).tick();
        }
    }

    public void render (Graphics2D _g2) {
        for (int i = 0 ; i < this.lightningFlashes.size() ; i++) {
            this.lightningFlashes.get(i).render(_g2);
        }
    }

    /**
     * Plays a random lightning/thunder sfx.
     */
    public void playLightningSFX () {
        StandardAudioController.play("src/resources/audio/sfx/thunder"
                + (int) (Math.random() * LIGHTNING_SFX) + ".wav", StandardAudioType.SFX);
    }

    public ArrayList<LightningFlash> getHandler () {
        return this.lightningFlashes;
    }
}
