package com.github.marnow955.yourview.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DirectoryImageLoader {

    private List<File> listOfImagesFiles;

    public DirectoryImageLoader(File directory) {
        listOfImagesFiles = new ArrayList<>();
        File[] filesInDirectory = directory.listFiles();
        for (int i = 0; i < filesInDirectory.length; i++) {
            if (filesInDirectory[i].isFile()) {
                if (OpenSaveImageDialog.checkFileExtension(filesInDirectory[i])) {
                    listOfImagesFiles.add(filesInDirectory[i]);
                }
            }
        }
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
}
