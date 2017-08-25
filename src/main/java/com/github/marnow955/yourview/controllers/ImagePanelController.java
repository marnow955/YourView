package com.github.marnow955.yourview.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class ImagePanelController {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private StackPane stackPane;
    @FXML
    private ImageView imageView;

    public void initialize() {
        //TODO: only if image selected and zoomed
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    void setImage(Image image) {
        stackPane.getChildren().remove(imageView);
        imageView = new ImageView();
        stackPane.getChildren().add(imageView);
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

    void scaleToWidth(Image image) {
        setImage(image);
        imageView.setFitWidth(scrollPane.getWidth()-2);
    }

    void scaleToHeiht(Image image) {
        setImage(image);
        imageView.setFitHeight(scrollPane.getHeight()-2);
    }
}
