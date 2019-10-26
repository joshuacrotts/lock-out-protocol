package com.dsd.game.objects;

import com.dsd.game.Game;
import com.dsd.game.SerializableObject;
import com.dsd.game.commands.AttackCommand;
import com.dsd.game.commands.DebugCommand;
import com.dsd.game.commands.DecrementWeaponCommand;
import com.dsd.game.commands.IncrementWeaponCommand;
import com.dsd.game.commands.MoveBackwardCommand;
import com.dsd.game.commands.MoveForwardCommand;
import com.dsd.game.commands.ReloadCommand;
import com.dsd.game.controller.DebugController;
import com.dsd.game.database.SerializableType;
import com.dsd.game.objects.enums.PlayerState;
import com.dsd.game.userinterface.Screen;
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
 * This class encompasses the model for the Player object. The player itself is
 * instantiated with its own camera, an inventory in the form of an ArrayList,
 * its list key commands accepted from the keyboard, its speed value, its
 * currency held in the form of an integer, its designated health value from the
 * Entity-Class, and finally, its own collider for collision detection purposes,
 * which is added to the Standard Collision Handler
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class Player extends Entity implements DeathListener, SerializableObject {

    //  Miscellaneous reference variables
    private StandardCamera sc;

    /**
     * Refers to the player's current state (walking, shooting, etc.)
     * PlayerState is set by commands
     */
    private PlayerState playerState;

    //  Inventory of the player, tells how much money they have, the current weapon, etc.
    private final Inventory inventory;

    //  Global commands
    private AttackCommand attackCommand;

    //  Variables representing the angle and approach velocity
    private final float APPROACH_VEL = -3.0f;

    //  Money amount
    private int money = 0;

    //  Health vars (this may change with time)
    private int maxHealth = 200;

    //  Sex of player
    private String sex = "male";

    public Player (int _x, int _y, Game _game, StandardCollisionHandler _sch) {
        super(_x, _y, 100, StandardID.Player, (Game) _game, _sch);
        //  Instantiate the inventory
        this.inventory = new Inventory(this.getGame(), this, _sch);

        //  Initializes the miscellaneous variables
        this.sc = this.getGame().getCamera();

        //  Sets the default animation
        this.setAnimation(this.inventory.getCurrentWeapon().getWalkFrames());

        //  Instantiate commands
        this.initCommands();

        //  Initializes the player's default state to standing
        this.playerState = PlayerState.STANDING;

        //  Adds the player to the list of collidable objects
        _sch.addCollider(StandardID.Player);
        _sch.flagAlive(StandardID.Player);
        this.setHealth(this.maxHealth);
    }

    @Override
    public void tick () {
        this.setAlive(this.getHealth() > 0);
        if (this.isAlive()) {
            //  If the player is not standing still, update the animation controller.
            if (!this.isStanding()) {
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
            this.checkCameraBounds();
        }
        else {
            this.uponDeath();
        }
    }

    /**
     * Renders the player's animation sprites and draws them to the screen
     *
     * @param _g2
     */
    @Override
    public void render (Graphics2D _g2) {
        if (DebugController.DEBUG_MODE) {
            this.drawBorder(_g2);
        }
        this.getAnimationController().renderFrame(_g2);
    }

    /**
     * Displays a death message upon player's health reaching 0, and then closes
     * out the application (currently WIP).
     */
    @Override
    public void uponDeath () {
        JOptionPane.showMessageDialog(this.getGame(), "You have died!");
        this.getGame().stopGame();
    }

    /**
     * Resets all information about the player.
     */
    public void resetPlayer () {
        this.money = 0;
        this.setHealth(this.maxHealth);
        this.inventory.resetInventory();
    }
    
//=========================== CRUD OPERATIONS ================================//
    @Override
    public String createObject (SerializableType _id) {
        if (_id != SerializableType.PLAYER) {
            return null;
        }

        StringBuilder playerDetails = new StringBuilder();

        playerDetails.append((int) this.getX()).append(";");
        playerDetails.append((int) this.getY()).append(";");
        playerDetails.append((int) this.getMoney()).append(";");
        playerDetails.append((int) this.getHealth()).append(";");

        return playerDetails.toString();
    }

    public void readObject (int _x, int _y, int _money, int _health) {
        this.setX(_x);
        this.setY(_y);
        this.setMoney(_money);
        this.setHealth(_health);
    }

    @Override
    public void updateObject (SerializableType _obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void destroyObject (SerializableType _obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Adjusts the player's position so they aren't able to move outside the
     * bounds of the camera.
     */
    private void checkCameraBounds () {
        StandardCamera cam = this.getGame().getCamera();
        this.checkXBounds(cam);
        this.checkYBounds(cam);
    }

    /**
     * Checks the x bounds of the player and makes sure they can't go too far to
     * the left or right.
     *
     * @param _cam
     */
    private void checkXBounds (StandardCamera _cam) {

        if (this.getX() <= _cam.getMinX() - Screen.gameHalfWidth) {
            this.setX(_cam.getMinX() - Screen.gameHalfWidth);
        }
        if (this.getX() > _cam.getMaxX() + Screen.gameHalfWidth - this.getWidth()) {
            this.setX(_cam.getMaxX() + Screen.gameHalfWidth - this.getWidth());
        }
    }

    /**
     * Checks the y bounds of the player and makes sure they can't go too far up
     * or down.
     *
     * @param _cam
     */
    private void checkYBounds (StandardCamera _cam) {
        if (this.getY() <= _cam.getMinY() - Screen.gameHalfHeight) {
            this.setY(_cam.getMinY() - Screen.gameHalfHeight);
        }
        if (this.getY() > _cam.getMaxY() + Screen.gameHalfHeight - this.getHeight()) {
            this.setY(_cam.getMaxY() + Screen.gameHalfHeight - this.getHeight());
        }
    }

    /**
     * Draws the bounds around the player when debug mode is enabled.
     *
     * @param _g2
     */
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

        /**
         * Sets the velocity according to how far away the sprite is from the
         * cursor, and according to what direction the player is facing.
         */
        int directionSign = this.getPlayerDirection();
        this.setVelX(directionSign * ((this.APPROACH_VEL / distance) * diffX));
        this.setVelY(directionSign * ((this.APPROACH_VEL / distance) * diffY));
    }

    /**
     * Updates the dimensions of the SGO according to the animation's current
     * sprite.
     */
    private void updateDimensions () {
        this.setWidth(this.getAnimationController().getStandardAnimation().getView().getCurrentFrame().getWidth());
        this.setHeight(this.getAnimationController().getStandardAnimation().getView().getCurrentFrame().getHeight());
    }

    /**
     * Instantiates the commmands and adds them to the listener (within the
     * command classes themselves).
     */
    private void initCommands () {
        this.attackCommand = new AttackCommand(this.getGame(), this, this.getHandler(), this.inventory.getCurrentWeapon().getAttackFrames());
        MoveForwardCommand moveForwardCommand = new MoveForwardCommand(this.getGame(), this);
        MoveBackwardCommand moveBackwardCommand = new MoveBackwardCommand(this.getGame(), this);
        ReloadCommand reloadCommand = new ReloadCommand(this.getGame(), this);
        IncrementWeaponCommand incWeaponCommand = new IncrementWeaponCommand(this.getGame(), this);
        DecrementWeaponCommand decWeaponCommand = new DecrementWeaponCommand(this.getGame(), this);
        DebugCommand debugCommand = new DebugCommand(this.getGame());
    }

//============================== GETTERS ================================//
    public Inventory getInventory () {
        return this.inventory;
    }

    public int getMoney () {
        return this.money;
    }

    /**
     * If either the state is walking forward OR backward, then we are
     * "walking".
     *
     * @return
     */
    public boolean isWalking () {
        return this.playerState == PlayerState.WALKING_FORWARD
                ^ this.playerState == PlayerState.WALKING_BACKWARD;
    }

    public boolean isStanding () {
        return this.playerState == PlayerState.STANDING;
    }

    public boolean isAttacking () {
        return this.playerState == PlayerState.ATTACKING;
    }

    public boolean isMovingForward () {
        return this.playerState == PlayerState.WALKING_FORWARD;
    }

    public boolean isMovingBackward () {
        return this.playerState == PlayerState.WALKING_BACKWARD;
    }

    public int getMaxHealth () {
        return this.maxHealth;
    }

    public int getPlayerDirection () {
        int directionSign = 0;
        switch (this.playerState) {
            case WALKING_FORWARD:
                directionSign = 1;
                break;
            case WALKING_BACKWARD:
                directionSign = -1;
                break;
        }
        return directionSign;
    }

    public String getPlayerSex () {
        return this.sex;
    }

//=============================== SETTERS ================================//
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

    public void setMaxHealth (int _max) {
        this.maxHealth = _max;
    }

    /**
     * Sets the player's sex, while also reloading the game's assets. It also
     * defaults the player's animation to be the one associated with their sex.
     *
     * @param _sex
     */
    public void setPlayerSex (String _sex) {
        this.sex = _sex;
        this.inventory.reloadInventoryAssets();
        this.setAnimation(this.inventory.getCurrentWeapon().getWalkFrames());
        this.setAttackAnimator(this.inventory.getCurrentWeapon().getAttackFrames());
    }
}
