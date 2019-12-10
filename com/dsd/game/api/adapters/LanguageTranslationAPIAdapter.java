package com.dsd.game.api.adapters;

/**
 * This interface defines the method that every LanguageTranslationAPI should
 * implement.
 *
 * [Group Name: Data Structure Deadheads]
 *
 * @author Joshua, Ronald, Rinty Last Updated: 12/10/2019
 */
public interface LanguageTranslationAPIAdapter {

    public String translateText(String _text, String _lang);
    
}
