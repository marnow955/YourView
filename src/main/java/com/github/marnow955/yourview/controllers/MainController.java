package com.github.marnow955.yourview.controllers;

import com.github.marnow955.yourview.data.DirectoryImageLoader;
import com.github.marnow955.yourview.data.ImageReaderWriter;
import com.github.marnow955.yourview.data.processing.ImageManipulationsController;
import com.sun.jna.platform.FileUtils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    private ScrollPane imageInfoPanel;
    @FXML
    private ImageInfoPanelController imageInfoPanelController;
    @FXML
    private ScrollPane imagePanel;
    @FXML
    private ImagePanelController imagePanelController;
    @FXML
    private CheckMenuItem chBackground;
    @FXML
    private ScrollPane thumbView;
    @FXML
    private ThumbViewController thumbViewController;

    private Stage window;
    private File imageFile;
    private DirectoryImageLoader directory;
    private Image image;
    private ImageManipulationsController processingController;

    BooleanProperty isImageSelectedProperty = new SimpleBooleanProperty(false);
    private IntegerProperty imageIndex = new SimpleIntegerProperty(-1);
    //TODO: change initial to settings value
    BooleanProperty isChBackgroundSelectedProperty = new SimpleBooleanProperty(false);
    BooleanProperty isThumbViewSelectedProperty = new SimpleBooleanProperty(false);

    public void setStageAndSetupView(Stage primaryStage) {
        window = primaryStage;
        menuBarController.injectMainController(this);
        toolbarController.injectMainController(this);
        imagePanelController.injectMainController(this);
        thumbViewController.injectMainController(this);
        menuBarController.setupView();
        toolbarController.setupView();
        thumbView.managedProperty().bind(thumbView.visibleProperty());
        thumbView.visibleProperty().bind(isThumbViewSelectedProperty);
        isChBackgroundSelectedProperty.addListener(((observable, oldValue, newValue) -> showCheckedBackground(newValue)));
    }

    void openFile() {
        File imageFile;
        if (isImageSelectedProperty.get()) {
            imageFile = ImageReaderWriter.showOpenDialog(window, this.imageFile.getParent());
        } else {
            imageFile = ImageReaderWriter.showOpenDialog(window, System.getProperty("user.home"));
        }
        openImage(imageFile);
    }

    private void openImage(File imageFile) {
        if (imageFile == null)
            return;
        this.imageFile = imageFile;
        image = ImageReaderWriter.openImage(this.imageFile, false);
        processingController = new ImageManipulationsController();
        if (image != null) {
            imagePanelController.setImage(image);
            imagePanelController.adjustImage(image);
            isImageSelectedProperty.set(true);
            imageInfoPanelController.setInfo(this.imageFile);
            directory = new DirectoryImageLoader(new File(MainController.this.imageFile.getParent()),
                    thumbViewController.getCellWidth(), thumbViewController.getCellHeight());
            imageIndex.set(directory.getImageIndex(MainController.this.imageFile));
            updateWindowTitle();
            loadThumbView();
        }
    }

    void selectImage(int index) {
        imageIndex.set(index);
        imageFile = directory.getImageFile(index);
        image = ImageReaderWriter.openImage(imageFile, false);
        imagePanelController.setImage(image);
        imagePanelController.adjustImage(image);
        isImageSelectedProperty.set(true);
        imageInfoPanelController.setInfo(imageFile);
        updateWindowTitle();
        thumbViewController.setSelected(index);
    }

    private void loadThumbView() {
        thumbViewController.setThumbView(directory.getThumbnails());
        thumbViewController.setSelected(imageIndex.get());
    }

    void updateWindowTitle() {
        window.setTitle("Your View - " +
                (imageIndex.get() + 1) + "/" + directory.getNrOfImagesInDirectory() + " - " +
                imageFile.getName() + " | " + imagePanelController.getZoomPercent() + "%");
    }

    void saveFile() {
        imageFile = ImageReaderWriter.showSaveDialog(window, imageFile);
        if (imageFile == null)
            return;
        ImageReaderWriter.saveImage(image, imageFile);
        openImage(imageFile);
    }

    void deleteFile() {
        if (deleteConfirm()) {
            FileUtils fileUtils = FileUtils.getInstance();
            if (fileUtils.hasTrash()) {
                try {
                    fileUtils.moveToTrash(new File[]{imageFile});
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                imageFile.delete();
            }
            if (directory.hasAnotherImage()) {
                openImage(directory.getImageFile(directory.getNextImageIndex(imageIndex.get())));
            } else {
                clearWorkspace();
            }
        }
    }

    private boolean deleteConfirm() {
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
        return result.get() == deleteBT;
    }

    void clearWorkspace() {
        imageFile = null;
        imageIndex.set(-1);
        directory = null;
        image = null;
        processingController = null;
        isImageSelectedProperty.set(false);
        thumbViewController.setThumbView(FXCollections.observableList(new ArrayList<>()));
        imagePanelController.setImage(null);
        window.setTitle("Your View");
    }

    void previousImage() {
        if (directory.hasAnotherImage())
            selectImage(directory.getPreviousImageIndex(imageIndex.get()));
    }

    void nextImage() {
        if (directory.hasAnotherImage())
            selectImage(directory.getNextImageIndex(imageIndex.get()));
    }

    void zoomOut() {
        imagePanelController.zoomOut();
        updateWindowTitle();
    }

    void zoomIn() {
        imagePanelController.zoomIn();
        updateWindowTitle();
    }

    void actualSize() {
        imagePanelController.setImage(image);
        updateWindowTitle();
    }

    void scaleToWidth() {
        imagePanelController.scaleToWidth(image);
        updateWindowTitle();
    }

    void scaleToHeight() {
        imagePanelController.scaleToHeight(image);
        updateWindowTitle();
    }

    void adjustImage() {
        imagePanelController.adjustImage(image);
        updateWindowTitle();
    }

    void adjustWindow() {
        window.setMaximized(false);
        toolbar.setPrefWidth(image.getWidth() + 2);
        imagePanelController.setImage(image);
        imagePanel.setPrefSize(image.getWidth() + 2, image.getHeight() + 2);
        window.sizeToScene();
        updateWindowTitle();
    }

    void rotateLeft() {
        //TODO: change to imageview rotate and save exif rotate flag tag (if user click save roate permanently)
        image = processingController.rotateLeft(image);
        imagePanelController.setImage(image);
        updateWindowTitle();
    }

    void rotateRight() {
        //TODO: same as rotateLeft
        image = processingController.rotateRight(image);
        imagePanelController.setImage(image);
        updateWindowTitle();
    }

    void horizontalFlip() {
        image = processingController.horizontalFlip(image);
        imagePanelController.setImage(image);
        updateWindowTitle();
    }

    void verticalFlip() {
        image = processingController.verticalFlip(image);
        imagePanelController.setImage(image);
        updateWindowTitle();
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

    void showImageInfo() {
        imageInfoPanelController.togglePanelVisibility();
    }

    void showSettings() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SettingsPanel.fxml"), resources);
        Parent root = null;
        try {
            root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().addAll(window.getScene().getStylesheets());
            Stage stage = new Stage();
            stage.setTitle(resources.getString("settings"));
            stage.getIcons().addAll(window.getIcons());
            stage.setScene(scene);
            stage.initOwner(window);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            GaussianBlur blurEffect = new GaussianBlur(5);
            window.getScene().getRoot().setEffect(blurEffect);
            stage.setOnCloseRequest(event -> window.getScene().getRoot().setEffect(null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
