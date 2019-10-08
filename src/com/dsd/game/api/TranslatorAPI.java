package com.dsd.game.api;

/**
 * This class is a translation class from the game to the city location and
 * weather APIs. Instead of allowing the game to directly talk to the APIs, this
 * translation class will serve as an intermediary step.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public final class TranslatorAPI {

//=================== GETTERS ====================================//
    /**
     * Returns the city retrieved by the CityLocator API.
     *
     * @return
     */
    public static String getCity() {
        return CityLocator.getCity();
    }

    /**
     * Returns the IP address of the user retrieved by the CityLocator API.
     *
     * @return
     */
    public static String getIPAddress() {
        return CityLocator.getIPAddress();
    }

    /**
     * Returns a string representation of the weather retrieved by the
     * WeatherConnector API. The string returned is what determines if it is
     * raining in the game or not.
     *
     * @return
     */
    public static String getWeather() {
        return WeatherConnector.getWeather(TranslatorAPI.getCity());
    }

    /**
     * Returns a string representation of the weather in the city _city from the
     * WeatherConnector API.
     *
     * @param _city
     * @return
     */
    public static String getWeather(String _city) {
        return WeatherConnector.getWeather(_city);
    }

    private TranslatorAPI() {

    }
}
