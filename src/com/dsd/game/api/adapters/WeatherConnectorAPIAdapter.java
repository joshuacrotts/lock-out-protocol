package com.dsd.game.api.adapters;

/**
 * This interface defines the method that every WeatherConnectorAPI should
 * implement.
 *
 * @author Joshua, Ronald, Rinty
 */
public interface WeatherConnectorAPIAdapter {

    public String getWeather (String _city);
}
