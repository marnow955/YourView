package com.github.marnow955.yourview.controllers;

import com.github.marnow955.yourview.Settings;
import com.github.marnow955.yourview.SettingsWriter;
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
        toolbarPosition.getSelectionModel().select(resources.getString("w_" + settings.getToolbarPosition()));
        thumbnailsChBox.setSelected(settings.isThumbViewSelected());
        thumbViewPosition.getSelectionModel().select(resources.getString("w_" + settings.getThumbnailsPosition()));
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
        //TODO: load settings values in view
        //TODO - maybe...reloadSettings? - so view is reloading because of binding
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
        settingsMap.put("toolbar_position", toolbarPosition.getSelectionModel().getSelectedItem().toLowerCase());
        settingsMap.put("thumbnails", String.valueOf(thumbnailsChBox.isSelected()));
        settingsMap.put("thumbnails_position", thumbViewPosition.getSelectionModel().getSelectedItem().toLowerCase());
        settingsMap.put("checked_bg", String.valueOf(checked_bg.isSelected()));
        return settingsMap;
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

        Optional<ButtonType> result = alert.showAndWait();
        return result;
    }
}
