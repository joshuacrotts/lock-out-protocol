/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game;

import com.dsd.game.controller.LanguageController;

/**
 *
 * @author Joshua
 */
public enum LanguageEnum {

    AR(LanguageController.translate("Arabic", "ar"), "ar"),
    ZH(LanguageController.translate("Chinese", "zh"), "zh"),
    CS(LanguageController.translate("Czech", "cs"), "cs"),
    NL(LanguageController.translate("Dutch", "nl"), "nl"),
    EN(LanguageController.translate("English", "en"), "en"),
    FR(LanguageController.translate("French", "fr"), "fr"),
    DE(LanguageController.translate("German", "de"), "de"),
    EL(LanguageController.translate("Greek", "el"), "el"),
    IT(LanguageController.translate("Italian", "it"), "it"),
    JA(LanguageController.translate("Japanese", "ja"), "ja"),
    PT(LanguageController.translate("Portuguese", "pt"), "pt"),
    RO(LanguageController.translate("Romanian", "ro"), "ro"),
    RU(LanguageController.translate("Russian", "ru"), "ru"),
    ES(LanguageController.translate("Spanish", "es"), "es"),
    VI(LanguageController.translate("Vietnamese", "vi"), "vi");

    private final String language;
    private final String isoCode;

    private static int languageIndex = 12;

    private static final LanguageEnum[] LANGUAGE_LIST = {
        AR, AR, AR, ZH, ZH, ZH, CS, CS, CS, NL, NL, NL, EN, EN, EN, FR, FR, FR, DE, DE, DE, EL, EL, EL, IT, IT, IT, JA, JA, JA, PT, PT, PT, RO, RO, RO, RU, RU, RU, ES, ES, ES, VI, VI, VI
    };

    /**
     * Increases the index pointer for the RESOLUTION_LIST array.
     */
    public static void increaseLanguage () {
        if (languageIndex < LANGUAGE_LIST.length - 1) {
            languageIndex++;
        }
        LanguageController.setLanguage(LANGUAGE_LIST[languageIndex].isoCode);
    }

    /**
     * Decreases the index pointer for the RESOLUTION_LIST array.
     */
    public static void decreaseLanguage () {
        if (languageIndex > 0) {
            languageIndex--;
        }
        LanguageController.setLanguage(LANGUAGE_LIST[languageIndex].isoCode);
    }

    private LanguageEnum (String _language, String _isoCode) {
        this.language = _language;
        this.isoCode = _isoCode;
    }

//================================ GETTERS ==================================//
    public static int getLanguageIndex () {
        return languageIndex;
    }

    public static void setLanguageIndex (int _n) {
        languageIndex = _n;
        LanguageController.setLanguage(LANGUAGE_LIST[languageIndex].isoCode);
    }

    /**
     * Returns a string representation of the language.
     *
     * @return
     */
    public String getLanguageString () {
        return this.language;
    }

    /**
     * Returns a string representation of the iso 2-letter code of the language.
     *
     * @return
     */
    public String getLanguageCode () {
        return this.isoCode;
    }

    /**
     * Returns a string representation of the language in the array.
     *
     * @return
     */
    public static String getLanguage () {
        return LANGUAGE_LIST[languageIndex].language;
    }

    /**
     * Returns the actual dimension object associated with each ResolutionEnum.
     *
     * @return
     */
    public static String getCode () {
        return LANGUAGE_LIST[languageIndex].isoCode;
    }
}
