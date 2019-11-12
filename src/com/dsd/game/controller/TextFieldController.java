package com.dsd.game.controller;

import com.dsd.game.userinterface.model.TextFieldModel;
import java.util.ArrayList;

/**
 * This class holds all text-fields in the game (currently only 2). When one is
 * active, all others need to be deactivated so text-input is not read into
 * them.
 *
 * @author Joshua
 */
public class TextFieldController {

    //  ArrayList of text fields
    public static ArrayList<TextFieldModel> textFieldController = new ArrayList<>();

    //  The selected text field index in the arraylist.
    private static int selectedTextField = -1;

    /**
     * Deactivates all text fields except the one that is passed.
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

    /**
     * Adds a text field that is selectable to the controller.
     *
     * @param _model
     */
    public static void addField (TextFieldModel _model) {
        textFieldController.add(_model);
    }

    /**
     * Increments the pointer that determines which text field is "highlighted".
     */
    public static void incrementSelectedTextField () {
        if (TextFieldController.selectedTextField + 1 >= TextFieldController.textFieldController.size()) {
            TextFieldController.selectedTextField = 0;
        }
        else {
            TextFieldController.selectedTextField++;
        }
        TextFieldModel selectedField = TextFieldController.textFieldController.get(selectedTextField);
        selectedField.setActive(true);
        TextFieldController.deactivate(selectedField);

    }
}
