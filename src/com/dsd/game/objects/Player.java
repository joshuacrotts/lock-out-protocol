package com.dsd.game.objects;

import com.dsd.game.PlayerState;
import com.dsd.game.Game;
import com.dsd.game.commands.AttackCommand;
import com.dsd.game.commands.DecrementWeaponCommand;
import com.dsd.game.commands.IncrementWeaponCommand;
import com.dsd.game.commands.MoveCommand;
import com.dsd.game.commands.ReloadCommand;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.main.StandardGame;
import com.revivedstandards.model.StandardID;
import java.awt.Graphics2D;
import org.apache.commons.math3.util.FastMath;

/**
 * This class encompasses the model for the Player object.
 * 
 * [Group Name: Data Structure Deadheads]
 * @author Joshua, Ronald, Rinty 
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
    //  Inventory of the player, tells how much money they have, the current
    //  weapon, etc.
    //
    private final Inventory inventory;

    //
    //  Commands for the player's actions
    //
    private final MoveCommand moveCommand;
    private final AttackCommand attackCommand;
    private final ReloadCommand reloadCommand;
    private final IncrementWeaponCommand incWeaponCommand;
    private final DecrementWeaponCommand decWeaponCommand;

    //
    //  Variables representing the angle and approach velocity
    //
    private float angle;
    private final float approachVel = -3.0f;

    //
    //  Money amt
    //
    private int money;

    public Player (int _x, int _y, StandardGame _game, StandardCamera _sc, StandardCollisionHandler _sch) {
        super(_x, _y, 100, StandardID.Player, (Game) _game, _sch);

        //  Instantiate the inventory
        this.inventory = new Inventory(this.getGame(), this, _sch);

        //  Initializes the miscellaneous variables
        this.sc = _sc;

        this.setAnimation(this.inventory.getCurrentWeapon().getWalkFrames());

        //  Instantiate commands
        this.attackCommand = new AttackCommand(this.getGame(), this, this.getHandler(), this.inventory.getCurrentWeapon().getAttackFrames());
        this.moveCommand = new MoveCommand(this.getGame(), this);
        this.reloadCommand = new ReloadCommand(this.getGame(), this);
        this.incWeaponCommand = new IncrementWeaponCommand(this.getGame(), this);
        this.decWeaponCommand = new DecrementWeaponCommand(this.getGame(), this);

        this.playerState = PlayerState.STANDING;

        //  Adds the player to the list of collidable objects
        _sch.addCollider(StandardID.Player);
        _sch.flagAlive(StandardID.Player);

        this.setHealth(200.0);

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
        double diffX = this.getX() - mx - Entity.approachFactor;
        double diffY = this.getY() - my - Entity.approachFactor;

        // Use the pythagorean theorem to solve for the hypotenuse distance
        double distance = (double) FastMath.sqrt(((this.getX() - mx) * (this.getX() - mx))
                + ((this.getY() - my) * (this.getY() - my)));

        // Sets the velocity according to how far away the sprite is from the cursor
        this.setVelX((this.approachVel / distance) * diffX);
        this.setVelY((this.approachVel / distance) * diffY);

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

    public Inventory getInventory () {
        return this.inventory;
    }

    public double getAngle () {
        return this.angle;
    }

    public int getMoney () {
        return this.money;
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

    public void setAttackAnimator (StandardAnimatorController sac) {
        this.attackCommand.setAnimation(sac);
    }

    public void setMoney (int _money) {
        this.money = _money;
    }

}
