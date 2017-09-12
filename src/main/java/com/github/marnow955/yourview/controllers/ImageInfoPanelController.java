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
import javafx.scene.control.Label;
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
    private VBox imageInfoPanel;
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

    private SimpleDateFormat dateFormatter;

    @FXML
    private void initialize() {
        dateFormatter = new SimpleDateFormat("EEEE, d MMMM yyyy HH:mm", resources.getLocale());
        imageInfoPanel.managedProperty().bind(imageInfoPanel.visibleProperty());
    }

    void setInfo(File imageFile) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
            for (Directory directory : metadata.getDirectories()) {

                //
                // Each Directory stores values in Tag objects
                //
                for (Tag tag : directory.getTags()) {
                    System.out.println(tag);
                }

                //
                // Each Directory may also contain error messages
                //
                if (directory.hasErrors()) {
                    for (String error : directory.getErrors()) {
                        System.err.println("ERROR: " + error);
                    }
                }
            }
            setFileMetadata(metadata);
            FileInputStream fis = new FileInputStream(imageFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            FileType fileType = FileTypeDetector.detectFileType(bis);
            setFileTypeMetadata(metadata, fileType);
        } catch (ImageProcessingException | IOException e) {
            e.printStackTrace();
        }
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

    @FXML
    void togglePanelVisibility() {
        imageInfoPanel.setVisible(!imageInfoPanel.isVisible());
    }
}
