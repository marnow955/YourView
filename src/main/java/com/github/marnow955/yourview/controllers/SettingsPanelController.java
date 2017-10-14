package com.github.marnow955.yourview.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.util.ResourceBundle;

public class SettingsPanelController {

    @FXML
    ResourceBundle resources;
    private ObservableList<String> languageOptions;
    private ObservableList<String> positionsOptions;
    @FXML
    private ComboBox<String> langChoiceBox;
    @FXML
    private ComboBox<String> toolbarPosition;
    @FXML
    private ComboBox<String> thumbViewPosition;

    @FXML
    private void initialize() {
        languageOptions = FXCollections.observableArrayList(
                resources.getString("english"),
                resources.getString("polish")
        );
        positionsOptions = FXCollections.observableArrayList(
                resources.getString("w_top"),
                resources.getString("w_left"),
                resources.getString("w_right"),
                resources.getString("w_bottom")
        );
        langChoiceBox.setItems(languageOptions);
        toolbarPosition.setItems(positionsOptions);
        thumbViewPosition.setItems(positionsOptions);
    }
}
