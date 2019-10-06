package com.dsd.game.controller;

import com.revivedstandards.controller.StandardAudioController;

/**
 * This class loads all necessary sfx for the game.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public abstract class AudioBoxController {

    public static void initialize (int _buffers) {
        StandardAudioController.init(_buffers);

        StandardAudioController.load("src/res/audio/sfx/pistol.wav", 16);
        StandardAudioController.load("src/res/audio/sfx/splat1.wav", 2);
        StandardAudioController.load("src/res/audio/sfx/splat2.wav", 2);
        StandardAudioController.load("src/res/audio/sfx/empty.wav", 1);
        StandardAudioController.load("src/res/audio/sfx/reload.wav", 1);
        StandardAudioController.load("src/res/audio/sfx/knife.wav", 3);
        StandardAudioController.load("src/res/audio/sfx/rifle.wav", 16);
        StandardAudioController.load("src/res/audio/sfx/coin.wav", 8);
        StandardAudioController.load("src/res/audio/sfx/round_change.wav", 1);

        AudioBoxController.initBasicMonsterSFX(5);
        AudioBoxController.initGreenMonsterSFX(5);
    }

    private static void initBasicMonsterSFX (int _sfxCount) {
        for (int i = 1 ; i < _sfxCount ; i++) {
            StandardAudioController.load("src/res/audio/sfx/basic_monster/zombie-" + i + ".wav", 2);
        }
    }

    private static void initGreenMonsterSFX (int _sfxCount) {
        for (int i = 1 ; i < _sfxCount ; i++) {
            StandardAudioController.load("src/res/audio/sfx/green_monster/pain" + i + ".wav", 2);
        }
    }
}
