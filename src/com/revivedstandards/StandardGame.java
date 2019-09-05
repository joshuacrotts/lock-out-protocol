/*
===========================================================================
                   Standards Java Game Library Source Code
           Copyright (C) 2017-2019 Joshua Crotts & Andrew Matzureff 
Standards is free software: you can redistribute it and/or modify it under 
the terms of the GNU General Public License as published by the Free Software 
Foundation, either version 3 of the License, or (at your option) any later 
version.

Standards Source Code is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Standards Source Code. If not, see <http://www.gnu.org/licenses/>.

Standards is the long-overdue update to the everlasting Standards 2.0 library
Andrew Matzureff and I created two years ago. I am including it in this project
to simplify the rendering and logic pipeline, but with a focus on the MVC
paradigm.
===========================================================================
 */
package com.revivedstandards;

import com.revivedstandards.controller.StandardGameEngineController;
import com.revivedstandards.view.StandardWindowView;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;

/**
 * This is the main class encompassing the window, render loop, physics/tick
 * loop, etc.
 */
public class StandardGame extends Canvas implements Runnable
{
    //
    //  Default window object
    //
    private StandardWindowView frame = null;

    //
    //  Game engine object to abstract the rendering, updating, and input 
    //  from this class.
    //
    private StandardGameEngineController gameEngine = null;

    //
    //  Determines if the thread is currently 
    //  running.
    //
    private boolean running = false;
    
    //
    //  Other miscellaneous flags.
    //
    private boolean debugMode = false;
    private int fps = -1;

    //
    // Render Thread
    //
    private Thread thread = null;

    //  
    //  Graphics Context to determine if we support multi-monitors
    //
    private static final GraphicsConfiguration GRAPHICS_CONFIG = initGraphics();

    public StandardGame ( int width, int height, String title )
    {
        //Rolled steel for making boilers...
        super( GRAPHICS_CONFIG );
        this.frame = new StandardWindowView( width, height, title, this, GRAPHICS_CONFIG );
        this.gameEngine = new StandardGameEngineController();
        this.setIgnoreRepaint( true );
        //keyboard = new Keyboard();
        //addKeyListener(keyboard);
        //mouse = new Mouse();
        //addMouseListener(mouse);
        //addMouseMotionListener(mouse);
        //addMouseWheelListener(mouse);
        this.createBufferStrategy( 3 );
        this.start();
    }

    private synchronized void start ()
    {
        if ( this.running )
        {
            return;
        }
        else
        {
            this.thread = new Thread( this );
            this.thread.start();
            this.running = true;
        }
    }

    private synchronized void stop ()
    {
        if ( !this.running )
        {
            return;
        }
        else
        {
            try
            {
                this.thread.join();
            }
            catch ( InterruptedException e )
            {
                e.printStackTrace();
            }
            
            this.running = false;
            System.exit( 0 );
        }
    }

    @Override
    public void run ()
    {
        requestFocus(); //Focuses the click/input on the frame/canvas.
        long lastTime = System.nanoTime(); //The current system's nanotime.
        double ns = 1000000000.0 / 60.0; //Retrieves how many nano-seconds are currently in one tick/update.
        double delta = 0; //How many unprocessed nanoseconds have gone by so far.
        long timer = System.currentTimeMillis();
        int frames = 0; //The frames per second.
        int updates = 0; //The updates per second.

        BufferStrategy bufferStrategy = null;

        while ( this.running )
        {
            boolean renderable = false; //Determines if the game should render the actual graphics.

            long now = System.nanoTime();//At this point, the current system's nanotime once again.
            delta += ( now - lastTime ) / ns;
            lastTime = now;
            //  If the amount of unprocessed ticks is or goes above one...
            //  Also determines if the game should update or not/render. Approximately sixty frames per second.*/
            while ( delta >= 1 )
            {
                this.gameEngine.update( delta );

                delta--;
                updates++;

                renderable = true;
            }

            // This clause replaces the original methods of tick and render.
            // One can use tick and render exclusively.
            if ( renderable )
            {
                frames++;
                bufferStrategy = this.getBufferStrategy();//Retrieves the current bufferstrategy
                Graphics2D renderer = ( Graphics2D ) bufferStrategy.getDrawGraphics(); //Sets up the available graphics on the StandardDraw Graphics2D object

                //Sets the color to black, and buffers the screen to eradicate the black flickering dilemma.
                this.doubleBufferScreen( renderer );

                //Calls the render method within the game engine class to render
                //other entities.
                this.gameEngine.render( renderer );
                
                this.drawFPSToScreen( renderer );

                renderer.dispose(); //Disposes of the Renderer Graphics object to free resources
                bufferStrategy.show(); //Shows the current BufferStrategy
            }

            if ( System.currentTimeMillis() - timer > 1000 )
            {
                if ( this.debugMode )
                {
                    this.frame.setTitle( this.frame.getTitle() + " | " + updates + " ups, " + frames + " fps" );
                    this.fps = frames;
                }

                timer += 1000;
                updates = 0;
                frames = 0;
            }
        }

        this.stop();
    }
    
    /**
     * Sets the color of the screen to black, and buffers the screen to not only
     * fix flickering, but to make sure all previous frames of animation are 
     * removed from the screen.
     * 
     * @param renderer 
     */
    private void doubleBufferScreen( Graphics2D renderer )
    {
        renderer.setColor( Color.BLACK );
        renderer.fillRect( 0, 0, this.frame.width(), this.frame.height() );
    }
    
    /**
     * Renders the current frames per second to the screen in the top-left corner.
     * @param renderer 
     */
    private void drawFPSToScreen( Graphics2D renderer)
    {
        renderer.setColor( Color.YELLOW );
        renderer.drawString( "FPS: " + this.fps, 0 + 20, 0 + 20 );
    }
    
    public void toggleDebugMode()
    {
        this.debugMode = !debugMode;
    }

    private static GraphicsConfiguration initGraphics ()
    {//NOTE: 2/6/19 - support multi monitor environment
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        return gc;
    }

    public StandardWindowView getWindow ()
    {
        return this.frame;
    }

    public StandardGameEngineController getGameEngine ()
    {
        return this.gameEngine;
    }
}
