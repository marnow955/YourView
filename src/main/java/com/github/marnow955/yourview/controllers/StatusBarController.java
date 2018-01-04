package com.github.marnow955.yourview.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import org.controlsfx.control.StatusBar;

public class StatusBarController {

    @FXML
    private StatusBar statusBar;
    @FXML
    private ToggleGroup toolbarPositionTG;
    @FXML
    private ToggleGroup navigationBarPositionTG;
    @FXML
    private ToggleGroup thumbViewPositionTG;
    private MainController mainController;
    private StringProperty toolbarPosition = new SimpleStringProperty("top");
    private StringProperty thumbnailsPosition = new SimpleStringProperty("bottom");
    private StringProperty navigationBarPosition = new SimpleStringProperty("center");

    void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    void setupView() {
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
    private void toggleToolbar() {
        mainController.isToolbarSelectedProperty.set(!mainController.isToolbarSelectedProperty.get());
    }

    @FXML
    private void toggleThumbnails() {
        mainController.isThumbViewSelectedProperty.set(!mainController.isThumbViewSelectedProperty.get());
    }

    @FXML
    private void toggleNavigationBar() {
        mainController.isNavigationBarVisibleProperty.set(!mainController.isNavigationBarVisibleProperty.get());
    }

    void updateText(String name, int index, int nrOfElements, int width, int height, long size, int zoomPercent) {
        String text = index + "/" + nrOfElements + "  ";
        text += name + "  ";
        text += width + "x" + height + " pixels  ";
        int iteration = 0;
        String bSizeText = "";
        double bSize = size;
        while (bSize >= 1024 && iteration < 3) {
            bSize = bSize/1024;
            iteration++;
        }
        bSize = Math.round(bSize*100.0)/100.0;
        switch (iteration) {
            case 0:
                bSizeText = bSize + "B";
                break;
            case 1:
                bSizeText = bSize + "KB";
                break;
            case 2:
                bSizeText = bSize + "MB";
                break;
        }
        text += bSizeText + "  ";
        text += zoomPercent + "%";
        statusBar.setText(text);
    }
}
