package com.dsd.game.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class is an example of connecting to and loading data from the weather
 * API.
 *
 * @author Joshua
 */
public class WeatherConnector {

    private static BufferedReader reader;
    private static InputStream inputStream;
    private static URL url;

    private static String line;
    private static String key;

    static {
        //  Loads in the key for the api connection
        WeatherConnector.inputStream = WeatherConnector.class.getClassLoader().getResourceAsStream(".config/.weather_config.txt");
        WeatherConnector.reader = new BufferedReader(new InputStreamReader(WeatherConnector.inputStream));
        try {
            WeatherConnector.line = WeatherConnector.reader.readLine();
        }
        catch (IOException ex) {
            Logger.getLogger(WeatherConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        //  Extracts the key from the line read in by the buffered reader
        WeatherConnector.key = WeatherConnector.line.substring(WeatherConnector.line.lastIndexOf(":") + 1);
    }

    /**
     * Connects to the weather API to retrieve the JSON-formatted weather
     * information for the supplied city.
     *
     * @param city
     * @return
     */
    private static String fetch (String city) {
        StringBuilder jsonInformation = null;

        try {
            //  Processes the request to the API, and reads the information
            //  into the StringBuilder object.
            jsonInformation = new StringBuilder();
            url = new URL(String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s ", city, key));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                jsonInformation.append(inputLine);
            }
            in.close();
        }
        catch (ProtocolException ex) {
            Logger.getLogger(WeatherConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(WeatherConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonInformation.toString();
    }

    /**
     * Returns the array from the collection of JSON objects from the API
     * connection.
     *
     * @param city
     * @return
     */
    private static JSONArray getWeatherArray (String city) {
        JSONObject weatherJSON = new JSONObject(WeatherConnector.fetch(city));
        return (JSONArray) weatherJSON.get("weather");
    }

    /**
     * Returns the text following the description JSON object
     *
     * @param weatherArray
     * @return
     */
    private static String getWeatherType (JSONArray weatherArray) {
        JSONObject indexOne = (JSONObject) weatherArray.getJSONObject(0);
        return (String) indexOne.get("description");
    }

    /**
     * Returns the weather type for the parameter city.
     *
     * @param city
     * @return
     */
    public static String getWeather (String city) {
        return getWeatherType(getWeatherArray(city));
    }
}
