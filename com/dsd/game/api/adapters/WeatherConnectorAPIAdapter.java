package com.dsd.game.api.adapters;

/**
 * This interface defines the method that every WeatherConnectorAPI should
 * implement.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 *
 * @updated 11/12/19
 */
public interface WeatherConnectorAPIAdapter {

    public String getWeather(String _city);
}
