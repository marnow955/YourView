package com.github.marnow955.yourview.controllers.edit_panes;

import com.github.marnow955.yourview.controllers.MainController;
import com.github.marnow955.yourview.data.processing.ImageManipulationsController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import org.controlsfx.glyphfont.Glyph;

public class CropController {

    @FXML
    private Label error;
    @FXML
    private Label infoHeight;
    @FXML
    private Label infoWidth;
    @FXML
    private Integer maxWidth;
    @FXML
    private Integer maxHeight;
    @FXML
    private ToggleGroup lockTG;
    @FXML
    private Spinner<Integer> xStart;
    @FXML
    private Spinner<Integer> yStart;
    @FXML
    private Spinner<Integer> xEnd;
    @FXML
    private Spinner<Integer> yEnd;
    @FXML
    private Spinner<Integer> width;
    @FXML
    private Spinner<Integer> height;

    private MainController mainController;
    private ImageManipulationsController processingController;

    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void injectImageProcessingController(ImageManipulationsController imageManipulationsController) {
        this.processingController = imageManipulationsController;
    }

    public void setupView() {
        //TODO: bug: arrows when "" - exception
        xStart.getEditor().textProperty().addListener((observable, oldValue, newValue) ->
                numberFormatter(xStart, oldValue, newValue));
        xStart.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> setMinValue(xStart));
        xStart.getEditor().setOnAction(event -> setMinValue(xStart));

        yStart.getEditor().textProperty().addListener((observable, oldValue, newValue) ->
                numberFormatter(yStart, oldValue, newValue));
        yStart.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> setMinValue(yStart));
        yStart.getEditor().setOnAction(event -> setMinValue(yStart));

        xEnd.getEditor().textProperty().addListener((observable, oldValue, newValue) ->
                numberFormatter(xEnd, oldValue, newValue));
        xEnd.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> setMinValue(xEnd));
        xEnd.getEditor().setOnAction(event -> setMinValue(xEnd));

        yEnd.getEditor().textProperty().addListener((observable, oldValue, newValue) ->
                numberFormatter(yEnd, oldValue, newValue));
        yEnd.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> setMinValue(yEnd));
        yEnd.getEditor().setOnAction(event -> setMinValue(yEnd));

        width.getEditor().textProperty().addListener((observable, oldValue, newValue) ->
                numberFormatter(width, oldValue, newValue));
        width.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> setMinValue(width));
        width.getEditor().setOnAction(event -> setMinValue(width));

        height.getEditor().textProperty().addListener((observable, oldValue, newValue) ->
                numberFormatter(height, oldValue, newValue));
        height.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> setMinValue(height));
        height.getEditor().setOnAction(event -> setMinValue(height));
        lockTG.getToggles().forEach(toggle -> {
            ((RadioButton) toggle).getStyleClass().remove("radio-button");
            ((RadioButton) toggle).getStyleClass().add("toggle-button");
        });
        lockTG.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            setEditable(false);
            lockTG.getToggles().forEach(toggle -> {
                if (toggle.isSelected()) {
                    ((ToggleButton) toggle).setGraphic(new Glyph("FontAwesome", "unlock"));
                    setEditable(true, ((ToggleButton) toggle).getId());
                } else {
                    ((ToggleButton) toggle).setGraphic(new Glyph("FontAwesome", "lock"));
                }
            });
        });
    }

    public void setupValuesFromImage(Image image) {
        maxWidth = (int) image.getWidth();
        maxHeight = (int) image.getHeight();
        updateImageInfo();
        updateValueFactory();
        updateEditorsValues();
        setEditable(false);
        setEditable(true, ((RadioButton) lockTG.getSelectedToggle()).getId());
    }

    @FXML
    private void crop() {
        error.setVisible(false);
        int x = xStart.getValue();
        int y = yStart.getValue();
        int resultWidth;
        int resultHeight;
        if (((RadioButton) lockTG.getSelectedToggle()).getId().equals("whLock")) {
            resultWidth = width.getValue();
            resultHeight = height.getValue();
        } else {
            resultWidth = xEnd.getValue() - xStart.getValue();
            resultHeight = yEnd.getValue() - yStart.getValue();
        }
        if (resultWidth > 0 && resultHeight > 0 && x + resultWidth <= maxWidth && y + maxHeight <= maxHeight) {
            Image image = processingController.cropImage(x, y, resultWidth, resultHeight);
            mainController.setImage(image);
            maxWidth = resultWidth;
            maxHeight = resultHeight;
            updateImageInfo();
            updateValueFactory();
            updateEditorsValues();
        } else if (resultWidth <= 0 || resultHeight <= 0) {
            error.setText("End coordinates must be bigger than starts");
            error.setVisible(true);
        } else if (x + resultWidth > maxWidth || y + maxHeight > maxHeight) {
            error.setText("Dimensions cannot be bigger than image dimensions");
            error.setVisible(true);
        }
    }

    private void updateImageInfo() {
        infoWidth.setText(maxWidth.toString());
        infoHeight.setText(maxHeight.toString());
    }

    private void updateValueFactory() {
        xStart.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxWidth));
        yStart.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxHeight));
        xEnd.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxWidth));
        yEnd.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxHeight));
        width.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxWidth));
        height.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxHeight));
    }

    private void updateEditorsValues() {
        xEnd.getEditor().setText(maxWidth.toString());
        yEnd.getEditor().setText(maxHeight.toString());
        width.getEditor().setText(maxWidth.toString());
        height.getEditor().setText(maxHeight.toString());
    }

    private void numberFormatter(Spinner spinner, String oldValue, String newValue) {
        if (!newValue.matches("[0-9]*")) {
            spinner.getEditor().setText(oldValue);
            return;
        }
        if (newValue.equals("")) {
            spinner.getEditor().setText(newValue);
            return;
        }
        if (newValue.startsWith("0") && newValue.length() > 1)
            newValue = newValue.substring(1);
        spinner.getValueFactory().setValue(Integer.valueOf(newValue));
    }

    private void setMinValue(Spinner spinner) {
        if (spinner.getEditor().getText().equals(""))
            spinner.getEditor().setText("0");
        int width;
        int height;
    }

    private void setEditable(boolean value) {
        xEnd.setEditable(value);
        xEnd.getChildrenUnmodifiable().forEach(node -> node.setDisable(!value));
        yEnd.setEditable(value);
        yEnd.getChildrenUnmodifiable().forEach(node -> node.setDisable(!value));
        width.setEditable(value);
        width.getChildrenUnmodifiable().forEach(node -> node.setDisable(!value));
        height.setEditable(value);
        height.getChildrenUnmodifiable().forEach(node -> node.setDisable(!value));
    }

    private void setEditable(boolean value, String lockItemId) {
        switch (lockItemId) {
            case "xyLock":
                xEnd.setEditable(value);
                xEnd.getChildrenUnmodifiable().forEach(node -> node.setDisable(!value));
                yEnd.setEditable(value);
                yEnd.getChildrenUnmodifiable().forEach(node -> node.setDisable(!value));
                break;
            case "whLock":
                width.setEditable(value);
                width.getChildrenUnmodifiable().forEach(node -> node.setDisable(!value));
                height.setEditable(value);
                height.getChildrenUnmodifiable().forEach(node -> node.setDisable(!value));
                break;
        }
    }
}
