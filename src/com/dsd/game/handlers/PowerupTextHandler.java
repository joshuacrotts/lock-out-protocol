package com.dsd.game.handlers;

import com.dsd.game.core.Game;
import com.dsd.game.userinterface.HUDScreen;
import com.dsd.game.userinterface.model.labels.PowerupTextLabel;
import com.revivedstandards.view.Renderable;
import com.revivedstandards.view.Updatable;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * This class ticks and updates all powerup text labels.
 *
 * @group Data Structure Deadheads
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 11/30/19
 */
public class PowerupTextHandler implements Renderable, Updatable {

    //  Miscellaneous references.
    private final Game game;
    private final HUDScreen hudScreen;

    //  ArrayList of Powerup Labels.
    private final ArrayList<PowerupTextLabel> labels;

    //  Positioning and config offsets/factors.
    private final int Y_OFFSET_FACTOR = 20;
    private final int MAX_LABELS = 5;

    public PowerupTextHandler(Game _game, HUDScreen _hudScreen) {
        this.game = _game;
        this.hudScreen = _hudScreen;
        this.labels = new ArrayList<>();
    }

    @Override
    public void tick() {
        if (this.labels.size() >= MAX_LABELS) {
            this.labels.remove(0);
        }

        for (int i = 0; i < this.labels.size(); i++) {
            this.labels.get(i).tick();
        }
    }

    @Override
    public void render(Graphics2D _g2) {
        for (int i = 0; i < this.labels.size(); i++) {
            this.labels.get(i).setYOffset(Y_OFFSET_FACTOR * i);
            this.labels.get(i).render(_g2);
        }
    }

    /**
     * Adds a new label to the handler.
     *
     * @param _text
     */
    public void addLabel(String _text) {
        this.labels.add(new PowerupTextLabel(this.game, _text));
    }

    /**
     * Removes all labels from the list.
     */
    public void clearLabels() {
        this.labels.clear();
    }
}
