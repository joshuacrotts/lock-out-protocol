package com.dsd.game.objects;

import com.dsd.game.PlayerState;
import com.dsd.game.Game;
import com.dsd.game.commands.MoveCommand;
import com.dsd.game.commands.ShootCommand;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.main.StandardGame;
import com.revivedstandards.model.StandardAnimation;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.model.StandardID;
import com.revivedstandards.util.StdOps;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import org.apache.commons.math3.util.FastMath;

/**
 * This class encompasses the model for the Player object.
 */
public class Player extends StandardGameObject {

    //
    //  Miscellaneous reference variables
    //
    private StandardGame sg;
    private StandardCamera sc;
    private StandardCollisionHandler globalHandler;

    //  Refers to the player's current state (walking, shooting, etc.)
    //  PlayerState is set by commands
    //
    private PlayerState playerState;

    //
    //  Commands for the player's actions
    //
    private MoveCommand moveCommand;
    private ShootCommand shootCommand;

    //
    //  Frames of animation for the player
    //
    private static final BufferedImage[] walkingFrames;
    private static final BufferedImage[] walkingRifleFrames;
    private static final BufferedImage[] shootingGunFrames;

    //
    //  Variables representing the angle and approach velocity
    //
    private float angle;
    private final float approachVel = -3.0f;

    public Player (StandardGame _sg, StandardCamera _sc, StandardCollisionHandler _sch, int x, int y) {
        super(x, y, StandardID.Player);

        //  Initializes the miscellaneous variables
        this.sg = _sg;
        this.sc = _sc;
        this.globalHandler = _sch;

        //  Instantiates the animation controllers
        StandardAnimatorController walkingAnimation = new StandardAnimatorController(
                new StandardAnimation(this, Player.walkingFrames, 10d));

        StandardAnimatorController walkingRifleAnimation = new StandardAnimatorController(
                new StandardAnimation(this, Player.walkingRifleFrames, 10d));

        StandardAnimatorController shootingAnimation = new StandardAnimatorController(
                new StandardAnimation(this, Player.shootingGunFrames, 20d));

        this.setAnimation(walkingAnimation);

        //  Assigns key-bindings to the commands
        this.shootCommand = new ShootCommand((Game) this.sg, this, this.globalHandler, shootingAnimation);
        this.shootCommand.bind(this.sg.getKeyboard(), KeyEvent.VK_SPACE);

        this.moveCommand = new MoveCommand((Game) this.sg, this);
        this.moveCommand.bind(this.sg.getKeyboard(), KeyEvent.VK_W);

        this.playerState = PlayerState.Standing;

    }

    @Override
    public void tick () {
        //  If the player is not standing still, update the animation controller.
        if (this.getPlayerState() != PlayerState.Standing) {
            this.getAnimationController().tick();
        }

        this.getAnimationController().getStandardAnimation().setRotation(this.angle);
        this.updateDimensions();

        //*******************************************************************//
        //      Causes the arrow to follow the cursor wherever on the screen //
        //*******************************************************************//
        // Save the mouse position
        double mx = this.sc.getX() + this.sg.getMouse().getMouseX() - this.sc.getVpw();
        double my = this.sc.getY() + this.sg.getMouse().getMouseY() - this.sc.getVph();

        // Calculate the distance between the sprite and the mouse
        double diffX = this.getX() - mx - 8;
        double diffY = this.getY() - my - 8;

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
            this.angle = (float) ((FastMath.PI * 0.5) + (FastMath.PI * 0.5 - this.angle));
        }
    }

    @Override
    public void render (Graphics2D _g2) {
        this.getAnimationController().renderFrame(_g2);
    }

    /**
     * Loads all files from a particular directory. All files need to be of the
     * same type (png, jpeg, etc.) and have distinct, ordinal names.
     *
     * @param _directory
     * @param _frameCount
     * @return
     */
    public static BufferedImage[] loadFrames (String _directory, int _frameCount) {
        File folder = new File(_directory);
        File[] listOfFiles = folder.listFiles();
        BufferedImage[] frames = new BufferedImage[_frameCount];

        for (int i = 0 ; i < frames.length ; i++) {
            frames[i] = StdOps.loadImage(listOfFiles[i].getPath());
        }
        return frames;
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
        this.playerState = PlayerState.Walking;
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
        walkingFrames = Player.loadFrames("src/res/img/player/player_walk_gun/", 6);
        shootingGunFrames = Player.loadFrames("src/res/img/player/player_shoot_gun/", 5);
        walkingRifleFrames = Player.loadFrames("src/res/img/player/player_walk_rifle/", 6);
    }

}
