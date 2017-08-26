package com.github.marnow955.yourview.controllers;

import com.github.marnow955.yourview.image.OpenSaveImageDialog;
import com.github.marnow955.yourview.image.processing.ImageManipulationsController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;

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
    private File originalImageFile;
    private Image originalImage;
    private Image image;
    private ImageManipulationsController processingController;

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
        OpenSaveImageDialog opener = new OpenSaveImageDialog(window);
        originalImageFile = opener.showOpenDialog();
        originalImage = opener.openImage(originalImageFile);
        image = originalImage;
        processingController = new ImageManipulationsController();
        if (originalImage != null) {
            imagePanelController.setImage(image);
            window.setTitle("Your View - " + originalImageFile.getName());
        }
    }

    void scaleToWidth() {
        imagePanelController.scaleToWidth(image);
    }

    void scaleToHeight() {
        imagePanelController.scaleToHeight(image);
    }

    void adjustImage() {
        imagePanelController.adjustImage(image);
    }

    void adjustWindow() {
        window.setMaximized(false);
        toolbar.setPrefWidth(image.getWidth() + 2);
        imagePanelController.setImage(image);
        imagePanel.setPrefSize(image.getWidth() + 2, image.getHeight() + 2);
        window.sizeToScene();
    }

    void rotateLeft() {
        //TODO: change to imageview rotate and save exif rotate flag tag (if user click save roate permanently)
        image = processingController.rotateLeft(image);
        imagePanelController.setImage(image);
    }

    void rotateRight() {
        //TODO: same as rotateLeft
        image = processingController.rotateRight(image);
        imagePanelController.setImage(image);
    }
}
