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
to simplify the rendering and logic pipeline.
===========================================================================
*/

package com.revivedstandards.model;

import com.revivedstandards.StandardGame;
import com.revivedstandards.controller.StandardAnimationController;
import com.revivedstandards.view.StandardSprite;
import java.awt.Graphics2D;

/**
 *  A StandardGameObject is any arbitrary object in the game. These 
 *  represent the models. This class must be extended.
 */
public abstract class StandardGameObject
{
    private StandardGame sg = null;
    private StandardSprite  sprite  = null;
    private StandardAnimationController sac = null;
    
    private int   life      = -1;
    private int   type      = 0;
    
    private double  x;
    private double  y;
    
    private double  velX;
    private double  velY;
    
    private double  width;
    private double  height;
    
    public StandardGameObject( StandardGame sg, StandardSprite sprite, int life )
    {
        this.sg     = sg;
        this.sprite = sprite;
        this.life   = life;
    }
    
    public StandardGameObject( StandardGame sg, StandardSprite sprite )
    {
        this.sg     = sg;
        this.sprite = sprite;
    }
    
    public StandardGameObject( StandardGame sg, StandardSprite sprite, double x, double y )
    {
        this.sg     = sg;
        this.sprite = sprite;
        this.x      = x;
        this.y      = y;
        this.width  = this.sprite.getSpriteWidth();
        this.height = this.sprite.getSpriteHeight();
    }
    
    public StandardGameObject( StandardGame sg, StandardAnimationController sac, double x, double y )
    {
        this.sg     = sg;
        this.sac    = sac;
        this.x = x;
        this.y = y;
    }
    
    public StandardGameObject( StandardGame sg, double x, double y )
    {
        this.sg = sg;
        this.x  = x;
        this.y  = y;
    }
    
    public abstract void update( double dt );
    
    public void renderGameObject( Graphics2D g2 )
    {
        if ( this.sprite == null && this.sac != null )
        {
            this.sac.renderFrame( g2 );
        }
        else
        {
            this.sprite.render( g2, this.x, this.y );
        }
    }

    public void setStandardAnimationController( StandardAnimationController s )
    {
        this.sac = s;
        this.sprite = null;
    }
    
    public StandardAnimationController getStandardAnimationController()
    {
        return this.sac;
    }
    
    public int getGameWidth()
    {
        return this.sg.getWindow().width();
    }
    
    public int getGameHeight()
    {
        return this.sg.getWindow().height();
    }
    
    public StandardGame getGame()
    {
        return this.sg;
    }
    
    public StandardSprite getSprite()
    {
        return this.sprite;
    }

    public int getLife ()
    {
        return life;
    }

    public void setLife ( int life )
    {
        this.life = life;
    }

    public int getType ()
    {
        return type;
    }

    public void setType ( int type )
    {
        this.type = type;
    }

    public double getX ()
    {
        return x;
    }

    public void setX ( double x )
    {
        this.x = x;
    }

    public double getY ()
    {
        return y;
    }

    public void setY ( double y )
    {
        this.y = y;
    }

    public double getVelX ()
    {
        return velX;
    }

    public void setVelX ( double velX )
    {
        this.velX = velX;
    }

    public double getVelY ()
    {
        return velY;
    }

    public void setVelY ( double velY )
    {
        this.velY = velY;
    }

    public double getWidth ()
    {
        return width;
    }

    public void setWidth ( double width )
    {
        this.width = width;
    }

    public double getHeight ()
    {
        return height;
    }

    public void setHeight ( double height )
    {
        this.height = height;
    }
   
}
