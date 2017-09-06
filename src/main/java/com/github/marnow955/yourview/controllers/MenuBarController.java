package com.github.marnow955.yourview.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;

public class MenuBarController {

    @FXML
    private CheckMenuItem chBackground;

    private MainController mainController;

    private BooleanProperty isDisabled = new SimpleBooleanProperty(false);

    public boolean getIsDisabled() {
        return isDisabled.get();
    }

    public BooleanProperty isDisabledProperty() {
        return isDisabled;
    }

    void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    void setupView() {
        isDisabled.bind(mainController.isImageSelectedProperty.not());
        chBackground.selectedProperty().bindBidirectional(mainController.isChBackgroundSelectedProperty);
    }

    @FXML
    private void openFile() {
        mainController.openFile();
    }

    @FXML
    private void saveFile() {
        mainController.saveFile();
    }
}
