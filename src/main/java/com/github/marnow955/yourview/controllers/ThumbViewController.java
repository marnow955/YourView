package com.github.marnow955.yourview.controllers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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

    private MainController mainController;
    private double cellWidth = 65.0;
    private double cellHeight = 65.0;
    private double cellContainerPadding = 5.0;
    private IntegerProperty selectedIndex = new SimpleIntegerProperty(-1);

    void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    void setThumbView(ObservableList<Image> thumbList) {
        thumbView.getChildren().clear();
        selectedIndex.set(-1);
        for (Image image : thumbList) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(cellWidth);
            imageView.setFitHeight(cellHeight);
            StackPane cellContainer = new StackPane(imageView);
            cellContainer.setPadding(new Insets(cellContainerPadding));
            cellContainer.getStyleClass().add("thumbViewCell");

            cellContainer.setOnMouseClicked(event ->
                    mainController.selectImage(thumbList.indexOf(image))
            );

            thumbView.getChildren().add(cellContainer);
        }

    }

    void setSelected(int index) {
        Node cell;
        if (selectedIndex.get() >= 0) {
            cell = thumbView.getChildren().get(selectedIndex.get());
            cell.getStyleClass().remove("thumbViewCellSelected");
        }
        cell = thumbView.getChildren().get(index);
        cell.getStyleClass().add("thumbViewCellSelected");
        selectedIndex.set(index);
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
