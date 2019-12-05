package com.dsd.game.controller;

import com.dsd.game.core.Game;
import com.revivedstandards.handlers.StandardParticleHandler;

/**
 * This class handles all blood particles for when the player hurts/shoots the
 * enemies.
 *
 * @group [Data Structure Deadheads]
 *
 * @author Joshua, Rinty, Ronald
 *
 * @updated 11/30/19
 */
public class BloodParticleHandler extends StandardParticleHandler {

    private final Game game;
    private static final int MAX_BLOOD_PARTICLES = 10000;

    public BloodParticleHandler(Game _game) {
        super(MAX_BLOOD_PARTICLES);

        this.game = _game;
        this.setCamera(this.game.getCamera());
    }
}
