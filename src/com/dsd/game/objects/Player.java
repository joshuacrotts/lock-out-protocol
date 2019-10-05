package com.dsd.game.objects;

import com.dsd.game.PlayerState;
import com.dsd.game.Game;
import com.dsd.game.commands.AttackCommand;
import com.dsd.game.commands.DebugCommand;
import com.dsd.game.commands.DecrementWeaponCommand;
import com.dsd.game.commands.IncrementWeaponCommand;
import com.dsd.game.commands.MoveCommand;
import com.dsd.game.commands.ReloadCommand;
import com.dsd.game.controller.DebugController;
import com.revivedstandards.controller.StandardAnimatorController;
import com.revivedstandards.handlers.StandardCollisionHandler;
import com.revivedstandards.main.StandardCamera;
import com.revivedstandards.model.DeathListener;
import com.revivedstandards.model.StandardID;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.JOptionPane;
import org.apache.commons.math3.util.FastMath;

/**
 * This class encompasses the model for the Player object.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class Player extends Entity implements DeathListener {

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
    private final DebugCommand debugCommand;

    //
    //  Variables representing the angle and approach velocity
    //
    private final float APPROACH_VEL = -3.0f;

    //
    //  Money amount
    //
    private int money;

    public Player (int _x, int _y, Game _game, StandardCollisionHandler _sch) {
        super(_x, _y, 100, StandardID.Player, (Game) _game, _sch);

        //  Instantiate the inventory
        this.inventory = new Inventory(this.getGame(), this, _sch);

        //  Initializes the miscellaneous variables
        this.sc = this.getGame().getCamera();

        //  Sets the default animation
        this.setAnimation(this.inventory.getCurrentWeapon().getWalkFrames());

        //  Instantiate commands
        this.attackCommand = new AttackCommand(this.getGame(), this, this.getHandler(), this.inventory.getCurrentWeapon().getAttackFrames());
        this.moveCommand = new MoveCommand(this.getGame(), this);
        this.reloadCommand = new ReloadCommand(this.getGame(), this);
        this.incWeaponCommand = new IncrementWeaponCommand(this.getGame(), this);
        this.decWeaponCommand = new DecrementWeaponCommand(this.getGame(), this);
        this.debugCommand = new DebugCommand(this.getGame());

        this.playerState = PlayerState.STANDING;

        //  Adds the player to the list of collidable objects
        _sch.addCollider(StandardID.Player);
        _sch.flagAlive(StandardID.Player);

        this.setHealth(200.0);
    }

    @Override
    public void tick () {
        this.setAlive(this.getHealth() > 0);

        if (this.isAlive()) {
            //  If the player is not standing still, update the animation controller.
            if (this.getPlayerState() != PlayerState.STANDING) {
                this.getAnimationController().tick();
            }

            this.getAnimationController().getStandardAnimation().setRotation(this.getAngle());
            this.updateDimensions();

            // Save the mouse position
            double mx = this.sc.getX() + this.getGame().getMouse().getMouseX() - this.sc.getVpw();
            double my = this.sc.getY() + this.getGame().getMouse().getMouseY() - this.sc.getVph();

            //*******************************************************************//
            //      Causes the player to follow the cursor wherever on the screen //
            //*******************************************************************//
            this.followCursor((int) mx, (int) my);

            //*****************************************************************//
            //      Calculates the angle the player needs to be in to face the   //
            //      cursor                                                     //
            //*****************************************************************//
            this.faceCursor((int) mx, (int) my);
        }
        else {
            this.uponDeath();
        }
    }

    @Override
    public void render (Graphics2D _g2) {
        if (DebugController.DEBUG_MODE) {
            this.drawBorder(_g2);
        }
        this.getAnimationController().renderFrame(_g2);
    }

    @Override
    public void uponDeath () {
        JOptionPane.showMessageDialog(this.getGame(), "You have died!");
        this.getGame().stopGame();
    }

    private void drawBorder (Graphics2D _g2) {
        _g2.setColor(Color.RED);
        _g2.draw(this.getBounds());
    }

    /**
     * Makes the player face the cursor depending on where it is in relation to
     * the screen.
     *
     * @param _mx
     * @param _my
     */
    private void faceCursor (int _mx, int _my) {
        float xSign = (float) FastMath.signum(_mx - this.getX());
        double dx = FastMath.abs(_mx - this.getX());
        double dy = FastMath.abs(_my - this.getY());

        this.setAngle((double) ((xSign) * (FastMath.atan((dx) / (dy)))));

        // If we're in Q1 (+x, -+y) or in Q2 (-x, +y)
        if ((_mx > this.getX() && _my > this.getY()) || (_mx < this.getX() && _my > this.getY())) {
            this.setAngle((float) ((FastMath.PI / 2) + (FastMath.PI / 2 - this.getAngle())));
        }
    }

    /**
     * Makes the player move towards the cursor whenever they press W.
     *
     * @param _mx
     * @param _my
     */
    private void followCursor (int _mx, int _my) {
        // Calculate the distance between the sprite and the mouse
        double diffX = this.getX() - _mx - Entity.APPROACH_FACTOR;
        double diffY = this.getY() - _my - Entity.APPROACH_FACTOR;

        // Use the pythagorean theorem to solve for the hypotenuse distance
        double distance = (double) FastMath.sqrt(((this.getX() - _mx) * (this.getX() - _mx))
                + ((this.getY() - _my) * (this.getY() - _my)));

        // Sets the velocity according to how far away the sprite is from the cursor
        this.setVelX((this.APPROACH_VEL / distance) * diffX);
        this.setVelY((this.APPROACH_VEL / distance) * diffY);
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
    public PlayerState getPlayerState () {
        return this.playerState;
    }

    public Inventory getInventory () {
        return this.inventory;
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

    static {

    }

}
