package com.dsd.game;

import com.revivedstandards.StandardGame;

public class GameTest
{
    public static void main ( String[] args )
    {
        StandardGame game = new StandardGame( 800, 600, "Test Game" );
        game.toggleDebugMode();
        
        for( int i = 0; i < 10; i++)
        {
            game.getGameEngine().addObject( new BallGameObject( game, 100, 100 ) );
        }

    }
}
