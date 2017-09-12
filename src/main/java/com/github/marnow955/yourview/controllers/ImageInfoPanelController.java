package com.github.marnow955.yourview.controllers;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.file.FileMetadataDirectory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.File;
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

    SimpleDateFormat dateFormatter;

    @FXML
    private void initialize() {
        dateFormatter = new SimpleDateFormat("EEEE, d MMMM yyyy HH:mm", resources.getLocale());
    }

    void setInfo(File imageFile) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
            FileMetadataDirectory fileMetadataDirectory = metadata.getFirstDirectoryOfType(FileMetadataDirectory.class);
            setFileMetadata(fileMetadataDirectory);
        } catch (ImageProcessingException | IOException e) {
            e.printStackTrace();
        }
    }

    private void setFileMetadata(FileMetadataDirectory directory) {
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
}
