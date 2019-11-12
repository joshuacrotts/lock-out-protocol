package com.dsd.game.controller;

import com.dsd.game.api.TranslatorAPI;

/**
 * This class controls which language is currently being used.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty
 */
public class LanguageController {

    public static String lang = "en";

    /**
     * Sets the overall language of the game. This is used upon reload.
     *
     * @param _lang
     */
    public static void setLanguage (String _lang) {
        LanguageController.lang = "en-" + _lang;
    }

    /**
     * Translates a string of text with the language set by this controller. If
     * the language is set to English, we just leave it as is.
     *
     * @param _text
     * @return
     */
    public static String translate (String _text) {
        if (lang.equals("en-en")) {
            return _text;
        }
        return TranslatorAPI.translate(_text, lang);
    }

    /**
     * Translates a string of text with the supplied language. If the language
     * is set to Engish, we just leave it as is.
     *
     * @param _text
     * @param _lang
     * @return
     */
    public static String translate (String _text, String _lang) {
        if (_lang.equals("en")) {
            return _text;
        }
        return TranslatorAPI.translate(_text, _lang);
    }
}
