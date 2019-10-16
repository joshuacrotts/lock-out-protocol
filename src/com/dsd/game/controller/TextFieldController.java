/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dsd.game.controller;

import com.dsd.game.userinterface.model.TextFieldModel;
import java.util.ArrayList;

/**
 *
 * @author Joshua
 */
public class TextFieldController {

    public static ArrayList<TextFieldModel> textFieldController = new ArrayList<>();
    private static int selectedTextField = -1;

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
