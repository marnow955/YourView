package com.github.marnow955.yourview.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoryImageLoader {

    private List<File> listOfImagesFiles;
    private List<Image> listOfImages;

    public DirectoryImageLoader(File directory) {
        listOfImagesFiles = new ArrayList<>();
        listOfImages = new ArrayList<>();
        File[] filesInDirectory = directory.listFiles();
        for (int i = 0; i < filesInDirectory.length; i++) {
            if (filesInDirectory[i].isFile()) {
                if (ImageReaderWriter.checkFileExtension(filesInDirectory[i])) {
                    listOfImagesFiles.add(filesInDirectory[i]);
                    listOfImages.add(ImageReaderWriter.openImage(filesInDirectory[i], true));
                }
            }
        }
    }

    public ObservableList<Image> getThumbnails(double requestedWidth, double requestedHeight) {
        List<Image> thumbnails = new ArrayList<>();
        for (int i = 0; i < listOfImagesFiles.size(); i++) {
            thumbnails.add(ImageReaderWriter.openImage(listOfImagesFiles.get(i), true,
                    requestedWidth, requestedHeight));
        }
        return FXCollections.observableList(thumbnails);
    }

    public ObservableList<Image> getObservableListOfImages() {
        return FXCollections.observableList(listOfImages);
    }

    public File getImageFile(Image image) {
        return listOfImagesFiles.get(listOfImages.indexOf(image));
    }

    public Image getPreviousImage(int index) {
        if (index > 0) {
            return listOfImages.get(--index);
        } else {
            return listOfImages.get(listOfImages.size() - 1);
        }
    }

    public Image getNextImage(int index) {
        if (index < listOfImages.size() - 1) {
            return listOfImages.get(++index);
        } else {
            return listOfImages.get(0);
        }
    }

    public int getNrOfImagesInDirectory() {
        return listOfImagesFiles.size();
    }

    public boolean hasAnotherImage() {
        if (listOfImagesFiles.size() > 1)
            return true;
        else
            return false;
    }

    public int getImageIndex(File file) {
        return listOfImagesFiles.indexOf(file);
    }

    public Image getImage(int index) {
        return listOfImages.get(index);
    }
}
