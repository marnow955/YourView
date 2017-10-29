package com.github.marnow955.yourview.controllers;

import com.github.marnow955.yourview.Settings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleGroup;

import java.util.ResourceBundle;

public class SettingsPanelController {

    private Settings settings;
    @FXML
    private ResourceBundle resources;
    private ObservableList<String> languageOptions;
    private ObservableList<String> positionsOptions;
    @FXML
    private ToggleGroup themeTG;
    @FXML
    private CheckBox checked_bg;
    @FXML
    private ComboBox<String> langCB;
    @FXML
    private CheckBox menuChBox;
    @FXML
    private CheckBox statusbarChBox;
    @FXML
    private CheckBox infoPanelChBox;
    @FXML
    private CheckBox toolbarChBox;
    @FXML
    private ComboBox<String> toolbarPosition;
    @FXML
    private CheckBox thumbnailsChBox;
    @FXML
    private ComboBox<String> thumbViewPosition;

    @FXML
    private void initialize() {
        settings = Settings.getSettingsInstance();
        languageOptions = FXCollections.observableArrayList(
                resources.getString("english"),
                resources.getString("polish")
        );
        positionsOptions = FXCollections.observableArrayList(
                resources.getString("w_top"),
                resources.getString("w_left"),
                resources.getString("w_right"),
                resources.getString("w_bottom")
        );
        langCB.setItems(languageOptions);
        toolbarPosition.setItems(positionsOptions);
        thumbViewPosition.setItems(positionsOptions);

        themeTG.getToggles().forEach(toggle -> {
            if (((Node) toggle).getId().equals(settings.getThemeName()))
                toggle.setSelected(true);
        });
        checked_bg.setSelected(settings.isChBackgroundSelected());
        langCB.getSelectionModel().select(resources.getString("lang_name"));

        menuChBox.setSelected(settings.isMenuVisible());
        statusbarChBox.setSelected(settings.isStatusbarVisible());
        infoPanelChBox.setSelected(settings.isInfoPanelSelected());
        toolbarChBox.setSelected(settings.isToolbarVisible());
        toolbarPosition.getSelectionModel().select(resources.getString("w_"+settings.getToolbarPosition()));
        thumbnailsChBox.setSelected(settings.isThumbViewSelected());
        thumbViewPosition.getSelectionModel().select(resources.getString("w_"+settings.getThumbnailsPosition()));
    }
}
