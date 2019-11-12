package com.dsd.game.userinterface.model;

import com.dsd.game.core.Game;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

/**
 * This class is a primitive model specifying the two different cursors for the
 * game: the crosshair, and the default cursor. When the user is in the game
 * state, they are presented with a crosshair for a cursor. Otherwise, it is the
 * normal cursor.
 *
 * @author Joshua
 */
public class Crosshair {

    //  Miscellaneous reference variables
    private final Game game;

    //  Reference variables for the AWT toolkit, the image itself, and
    //  the corresponding crosshair object.
    private static final Cursor crosshairCursor;
    private static final Image crosshairImage;
    private static final Toolkit toolkit;

    public Crosshair (Game _game) {
        this.game = _game;
    }

    /**
     * Updates the cursor depending on what state the game is in.
     */
    public void updateCursor () {
        Cursor assignedCursor = this.game.isInGameState() ? Crosshair.crosshairCursor : Cursor.getDefaultCursor();
        this.game.setCursor(assignedCursor);
    }

    static {
        toolkit = Toolkit.getDefaultToolkit();
        crosshairImage = Crosshair.toolkit.getImage("src/resources/img/ui/crosshair.png");
        crosshairCursor = Crosshair.toolkit.createCustomCursor(Crosshair.crosshairImage, new Point(0, 0), "crosshair");
    }
}
