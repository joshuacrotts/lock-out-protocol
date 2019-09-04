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

import com.revivedstandards.model.StandardGameObject;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The StandardGameEngineController has an ArrayList of SGOs. In the main
 * game loop, the update method will call SGEC, and update every SGO
 * that is inside of the array list. It will also render the sprite views
 * for these SGOs. May need possible refactoring down the road.
 */
public class StandardGameEngineController
{
    
    // Arraylist of game object controllers.
    private ArrayList<StandardGameObject> sgoHandler = new ArrayList<>();
    
    /**
     * The update() function updates all game logic and physics within the
     * world. Rendering will take place in a separate thread. This will also
     * update all extraneous game logic.
     */
    public void update( double dt )
    {
        Iterator<StandardGameObject> sgoHandlerIt = sgoHandler.listIterator();
        
        while( sgoHandlerIt.hasNext() )
        {
            StandardGameObject sgo = sgoHandlerIt.next();
            sgo.update( dt );
            
            if ( sgo.getLife() < 0 )
            {
                sgoHandlerIt.remove();
            }
        }
    }
    
    public void render( Graphics2D g2 )
    {
        Iterator<StandardGameObject> sgoHandlerIt = sgoHandler.listIterator();
        while( sgoHandlerIt.hasNext() )
        {
            sgoHandlerIt.next().renderGameObject( g2 );
        }
    }
    
    public void addObject( StandardGameObject sgo )
    {
        this.sgoHandler.add( sgo );
    }
}
