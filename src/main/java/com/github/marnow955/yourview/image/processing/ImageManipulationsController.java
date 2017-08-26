package com.github.marnow955.yourview.image.processing;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import photo.processor.core.PhotoProcessor;
import photo.processor.core.Rotate;

import java.awt.image.BufferedImage;

public class ImageManipulationsController {

    OperationsResults history;

    public ImageManipulationsController() {
        history = new OperationsResults();
    }

    public Image rotateLeft(Image image) {
        PhotoProcessor pp = new Rotate(Rotate.Direction.LEFT);
        BufferedImage result = pp.getTransformedImage(toBufferedImage(image));
        Image resultImage = toFXImage(result);
        history.addToHistory(resultImage);
        return resultImage;
    }

    public Image rotateRight(Image image) {
        PhotoProcessor pp = new Rotate(Rotate.Direction.RIGHT);
        BufferedImage result = pp.getTransformedImage(toBufferedImage(image));
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
