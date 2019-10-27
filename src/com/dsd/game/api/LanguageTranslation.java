/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 * This API calls the yandex language API for a language translation.
 *
 * @author Joshua
 */
public class LanguageTranslation {

    private static BufferedReader reader;
    private static InputStream inputStream;

    private static URL url;
    private static String line;
    private static String key;

    static {
        //  Loads in the key for the api connection
        LanguageTranslation.inputStream = LanguageTranslation.class.getClassLoader().getResourceAsStream(".config/.language_config.txt");
        LanguageTranslation.reader = new BufferedReader(new InputStreamReader(LanguageTranslation.inputStream));
        try {
            LanguageTranslation.line = LanguageTranslation.reader.readLine();
        }
        catch (IOException ex) {
            Logger.getLogger(LanguageTranslation.class.getName()).log(Level.SEVERE, null, ex);
        }
        //  Extracts the key from the line read in by the buffered reader
        LanguageTranslation.key = LanguageTranslation.line.substring(LanguageTranslation.line.lastIndexOf(":") + 1);
    }

    /**
     * Connects to the weather API to retrieve the JSON-formatted weather
     * information for the supplied city.
     *
     * @param city
     * @return
     */
    private static String fetch (String _text, String _lang) {
        StringBuilder jsonInformation = null;

        try {
            //  Processes the request to the API, and reads the information
            //  into the StringBuilder object.
            jsonInformation = new StringBuilder();
            url = new URL(String.format("https://translate.yandex.net/api/v1.5/tr.json/translate?key=%s&text=%s&lang=%s", key, _text, _lang));
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
            Logger.getLogger(LanguageTranslation.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(LanguageTranslation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonInformation.toString().substring(jsonInformation.toString().indexOf("[") + 2, jsonInformation.toString().lastIndexOf("]") - 1);
    }

    public static String translateText (String _s, String _lang) {
        return fixString(_s, _lang);
    }

    private static String fixString (String _s, String _lang) {
        String fixedString = _s.replaceAll("\\s", "%20");
        return fetch(fixedString, _lang);
    }
}
