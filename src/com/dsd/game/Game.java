package com.dsd.game;

import com.revivedstandards.main.StandardGame;
import javax.swing.JOptionPane;

/**
 * The are all for showcasing the potential of Standards, and what we CAN make.
 * It is in no way a representation of what state the game is in. Actual
 * development of the game hasn't even started yet. I simply want to get y'all
 * in the mindset of using this library.
 *
 * @author Joshua
 */
public class Game extends StandardGame
{

    public Game ( int width, int height, String title )
    {
        super( 1280, 720, title );

        this.StartGame();
    }

    public void tick ()
    {

    }

    public void render ()
    {
        
    }

    public static void main ( String[] args )
    {
        StandardGame game = new Game( 1280, 720, "CSC-340-Game" );
        
        String city = JOptionPane.showInputDialog( "Enter a city: " );
        JOptionPane.showMessageDialog( null, WeatherConnector.getWeather( city ) );        
    }
}
