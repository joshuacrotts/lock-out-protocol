package com.dsd.game;

import com.dsd.game.entities.BallGameObject;
import com.dsd.game.entities.Zombie;
import com.revivedstandards.StandardGame;
import com.revivedstandards.StdOps;

/**
 * These few classes (GameTest, Direction, InputController, BallGameObject, and
 * Zombie) are all for showcasing the potential of Standards, and what we CAN
 * make. It is in no way a representation of what state the game is in. Actual
 * development of the game hasn't even started yet. I simply want to get y'all
 * in the mindset of using this library.
 *
 * @author Joshua
 */
public class GameTest
{

    public static void main ( String[] args )
    {
        StandardGame game = new StandardGame( 800, 600, "Test Game" );
        game.toggleDebugMode();

        //Create a new Zombie object acting as the player
        Zombie player = new Zombie( game, 300, 100 );

        //Creates an input controller and assigns the player to be 
        //the controlling SGO
        InputController ic = new InputController( player );

        //Adds the listener (InputController) to the StandardGame
        game.addKeyListener( ic );

        // Add the player to the game engine for rendering & updating
        game.getGameEngine().addObject( player );

        //Adds 5 random Pokéballs
        for ( int i = 0 ; i < 5 ; i++ )
        {
            BallGameObject pokeball = new BallGameObject( game, StdOps.rand( 100, 400 ), StdOps.rand( 100, 400 ) );
            game.getGameEngine().addObject( pokeball );

        }

    }
}
