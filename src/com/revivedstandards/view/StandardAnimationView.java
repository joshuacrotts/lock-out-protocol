package com.revivedstandards.view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class StandardAnimationView implements Renderable{
   
    private BufferedImage[] animationFrames;
    private BufferedImage currentFrame;
    
    public StandardAnimationView( BufferedImage[] frames )
    {
        this.animationFrames = frames;
    }
    
    @Override
    public void render( Graphics2D g2, double x, double y )
    {
        g2.drawImage( currentFrame, ( int ) x, ( int ) y, null );
    }
    
    public BufferedImage[] getFrames()
    {
        return this.animationFrames;
    }
    
    public BufferedImage getCurrentFrame()
    {
        return this.currentFrame;
    }
    
    public void setCurrentFrameIndex( int frame )
    {
        this.currentFrame = this.animationFrames[ frame ];
    }
}
