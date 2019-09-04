package com.revivedstandards.controller;

import com.revivedstandards.model.StandardAnimation;
import com.revivedstandards.model.StandardGameObject;
import java.awt.Graphics2D;

public class StandardAnimationController {
    
    private StandardAnimation animation;
    
    private int timer = 20;
    private int currentTime = 0;
    
    public StandardAnimationController( StandardAnimation animation )
    {
        this.animation = animation;
        this.animation.getView().setCurrentFrameIndex( 0 );
    }
    
    public void runAnimation()
    {   
        System.out.println( currentTime );
        if ( this.currentTime < this.timer )
        {
            this.currentTime++;
        }
        else
        {
            this.animation.setCurrentFrameIndex( this.animation.getCurrentFrameIndex() + 1 );
            System.out.println( "curent frame: " + this.animation.getCurrentFrameIndex() );
            if ( this.frameOutOfBounds() )
            {
                this.animation.setCurrentFrameIndex( 0 );
            }
            
            this.resetTime();
        }
    }
    
    public void renderFrame( Graphics2D g2 )
    {
        this.runAnimation();
        
        StandardGameObject sgo = this.animation.getStandardGameObject();
        
        this.animation.getView().render( g2, sgo.getX(), sgo.getY());
    }
    
    public void resetTime()
    {
        this.currentTime = 0;
    }
    
    public boolean frameOutOfBounds()
    {
        return this.animation.getCurrentFrameIndex() >= this.animation.getView().getFrames().length;
    }
    
    
}
