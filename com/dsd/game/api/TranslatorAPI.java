package com.dsd.game.api;

import com.dsd.game.api.adapters.WeatherConnectorAPIAdapter;
import com.dsd.game.api.adapters.CityLocatorAPIAdapter;
import com.dsd.game.api.adapters.LanguageTranslationAPIAdapter;

/**
 * This class is a translation class from the game to the city location and
 * weather APIs. Instead of allowing the game to directly talk to the APIs, this
 * translation class will serve as an intermediary step.
 *
 * To change the APIs, simply change the constructors for the three instance
 * variables.
 * 
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 * 
 * @updated 12/10/19
 */
public final class TranslatorAPI {

    private static final LanguageTranslationAPIAdapter languageAPI = new LanguageTranslation();
    private static final CityLocatorAPIAdapter cityLocatorAPI = new CityLocator();
    private static final WeatherConnectorAPIAdapter weatherAPI = new WeatherConnector();

//=================== GETTERS ====================================
    /**
     * Returns the city retrieved by the CityLocator API.
     *
     * @return
     */
    public static String getCity() {
        return cityLocatorAPI.getCity();
    }

    /**
     * Returns the IP address of the user retrieved by the CityLocator API.
     *
     * @return
     */
    public static String getIPAddress() {
        return cityLocatorAPI.getIPAddress();
    }

    /**
     * Returns a string representation of the weather retrieved by the
     * WeatherConnector API. The string returned is what determines if it is
     * raining in the game or not.
     *
     * @return
     */
    public static String getWeather() {
        return weatherAPI.getWeather(TranslatorAPI.getCity());
    }

    /**
     * Returns a string representation of the weather in the city _city from the
     * WeatherConnector API.
     *
     * @param _city
     * @return
     */
    public static String getWeather(String _city) {
        return weatherAPI.getWeather(_city);
    }

    /**
     * Contacts the translator API to translate a string of text to a specific
     * language.
     *
     * @param _text
     * @param _lang
     * @return
     */
    public static String translate(String _text, String _lang) {
        if (_lang.equals("en")) {
            return _text;
        }
        return languageAPI.translateText(_text, _lang);
    }

    private TranslatorAPI() {
    }

}
