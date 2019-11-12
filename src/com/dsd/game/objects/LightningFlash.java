package com.dsd.game.objects;

import com.dsd.game.core.Game;
import com.dsd.game.userinterface.Screen;
import com.revivedstandards.view.Renderable;
import com.revivedstandards.view.Updatable;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * This class controls the random lighting flash effects when it is raining in
 * the game.
 *
 * @author Joshua, Ronald, Rinty
 */
public class LightningFlash implements Renderable, Updatable {

    //  Miscellaneous reference variable.
    private final Game game;

    //  List for all flash references.
    private final ArrayList<LightningFlash> flashList;
    private final double flashDuration;
    private float flashFadeFloat = 1.0f;
    private static final float FADE_THRESHOLD = 0.01f;

    public LightningFlash (Game _game, ArrayList<LightningFlash> _flashList) {
        this.game = _game;
        this.flashList = _flashList;
        this.flashDuration = 0.005f;
    }

    @Override
    public void tick () {
        if (this.flashFadeFloat <= FADE_THRESHOLD) {
            this.flashList.remove(this);
        }
        else {
            this.flashFadeFloat -= this.flashDuration;
        }
    }

    @Override
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
