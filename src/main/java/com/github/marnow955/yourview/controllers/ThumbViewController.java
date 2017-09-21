package com.github.marnow955.yourview.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

public class ThumbViewController {

    @FXML
    private TilePane thumbView;

    private int selectedIndex = -1;
    private MainController mainController;
    private double cellWidth = 65.0;
    private double cellHeight = 65.0;
    private double cellContainerPadding = 5.0;

    void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    void setThumbView(ObservableList<Image> thumbList) {
        thumbView.getChildren().clear();
        for (Image image : thumbList) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(cellWidth);
            imageView.setFitHeight(cellHeight);
            StackPane cellContainer = new StackPane(imageView);
            cellContainer.setPadding(new Insets(cellContainerPadding));
            cellContainer.getStyleClass().add("thumbViewCell");

            imageView.setOnMouseClicked(event ->
                    mainController.selectImage(image)
            );

            thumbView.getChildren().add(cellContainer);
        }

    }

    void setSelected(int index) {
        selectedIndex = index;
        Node cell = thumbView.getChildren().get(index);
        cell.getStyleClass().add("thumbViewCellSelected");
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
