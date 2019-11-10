package com.dsd.game.userinterface;

import com.dsd.game.Game;
import com.dsd.game.userinterface.model.Interactor;
import com.revivedstandards.handlers.StandardHandler;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.util.StdOps;
import com.revivedstandards.view.Renderable;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

/**
 * This handler acts as a normal handler, with the exception that it renders and
 * updates logic for interactors (UI elements). This includes listening for
 * mouse clicks and hovers.
 *
 * @author Joshua, Ronald, Rinty
 */
public class StandardInteractorHandler extends StandardHandler implements MouseListener, MouseMotionListener, Renderable {

    private final LinkedList<Interactor> interactors;
    private final Game game;

    public StandardInteractorHandler (Game _game) {
        
        this.interactors = new LinkedList<>();
        this.game = _game;
    }

    @Override
    public void tick () {
        
        for (int i = 0 ; i < this.interactors.size() ; i++) {
            
            this.interactors.get(i).tick();
        }
        
    }

    @Override
    public void render (Graphics2D _g2) {
        
        for (int i = 0 ; i < interactors.size() ; i++) {
            
            this.interactors.get(i).render(_g2);
        }
        
    }

    public void addInteractor (Interactor _interactor) {
        
        this.interactors.add(_interactor);
    }

    @Override
    public void addEntity (StandardGameObject _obj) {
        
        throw new UnsupportedOperationException("You cannot add an SGO to this particular handler.");
    }

    /**
     * Iterates over the interactors to determine which one was clicked
     *
     * @param e
     */
    @Override
    public void mousePressed (MouseEvent e) {
        
        for (int i = 0 ; i < interactors.size() ; i++) {
            
            Interactor inter = this.interactors.get(i);
            int scaleXOffset = 0;
            int scaleYOffset = 0;

            /**
             * If the interactor is on the main game screen (where it will be
             * scaled with the main camera, we apply an offset to its bounds so
             * the mouse-over detection can be called.
             */
            if (inter.isScaled()) {
                
                scaleXOffset = (int) this.game.getCamera().getX() - Screen.gameHalfWidth;
                scaleYOffset = (int) this.game.getCamera().getY() - Screen.gameHalfHeight;
            }

            if (StdOps.mouseOver(this.game.getMouse().getMouseX(), this.game.getMouse().getMouseY(),
                    inter.getX() - scaleXOffset, inter.getY() - scaleYOffset, inter.getWidth(), inter.getHeight())) {

                inter.onMouseClick();
            }
            
        }
        
    }

    /**
     * If the mouse is over the specified interactor, its onMouseHover() method
     * is called.
     *
     * @param e
     */
    @Override
    public void mouseMoved (MouseEvent e) {
        
        for (int i = 0 ; i < interactors.size() ; i++) {
            
            Interactor inter = this.interactors.get(i);
            int scaleXOffset = 0;
            int scaleYOffset = 0;

            /**
             * If the interactor is on the main game screen (where it will be
             * scaled with the main camera, we apply an offset to its bounds so
             * the mouse-over detection can be called.
             */
            if (inter.isScaled()) {
                
                scaleXOffset = (int) this.game.getCamera().getX() - Screen.gameHalfWidth;
                scaleYOffset = (int) this.game.getCamera().getY() - Screen.gameHalfHeight;
            }
            
            if (StdOps.mouseOver(this.game.getMouse().getMouseX(), this.game.getMouse().getMouseY(),
                    inter.getX() - scaleXOffset, inter.getY() - scaleYOffset, inter.getWidth(), inter.getHeight())) {
                
                inter.onMouseEnterHover();
            }
            
            else {
                
                inter.onMouseExitHover();
            }
            
        }
        
    }

    @Override
    public void mouseDragged (MouseEvent e) {
        
        for (int i = 0 ; i < interactors.size() ; i++) {
            
            Interactor inter = this.interactors.get(i);
            
            if (!inter.isDraggable()) {
                
                continue;
            }

            inter.onMouseClick();
        }
    }

    @Override
    public void mouseClicked (MouseEvent e) {
    }

    @Override
    public void mouseReleased (MouseEvent e) {
    }

    @Override
    public void mouseEntered (MouseEvent e) {
    }

    @Override
    public void mouseExited (MouseEvent e) {
    }

//====================== GETTERS ===============================
    public LinkedList<Interactor> getInteractors () {
        
        return this.interactors;
    }
    
}
