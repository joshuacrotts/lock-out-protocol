package com.dsd.game.objects;

import com.dsd.game.objects.enums.ExplosionType;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.model.StandardAnimation;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import java.awt.Graphics2D;

/**
 * This class is a simple explosion artifact in the game.
 *
 * @author Joshua, Ronald, Rinty
 */
public class Explosion extends StandardGameObject {

    //  Miscellaneous reference variables
    private final StandardCollisionHandler parentContainer;
    private final StandardAnimatorController animation;
    //  The gun that the bullet comes from determines what type of explosion
    //  to use (i.e. which frames, etc).
    private ExplosionType explosionType;
    //  Information about the FPS of the explosion and its damage (if applicable).
    private static final int EXPLOSION_FPS = 45;
    private final int DAMAGE;

    public Explosion (int _x, int _y, int _damage, ExplosionType _type, StandardCollisionHandler _parentContainer) {
        
        super(_x, _y, StandardID.Tile1);
        this.explosionType = _type;
        this.DAMAGE = _damage;
        this.parentContainer = _parentContainer;
        this.animation = new StandardAnimatorController(new StandardAnimation(this,
                _type.getExplosionFrames(), EXPLOSION_FPS, _type.getExplosionFrames().length - 1));
        this.parentContainer.addCollider(this.getId());
        this.parentContainer.flagAlive(this.getId());
    }

    @Override
    public void render (Graphics2D _gd) {
        
        if (this.isAlive()) {
            
            this.animation.renderFrame(_gd);
        }
        
    }

    @Override
    public void tick () {
        
        if (this.isAlive()) {
            
            this.animation.tick();
        }
        
        if (this.animation.getStandardAnimation().getCurrentFrameIndex() == explosionType.getExplosionFrames().length - 1) {
            
            this.setAlive(false);
        }
        
    }

//================================= GETTERS =====================================
    public int getDamage () {
        
        return this.DAMAGE;
    }
    
}
