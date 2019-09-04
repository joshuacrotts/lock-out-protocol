package com.dsd.game;

import com.revivedstandards.StandardGame;
import com.revivedstandards.StdOps;
import com.revivedstandards.model.StandardGameObject;
import com.revivedstandards.view.StandardSprite;

public class BallGameObject extends StandardGameObject
{
    private StandardSprite sprite;
    
    public BallGameObject( StandardGame sg, int x, int y )
    {
        super( sg, new StandardSprite( "src/res/img/ball.png"), x, y );
        this.setLife( 1 );
        this.setVelX( StdOps.rand( 3.0, 7.0 ) );
        this.setVelY( StdOps.rand( 3.0, 7.0 ) );
    }
    
    @Override
    public void update ( double dt )
    {
        if ( this.getX() <= 0 || this.getX() > this.getGameWidth() - this.getWidth() )
        {
            this.setVelX( -this.getVelX() );
        }
        else if ( this.getY() <= 0 || this.getY() > this.getGameHeight() - this.getHeight() )
        {
            this.setVelY( -this.getVelY() );
        }
        
        this.setX( this.getX() + this.getVelX() );
        this.setY( this.getY() + this.getVelY() );
    }
}
