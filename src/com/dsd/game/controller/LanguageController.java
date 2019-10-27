package com.dsd.game.controller;

import com.dsd.game.api.TranslatorAPI;

/**
 *
 * @author Joshua
 */
public class LanguageController {

    public static String lang = "en";

    public static void setLanguage (String _lang) {
        LanguageController.lang = "en-" + _lang;
    }

    public static String translate (String _text) {
        return TranslatorAPI.translate(_text, lang);
    }

    public static String translate (String _text, String _lang) {
        return TranslatorAPI.translate(_text, _lang);
    }
}
