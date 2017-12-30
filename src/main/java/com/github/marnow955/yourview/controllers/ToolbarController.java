package com.github.marnow955.yourview.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;

public class ToolbarController {

    @FXML
    private ToggleButton shInfoPanel;
    @FXML
    private ToggleButton shThumbView;
    @FXML
    private ToggleButton shBackgroundBtn;

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
        shThumbView.selectedProperty().bindBidirectional(mainController.isThumbViewSelectedProperty);
        shBackgroundBtn.selectedProperty().bindBidirectional(mainController.isChBackgroundSelectedProperty);
        shInfoPanel.selectedProperty().bindBidirectional(mainController.isImageInfoPanelSelectedProperty);
    }

    @FXML
    private void scaleToWidth() {
        mainController.scaleToWidth();
    }

    @FXML
    private void scaleToHeight() {
        mainController.scaleToHeight();
    }

    @FXML
    private void adjustImage() {
        mainController.adjustImage();
    }

    @FXML
    private void adjustWindow() {
        mainController.adjustWindow();
    }

    @FXML
    private void rotateLeft() {
        mainController.rotateLeft();
    }

    @FXML
    private void rotateRight() {
        mainController.rotateRight();
    }

    @FXML
    private void horizontalFlip() {
        mainController.horizontalFlip();
    }

    @FXML
    private void verticalFlip() {
        mainController.verticalFlip();
    }

    @FXML
    private void saveFile() {
        mainController.saveFile();
    }

    @FXML
    private void previousImage() {
        mainController.previousImage();
    }

    @FXML
    private void nextImage() {
        mainController.nextImage();
    }

    @FXML
    private void printImage() {
        mainController.printImage();
    }

    @FXML
    private void deleteFile() {
        mainController.deleteFile();
    }

    @FXML
    private void zoomOut() {
        mainController.zoomOut();
    }

    @FXML
    private void zoomIn() {
        mainController.zoomIn();
    }

    @FXML
    private void actualSize() {
        mainController.actualSize();
    }

    @FXML
    private void showSettings() {
        mainController.showSettings();
    }
}
