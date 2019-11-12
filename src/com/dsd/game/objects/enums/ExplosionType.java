package com.dsd.game.objects.enums;

import com.dsd.game.util.Utilities;
import java.awt.image.BufferedImage;

/**
 * This enum separates the different types of explosions. Shotgun explosions are
 * different from grenade launcher explosions. We only want to load in the
 * information once, so we can delegate it to an enum and call it a day.
 *
 * @author Joshua
 */
public enum ExplosionType {

    //  Various types of explosions (and possibly more to come).
    SHOTGUN_EXPLOSION("src/resources/img/bullet/explosion/explosion1", 16, 16),
    GRENADE_EXPLOSION("src/resources/img/bullet/explosion/explosion2", 16, 8);

    //  Information relevant to the explosion animation.
    private final String explosionPath;
    private final BufferedImage[] explosionImages;
    private final int frames;
    private final int explosionFPS;

    private ExplosionType (String _path, int _frames, int _fps) {
        this.explosionPath = _path;
        this.frames = _frames;
        this.explosionFPS = _fps;
        this.explosionImages = Utilities.loadFrames(this.explosionPath, this.frames);
    }

//=============================== GETTERS ===================================//
    public BufferedImage[] getExplosionFrames () {
        return this.explosionImages;
    }
}
