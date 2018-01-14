package com.github.marnow955.yourview.controllers;

import com.github.marnow955.yourview.controllers.edit_panes.CropController;
import com.github.marnow955.yourview.data.processing.ImageManipulationsController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;

import java.io.IOException;

public class EditPanelController {

    @FXML
    private Accordion panes;
    @FXML
    private TitledPane cropPanel;
    @FXML
    private CropController cropController;

    private MainController mainController;

    void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    void setupView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/edit_panes/Crop.fxml"));
        try {
            cropPanel = (TitledPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cropController = loader.<CropController>getController();
        panes.getPanes().add(cropPanel);
        cropController.injectMainController(mainController);
        cropController.setupView();
    }

    void loadEditView(Image image, ImageManipulationsController processingController) {
        cropController.setupValuesFromImage(image);
    }

    @FXML
    private void saveChanges() {
    }

    @FXML
    private void cancelChanges() {
        mainController.isEditPanelSelectedProperty.set(false);
    }
}
