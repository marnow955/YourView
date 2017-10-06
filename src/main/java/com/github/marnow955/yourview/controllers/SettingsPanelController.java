package com.github.marnow955.yourview.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;

public class SettingsPanelController {

    @FXML
    private ScrollPane settingsPanel;

    @FXML
    private void initialize() {
        settingsPanel.managedProperty().bind(settingsPanel.visibleProperty());
    }

    void togglePanelVisibility() {
        settingsPanel.setVisible(!settingsPanel.isVisible());
    }
}
