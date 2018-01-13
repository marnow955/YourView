package com.github.marnow955.yourview.controllers;

import javafx.fxml.FXML;

public class EditPanelController {

    private MainController mainController;

    void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void saveChanges() {
    }

    @FXML
    private void cancelChanges() {
        mainController.isEditPanelSelectedProperty.set(false);
    }
}
