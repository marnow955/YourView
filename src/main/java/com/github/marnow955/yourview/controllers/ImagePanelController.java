package com.github.marnow955.yourview.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImagePanelController {

    @FXML
    private ImageView imageView;

    public void setImage(Image image) {
        imageView.setPreserveRatio(true);
        imageView.setImage(image);
    }
}
