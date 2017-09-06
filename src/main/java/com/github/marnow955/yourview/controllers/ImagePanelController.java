package com.github.marnow955.yourview.controllers;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;

public class ImagePanelController {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private StackPane stackPane;
    @FXML
    private ImageView imageView;

    private BooleanProperty isZoom = new SimpleBooleanProperty(false);

    public void initialize() {
        //TODO: only if image selected and zoomed
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scrollPane.addEventFilter(ScrollEvent.ANY, event -> {
            isZoom.set(true);
            if (event.getDeltaY() > 0) {
                zoomIn();
            } else if (event.getDeltaY() < 0) {
                zoomOut();
            }
        });
    }

    void zoomIn() {
        isZoom.set(true);
        imageView.setFitWidth(imageView.getFitWidth()*1.1);
        imageView.setFitHeight(imageView.getFitHeight()*1.1);
    }

    void zoomOut() {
        isZoom.set(true);
        imageView.setFitWidth(imageView.getFitWidth()/1.1);
        imageView.setFitHeight(imageView.getFitHeight()/1.1);
    }

    void clearZoom() {
        isZoom.set(false);
    }

    void setImage(Image image) {
        clearZoom();
        stackPane.getChildren().remove(imageView);
        imageView = new ImageView();
        stackPane.getChildren().add(imageView);
        imageView.setPreserveRatio(true);
        imageView.setImage(image);
        imageView.setFitWidth(image.getWidth());
        imageView.setFitHeight(image.getHeight());
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
        imageView.setFitWidth(scrollPane.getWidth() - 2);
        imageView.setFitHeight(0);
    }

    void scaleToHeight(Image image) {
        setImage(image);
        imageView.setFitWidth(0);
        imageView.setFitHeight(scrollPane.getHeight() - 2);
    }

    void adjustImage(Image image) {
        setImage(image);
        imageView.setFitWidth(scrollPane.getWidth() - 2);
        imageView.setFitHeight(scrollPane.getHeight() - 2);
    }
}
