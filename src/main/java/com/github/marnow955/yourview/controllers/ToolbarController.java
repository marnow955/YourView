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
}
