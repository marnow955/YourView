package com.github.marnow955.yourview.controllers;

import com.github.marnow955.yourview.OpenSaveImageDialog;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainController {

    private Stage window;
    private Image originalImage;

    @FXML
    private ToolBar toolbar;
    @FXML
    private ScrollPane imagePanel;
    @FXML
    private ImagePanelController imagePanelController;

    public void setStageAndSetupView(Stage primaryStage) {
        window = primaryStage;
    }

    public void openFile() {
        originalImage = new OpenSaveImageDialog().openImage(window);
        if (originalImage != null) {
            imagePanelController.setImage(originalImage);
        }
    }
}
