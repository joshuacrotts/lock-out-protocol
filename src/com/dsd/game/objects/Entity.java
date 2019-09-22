package com.dsd.game.objects;

import com.dsd.game.Game;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;

/**
 * Abstract class representing an entity that belongs to a standard collision
 * handler, and has health.
 *
 * @author Joshua
 */
public abstract class Entity extends StandardGameObject {

    //
    //  Miscellaneous reference variables
    //
    private final Game game;
    private final StandardCollisionHandler parentContainer;

    //  Health of entity
    private double health = 0;

    public Entity (int _x, int _y, int _health, StandardID _id, Game _game, StandardCollisionHandler _parentContainer) {
        super(_x, _y, _id);
        this.game = _game;
        this.parentContainer = _parentContainer;
        this.health = _health;
    }
//========================= GETTERS =============================//

    public Game getGame () {
        return this.game;
    }

    public StandardCollisionHandler getHandler () {
        return this.parentContainer;
    }

    public double getHealth () {
        return this.health;
    }

//========================= SETTERS =============================//
    public void setHealth (double h) {
        this.health = h;
    }

}
