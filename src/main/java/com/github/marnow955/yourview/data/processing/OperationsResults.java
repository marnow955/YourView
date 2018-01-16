package com.github.marnow955.yourview.data.processing;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class OperationsResults {

    private ArrayList<Image> history;
    private int historyIndex = -1;

    OperationsResults(Image image) {
        history = new ArrayList<>();
        history.add(image);
        historyIndex = 0;
    }

    void addToHistory(Image image) {
        if (historyIndex != history.size() - 1) {
            for (int i = historyIndex + 1; i < history.size(); i++) {
                history.remove(i);
            }
        }
        history.add(image);
        historyIndex++;
    }

    public boolean canUndo() {
        return historyIndex > 0;
    }

    public boolean canRedo() {
        return historyIndex < history.size() - 1 && historyIndex >= 0;
    }

    public Image undo() throws Exception {
        if (historyIndex > 0) {
            return history.get(--historyIndex);
        }
        throw new Exception();
    }

    public Image redo() throws Exception {
        if (historyIndex < history.size() - 1 && historyIndex >= 0) {
            return history.get(++historyIndex);
        }
        throw new Exception();
    }

}
