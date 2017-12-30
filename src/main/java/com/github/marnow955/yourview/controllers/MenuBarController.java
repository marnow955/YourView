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

    private MainController mainController;

    private StringProperty toolbarPosition = new SimpleStringProperty("top");

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
        toolbarPosition.bindBidirectional(mainController.toolbarPosition);
        selectToolbarPosition();
        toolbarPosition.addListener((observable, oldValue, newValue) -> {
            selectToolbarPosition();
        });
        toolbarPositionTG.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                RadioMenuItem selected = (RadioMenuItem) toolbarPositionTG.getSelectedToggle();
                toolbarPosition.set(selected.getId());
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
}
