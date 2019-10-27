package com.dsd.game;

import com.dsd.game.controller.LanguageController;

/**
 * This enum defines the possible languages the user can switch to in the game.
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
    UR(LanguageController.translate("Urdu", "ur"), "ur"),
    VI(LanguageController.translate("Vietnamese", "vi"), "vi");

    private final String language;
    private final String isoCode;

    private static int languageIndex = 5;

    private static final LanguageEnum[] LANGUAGE_LIST = {
        AR, ZH, CS, NL, EN, FR, DE, EL, IT, JA, PT, RO, RU, ES, UR, VI
    };

    /**
     * Increases the index pointer for the LANGUAGE_LIST array.
     */
    public static void increaseLanguage () {
        if (languageIndex < LANGUAGE_LIST.length - 1) {
            languageIndex++;
        }
        LanguageController.setLanguage(LANGUAGE_LIST[languageIndex].isoCode);
    }

    /**
     * Decreases the index pointer for the LANGUAGE_LIST array.
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
     * Returns the language code for the language pointed at by languageIndex in
     * the array.
     *
     * @return
     */
    public static String getCode () {
        return LANGUAGE_LIST[languageIndex].isoCode;
    }
}
