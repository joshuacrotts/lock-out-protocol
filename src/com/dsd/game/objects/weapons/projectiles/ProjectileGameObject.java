package com.dsd.game.objects.weapons.projectiles;

import com.dsd.game.Game;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.model.StandardAnimation;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.apache.commons.math3.util.FastMath;

/**
 * This class represents some arbitrary projectile in the game. This can range
 * from bullets shot from the player, or projectiles shot by the enemy, etc.
 *
 * @author Joshua, Ronald, Rinty
 */
public class ProjectileGameObject extends StandardGameObject {

    //  Miscellaneous variables
    private final Game game;
    private final StandardCollisionHandler sch;
    private final StandardCamera camera;
    private int damage = 0;
    //  Velocity factor applied to the bullet.
    private final int VEL_FACTOR;
    //  Animation frame per second setting
    private static final int BULLET_FPS = 20;

    public ProjectileGameObject (int _x, int _y, double _angle, int _damage,
            int _velFactor, BufferedImage[] _frames, int _projectileFPS, Game _game,
            StandardCollisionHandler _parentContainer, StandardGameObject _parent, StandardID _id) {
        
        super(_x, _y, _id);
        this.game = _game;
        this.sch = _parentContainer;
        this.damage = _damage;
        this.setAnimation(new StandardAnimatorController(
                new StandardAnimation(this, _frames, _projectileFPS)));
        this.setWidth(this.getWidth());
        this.setHeight(this.getHeight());
        this.setAlive(true);
        this.setAngle(_angle);
        this.VEL_FACTOR = _velFactor;
        this.setVelocity(_parent.getX(), _parent.getY(), _game.getCamera().getCamMouseX(), _game.getCamera().getCamMouseY());
        this.sch.flagAlive(this.getId());
        this.sch.addCollider(this.getId());
        this.camera = this.game.getCamera();
    }

    @Override
    public void tick () {
        
        if (this.camera.SGOInBounds(this)) {
            
            this.setX(this.getX() + this.getVelX());
            this.setY(this.getY() + this.getVelY());
            
            //As long as the object is alive, we can tick it.
            if (this.isAlive()) {
                
                this.getAnimationController().tick();
            }
            
        }
        
        else {
            
            this.sch.removeEntity(this);
        }
        
    }

    @Override
    public void render (Graphics2D _g2) {
        
        /**
         * If they're alive, draw the frame that the bullet animation is on.
         * Otherwise, render the explosion handler
         */
        if (this.isAlive()) {
            
            this.getAnimationController().getStandardAnimation().setRotation(this.getAngle());
            this.getAnimationController().renderFrame(_g2);
        }
        
    }
    
//========================== GETTERS =======================================
    public int getDamage () {
        return this.damage;
    }

//============================ SETTERS ====================================
    /**
     * Instantiates the velocity of the bullet depending on where the cursor is
     * in relation to the player.
     *
     * @param _x
     * @param _y
     * @param _mx
     * @param _my
     */
    private void setVelocity (double _x, double _y, int _mx, int _my) {
        
        double deltaX = (_mx - _x);
        double deltaY = (_my - _y);
        // Use the pythagorean theorem to solve for the hypotenuse distance
        double distance = (double) FastMath.sqrt(((deltaX) * (deltaX))
                + ((deltaY) * (deltaY)));
        deltaX = (deltaX / distance) * this.VEL_FACTOR;
        deltaY = (deltaY / distance) * this.VEL_FACTOR;
        this.setVelX(deltaX);
        this.setVelY(deltaY);
    }
    
}
