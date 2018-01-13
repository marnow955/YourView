package com.github.marnow955.yourview.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;

public class ToolbarController {

    @FXML
    private ToolBar root;
    @FXML
    private ToggleButton shInfoPanel;
    @FXML
    private ToggleButton shThumbView;
    @FXML
    private ToggleButton shBackgroundBtn;
    @FXML
    private ToggleButton fullScreenTB;

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
        fullScreenTB.selectedProperty().bindBidirectional(mainController.isFullScreenSelectedProperty);
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

    void setOrientation(Orientation orientation) {
        root.setOrientation(orientation);
        if (orientation == Orientation.VERTICAL) {
            root.setPrefWidth(-1);
        } else {
            root.setPrefHeight(-1);
        }
    }

    @FXML
    private void playSlideshow() {
        mainController.playSlideshow();
    }
}
