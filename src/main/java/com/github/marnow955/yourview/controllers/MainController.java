package com.github.marnow955.yourview.controllers;

import com.github.marnow955.yourview.data.DirectoryImageLoader;
import com.github.marnow955.yourview.data.ImageReaderWriter;
import com.github.marnow955.yourview.data.processing.ImageManipulationsController;
import com.sun.jna.platform.FileUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController {

    @FXML
    ResourceBundle resources;
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuBarController menuBarController;
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
    BooleanProperty isImageSelectedProperty = new SimpleBooleanProperty(false);

    public void setStageAndSetupView(Stage primaryStage) {
        window = primaryStage;
        menuBarController.injectMainController(this);
        toolbarController.injectMainController(this);
        menuBarController.setupView();
        toolbarController.setupView();
        isChBackgroundSelectedProperty.addListener(((observable, oldValue, newValue) -> showCheckedBackground(newValue)));
    }

    void openFile() {
        //TODO: open on working directory - especially when another image is already selected
        File imageFile = ImageReaderWriter.showOpenDialog(window, System.getProperty("user.home"));
        openImage(imageFile);
    }

    private void openImage(File imageFile) {
        directory = new DirectoryImageLoader(new File(imageFile.getParent()));
        originalImageFile = imageFile;
        originalImage = ImageReaderWriter.openImage(originalImageFile);
        image = originalImage;
        processingController = new ImageManipulationsController();
        if (image != null) {
            imagePanelController.setImage(image);
            isImageSelectedProperty.set(true);
            window.setTitle("Your View - " + originalImageFile.getName());
        }
    }

    void saveFile() {
        //TODO: check selected flag (its no sense to check if it is null now
        //TODO: check null condition (also in openFile) - transfer code to setImage?
        //TODO: check is not null
        originalImageFile = ImageReaderWriter.showSaveDialog(window, originalImageFile);
        ImageReaderWriter.saveImage(image, originalImageFile);
        originalImage = image;
        processingController = new ImageManipulationsController();
        if (image != null) {
            imagePanelController.setImage(image);
            window.setTitle("Your View - " + originalImageFile.getName());
        }
    }

    void deleteFile() {
        //TODO: check selected img flag
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Stage alertWindow = (Stage) alert.getDialogPane().getScene().getWindow();
        alertWindow.getIcons().addAll(window.getIcons());
        alert.setTitle(resources.getString("del_conf_title"));
        alert.setHeaderText(null);
        alert.setContentText(resources.getString("del_conf_content"));
        ButtonType deleteBT = new ButtonType(resources.getString("w_delete"), ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelBT = new ButtonType(resources.getString("w_cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(deleteBT, cancelBT);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == deleteBT) {
            FileUtils fileUtils = FileUtils.getInstance();
            if (fileUtils.hasTrash()) {
                try {
                    fileUtils.moveToTrash(new File[]{originalImageFile});
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                originalImageFile.delete();
            }
            if (directory.hasAnotherImage()) {
                openImage(directory.getNextImage(originalImageFile));
            } else {
                //TODO: clearImage (delete file, image set flag, clear view etc
            }
        }
    }

    void previousImage() {
        if (directory.hasAnotherImage())
            openImage(directory.getPreviousImage(originalImageFile));
    }

    void nextImage() {
        if (directory.hasAnotherImage())
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

    void printImage() {
        ImageView printableContener = new ImageView(image);
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null) {
            boolean confirm = printerJob.showPrintDialog(window);
            if (confirm) {
                boolean succeeded = printerJob.printPage(printableContener);
                if (succeeded) {
                    printerJob.endJob();
                }
            }
        }
    }
}
