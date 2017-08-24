package com.github.marnow955.yourview.controllers;

import com.github.marnow955.yourview.OpenSaveImageDialog;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private ToolBar toolbar;
    @FXML
    private ToolbarController toolbarController;
    @FXML
    private ScrollPane imagePanel;
    @FXML
    private ImagePanelController imagePanelController;
    @FXML
    private CheckMenuItem chBackground;

    private Stage window;
    private Image originalImage;

    //TODO: change initial to settings value
    BooleanProperty isChBackgroundSelectedProperty = new SimpleBooleanProperty(false);

    public void setStageAndSetupView(Stage primaryStage) {
        window = primaryStage;
        toolbarController.injectMainController(this);
        toolbarController.setupView();
        chBackground.selectedProperty().bindBidirectional(isChBackgroundSelectedProperty);
        isChBackgroundSelectedProperty.addListener(((observable, oldValue, newValue) -> showCheckedBackground(newValue)));
    }

    private void showCheckedBackground(Boolean newValue) {
        isChBackgroundSelectedProperty.set(newValue);
        imagePanelController.showCheckedBackground(newValue);
    }

    @FXML
    void openFile() {
        originalImage = new OpenSaveImageDialog().openImage(window);
        if (originalImage != null) {
            imagePanelController.setImage(originalImage);
        }
    }
}
