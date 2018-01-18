package com.github.marnow955.yourview.controllers;

import com.github.marnow955.yourview.controllers.edit_panes.CropController;
import com.github.marnow955.yourview.data.processing.ImageManipulationsController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.ResourceBundle;

public class EditPanelController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private Button undoB;
    @FXML
    private Button redoB;
    @FXML
    private Accordion panes;
    @FXML
    private TitledPane cropPanel;
    @FXML
    private CropController cropController;

    private Image image;
    private MainController mainController;
    private ImageManipulationsController processingController;
    private BooleanProperty hasPreviousDisabled = new SimpleBooleanProperty(true);
    private BooleanProperty hasNextDisabled = new SimpleBooleanProperty(true);

    public boolean getHasPreviousDisabled() {
        return hasPreviousDisabled.get();
    }

    public boolean getHasNextDisabled() {
        return hasNextDisabled.get();
    }

    void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    void setupView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/edit_panes/Crop.fxml"));
        loader.setResources(resources);
        try {
            cropPanel = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cropController = loader.getController();
        panes.getPanes().add(cropPanel);
        cropController.injectMainController(this);
        cropController.setupView();
        undoB.disableProperty().bind(hasPreviousDisabled);
        redoB.disableProperty().bind(hasNextDisabled);
    }

    void loadEditView(Image image) {
        //TODO: load date later when panel is extended
        this.image = image;
        cropController.setupValuesFromImage(image);
        processingController = new ImageManipulationsController(this.image);
        cropController.injectImageProcessingController(processingController);
        updateUndoRedoButtons();
    }

    @FXML
    private void saveChanges() {
        if (mainController.saveImage(processingController.getCurrentImage())) {
            mainController.isEditPanelSelectedProperty.set(false);
        }
    }

    @FXML
    private void cancelChanges() {
        mainController.isEditPanelSelectedProperty.set(false);
        mainController.setImage(image);
    }

    @FXML
    private void undo() {
        if (processingController.hasPrevious()) {
            try {
                Image image = processingController.previous();
                setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void redo() {
        if (processingController.hasNext()) {
            try {
                Image image = processingController.next();
                setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateUndoRedoButtons() {
        hasPreviousDisabled.set(!processingController.hasPrevious());
        hasNextDisabled.set(!processingController.hasNext());
    }

    public void setImage(Image image) {
        mainController.setImage(image);
        updateUndoRedoButtons();
        if (panes.getExpandedPane() == cropPanel)
            cropController.setupValuesFromImage(image);
    }
}
