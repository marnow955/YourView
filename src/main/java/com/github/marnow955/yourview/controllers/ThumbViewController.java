package com.github.marnow955.yourview.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

public class ThumbViewController {

    @FXML
    private TilePane thumbView;

    private int selectedIndex = -1;
    private MainController mainController;
    private double cellWidth = 65.0;
    private double cellHeight = 65.0;

    void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    void setThumbView(ObservableList<Image> thumbList) {
        thumbView.getChildren().clear();
        for (Image image : thumbList) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(cellWidth);
            imageView.setFitHeight(cellHeight);

            imageView.setOnMouseEntered(event -> {
                DropShadow dropShadow = new DropShadow();
                dropShadow.setOffsetX(6.0);
                dropShadow.setOffsetY(4.0);

                imageView.setEffect(dropShadow);
            });
            imageView.setOnMouseExited(event -> {
                if (thumbList.indexOf(image) == selectedIndex)
                    setSelected(selectedIndex);
                else
                    imageView.setEffect(null);
            });

            imageView.setOnMousePressed(event -> {
                InnerShadow innerShadow = new InnerShadow();
                innerShadow.setOffsetX(4);
                innerShadow.setOffsetY(4);
                imageView.setEffect(innerShadow);
            });

            imageView.setOnMouseClicked(event -> mainController.selectImage(image));

            thumbView.getChildren().add(imageView);
        }

    }

    void setSelected(int index) {
        selectedIndex = index;
        ImageView imageView = (ImageView) thumbView.getChildren().get(index);
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setOffsetX(4);
        innerShadow.setOffsetY(4);
        innerShadow.setColor(Color.web("0x3b596d"));
        imageView.setEffect(innerShadow);
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
