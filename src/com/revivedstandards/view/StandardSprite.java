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

package com.revivedstandards.view;

import com.revivedstandards.StdOps;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *  A Sprite is a view for any arbitrary SGO. It represents some 2D image.
 */
public class StandardSprite implements Renderable
{
   private BufferedImage imageView;
   
   public StandardSprite( String filePath ) 
   {
       this.imageView = StdOps.loadImage( filePath );
   }
   
   /**
    * Renders the Sprite at some position x and y.
    * @param g2
    * @param x
    * @param y 
    */
   @Override
   public void render( Graphics2D g2, double x, double y )
   {
       g2.drawImage( this.imageView, ( int ) x, ( int ) y, null );
   }
   
   public int getSpriteWidth()
   {
       return this.imageView.getWidth();
   }
   
   public int getSpriteHeight()
   {
       return this.imageView.getHeight();
   }

}
