package com.dsd.game;

import com.revivedstandards.StandardGame;
import com.revivedstandards.StdOps;
import com.revivedstandards.controller.StandardAnimationController;
import com.revivedstandards.model.StandardAnimation;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.view.StandardSprite;
import java.awt.image.BufferedImage;

public class BallGameObject extends StandardGameObject
{
    private StandardSprite sprite;
    private static BufferedImage[] frames;
    static
    {
        frames = new BufferedImage[5];
        frames[ 0 ] = StdOps.loadImage( "src/res/img/ball1.png" );
        frames[ 1 ] = StdOps.loadImage( "src/res/img/ball2.png" );
        frames[ 2 ] = StdOps.loadImage( "src/res/img/ball3.png" );
        frames[ 3 ] = StdOps.loadImage( "src/res/img/ball4.png" );
        frames[ 4 ] = StdOps.loadImage( "src/res/img/ball5.png" );
    }
    
    public BallGameObject( StandardGame sg, int x, int y )
    {
        super( sg, x, y );
        this.setLife( 1 );
        this.setVelX( StdOps.rand( 3.0, 7.0 ) );
        this.setVelY( StdOps.rand( 3.0, 7.0 ) );
        
        this.setStandardAnimationController( new StandardAnimationController( new StandardAnimation(this, frames, 20)));
        
    }
    
    @Override
    public void update ( double dt )
    {
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
}
