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

package com.revivedstandards.model;

import com.revivedstandards.view.StandardAnimationView;
import java.awt.image.BufferedImage;

/**
 * This class represents the model for an animation. 
 */
public final class StandardAnimation{
    
    private final StandardGameObject sgo;
    private final StandardAnimationView view;
    
    private int frameIndex;     // Current frame index in the array of images
    private long lastTime = 0;  // Time between the last system call and the current one
    private double fps;         // How many frames of animation should we have per second

    public StandardAnimation( StandardGameObject sgo, BufferedImage[] frames, double fps )
    {
        this.sgo  = sgo;
        this.view = new StandardAnimationView( frames );
        this.fps  = fps;
        
        this.setCurrentFrameIndex( 0 );
        this.setDefaultDimensions();
    }
    
    /**
     * Sets the current frame of animation with the supplied index.
     * 
     * Contacts the view to set its current frame bufferedimage variable
     * to point to frameIndex's position in the bufferedimage array.
     * @param frameIndex 
     */
    private void setCurrentFrameIndex( int frameIndex )
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
    
    /**
     * Advances the current frame of animation to the next one in succession.
     */
    public void advanceFrame()
    {
        this.setCurrentFrameIndex( this.getCurrentFrameIndex() + 1 );
    }
    
    /**
     * Assigns the default SGO width/height to the width and height of the 
     * first frame of animation.
     */
    private void setDefaultDimensions()
    {
        this.sgo.setWidth( this.getView().getCurrentFrame().getWidth() );
        this.sgo.setHeight(this.getView().getCurrentFrame().getHeight() );
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
    
    public double getFPS()
    {
        return this.fps;
    }
    
    public long getLastTime()
    {
        return this.lastTime;
    }
    
    public void setLastTime( long t )
    {
        this.lastTime = t;
    }
    
}
