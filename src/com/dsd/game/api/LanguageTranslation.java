package com.dsd.game.api;

import com.dsd.game.api.adapters.LanguageTranslationAPIAdapter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This API calls the yandex language API for a language translation.
 *
 * @author Joshua, Ronald, Rinty
 */
public class LanguageTranslation implements LanguageTranslationAPIAdapter {

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
     * Connects to the yandex API to translate a string of text to a
     * pre-determined language.
     *
     * @param _text to translate
     * @param _lang to translate _text to.
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
        catch (IOException ex) {
            Logger.getLogger(LanguageTranslation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return jsonInformation.toString().substring(jsonInformation.toString().indexOf("[") + 2, jsonInformation.toString().lastIndexOf("]") - 1);
    }

    /**
     * Intermediary method call by the TranslatorAPI class to translate a string
     * of text to a certain language.
     *
     * @param _s
     * @param _lang
     * @return
     */
    @Override
    public String translateText (String _s, String _lang) {
        return fixString(_s, _lang);
    }

    /**
     * If there are any spaces inside of the string to be translated, we need to
     * replace them with %20 calls because the URL is unable to parse spaces.
     *
     * @param _s
     * @param _lang
     * @return
     */
    private static String fixString (String _s, String _lang) {
        String fixedString = _s.replaceAll("\\s", "%20");
        return fetch(fixedString, _lang);
    }

}
