package com.github.marnow955.yourview.controllers;

import com.github.marnow955.yourview.settings.Settings;
import com.github.marnow955.yourview.settings.SettingsWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class SettingsPanelController {

    private Settings settings;
    private Stage stage;
    @FXML
    private ResourceBundle resources;
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
    private CheckBox navigationBarChBox;
    @FXML
    private ComboBox<String> navigationBarPosition;
    @FXML
    private CheckBox hideNavBarChBox;

    @FXML
    private void initialize() {
        settings = Settings.getSettingsInstance();
        ObservableList<String> languageOptions = FXCollections.observableArrayList(
                resources.getString("english"),
                resources.getString("polish")
        );
        ObservableList<String> positionsOptions = FXCollections.observableArrayList(
                resources.getString("w_top"),
                resources.getString("w_left"),
                resources.getString("w_right"),
                resources.getString("w_bottom")
        );
        ObservableList<String> navigationBarPositionOptions = FXCollections.observableArrayList(
                resources.getString("w_top"),
                resources.getString("w_center"),
                resources.getString("w_bottom")
        );
        langCB.setItems(languageOptions);
        toolbarPosition.setItems(positionsOptions);
        thumbViewPosition.setItems(positionsOptions);
        navigationBarPosition.setItems(navigationBarPositionOptions);

        loadValuesFromSettings(settings);
    }

    private void loadValuesFromSettings(Settings settings) {
        themeTG.getToggles().forEach(toggle -> {
            if (((Node) toggle).getId().equals(settings.getThemeName()))
                toggle.setSelected(true);
        });
        checked_bg.setSelected(settings.isChBackgroundSelected());
        langCB.getSelectionModel().select(resources.getString(resources.getString("lang_name")));

        menuChBox.setSelected(settings.isMenuVisible());
        statusbarChBox.setSelected(settings.isStatusbarVisible());
        infoPanelChBox.setSelected(settings.isInfoPanelSelected());
        toolbarChBox.setSelected(settings.isToolbarVisible());
        toolbarPosition.getSelectionModel().select(resources.getString("w_" + settings.getToolbarPosition()));
        thumbnailsChBox.setSelected(settings.isThumbViewSelected());
        thumbViewPosition.getSelectionModel().select(resources.getString("w_" + settings.getThumbnailsPosition()));
        navigationBarChBox.setSelected(settings.isNavigationBarVisible());
        navigationBarPosition.getSelectionModel().select(resources.getString("w_" + settings.getNavigationBarPosition()));
        hideNavBarChBox.setSelected(settings.isHideNavBarOnClickSelected());
    }

    void setStage(Stage stage) {
        this.stage = stage;
    }

    void closeSettingsPanel() {
        if (isSettingsChanged()) {
            Optional<ButtonType> buttonType = askToSave();
            buttonType.ifPresent(buttonType1 -> {
                if (buttonType1.getButtonData() == ButtonBar.ButtonData.YES) {
                    saveChanges();
                } else if (buttonType1.getButtonData() == ButtonBar.ButtonData.NO) {
                    cancelChanges();
                }
            });
        } else {
            stage.close();
        }
    }

    @FXML
    private void saveChanges() {
        if (isSettingsChanged()) {
            settings.setSettingsFromMap(getUserSettingsAsMap());
            SettingsWriter.writeSettingsToFile(settings, "settings.properties");
        }
        stage.close();
    }

    @FXML
    void cancelChanges() {
        stage.close();
    }

    private boolean isSettingsChanged() {
        return !getUserSettingsAsMap().equals(settings.getSettingsAsMap());
    }

    private Map<String, String> getUserSettingsAsMap() {
        Map<String, String> settingsMap = new HashMap<>();
        String lang = langCB.getSelectionModel().getSelectedItem();
        if (lang.equals(resources.getString("english")))
            settingsMap.put("language", "en");
        else if (lang.equals(resources.getString("polish"))) {
            settingsMap.put("language", "pl");
        }
        settingsMap.put("theme", ((Node) themeTG.getSelectedToggle()).getId());
        settingsMap.put("menu", String.valueOf(menuChBox.isSelected()));
        settingsMap.put("statusbar", String.valueOf(statusbarChBox.isSelected()));
        settingsMap.put("info_panel", String.valueOf(infoPanelChBox.isSelected()));
        settingsMap.put("toolbar", String.valueOf(toolbarChBox.isSelected()));
        String pos = toolbarPosition.getSelectionModel().getSelectedItem();
        settingsMap.put("toolbar_position", getPositionString(pos));
        settingsMap.put("thumbnails", String.valueOf(thumbnailsChBox.isSelected()));
        pos = thumbViewPosition.getSelectionModel().getSelectedItem();
        settingsMap.put("thumbnails_position", getPositionString(pos));
        settingsMap.put("checked_bg", String.valueOf(checked_bg.isSelected()));
        settingsMap.put("navigation_bar", String.valueOf(navigationBarChBox.isSelected()));
        pos = navigationBarPosition.getSelectionModel().getSelectedItem();
        settingsMap.put("navigation_bar_position", getPositionString(pos));
        settingsMap.put("hide_nav_bar", String.valueOf(hideNavBarChBox.isSelected()));
        return settingsMap;
    }

    private String getPositionString(String pos) {
        if (pos.equals(resources.getString("w_top")))
            return "top";
        else if (pos.equals(resources.getString("w_left")))
            return "left";
        else if (pos.equals(resources.getString("w_right")))
            return "right";
        else if (pos.equals(resources.getString("w_bottom")))
            return "bottom";
        else if (pos.equals(resources.getString("w_center")))
            return "center";
        else
            return "top";
    }

    private Optional<ButtonType> askToSave() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        Stage alertWindow = (Stage) alert.getDialogPane().getScene().getWindow();
        alertWindow.getIcons().addAll(stage.getIcons());
        alert.setTitle(resources.getString("settings"));
        alert.setHeaderText(null);
        alert.setContentText(resources.getString("settings_conf_content"));
        ButtonType yesButton = new ButtonType(resources.getString("w_yes"), ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType(resources.getString("w_no"), ButtonBar.ButtonData.NO);
        ButtonType cancelButton = new ButtonType(resources.getString("w_cancel"), ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesButton, noButton, cancelButton);

        return alert.showAndWait();
    }

    @FXML
    private void loadDefault() {
        Settings defaultSettings = settings.getDefaultSettings();
        loadValuesFromSettings(defaultSettings);
    }
}
