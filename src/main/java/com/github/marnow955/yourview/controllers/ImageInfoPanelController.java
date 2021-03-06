package com.github.marnow955.yourview.controllers;

import com.drew.imaging.FileType;
import com.drew.imaging.FileTypeDetector;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.file.FileMetadataDirectory;
import com.drew.metadata.gif.GifHeaderDirectory;
import com.drew.metadata.jpeg.JpegDirectory;
import com.drew.metadata.png.PngDirectory;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ImageInfoPanelController {

    @FXML
    ResourceBundle resources;
    @FXML
    private Label fileName;
    @FXML
    private Label fileModificationDate;
    @FXML
    private Label fileSize;
    @FXML
    private Label dimensions;
    @FXML
    private VBox allMetadataBox;

    private SimpleDateFormat dateFormatter;
    private MainController mainController;

    @FXML
    private void initialize() {
        dateFormatter = new SimpleDateFormat("EEEE, d MMMM yyyy HH:mm", resources.getLocale());
        allMetadataBox.managedProperty().bind(allMetadataBox.visibleProperty());
    }

    void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    void setInfo(File imageFile) {
        clearInfoPanel();
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
            setFileMetadata(metadata);
            FileInputStream fis = new FileInputStream(imageFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            FileType fileType = FileTypeDetector.detectFileType(bis);
            setFileTypeMetadata(metadata, fileType);
            setAllMetadataText(metadata);
        } catch (ImageProcessingException | IOException e) {
            e.printStackTrace();
        }
    }

    private void clearInfoPanel() {
        fileName.setText("");
        fileModificationDate.setText("");
        fileSize.setText("");
        dimensions.setText("");
        allMetadataBox.getChildren().clear();
    }

    private void setFileMetadata(Metadata metadata) {
        FileMetadataDirectory directory = metadata.getFirstDirectoryOfType(FileMetadataDirectory.class);
        fileName.setText(directory.getDescription(FileMetadataDirectory.TAG_FILE_NAME));
        Date modificationDate = directory.getDate(FileMetadataDirectory.TAG_FILE_MODIFIED_DATE);
        fileModificationDate.setText(dateFormatter.format(modificationDate));
        try {
            int bytesSize = directory.getInt(FileMetadataDirectory.TAG_FILE_SIZE);
            double size = bytesSize/1024.0;
            fileSize.setText(String.format("%.2f KB", size));
            if (size >= 1024.0) {
                size = size/1024.0;
                fileSize.setText(String.format("%.2f MB", size));
            }
        } catch (MetadataException e) {
            e.printStackTrace();
        }
    }

    private void setFileTypeMetadata(Metadata metadata, FileType fileType) {
        Directory fileTypeDirectory = null;
        Integer width = null;
        Integer height = null;
        if (fileType == FileType.Jpeg) {
            fileTypeDirectory = metadata.getFirstDirectoryOfType(JpegDirectory.class);
            width = fileTypeDirectory.getInteger(JpegDirectory.TAG_IMAGE_WIDTH);
            height = fileTypeDirectory.getInteger(JpegDirectory.TAG_IMAGE_HEIGHT);
        } else if (fileType == FileType.Png) {
            fileTypeDirectory = metadata.getFirstDirectoryOfType(PngDirectory.class);
            width = fileTypeDirectory.getInteger(PngDirectory.TAG_IMAGE_WIDTH);
            height = fileTypeDirectory.getInteger(PngDirectory.TAG_IMAGE_HEIGHT);
        } else if (fileType == FileType.Gif) {
            fileTypeDirectory = metadata.getFirstDirectoryOfType(GifHeaderDirectory.class);
            width = fileTypeDirectory.getInteger(GifHeaderDirectory.TAG_IMAGE_WIDTH);
            height = fileTypeDirectory.getInteger(GifHeaderDirectory.TAG_IMAGE_HEIGHT);
        }
        dimensions.setText(width + " x " + height);
    }

    private void setAllMetadataText(Metadata metadata) {
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                Label tagName = new Label(tag.getTagName());
                tagName.getStyleClass().add("infoLabel");
                tagName.setWrapText(true);
                Label directoryName = new Label("[" + tag.getDirectoryName() + "]");
                directoryName.getStyleClass().add("directoryName");
                directoryName.setWrapText(true);
                HBox label = new HBox(tagName, directoryName);
                label.setSpacing(5);
                label.setAlignment(Pos.CENTER_LEFT);
                Label description = new Label(tag.getDescription());
                description.getStyleClass().add("infoContent");
                description.setWrapText(true);
                VBox container = new VBox(label, description);
                container.setPadding(new Insets(0, 0, 10, 0));
                allMetadataBox.getChildren().addAll(container);
            }
        }
    }

    @FXML
    void togglePanelVisibility() {
        mainController.isImageInfoPanelSelectedProperty.set(!mainController.isImageInfoPanelSelectedProperty.get());
    }

    @FXML
    private void showAllMetadata() {
        allMetadataBox.setVisible(!allMetadataBox.isVisible());
    }
}
