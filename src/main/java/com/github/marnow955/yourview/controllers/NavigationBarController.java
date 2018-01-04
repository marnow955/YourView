package com.github.marnow955.yourview.controllers;

import javafx.fxml.FXML;

public class NavigationBarController {

    private MainController mainController;

    void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void previousImage() {
        mainController.previousImage();
    }

    @FXML
    private void nextImage() {
        mainController.nextImage();
    }
}
