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

package com.revivedstandards.controller;

import com.revivedstandards.model.StandardAnimation;
import com.revivedstandards.model.StandardGameObject;
import java.awt.Graphics2D;

public class StandardAnimationController
{
    private final StandardAnimation animation;

    public StandardAnimationController ( StandardAnimation animation )
    {
        this.animation = animation;
        this.animation.getView().setCurrentFrameIndex( 0 );
    }

    /**
     * Increments the timer based on the delay. If the current animation timer 
     * is greater than the delay, we move to the next frame of animation.
     * 
     * @param dt 
     */
    public void update ( double dt )
    {
        if ( System.nanoTime() > this.animation.getLastTime() + 
                                  ( 1_000_000_000 / this.animation.getFPS() ) )
        {
            this.animation.advanceFrame();
            this.animation.setLastTime( System.nanoTime() );
        }
    }

    public void renderFrame ( Graphics2D g2 )
    {
        StandardGameObject sgo = this.animation.getStandardGameObject();

        this.animation.getView().render( g2, sgo.getX(), sgo.getY() );
    }

}