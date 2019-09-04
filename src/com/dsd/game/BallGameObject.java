package com.dsd.game;

import com.revivedstandards.StandardGame;
import com.revivedstandards.StdOps;
import com.revivedstandards.controller.StandardAnimationController;
import com.revivedstandards.model.StandardAnimation;
import com.revivedstandards.model.StandardGameObject;
import java.awt.image.BufferedImage;

public class BallGameObject extends StandardGameObject
{
    private int ballType;
    
    public BallGameObject( StandardGame sg, int x, int y )
    {
        super( sg, x, y );
        this.setLife( 1 );
        this.setVelX( StdOps.rand( 3.0, 7.0 ) );
        this.setVelY( StdOps.rand( 3.0, 7.0 ) );
        
        this.initFrames();

        this.setStandardAnimationController( new StandardAnimationController( 
                                             new StandardAnimation( this, this.initFrames(), 0.5d, 1.0d ) ) );
    }
    
    @Override
    public void update ( double dt )
    {
        this.getStandardAnimationController().update( dt );
        
        if ( this.getX() <= 0 || this.getX() > this.getGameWidth() - this.getWidth() )
        {
            this.setVelX( -this.getVelX() );
        }
        else if ( this.getY() <= 0 || this.getY() > this.getGameHeight() - this.getHeight() * 1.4 )
        {
            this.setVelY( -this.getVelY() );
        }
        
        this.setX( this.getX() + this.getVelX() );
        this.setY( this.getY() + this.getVelY() );
    }
    
    private BufferedImage[] initFrames()
    {
        BufferedImage[] frames = new BufferedImage[ 12 ];
        
        int ballGenerator = StdOps.rand( 1, 20 );
        
        if ( ballGenerator <= 10 )
        {
            this.ballType = 1;
        }
        else if ( ballGenerator <= 15 )
        {
            this.ballType = 2;
        }
        else if ( ballGenerator <= 20 )
        {
            this.ballType = 3;
        }
        
        for( int i = 0; i < frames.length; i++ )
        {
            if ( i < 10 )
            {
                frames[ i ] = StdOps.loadImage( "src/res/img/ball_" 
                                               + this.ballType + "/tile00" + i + ".png" );
            }
            else
            {
                frames[ i ] = StdOps.loadImage( "src/res/img/ball_" 
                                               + this.ballType + "/tile0" + i + ".png" );
            }
                
        }
        
        return frames;
    }
}
