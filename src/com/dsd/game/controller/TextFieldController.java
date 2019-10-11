package com.dsd.game.controller;

import com.dsd.game.userinterface.model.TextFieldModel;
import java.util.ArrayList;

/**
 * Controller to handle the text-fields in the game; if the user clicks on one,
 * all the others will deactivate.
 *
 * @author Joshua
 */
public class TextFieldController {

    public static ArrayList<TextFieldModel> textFieldController = new ArrayList<>();

    /**
     * Iterates over the list of TextFieldModels and deactivates all that are
     * not _field.
     *
     * @param _field
     */
    public static void deactivate (TextFieldModel _field) {
        for (int i = 0 ; i < textFieldController.size() ; i++) {
            TextFieldModel otherModel = textFieldController.get(i);
            if (otherModel.isActive() && otherModel != _field) {
                otherModel.setActive(false);
            }
        }
    }

    public static void addField (TextFieldModel _model) {
        textFieldController.add(_model);
    }
}
