package com.github.marnow955.yourview.controllers.edit_panes;

import com.github.marnow955.yourview.controllers.MainController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.util.converter.NumberStringConverter;
import org.controlsfx.glyphfont.Glyph;

public class CropController {

    @FXML
    private Label infoHeight;
    @FXML
    private Label infoWidth;
    @FXML
    private Integer maxX;
    @FXML
    private Integer maxY;
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
    private IntegerProperty xStartProperty;
    private IntegerProperty yStartProperty;
    private IntegerProperty xEndProperty;
    private IntegerProperty yEndProperty;
    private IntegerProperty widtProperty;
    private IntegerProperty heightProperty;

    public void injectMainController(MainController mainController) {
        this.mainController = mainController;
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
            setEditable(true);
            lockTG.getToggles().forEach(toggle -> {
                if (toggle.isSelected()) {
                    ((ToggleButton) toggle).setGraphic(new Glyph("FontAwesome", "lock"));
                    setEditable(false, ((ToggleButton) toggle).getId());
                } else {
                    ((ToggleButton) toggle).setGraphic(new Glyph("FontAwesome", "unlock"));
                }
            });
        });
    }

    public void setupValuesFromImage(Image image) {
        maxX = (int) image.getWidth();
        maxY = (int) image.getHeight();
        maxWidth = (int) image.getWidth();
        maxHeight = (int) image.getHeight();
        infoWidth.setText(infoWidth.getText() + (int) image.getWidth());
        infoHeight.setText(infoHeight.getText() + (int) image.getHeight());
        xStart.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxX));
        yStart.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxY));
        xEnd.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxX));
        yEnd.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxY));
        width.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxWidth));
        height.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, maxHeight));
        xStartProperty = new SimpleIntegerProperty(0);
        yStartProperty = new SimpleIntegerProperty(0);
        xEndProperty = new SimpleIntegerProperty(maxWidth);
        yEndProperty = new SimpleIntegerProperty(maxHeight);
        widtProperty = new SimpleIntegerProperty(maxWidth);
        heightProperty = new SimpleIntegerProperty(maxHeight);
        Bindings.bindBidirectional(xStart.getEditor().textProperty(), xStartProperty, new NumberStringConverter());
        Bindings.bindBidirectional(yStart.getEditor().textProperty(), yStartProperty, new NumberStringConverter());
        Bindings.bindBidirectional(xEnd.getEditor().textProperty(), xEndProperty, new NumberStringConverter());
        Bindings.bindBidirectional(yEnd.getEditor().textProperty(), yEndProperty, new NumberStringConverter());
        Bindings.bindBidirectional(width.getEditor().textProperty(), widtProperty, new NumberStringConverter());
        Bindings.bindBidirectional(height.getEditor().textProperty(), heightProperty, new NumberStringConverter());
        setEditable(false, ((RadioButton) lockTG.getSelectedToggle()).getId());
    }

    @FXML
    private void crop() {
        System.out.println(widtProperty);
        System.out.println(((RadioButton) lockTG.getSelectedToggle()).getId());
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
    }

    private void setEditable(boolean value) {
        xStart.setEditable(value);
        xStart.getChildrenUnmodifiable().forEach(node -> node.setDisable(!value));
        yStart.setEditable(value);
        yStart.getChildrenUnmodifiable().forEach(node -> node.setDisable(!value));
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
            case "xyStartLock":
                xStart.setEditable(value);
                xStart.getChildrenUnmodifiable().forEach(node -> node.setDisable(!value));
                yStart.setEditable(value);
                yStart.getChildrenUnmodifiable().forEach(node -> node.setDisable(!value));
                break;
            case "xyEndLock":
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
