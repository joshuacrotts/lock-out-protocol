/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game.enemies;

import com.dsd.game.EnemyState;
import com.dsd.game.Game;
import com.dsd.game.objects.Entity;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.model.StandardAnimation;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class is a template for an enemy. Before, we had everything extending
 * Entity, but because the Player and other enemies act differently, we needed a
 * way to further distinguish between the two. Enemy has three animator
 * controllers per entity: walking, attacking, and death. Depending on the state
 * of the enemy, one of these will be set.
 *
 * @author Joshua
 */
public abstract class Enemy extends Entity {

    //  Miscellaneous reference variables
    private Entity target;
    private final StandardCamera sc;
    private EnemyState enemyState;

    //  Animation controllers
    private StandardAnimatorController walkingController;
    private StandardAnimatorController attackingController;
    private StandardAnimatorController deathController;

    //  Variables for the disappearing effect when the monster
    //  dies.
    private float deathTransparencyFactor;
    private float deathTransparency = 1.0f;

    //  How much damage the enemy does when running into the player
    protected double damage;

    //  Alpha composition object for when the monster dies.
    protected AlphaComposite deathTransparentComposite;

    public Enemy (int _x, int _y, int _health, StandardID _id, Game _game, StandardCollisionHandler _sch) {
        super(_x, _y, _health, _id, _game, _sch);

        this.sc = this.getGame().getCamera();
    }

    @Override
    public abstract void render (Graphics2D _g2);

    @Override
    public abstract void tick ();

    /**
     * Generates a random track to play when the monster is hurt. _sfxTrack
     * should be a number between 1 and the number of sfx are available for that
     * monster.
     *
     * @param _sfxTrack
     */
    public abstract void generateHurtSound (int _sfxTrack);

    /**
     * Sets the dimensions of the enemy to the animation's current frame
     * dimensions.
     */
    public void setDimensions () {
        this.setWidth(this.getAnimationController().getStandardAnimation().getView().getCurrentFrame().getWidth());
        this.setHeight(this.getAnimationController().getStandardAnimation().getView().getCurrentFrame().getHeight());
    }

    /**
     * Instantiates the walking animation controller.
     *
     * @param _frames
     * @param _fps
     */
    protected void initWalkingFrames (BufferedImage[] _frames, int _fps) {
        this.walkingController = new StandardAnimatorController(this, _frames, _fps);
    }

    /**
     * Instantiates the walking animation controller, but also sets the halt
     * frame in the StandardAnimation object to be _haltFrame.
     *
     * @param _frames
     * @param _fps
     * @param _haltFrame;
     */
    protected void initWalkingFrames (BufferedImage[] _frames, int _fps, int _haltFrame) {
        this.walkingController = new StandardAnimatorController(new StandardAnimation(this, _frames, _fps, _haltFrame));
    }

    /**
     * Instantiates the attacking animation controller.
     *
     * @param _frames
     * @param _fps
     */
    protected void initAttackingFrames (BufferedImage[] _frames, int _fps) {
        this.attackingController = new StandardAnimatorController(this, _frames, _fps);
    }

    /**
     * Instantiates the attacking animation controller, but also sets the halt
     * frame in the StandardAnimation object to be _haltFrame.
     *
     * @param _frames
     * @param _fps
     * @param _haltFrame;
     */
    protected void initAttackingFrames (BufferedImage[] _frames, int _fps, int _haltFrame) {
        this.attackingController = new StandardAnimatorController(new StandardAnimation(this, _frames, _fps, _haltFrame));
    }

    /**
     * Instantiates the death animation controller.
     *
     * @param _frames
     * @param _fps
     */
    protected void initDeathFrames (BufferedImage[] _frames, int _fps) {
        this.deathController = new StandardAnimatorController(this, _frames, _fps);
    }

    /**
     * Instantiates the death animation controller, but also sets the halt frame
     * in the StandardAnimation object to be _haltFrame.
     *
     * @param _frames
     * @param _fps
     * @param _haltFrame;
     */
    protected void initDeathFrames (BufferedImage[] _frames, int _fps, int _haltFrame) {
        this.deathController = new StandardAnimatorController(new StandardAnimation(this, _frames, _fps, _haltFrame));
    }

    /**
     * Moves the current entity to the front of the handler. Actually performs a
     * swap.
     */
    protected void moveEntityToFront () {
        ArrayList<StandardGameObject> entities = this.getHandler().getEntities();
        Collections.swap(entities, 0, entities.indexOf(this));
    }

    /**
     * Applies the composition factor to the actual transparency.
     */
    protected void updateComposite () {
        this.deathTransparentComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.deathTransparency);
        this.deathTransparency -= this.deathTransparencyFactor;
    }

//================================ GETTERS ===================================//
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

    public float getTransparency () {
        return this.deathTransparency;
    }

    public boolean isAttacking () {
        return this.enemyState == EnemyState.ATTACKING;
    }

    public boolean isWalking () {
        return this.enemyState == EnemyState.WALKING;
    }

//================================ SETTERS ===================================//
    public void setTarget (Entity _target) {
        this.target = _target;
    }

    public void setDamage (double _damage) {
        this.damage = _damage;
    }

    public void setTransparentFactor (float _alphaFactor) {
        this.deathTransparencyFactor = _alphaFactor;
    }

    public void setEnemyState (EnemyState _state) {
        this.enemyState = _state;
    }

}
