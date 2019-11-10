package com.dsd.game.controller;

import com.dsd.game.api.TranslatorAPI;

/**
 * This class controls which language is currently being used.
 *
 * @author Joshua, Ronald, Rinty
 */
public class LanguageController {

    public static String lang = "en";

    public static void setLanguage (String _lang) {

        LanguageController.lang = "en-" + _lang;
    }

    public static String translate (String _text) {

        if (lang.equals("en-en")) {

            return _text;
        }

        return TranslatorAPI.translate(_text, lang);
    }

    public static String translate (String _text, String _lang) {

        if (_lang.equals("en")) {

            return _text;
        }

        return TranslatorAPI.translate(_text, _lang);
    }

}
