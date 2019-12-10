package com.dsd.game.api;

import com.dsd.game.api.adapters.WeatherConnectorAPIAdapter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class is an example of connecting to and loading data from the weather
 * API.
 * 
 *[Group Name: Data Structure Deadheads]
 * 
 * @author Joshua, Ronald, Rinty
 * 
 * @updated 12/10/19
 */
public class WeatherConnector implements WeatherConnectorAPIAdapter {

    private static BufferedReader reader;
    private static InputStream inputStream;
    private static URL url;
    private static String line;
    private static String key;

    static {
        //  Loads in the key for the api connection.
        WeatherConnector.inputStream = WeatherConnector.class.getClassLoader().getResourceAsStream(".config/.weather_config.txt");
        WeatherConnector.reader = new BufferedReader(new InputStreamReader(WeatherConnector.inputStream));
        try {
            WeatherConnector.line = WeatherConnector.reader.readLine();
        } catch (IOException _ex) {
            Logger.getLogger(WeatherConnector.class.getName()).log(Level.SEVERE, null, _ex);
        }
        //  Extracts the key from the line read in by the buffered reader.
        WeatherConnector.key = WeatherConnector.line.substring(WeatherConnector.line.lastIndexOf(":") + 1);
    }
    
    /**
     * Connects to the weather API to retrieve the JSON-formatted weather
     * information for the supplied city.
     *
     * @param city
     * @return this in the string object.
     */
    private static String fetch(String _city) {
        StringBuilder jsonInformation = null;
        try {
            //  Processes the request to the API, and reads the information into the StriBuilder object.
            jsonInformation = new StringBuilder();
            url = new URL(String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s ", _city, key));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                jsonInformation.append(inputLine);
            }
            in.close();
        } catch (IOException _ex) {
            Logger.getLogger(WeatherConnector.class.getName()).log(Level.SEVERE, null, _ex);
        }
        return jsonInformation.toString();
    }

    //=================================== GETTERS ==============================
    @Override
    public String getWeather(String _city) {
        // Returns the weather type for the parameter city.
        return getWeatherType(getWeatherArray(_city));
    }

    private static JSONArray getWeatherArray(String _city) {
        JSONObject weatherJSON = new JSONObject(WeatherConnector.fetch(_city));
        // Returns the array from the collection of JSON onjects from the API connection.
        return (JSONArray) weatherJSON.get("weather");
    }

    private static String getWeatherType(JSONArray _weatherArray) {
        JSONObject indexOne = (JSONObject) _weatherArray.getJSONObject(0);
        // Returns the text following the description JSON object.
        return (String) indexOne.get("description");
    }

}
