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
                }
            }
        }
    }

    public ObservableList<Image> getObservableListOfImages() {
        for (int i = 0; i < listOfImagesFiles.size(); i++) {
            listOfImages.add(ImageReaderWriter.openImage(listOfImagesFiles.get(i)));
        }
        return FXCollections.observableList(listOfImages);
    }

    public File getImageFile(Image image) {
        return listOfImagesFiles.get(listOfImages.indexOf(image));
    }

    public File getPreviousImage(File file) {
        int index = listOfImagesFiles.indexOf(file);
        if (index > 0) {
            return listOfImagesFiles.get(--index);
        } else {
            return listOfImagesFiles.get(listOfImagesFiles.size() - 1);
        }
    }

    public File getNextImage(File file) {
        int index = listOfImagesFiles.indexOf(file);
        if (index < listOfImagesFiles.size() - 1) {
            return listOfImagesFiles.get(++index);
        } else {
            return listOfImagesFiles.get(0);
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
        return listOfImagesFiles.indexOf(file) + 1;
    }
}
