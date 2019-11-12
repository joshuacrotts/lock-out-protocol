package com.dsd.game.api.adapters;

/**
 * This interface defines the method that every LanguageTranslationAPI should
 * implement.
 *
 * @author Joshua, Ronald, Rinty
 */
public interface LanguageTranslationAPIAdapter {

    public String translateText (String _text, String _lang);
}
