package com.github.marnow955.yourview;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class OpenSaveImageDialog {

    private Stage display;
    private FileChooser fileChooser;

    private FileChooser.ExtensionFilter[] filters = {
            new FileChooser.ExtensionFilter("Image (*.png, *.jpg, *.gif, *.bmp)", "*.png", "*.jpg", "*.gif", "*.bmp", "*.jpeg",
                    "*.PNG", "*.JPG", "*.GIF", "*.BMP", "*.JPEG"),
            new FileChooser.ExtensionFilter(".png", "*.png", "*.PNG"),
            new FileChooser.ExtensionFilter(".jpg", "*.jpg", "*.JPG", "*.jpeg", "*.JPEG"),
            new FileChooser.ExtensionFilter(".gif", "*.gif", "*.GIF"),
            new FileChooser.ExtensionFilter(".bmp", "*.bmp", "*.BMP")
    };

    public OpenSaveImageDialog(Stage display) {
        this.display = display;
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(filters);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    }

    public File showOpenDialog() {
        return fileChooser.showOpenDialog(display);
    }

    public File showSaveDialog() {
        fileChooser.setInitialFileName("*.png");
        return fileChooser.showSaveDialog(display);
    }

    public Image openImage(File file) {
        if (file != null) {
            if (checkFileExtension(file)) {
                return new Image(file.toURI().toString());
            } else {
                new Alert(Alert.AlertType.ERROR, file.getName() + " has no valid file-extension").show();
            }
        }
        return null;
    }

    public void saveImage(Image image, File path) {
        if (path != null) {
            try {
                if (checkFileExtension(path)) {
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), path.getName().substring(
                            path.getName().lastIndexOf(".") + 1), path);
                } else {
                    new Alert(Alert.AlertType.ERROR, path.getName() + " has no valid file-extension").show();
                }
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR, String.format("Cannot save file %s", path.getPath())).show();
            }
        }
    }

    private boolean checkFileExtension(File file) {
        for (String filter : filters[0].getExtensions()) {
            if (file.getName().endsWith(filter.substring(1))) {
                return true;
            }
        }
        return false;
    }

}

