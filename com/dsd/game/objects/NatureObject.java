package com.dsd.game.objects;

import com.dsd.game.core.Game;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.view.Renderable;
import com.revivedstandards.view.Updatable;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * This class is a template for objects in the game that act as foliage.
 *
 * @author Joshua
 */
public class NatureObject extends StandardGameObject implements Renderable, Updatable {

    //  Miscellaneous reference variables.
    private final Game game;
    private final StandardCollisionHandler parentContainer;

    //
    //  There are two types of nature objects: ones that are collidable by other
    //  entities, and ones that are not (the user just hovers over them).
    //
    private boolean collidable = false;

    public NatureObject(Game _game, StandardCollisionHandler _parentContainer,
            int _x, int _y, StandardID _id, BufferedImage _sprite, boolean _collidable) {
        super(_x, _y, _sprite, _id);
        this.game = _game;
        this.parentContainer = _parentContainer;
        this.collidable = _collidable;
        if (this.collidable) {
            this.parentContainer.addCollider(this.getId());
            this.parentContainer.flagAlive(this.getId());
            this.setWidth(this.getCurrentSprite().getWidth());
            this.setHeight(this.getCurrentSprite().getHeight());
        }
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics2D _g2) {
        _g2.drawImage(this.getCurrentSprite(), (int) this.getX(), (int) this.getY(), null);
    }
}
