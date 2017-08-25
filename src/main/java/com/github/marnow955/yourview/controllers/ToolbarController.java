package com.github.marnow955.yourview.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;

public class ToolbarController {

    @FXML
    private ToggleButton shBackgroundBtn;

    private MainController mainController;

    void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    void setupView() {
        shBackgroundBtn.selectedProperty().bindBidirectional(mainController.isChBackgroundSelectedProperty);
    }

    @FXML
    private void scaleToWidth() {
        mainController.scaleToWidth();
    }

    @FXML
    private void scaleToHeight() {
        mainController.scaleToHeight();
    }
}
