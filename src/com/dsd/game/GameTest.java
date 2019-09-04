package com.dsd.game;

import com.revivedstandards.StandardGame;

public class GameTest
{
    public static void main ( String[] args )
    {
        StandardGame game = new StandardGame( 800, 600, "Test Game" );
        game.toggleDebugMode();
        game.getGameEngine().addObject( new BallGameObject( game, 20, 20 ) );
        game.getGameEngine().addObject( new BallGameObject( game, 40, 40 ) );
        
    }
}
