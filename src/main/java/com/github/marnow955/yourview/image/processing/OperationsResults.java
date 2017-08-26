package com.github.marnow955.yourview.image.processing;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class OperationsResults {

    private final List<Image> history;
    private int historyIndex = -1;

    OperationsResults() {
        history = new ArrayList<>();
    }

    void addToHistory(Image image) {
        history.add(image);
        historyIndex++;
    }

    public Image undo() throws Exception {
        if (historyIndex >= 0) {
            historyIndex--;
            return history.get(historyIndex+1);
        }
        throw new Exception();
    }

    public Image redo() throws Exception {
        if (historyIndex < history.size()-1) {
            historyIndex++;
            history.get(historyIndex);
        }
        throw new Exception();
    }

}
