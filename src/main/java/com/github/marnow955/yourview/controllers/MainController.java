package com.github.marnow955.yourview.controllers;

import com.github.marnow955.yourview.data.DirectoryImageLoader;
import com.github.marnow955.yourview.data.OpenSaveImageDialog;
import com.github.marnow955.yourview.data.processing.ImageManipulationsController;
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
    private DirectoryImageLoader directory;
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

    @FXML
    private void openFile() {
        //TODO: open on working directory - especially when another image is already selected
        OpenSaveImageDialog dialog = new OpenSaveImageDialog(window, System.getProperty("user.home"));
        File imageFile = dialog.showOpenDialog();
        directory = new DirectoryImageLoader(new File(imageFile.getParent()));
        openImage(imageFile);
    }

    private void openImage(File imageFile) {
        originalImageFile = imageFile;
        originalImage = OpenSaveImageDialog.openImage(originalImageFile);
        image = originalImage;
        processingController = new ImageManipulationsController();
        if (image != null) {
            imagePanelController.setImage(image);
            window.setTitle("Your View - " + originalImageFile.getName());
        }
    }

    void saveFile() {
        //TODO: check selected flag (its no sense to check if it is null now
        //TODO: check null condition (also in openFile) - transfer code to setImage?
        OpenSaveImageDialog dialog = new OpenSaveImageDialog(window, originalImageFile.getParent());
        //TODO: check is not null
        originalImageFile = dialog.showSaveDialog(originalImageFile);
        dialog.saveImage(image, originalImageFile);
        originalImage = image;
        processingController = new ImageManipulationsController();
        if (image != null) {
            imagePanelController.setImage(image);
            window.setTitle("Your View - " + originalImageFile.getName());
        }
    }

    void previousImage() {
        openImage(directory.getPreviousImage(originalImageFile));
    }

    void nextImage() {
        openImage(directory.getNextImage(originalImageFile));
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

    void horizontalFlip() {
        //TODO: check if exif flags exist
        image = processingController.horizontalFlip(image);
        imagePanelController.setImage(image);
    }

    void verticalFlip() {
        image = processingController.verticalFlip(image);
        imagePanelController.setImage(image);
    }

    private void showCheckedBackground(Boolean newValue) {
        imagePanelController.showCheckedBackground(newValue);
    }
}
