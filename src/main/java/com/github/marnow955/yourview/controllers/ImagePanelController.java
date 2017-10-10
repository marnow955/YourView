package com.github.marnow955.yourview.controllers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
    private MainController mainController;

    public void initialize() {
        //TODO: only if is needed
        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scrollPane.addEventFilter(ScrollEvent.ANY, event -> {
            if (mainController.isImageSelectedProperty.get()) {
                isZoom.set(true);
                if (event.getDeltaY() > 0) {
                    zoomIn();
                } else if (event.getDeltaY() < 0) {
                    zoomOut();
                }
                mainController.updateWindowTitle();
            }
        });
    }

    void injectMainController(MainController mainController) {
        this.mainController = mainController;
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

    int getZoomPercent() {
        int zoomPercent = (int) ((imageView.getFitHeight()/imageView.getImage().getHeight())*100);
        if (zoomPercent <= 0) {
            zoomPercent = (int) ((imageView.getFitWidth()/imageView.getImage().getWidth())*100);
        }
        return zoomPercent;
    }

    void setImage(Image image) {
        clearZoom();
        stackPane.getChildren().remove(imageView);
        imageView = new ImageView();
        stackPane.getChildren().add(imageView);
        imageView.setPreserveRatio(true);
        if (image != null) {
            imageView.setImage(image);
            imageView.setFitWidth(image.getWidth());
            imageView.setFitHeight(image.getHeight());
        }
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
