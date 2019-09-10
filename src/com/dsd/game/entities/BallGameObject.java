package com.dsd.game.entities;

import com.revivedstandards.StandardGame;
import com.revivedstandards.StdOps;
import com.revivedstandards.controller.StandardAnimationController;
import com.revivedstandards.model.StandardAnimation;
import com.revivedstandards.model.StandardGameObject;
import java.awt.image.BufferedImage;

public class BallGameObject extends StandardGameObject
{
    private final int BALL_FRAMES = 12;

    private int ballType;
    private double rotation = 0;
    private double fps = 120;
    
    public BallGameObject ( StandardGame sg, int x, int y )
    {
        super( sg, x, y );
        this.setLife( 1 );
        this.setVelX( StdOps.rand( 3.0, 7.0 ) );
        this.setVelY( StdOps.rand( 3.0, 7.0 ) );

        this.rotation = StdOps.rand( 0.0001, 0.01 );

        this.initFrames();

        this.setStandardAnimationController( new StandardAnimationController(
                new StandardAnimation( this, this.initFrames(), this.fps ) ) );
    }

    @Override
    public void update ( double dt )
    {
        this.getStandardAnimationController().update( dt );
        this.getStandardAnimationController().getStandardAnimation().setRotation(
                this.getStandardAnimationController().getStandardAnimation().getRotation() + this.rotation );

        if ( this.getX() <= 0 || this.getX() > this.getGameWidth() - this.getWidth() )
        {
            this.setVelX( -this.getVelX() );
        }
        else if ( this.getY() <= 0 || this.getY() > this.getGameHeight() - this.getHeight() * 1.4 )
        {
            this.setVelY( -this.getVelY() );
        }

        this.updateVelocity();
    }

    private void updateVelocity ()
    {
        this.setX( this.getX() + this.getVelX() );
        this.setY( this.getY() + this.getVelY() );
    }

    private BufferedImage[] initFrames ()
    {
        BufferedImage[] frames = new BufferedImage[ BALL_FRAMES ];

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

        for ( int i = 0 ; i < frames.length ; i++ )
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
