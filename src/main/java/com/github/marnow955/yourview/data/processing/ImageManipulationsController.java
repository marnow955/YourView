package com.github.marnow955.yourview.data.processing;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import photo.processor.core.Flip;
import photo.processor.core.PhotoProcessor;
import photo.processor.core.Rotate;
import photo.processor.core.size.Crop;

import java.awt.image.BufferedImage;

public class ImageManipulationsController {

    Image image;
    OperationsResults history;

    public ImageManipulationsController(Image image) {
        this.image = image;
        history = new OperationsResults();
    }

    public Image rotateLeft() {
        PhotoProcessor pp = new Rotate(Rotate.Direction.LEFT);
        return transformImage(pp, image);
    }

    public Image rotateRight() {
        PhotoProcessor pp = new Rotate(Rotate.Direction.RIGHT);
        return transformImage(pp, image);
    }

    public Image horizontalFlip() {
        PhotoProcessor pp = new Flip(Flip.Orientation.HORIZONTAL);
        return transformImage(pp, image);
    }

    public Image verticalFlip() {
        PhotoProcessor pp = new Flip(Flip.Orientation.VERTICAL);
        return transformImage(pp, image);
    }

    public Image cropImage(int x, int y, int width, int height) {
        PhotoProcessor pp = Crop.CropSize(x, y, width, height);
        return transformImage(pp, image);
    }

    private Image transformImage(PhotoProcessor processor, Image image) {
        BufferedImage result = processor.getTransformedImage(toBufferedImage(image));
        Image resultImage = toFXImage(result);
        history.addToHistory(resultImage);
        return resultImage;
    }

    private BufferedImage toBufferedImage(Image image) {
        return SwingFXUtils.fromFXImage(image, null);
    }

    private Image toFXImage(BufferedImage image) {
        return SwingFXUtils.toFXImage(image, null);
    }
}
