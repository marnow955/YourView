package com.github.marnow955.yourview.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class ThumbViewController {

    @FXML
    private FlowPane thumbView;

    private double cellWidth = 65.0;
    private double cellHeight = 65.0;

    void setThumbView(ObservableList<Image> thumbList) {
        for (Image image : thumbList) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(cellWidth);
            imageView.setFitHeight(cellHeight);
            thumbView.getChildren().add(imageView);
        }
    }

    double getCellWidth() {
        return cellWidth;
    }

    void setCellWidth(double cellWidth) {
        this.cellWidth = cellWidth;
    }

    double getCellHeight() {
        return cellHeight;
    }

    void setCellHeight(double cellHeight) {
        this.cellHeight = cellHeight;
    }
}
