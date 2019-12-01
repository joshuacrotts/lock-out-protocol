package com.dsd.game.objects.powerups;

/**
 *
 * @author joshu
 */
public enum PowerupType {
    HEALTH("HEALTH"), INFINITE_AMMO("INFINITE AMMO"), BERSERK("BERSERK"), COIN("COIN");

    private final String label;

    private PowerupType(String _label) {
        this.label = _label;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
