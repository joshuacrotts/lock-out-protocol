package com.dsd.game.userinterface;

import com.dsd.game.userinterface.model.Interactor;
import com.revivedstandards.handlers.StandardHandler;
import com.revivedstandards.model.StandardGameObject;
import java.awt.Graphics2D;
import java.util.LinkedList;

/**
 * This handler acts as a normal handler, with the exception that it renders and
 * updates logic for interactors (UI elements).
 *
 */
public class StandardInteractorHandler extends StandardHandler {

    private final LinkedList<Interactor> interactors;

    public StandardInteractorHandler () {
        this.interactors = new LinkedList<>();
    }

    public void addInteractor (Interactor _interactor) {
        this.interactors.add(_interactor);
    }

    @Override
    public void addEntity (StandardGameObject _obj) {
        throw new UnsupportedOperationException("You cannot add an SGO to this particular handler.");
    }

    @Override
    public void tick () {
        for (int i = 0 ; i < interactors.size() ; i++) {
            this.interactors.get(i).tick();
        }
    }

    @Override
    public void render (Graphics2D _g2) {
        for (int i = 0 ; i < interactors.size() ; i++) {
            this.interactors.get(i).render(_g2);
        }
    }

    public LinkedList<Interactor> getInteractors () {
        return this.interactors;
    }
}
