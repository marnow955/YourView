package com.github.marnow955.yourview.controllers;

import com.github.marnow955.yourview.settings.Settings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;

public class StatusBarController {

    @FXML
    private ToggleGroup toolbarPositionTG;
    @FXML
    private ToggleGroup thumbViewPositionTG;
    private MainController mainController;
    private StringProperty toolbarPosition = new SimpleStringProperty("top");
    private StringProperty thumbnailsPosition = new SimpleStringProperty("bottom");

    void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    void setupView() {
        toolbarPosition.bindBidirectional(mainController.toolbarPosition);
        thumbnailsPosition.bindBidirectional(mainController.thumbnailsPosition);
        selectToolbarPosition();
        selectThumbnailsPosition();
        toolbarPosition.addListener((observable, oldValue, newValue) -> {
            selectToolbarPosition();
        });
        thumbnailsPosition.addListener((observable, oldValue, newValue) -> {
            selectThumbnailsPosition();
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

    @FXML
    private void toggleToolbar() {
        mainController.isToolbarSelectedProperty.set(!mainController.isToolbarSelectedProperty.get());
    }

    @FXML
    private void toggleThumbnails() {
        mainController.isThumbViewSelectedProperty.set(!mainController.isThumbViewSelectedProperty.get());
    }
}
