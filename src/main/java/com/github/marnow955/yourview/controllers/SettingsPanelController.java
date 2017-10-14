package com.github.marnow955.yourview.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleGroup;

import java.util.Properties;
import java.util.ResourceBundle;

public class SettingsPanelController {

    private Properties properties;
    @FXML
    private ResourceBundle resources;
    @FXML
    private ToggleGroup themeTG;
    private ObservableList<String> languageOptions;
    private ObservableList<String> positionsOptions;
    @FXML
    private ComboBox<String> langCB;
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
        langCB.setItems(languageOptions);
        toolbarPosition.setItems(positionsOptions);
        thumbViewPosition.setItems(positionsOptions);
    }

    void injectProperties(Properties properties) {
        this.properties = properties;
        setupView();
    }

    private void setupView() {
        themeTG.getToggles().forEach(toggle -> {
            if (((Node) toggle).getId().equals(properties.getProperty("theme")))
                toggle.setSelected(true);
        });
        langCB.getSelectionModel().select(resources.getString("lang_name"));
    }
}
