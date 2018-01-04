package com.github.marnow955.yourview.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class NavigationBarController {

    @FXML
    private Button fullScrAdditionalBtn;
    @FXML
    private HBox posSpace;
    private MainController mainController;

    void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void previousImage() {
        mainController.previousImage();
    }

    @FXML
    private void nextImage() {
        mainController.nextImage();
    }

    void setPosition(Pos pos) {
        switch (pos) {
            case TOP_CENTER: {
                posSpace.setManaged(false);
                fullScrAdditionalBtn.setVisible(true);
            }
            break;
            case CENTER: {
                posSpace.setManaged(true);
                fullScrAdditionalBtn.setVisible(false);
            }
            break;
            case BOTTOM_CENTER: {
                posSpace.setManaged(false);
                fullScrAdditionalBtn.setVisible(true);
            }
        }
    }
}
