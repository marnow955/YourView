package com.github.marnow955.yourview.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class SettingsPanelController {

    @FXML
    private ComboBox<String> langChoiceBox;

    @FXML
    private void initialize() {
        setLangList();
    }

    private void setLangList() {
        langChoiceBox.getItems().addAll(
                "English",
                "Polish"
        );
    }
}
