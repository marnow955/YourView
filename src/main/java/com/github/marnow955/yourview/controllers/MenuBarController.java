package com.github.marnow955.yourview.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;

public class MenuBarController {

    @FXML
    private CheckMenuItem chBackground;

    private MainController mainController;

    void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    void setupView() {
        chBackground.selectedProperty().bindBidirectional(mainController.isChBackgroundSelectedProperty);
    }

    @FXML
    private void openFile() {
        mainController.openFile();
    }
}
