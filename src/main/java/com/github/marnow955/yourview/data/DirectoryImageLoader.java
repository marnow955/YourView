package com.github.marnow955.yourview.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoryImageLoader {

    private List<File> listOfImagesFiles;
    private List<Image> thumbnails;

    public DirectoryImageLoader(File directory, double thumbnailsRequestedWidth, double thumbnailsRequestedHeight) {
        listOfImagesFiles = new ArrayList<>();
        thumbnails = new ArrayList<>();
        File[] filesInDirectory = directory.listFiles();
        for (int i = 0; i < filesInDirectory.length; i++) {
            if (filesInDirectory[i].isFile()) {
                if (ImageReaderWriter.checkFileExtension(filesInDirectory[i])) {
                    listOfImagesFiles.add(filesInDirectory[i]);
                    thumbnails.add(ImageReaderWriter.openImage(filesInDirectory[i], true,
                            thumbnailsRequestedWidth, thumbnailsRequestedHeight));
                }
            }
        }
    }

    public ObservableList<Image> getThumbnails() {
        return FXCollections.observableList(thumbnails);
    }

    public File getImageFile(int index) {
        return listOfImagesFiles.get(index);
    }


    public int getPreviousImageIndex(int index) {
        if (index > 0) {
            return --index;
        } else {
            return listOfImagesFiles.size() - 1;
        }
    }

    public File getPreviousImageFile(int index) {
        if (index > 0) {
            return listOfImagesFiles.get(--index);
        } else {
            return listOfImagesFiles.get(listOfImagesFiles.size() - 1);
        }
    }

    public int getNextImageIndex(int index) {
        if (index < listOfImagesFiles.size() - 1) {
            return ++index;
        } else {
            return 0;
        }
    }

    public File getNextImageFile(int index) {
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
        return listOfImagesFiles.indexOf(file);
    }

}
