/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game.objects;

import com.dsd.game.Game;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.model.StandardID;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Joshua
 */
public abstract class Enemy extends Entity {

    //
    //  Miscellaneous reference variables
    //
    private Entity target;
    private final StandardCamera sc;

    //
    //  Animation controllers
    //
    private StandardAnimatorController walkingController;
    private StandardAnimatorController attackingController;
    private StandardAnimatorController deathController;

    //  How much damage the enemy does when running into the player
    protected double damage;

    public Enemy (int _x, int _y, int _health, StandardID _id, Game _game, StandardCollisionHandler _sch) {
        super(_x, _y, _health, _id, _game, _sch);

        this.sc = this.getGame().getCamera();
    }

    @Override
    public abstract void render (Graphics2D _g2);

    @Override
    public abstract void tick ();

    public void setDimensions () {
        this.setWidth(this.getAnimationController().getStandardAnimation().getView().getCurrentFrame().getWidth());
        this.setHeight(this.getAnimationController().getStandardAnimation().getView().getCurrentFrame().getHeight());
    }

    protected void initWalkingFrames (BufferedImage[] _frames, int _fps) {
        this.walkingController = new StandardAnimatorController(this, _frames, _fps);
    }

    protected void initAttackingFrames (BufferedImage[] _frames, int _fps) {
        this.attackingController = new StandardAnimatorController(this, _frames, _fps);
    }

    protected void initDeathFrames (BufferedImage[] _frames, int _fps) {
        this.deathController = new StandardAnimatorController(this, _frames, _fps);
    }

    public StandardAnimatorController getWalkingAnimation () {
        return this.walkingController;
    }

    public StandardAnimatorController getAttackAnimation () {
        return this.attackingController;
    }

    public StandardAnimatorController getDeathAnimation () {
        return this.deathController;
    }

    public Entity getTarget () {
        return this.target;
    }

    public StandardCamera getCamera () {
        return this.sc;
    }

    public double getDamage () {
        return this.damage;
    }

    public void setTarget (Entity _target) {
        this.target = _target;
    }

    public void setDamage (double _damage) {
        this.damage = _damage;
    }

}
