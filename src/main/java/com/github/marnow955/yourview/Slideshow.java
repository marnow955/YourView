package com.github.marnow955.yourview;

import com.github.marnow955.yourview.data.DirectoryImageLoader;
import com.github.marnow955.yourview.data.ImageReaderWriter;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class Slideshow {

    private Stage stage;
    private Scene appScene;
    private Scene slideshowScene;
    private StackPane root;
    private ImageView imageView;
    private Tooltip exitHint;
    private DirectoryImageLoader fileLoader;
    private int imageIndex;
    private File imageFile;
    private Image image;
    private Timeline imageChangerTimeline;

    public Slideshow(Stage stage, DirectoryImageLoader fileLoader, Duration delay, String cssColor) {
        this.stage = stage;
        appScene = stage.getScene();
        imageView = new ImageView();
        root = new StackPane(imageView);
        root.setStyle("-fx-background-color: " + cssColor);
        slideshowScene = new Scene(root);
        slideshowScene.getStylesheets().addAll(appScene.getStylesheets());
        this.fileLoader = fileLoader;

        exitHint = new Tooltip("Press ESC to exit slideshow");
        updateTooltipBehavior(exitHint);

        imageChangerTimeline = new Timeline(new KeyFrame(delay, event -> {
            showNextImage();
        }));
        imageChangerTimeline.setCycleCount(Timeline.INDEFINITE);

        slideshowScene.setOnKeyPressed(event -> {
            imageChangerTimeline.stop();
            if (event.getCode().getName().equals("Space") || event.getCode().getName().equals("Right")) {
                showNextImage();
            } else if (event.getCode().getName().equals("Left")) {
                showPreviousImage();
            }
        });

        stage.fullScreenProperty().addListener((observable, oldValue, newValue) -> {
            if (!stage.isFullScreen()) {
                imageChangerTimeline.stop();
                stage.setScene(appScene);
            }
        });
    }

    public void play() {
        imageIndex = 0;
        showImage();
        if (fileLoader.hasAnotherImage()) {
            imageChangerTimeline.play();
        }
        stage.setScene(slideshowScene);
        stage.setFullScreenExitHint("");
        stage.setFullScreen(true);
        exitHint.show(stage);
    }

    private void showNextImage() {
        imageIndex = fileLoader.getNextImageIndex(imageIndex);
        showImage();
    }

    private void showPreviousImage() {
        imageIndex = fileLoader.getPreviousImageIndex(imageIndex);
        showImage();
    }

    private void showImage() {
        imageFile = fileLoader.getImageFile(imageIndex);
        image = ImageReaderWriter.openImage(imageFile, false);
        imageView.setImage(image);
    }

    private void updateTooltipBehavior(Tooltip tooltip) {
        tooltip.setStyle("-fx-font-size: 11pt");
        Timeline tooltipHide = new Timeline();
        tooltipHide.getKeyFrames().add(new KeyFrame(Duration.millis(1500), event -> {
            tooltip.hide();
        }));
        tooltip.setOnShown(event -> tooltipHide.play());
    }


}
