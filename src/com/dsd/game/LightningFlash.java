package com.dsd.game;

import com.dsd.game.userinterface.Screen;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * This class controls the random lighting flash effects when it is raining in
 * the game.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class LightningFlash {

    //  Miscellaneous reference variable.
    private final Game game;

    //  List for all flash references.
    private final ArrayList<LightningFlash> flashList;

    private final double flashDuration;    //(0.0, 1.0).
    private float flashFadeFloat = 1.0f;

    public LightningFlash (Game _game, ArrayList<LightningFlash> _flashList) {
        this.game = _game;
        this.flashList = _flashList;
        this.flashDuration = 0.005f;
    }

    public void tick () {
        if (flashFadeFloat <= 0.01) {
            this.flashList.remove(this);
        }
        else {
            this.flashFadeFloat -= flashDuration;
        }
    }

    public void render (Graphics2D _g2) {
        Color oldColor = _g2.getColor();
        _g2.setColor(new Color(1.0f, 1.0f, 1.0f, this.flashFadeFloat));
        _g2.fillRect((int) (this.game.getCamera().getX() - Screen.gameHalfWidth),
                (int) (this.game.getCamera().getY() - Screen.gameHalfHeight),
                (int) (this.game.getCamera().getX() + Screen.gameDoubleWidth),
                (int) (this.game.getCamera().getY() + Screen.gameDoubleHeight));
        _g2.setColor(oldColor);
    }
}
