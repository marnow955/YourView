package com.github.marnow955.yourview.controllers;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

public class ThumbViewController {

    @FXML
    private ScrollPane thumbViewScrollPane;
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
        centerCellInScrollPane(thumbViewScrollPane, cell);
    }

    private void centerCellInScrollPane(ScrollPane thumbViewScrollPane, Node node) {
        double h = thumbViewScrollPane.getContent().getBoundsInLocal().getHeight();
        double y = (node.getBoundsInParent().getMaxY() +
                node.getBoundsInParent().getMinY())/2.0;
        double v = thumbViewScrollPane.getViewportBounds().getHeight();
        thumbViewScrollPane.setVvalue(thumbViewScrollPane.getVmax()*((y - 0.5*v)/(h - v)));
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

    void setOrientation(Orientation orientation) {
//        thumbView.setOrientation(orientation);
        if (orientation == Orientation.VERTICAL)
            thumbViewScrollPane.setPrefViewportWidth(cellWidth + 3*cellContainerPadding);
    }
}
