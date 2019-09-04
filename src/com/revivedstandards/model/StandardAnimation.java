package com.revivedstandards.model;

import com.revivedstandards.view.StandardAnimationView;
import java.awt.image.BufferedImage;

public class StandardAnimation{
    
    private StandardGameObject sgo;
    private StandardAnimationView view;
    
    private int frameIndex;
   
    public StandardAnimation( StandardGameObject sgo, BufferedImage[] frames, int timer )
    {
        this.sgo = sgo;
        this.view = new StandardAnimationView( frames );
        this.setCurrentFrameIndex( 0 );
    }
    
    public void setCurrentFrameIndex( int frameIndex )
    {
        if ( frameIndex < 0 || frameIndex >= this.view.getFrames().length )
        {
            this.frameIndex = 0;
            this.view.setCurrentFrameIndex( this.frameIndex );
        }
        else
        {
            this.view.setCurrentFrameIndex( frameIndex );
            this.frameIndex = frameIndex;
        }
    }
    
    public StandardGameObject getStandardGameObject()
    {
        return this.sgo;
    }
        
    public int getCurrentFrameIndex()
    {
        return this.frameIndex;
    }
    
    public StandardAnimationView getView()
    {
        return this.view;
    }
    
}
