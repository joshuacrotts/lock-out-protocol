package com.dsd.game.controller;

import com.revivedstandards.controller.StandardAudioController;

/**
 * This class loads all necessary sfx for the game.
 *
 * @author Joshua
 */
public abstract class AudioBoxController {

    public static void initialize (int _buffers) {
        StandardAudioController.init(_buffers);

        StandardAudioController.load("src/res/audio/sfx/pistol.wav", 16);
        StandardAudioController.load("src/res/audio/sfx/splat1.wav", 2);
        StandardAudioController.load("src/res/audio/sfx/splat2.wav", 2);
    }
}
