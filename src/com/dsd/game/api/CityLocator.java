package com.dsd.game.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 * This class is an example of connecting to and loading data from the weather
 * API.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class CityLocator {

    private static URL url;
    private static InputStream inputStream;
    private static BufferedReader reader;

    private static String line;
    private static String key;

    static {
        //  Loads in the API key to connect with
        CityLocator.inputStream = CityLocator.class.getClassLoader().getResourceAsStream(".config/.city_config.txt");
        CityLocator.reader = new BufferedReader(new InputStreamReader(CityLocator.inputStream));
        try {
            CityLocator.line = CityLocator.reader.readLine();
        }
        catch (IOException ex) {
            Logger.getLogger(CityLocator.class.getName()).log(Level.SEVERE, null, ex);
        }
        CityLocator.key = CityLocator.line.substring(CityLocator.line.lastIndexOf(":") + 1);
    }

    //=============================== GETTERS ======================================
    /**
     * Returns the city that the user is located in.
     *
     * @return
     */
    public static String getCity () {
        return CityLocator.getCityJSON().getString("city");
    }

    /**
     * Connects to the Amazon AWS API to generate the IP address of the user.
     *
     * @return string representation of IP address
     */
    public static String getIPAddress () {
        String ipAddress = null;

        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            ipAddress = in.readLine();
        }
        catch (MalformedURLException ex) {
            Logger.getLogger(CityLocator.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(CityLocator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ipAddress;
    }

    /**
     * Connects to the IPStack API with the supplied IP, and constructs a
     * JSONObject with the 'city' field. Returns this in a String object.
     *
     * @param city
     * @return
     */
    private static String fetch (String _ipAddress) {
        StringBuilder jsonInformation = null;

        try {
            //  Processes the request to the API, and reads the information
            //  into the StringBuilder object.
            jsonInformation = new StringBuilder();
            CityLocator.url = new URL(String.format("http://api.ipstack.com/%s?access_key=%s&fields=city", _ipAddress, CityLocator.key));
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
            Logger.getLogger(CityLocator.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(CityLocator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonInformation.toString();
    }

    /**
     * Returns the JSON object fetched by the API call with the supplied IP
     * address
     *
     * @return
     */
    private static JSONObject getCityJSON () {
        return new JSONObject(CityLocator.fetch(CityLocator.getIPAddress()));
    }
}
