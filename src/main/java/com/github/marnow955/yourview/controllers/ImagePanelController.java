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
    private final DoubleProperty zoomProperty = new SimpleDoubleProperty(200);

    public void initialize() {
        //TODO: only if image selected and zoomed
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        zoomProperty.addListener(arg0 -> {
            imageView.setFitWidth(zoomProperty.get()*4);
            imageView.setFitHeight(zoomProperty.get()*3);
        });

        scrollPane.addEventFilter(ScrollEvent.ANY, event -> {
            isZoom.set(true);
            imageView.fitWidthProperty().unbind();
            imageView.fitHeightProperty().unbind();
            if (event.getDeltaY() > 0) {
                zoomIn();
            } else if (event.getDeltaY() < 0) {
                zoomOut();
            }
        });
    }

    void zoomIn() {
        zoomProperty.set(zoomProperty.get()*1.1);
    }

    void zoomOut() {
        zoomProperty.set(zoomProperty.get()/1.1);
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
        imageView.setFitWidth(scrollPane.getWidth() - 2);
    }

    void scaleToHeight(Image image) {
        setImage(image);
        imageView.setFitHeight(scrollPane.getHeight() - 2);
    }

    void adjustImage(Image image) {
        setImage(image);
        imageView.setFitWidth(scrollPane.getWidth() - 2);
        imageView.setFitHeight(scrollPane.getHeight() - 2);
    }
}
