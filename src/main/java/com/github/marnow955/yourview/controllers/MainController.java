package com.github.marnow955.yourview.controllers;

import com.github.marnow955.yourview.Slideshow;
import com.github.marnow955.yourview.data.DirectoryImageLoader;
import com.github.marnow955.yourview.data.ImageReaderWriter;
import com.github.marnow955.yourview.data.processing.ImageManipulationsController;
import com.github.marnow955.yourview.settings.Settings;
import com.sun.jna.platform.FileUtils;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.StatusBar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController {

    @FXML
    private HBox navigationBar;
    @FXML
    private VBox bottom;
    @FXML
    private HBox left;
    @FXML
    private HBox right;
    @FXML
    private VBox top;
    private Settings settings;
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
    @FXML
    private StatusBar statusBar;
    @FXML
    private StatusBarController statusBarController;
    @FXML
    private NavigationBarController navigationBarController;

    private Stage window;
    private File imageFile;
    private DirectoryImageLoader directory;
    private Image image;
    private ImageManipulationsController processingController;

    private IntegerProperty imageIndex = new SimpleIntegerProperty(-1);
    BooleanProperty isImageSelectedProperty = new SimpleBooleanProperty(false);

    BooleanProperty isChBackgroundSelectedProperty = new SimpleBooleanProperty(false);
    BooleanProperty isThumbViewSelectedProperty = new SimpleBooleanProperty(false);
    BooleanProperty isMenuVisibleProperty = new SimpleBooleanProperty(true);
    BooleanProperty isImageInfoPanelSelectedProperty = new SimpleBooleanProperty(false);
    BooleanProperty isToolbarSelectedProperty = new SimpleBooleanProperty(true);
    BooleanProperty isStatusBarVisibleProperty = new SimpleBooleanProperty(false);
    BooleanProperty isNavigationBarVisibleProperty = new SimpleBooleanProperty(true);

    StringProperty toolbarPosition = new SimpleStringProperty("top");
    StringProperty thumbnailsPosition = new SimpleStringProperty("bottom");

    public void setStage(Stage primaryStage) {
        window = primaryStage;
    }

    public void setupView() {
        menuBarController.injectMainController(this);
        toolbarController.injectMainController(this);
        imagePanelController.injectMainController(this);
        thumbViewController.injectMainController(this);
        imageInfoPanelController.injectMainController(this);
        statusBarController.injectMainController(this);
        navigationBarController.injectMainController(this);
        menuBarController.setupView();
        toolbarController.setupView();
        statusBarController.setupView();
        thumbView.managedProperty().bind(thumbView.visibleProperty());
        thumbView.visibleProperty().bind(isThumbViewSelectedProperty);
        menuBar.managedProperty().bind(menuBar.visibleProperty());
        menuBar.visibleProperty().bind(isMenuVisibleProperty);
        toolbar.managedProperty().bind(toolbar.visibleProperty());
        toolbar.visibleProperty().bind(isToolbarSelectedProperty);
        statusBar.managedProperty().bind(statusBar.visibleProperty());
        statusBar.visibleProperty().bind(isStatusBarVisibleProperty);
        navigationBar.managedProperty().bind(navigationBar.visibleProperty());
        navigationBar.visibleProperty().bind(Bindings.and(isNavigationBarVisibleProperty,isImageSelectedProperty));
        imageInfoPanel.managedProperty().bind(imageInfoPanel.visibleProperty());
        imageInfoPanel.visibleProperty().bind(isImageInfoPanelSelectedProperty);
        isChBackgroundSelectedProperty.addListener(((observable, oldValue, newValue) -> showCheckedBackground(newValue)));
        toolbarPosition.addListener((observable, oldValue, newValue) -> {
            changeToolbarPosition();
        });
        thumbnailsPosition.addListener((observable, oldValue, newValue) -> {
            changeThumbViewPosition();
        });
    }

    public void loadSettings() {
        settings = Settings.getSettingsInstance();
        settings = settings.removeListeners();
        isChBackgroundSelectedProperty.set(settings.isChBackgroundSelected());
        settings.isChBackgroundSelectedProperty().addListener((observable, oldValue, newValue) -> {
            isChBackgroundSelectedProperty.set(newValue);
        });
        isThumbViewSelectedProperty.set(settings.isThumbViewSelected());
        settings.getLanguageProperty().addListener((observable, oldValue, newValue) -> {
            reloadView();
        });
        settings.getThemeNameProperty().addListener((observable, oldValue, newValue) -> {
            String theme = getClass().getResource("/styles/MainView_" + settings.getThemeName() + ".css").toExternalForm();
            window.getScene().getStylesheets().setAll(theme);
        });
        isMenuVisibleProperty.set(settings.isMenuVisible());
        settings.isMenuVisibleProperty().addListener((observable, oldValue, newValue) -> {
            isMenuVisibleProperty.set(newValue);
        });
        isImageInfoPanelSelectedProperty.set(settings.isInfoPanelSelected());
        settings.isInfoPanelSelectedProperty().addListener((observable, oldValue, newValue) -> {
            isImageInfoPanelSelectedProperty.set(newValue);
        });
        isToolbarSelectedProperty.set(settings.isToolbarVisible());
        settings.isToolbarVisibleProperty().addListener((observable, oldValue, newValue) -> {
            isToolbarSelectedProperty.set(newValue);
        });
        isThumbViewSelectedProperty.set(settings.isThumbViewSelected());
        settings.isThumbViewSelectedProperty().addListener((observable, oldValue, newValue) -> {
            isThumbViewSelectedProperty.set(newValue);
        });
        toolbarPosition.set(settings.getToolbarPosition());
        settings.getToolbarPositionProperty().addListener((observable, oldValue, newValue) -> {
            toolbarPosition.set(newValue);
        });
        thumbnailsPosition.set(settings.getThumbnailsPosition());
        settings.getThumbnailsPositionProperty().addListener((observable, oldValue, newValue) -> {
            thumbnailsPosition.set(newValue);
        });
        isStatusBarVisibleProperty.set(settings.isStatusbarVisible());
        settings.isStatusbarVisibleProperty().addListener((observable, oldValue, newValue) -> {
            isStatusBarVisibleProperty.set(newValue);
        });
        isNavigationBarVisibleProperty.set(settings.isNavigationBarVisible());
        settings.isNavigationBarVisibleProperty().addListener((observable, oldValue, newValue) -> {
            isNavigationBarVisibleProperty.set(newValue);
        });
    }

    private void changeToolbarPosition() {
        ((Pane) toolbar.getParent()).getChildren().remove(toolbar);
        switch (toolbarPosition.get()) {
            case "top": {
                top.getChildren().add(1, toolbar);
                toolbarController.setOrientation(Orientation.HORIZONTAL);
            }
            break;
            case "left": {
                left.getChildren().add(0, toolbar);
                toolbarController.setOrientation(Orientation.VERTICAL);
            }
            break;
            case "right": {
                right.getChildren().add(right.getChildren().size(), toolbar);
                toolbarController.setOrientation(Orientation.VERTICAL);
            }
            break;
            case "bottom": {
                bottom.getChildren().add(bottom.getChildren().size() - 1, toolbar);
                toolbarController.setOrientation(Orientation.HORIZONTAL);
            }
        }
    }

    private void changeThumbViewPosition() {
        ((Pane) thumbView.getParent()).getChildren().remove(thumbView);
        switch (thumbnailsPosition.get()) {
            case "top": {
                top.getChildren().add(top.getChildren().size(), thumbView);
                thumbViewController.setOrientation(Orientation.HORIZONTAL);
            }
            break;
            case "left": {
                left.getChildren().add(left.getChildren().size() - 1, thumbView);
                thumbViewController.setOrientation(Orientation.VERTICAL);
            }
            break;
            case "right": {
                right.getChildren().add(0, thumbView);
                thumbViewController.setOrientation(Orientation.VERTICAL);
            }
            break;
            case "bottom": {
                bottom.getChildren().add(0, thumbView);
                thumbViewController.setOrientation(Orientation.HORIZONTAL);
            }
        }
    }

    private void reloadView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            fxmlLoader.setResources(ResourceBundle.getBundle("bundles.lang", new Locale(settings.getLanguage())));
            Parent root = fxmlLoader.load();
            MainController controller = fxmlLoader.getController();
            controller.setStage(window);
            controller.setupView();
            controller.loadSettings();
            window.getScene().setRoot(root);
            if (imageFile != null)
                Platform.runLater(() -> controller.openImage(imageFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            updateStatusBarText();
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
        updateStatusBarText();
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

    void updateStatusBarText() {
        statusBarController.updateText(imageFile.getName(), imageIndex.get() + 1,
                directory.getNrOfImagesInDirectory(), (int) image.getWidth(), (int) image.getHeight(),
                imageFile.length(), imagePanelController.getZoomPercent());
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
        alert.getButtonTypes().setAll(deleteBT, cancelBT);
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
        updateStatusBarText();
    }

    void zoomIn() {
        imagePanelController.zoomIn();
        updateWindowTitle();
        updateStatusBarText();
    }

    void actualSize() {
        imagePanelController.setImage(image);
        updateWindowTitle();
        updateStatusBarText();
    }

    void scaleToWidth() {
        imagePanelController.scaleToWidth(image);
        updateWindowTitle();
        updateStatusBarText();
    }

    void scaleToHeight() {
        imagePanelController.scaleToHeight(image);
        updateWindowTitle();
        updateStatusBarText();
    }

    void adjustImage() {
        imagePanelController.adjustImage(image);
        updateWindowTitle();
        updateStatusBarText();
    }

    void adjustWindow() {
        window.setMaximized(false);
        if (toolbar.getOrientation() == Orientation.HORIZONTAL) {
            toolbar.setPrefWidth(image.getWidth() + 2);
            toolbar.setPrefHeight(-1);
        } else {
            toolbar.setPrefWidth(-1);
            toolbar.setPrefHeight(image.getHeight() + 2);
        }
        imagePanelController.setImage(image);
        imagePanel.setPrefSize(image.getWidth() + 2, image.getHeight() + 2);
        window.sizeToScene();
        updateWindowTitle();
        updateStatusBarText();
    }

    void rotateLeft() {
        //TODO: change to imageview rotate and save exif rotate flag tag (if user click save roate permanently)
        image = processingController.rotateLeft(image);
        imagePanelController.setImage(image);
        updateWindowTitle();
        updateStatusBarText();
    }

    void rotateRight() {
        //TODO: same as rotateLeft
        image = processingController.rotateRight(image);
        imagePanelController.setImage(image);
        updateWindowTitle();
        updateStatusBarText();
    }

    void horizontalFlip() {
        image = processingController.horizontalFlip(image);
        imagePanelController.setImage(image);
        updateWindowTitle();
        updateStatusBarText();
    }

    void verticalFlip() {
        image = processingController.verticalFlip(image);
        imagePanelController.setImage(image);
        updateWindowTitle();
        updateStatusBarText();
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

    void showSettings() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SettingsPanel.fxml"), resources);
        Parent root = null;
        try {
            root = loader.load();
            SettingsPanelController settingsController = loader.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().addAll(window.getScene().getStylesheets());
            Stage stage = new Stage();
            stage.setTitle(resources.getString("settings"));
            stage.getIcons().addAll(window.getIcons());
            stage.setScene(scene);
            stage.initOwner(window);
            stage.initModality(Modality.APPLICATION_MODAL);
            settingsController.setStage(stage);
            stage.show();
            GaussianBlur blurEffect = new GaussianBlur(5);
            window.getScene().getRoot().setEffect(blurEffect);
            stage.setOnHidden(event -> window.getScene().getRoot().setEffect(null));
            stage.setOnCloseRequest(event -> {
                event.consume();
                settingsController.closeSettingsPanel();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void playSlideshow() {
        Slideshow slideshow = new Slideshow(window, directory, Duration.millis(3000), "-fx-slideshow");
        slideshow.play();
    }
}
