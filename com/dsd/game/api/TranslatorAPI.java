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
 * @author Joshua, Ronald, Rinty Last Updated: 12/10/2019
 */
public final class TranslatorAPI {

    private static final LanguageTranslationAPIAdapter languageAPI = new LanguageTranslation();
    private static final CityLocatorAPIAdapter cityLocatorAPI = new CityLocator();
    private static final WeatherConnectorAPIAdapter weatherAPI = new WeatherConnector();

//=================== GETTERS ====================================
    public static String getCity() {
        // Returns the city retrieved by the CityLocator API.
        return cityLocatorAPI.getCity();
    }

    public static String getIPAddress() {
        // Returns the IP address of the user retrieved by the CityLocator API.
        return cityLocatorAPI.getIPAddress();
    }

    public static String getWeather() {
        // Returns a string representation of the weather retrieved by the WeatherConnector API.
        return weatherAPI.getWeather(TranslatorAPI.getCity());
    }

    public static String getWeather(String _city) {
        // Returns a string representation of the weather in the city _city from the WeatherConnector API.
        return weatherAPI.getWeather(_city);
    }

    public static String translate(String _text, String _lang) {
        if (_lang.equals("en")) {
            return _text;
        }
        // Returns a translated string of text to a specific language.
        return languageAPI.translateText(_text, _lang);
    }

    private TranslatorAPI() {
    }

}
