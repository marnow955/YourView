package com.github.marnow955.yourview.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class ImagePanelController {

    @FXML
    private StackPane stackPane;
    @FXML
    private ImageView imageView;

    void setImage(Image image) {
        imageView.setPreserveRatio(true);
        imageView.setImage(image);
    }

    void showCheckedBackground(Boolean newValue) {
        if (newValue) {
            stackPane.getStyleClass().add("checked");
        } else {
            stackPane.getStyleClass().remove("checked");
        }
    }
}
