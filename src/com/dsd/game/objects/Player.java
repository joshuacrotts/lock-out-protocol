package com.dsd.game.objects;

import com.dsd.game.PlayerState;
import com.dsd.game.Game;
import com.dsd.game.commands.MoveCommand;
import com.dsd.game.commands.ShootCommand;
import com.dsd.game.util.Utilities;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.main.StandardGame;
import com.revivedstandards.model.StandardAnimation;
import com.revivedstandards.model.StandardID;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import org.apache.commons.math3.util.FastMath;

/**
 * This class encompasses the model for the Player object.
 */
public class Player extends Entity {

    //
    //  Miscellaneous reference variables
    //
    private StandardCamera sc;

    //  Refers to the player's current state (walking, shooting, etc.)
    //  PlayerState is set by commands
    //
    private PlayerState playerState;

    //
    //  Commands for the player's actions
    //
    private final MoveCommand moveCommand;
    private final ShootCommand shootCommand;

    //
    //  Frames of animation for the player
    //
    private static final BufferedImage[] walkingFrames;
    private static final BufferedImage[] walkingRifleFrames;
    private static final BufferedImage[] shootingGunFrames;

    //
    //  Animation frame per second settings
    //
    private static final int WALKING_FPS = 10;
    private static final int WALK_RIFLE_FPS = 10;
    private static final int SHOOT_GUN_FPS = 20;

    //
    //  Variables representing the angle and approach velocity
    //
    private float angle;
    private final float approachVel = -3.0f;

    public Player (int _x, int _y, StandardGame _sg, StandardCamera _sc, StandardCollisionHandler _sch) {
        super(_x, _y, 100, StandardID.Player, (Game) _sg, _sch);

        //  Initializes the miscellaneous variables
        this.sc = _sc;

        //  Instantiates the animation controllers
        StandardAnimatorController walkingAnimation = new StandardAnimatorController(
                new StandardAnimation(this, Player.walkingFrames, Player.WALKING_FPS));

        StandardAnimatorController walkingRifleAnimation = new StandardAnimatorController(
                new StandardAnimation(this, Player.walkingRifleFrames, Player.WALK_RIFLE_FPS));

        StandardAnimatorController shootingAnimation = new StandardAnimatorController(
                new StandardAnimation(this, Player.shootingGunFrames, Player.SHOOT_GUN_FPS));

        this.setAnimation(walkingAnimation);

        //  Assigns key-bindings to the commands
        this.shootCommand = new ShootCommand(this.getGame(), this, this.getHandler(), shootingAnimation);
        this.shootCommand.bind(this.getGame().getKeyboard(), KeyEvent.VK_SPACE);
        this.moveCommand = new MoveCommand(this.getGame(), this);
        this.moveCommand.bind(this.getGame().getKeyboard(), KeyEvent.VK_W);

        this.playerState = PlayerState.STANDING;

        //  Adds the player to the list of collidable objects
        _sch.addCollider(StandardID.Player);
        _sch.flagAlive(StandardID.Player);
    }

    @Override
    public void tick () {
        //  If the player is not standing still, update the animation controller.
        if (this.getPlayerState() != PlayerState.STANDING) {
            this.getAnimationController().tick();
        }

        this.getAnimationController().getStandardAnimation().setRotation(this.angle);
        this.updateDimensions();

        //*******************************************************************//
        //      Causes the arrow to follow the cursor wherever on the screen //
        //*******************************************************************//
        // Save the mouse position
        double mx = this.sc.getX() + this.getGame().getMouse().getMouseX() - this.sc.getVpw();
        double my = this.sc.getY() + this.getGame().getMouse().getMouseY() - this.sc.getVph();

        // Calculate the distance between the sprite and the mouse
        double diffX = this.getX() - mx - Entity.APPROACH_FACTOR;
        double diffY = this.getY() - my - Entity.APPROACH_FACTOR;

        // Use the pythagorean theorem to solve for the hypotenuse distance
        double distance = (double) FastMath.sqrt(((this.getX() - mx) * (this.getX() - mx))
                + ((this.getY() - my) * (this.getY() - my)));

        // Sets the velocity according to how far away the sprite is from the cursor
        this.setVelX(((this.approachVel / distance) * (int) diffX));
        this.setVelY(((this.approachVel / distance) * (int) diffY));

        //*****************************************************************//
        //      Calculates the angle the ship needs to be in to face the   //
        //      cursor                                                     //
        //*****************************************************************//
        float xSign = (float) FastMath.signum(mx - this.getX());
        double dx = FastMath.abs(mx - this.getX());
        double dy = FastMath.abs(my - this.getY());

        this.angle = (float) ((xSign) * (FastMath.atan((dx) / (dy))));

        // If we're in Q1 (+x, -+y) or in Q2 (-x, +y)
        if ((mx > this.getX() && my > this.getY()) || (mx < this.getX() && my > this.getY())) {
            this.angle = (float) ((FastMath.PI / 2) + (FastMath.PI / 2 - this.angle));
        }
    }

    @Override
    public void render (Graphics2D _g2) {
        this.getAnimationController().renderFrame(_g2);
    }

    /**
     * Updates the dimensions of the SGO according to the animation's current
     * sprite.
     */
    private void updateDimensions () {
        this.setWidth(this.getAnimationController().getStandardAnimation().getView().getCurrentFrame().getWidth());
        this.setHeight(this.getAnimationController().getStandardAnimation().getView().getCurrentFrame().getHeight());
    }

//============================== GETTERS ================================//
    public StandardCamera getCamera () {
        return this.sc;
    }

    public PlayerState getPlayerState () {
        return this.playerState;
    }

    public double getAngle () {
        return this.angle;
    }

//=============================== SETTERS ================================//
    public void setWalking () {
        this.playerState = PlayerState.WALKING;
    }

    public void setCamera (StandardCamera _sc) {
        this.sc = _sc;
    }

    public void setPlayerState (PlayerState _playerState) {
        this.playerState = _playerState;
    }

    //
    //  Initializes the images used in the animation frames
    //
    static {
        walkingFrames = Utilities.loadFrames("src/res/img/player/player_walk_gun/", 6);
        shootingGunFrames = Utilities.loadFrames("src/res/img/player/player_shoot_gun/", 5);
        walkingRifleFrames = Utilities.loadFrames("src/res/img/player/player_walk_rifle/", 6);
    }

}
