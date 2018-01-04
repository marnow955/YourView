package com.github.marnow955.yourview.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;

public class MenuBarController {

    @FXML
    private CheckMenuItem navigationBar;
    @FXML
    private CheckMenuItem statusBar;
    @FXML
    private CheckMenuItem menu;
    @FXML
    private CheckMenuItem toolbar;
    @FXML
    private CheckMenuItem infoPanel;
    @FXML
    private CheckMenuItem thView;
    @FXML
    private CheckMenuItem chBackground;
    @FXML
    private ToggleGroup toolbarPositionTG;
    @FXML
    private ToggleGroup thumbViewPositionTG;
    @FXML
    private ToggleGroup navigationBarPositionTG;

    private MainController mainController;

    private StringProperty toolbarPosition = new SimpleStringProperty("top");

    private StringProperty thumbnailsPosition = new SimpleStringProperty("bottom");

    private StringProperty navigationBarPosition = new SimpleStringProperty("center");

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
        thView.selectedProperty().bindBidirectional(mainController.isThumbViewSelectedProperty);
        chBackground.selectedProperty().bindBidirectional(mainController.isChBackgroundSelectedProperty);
        toolbar.selectedProperty().bindBidirectional(mainController.isToolbarSelectedProperty);
        infoPanel.selectedProperty().bindBidirectional(mainController.isImageInfoPanelSelectedProperty);
        menu.selectedProperty().bindBidirectional(mainController.isMenuVisibleProperty);
        statusBar.selectedProperty().bindBidirectional(mainController.isStatusBarVisibleProperty);
        navigationBar.selectedProperty().bindBidirectional(mainController.isNavigationBarVisibleProperty);
        toolbarPosition.bindBidirectional(mainController.toolbarPosition);
        thumbnailsPosition.bindBidirectional(mainController.thumbnailsPosition);
        navigationBarPosition.bindBidirectional(mainController.navigationBarPosition);
        selectToolbarPosition();
        selectThumbnailsPosition();
        selectNavigationBarPosition();
        toolbarPosition.addListener((observable, oldValue, newValue) -> {
            selectToolbarPosition();
        });
        thumbnailsPosition.addListener((observable, oldValue, newValue) -> {
            selectThumbnailsPosition();
        });
        navigationBarPosition.addListener((observable, oldValue, newValue) -> {
            selectNavigationBarPosition();
        });
        toolbarPositionTG.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                RadioMenuItem selected = (RadioMenuItem) toolbarPositionTG.getSelectedToggle();
                toolbarPosition.set(selected.getId());
            }
        });
        thumbViewPositionTG.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                RadioMenuItem selected = (RadioMenuItem) thumbViewPositionTG.getSelectedToggle();
                thumbnailsPosition.set(selected.getId());
            }
        });
        navigationBarPositionTG.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                RadioMenuItem selected = (RadioMenuItem) navigationBarPositionTG.getSelectedToggle();
                navigationBarPosition.set(selected.getId());
            }
        });
    }

    private void selectToolbarPosition() {
        toolbarPositionTG.getToggles().forEach(toggle -> {
            if (((RadioMenuItem) toggle).getId().equals(toolbarPosition.get())) {
                toggle.setSelected(true);
            }
        });
    }

    private void selectThumbnailsPosition() {
        thumbViewPositionTG.getToggles().forEach(toggle -> {
            if (((RadioMenuItem) toggle).getId().equals(thumbnailsPosition.get())) {
                toggle.setSelected(true);
            }
        });
    }

    private void selectNavigationBarPosition() {
        navigationBarPositionTG.getToggles().forEach(toggle -> {
            if (((RadioMenuItem) toggle).getId().equals(navigationBarPosition.get())) {
                toggle.setSelected(true);
            }
        });
    }

    @FXML
    private void openFile() {
        mainController.openFile();
    }

    @FXML
    private void saveFile() {
        mainController.saveFile();
    }

    @FXML
    private void showSettings() {
        mainController.showSettings();
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
    private void previousImage() {
        mainController.previousImage();
    }

    @FXML
    private void nextImage() {
        mainController.nextImage();
    }

    @FXML
    private void playSlideshow() {
        mainController.playSlideshow();
    }
}
