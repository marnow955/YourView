package com.github.marnow955.yourview;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.HashMap;
import java.util.Map;

public class Settings {

    private static Settings settings = null;

    private String language;
    private String themeName;
    private BooleanProperty isMenuVisible;
    private BooleanProperty isStatusbarVisible;
    private BooleanProperty isInfoPanelSelected;
    private BooleanProperty isToolbarVisible;
    private String toolbarPosition;
    private BooleanProperty isThumbViewSelected;
    private String thumbnailsPosition;
    private BooleanProperty isChBackgroundSelected;

    private Settings() {
        language = "en";
        themeName = "dark";
        isMenuVisible = new SimpleBooleanProperty(true);
        isStatusbarVisible = new SimpleBooleanProperty(false);
        isInfoPanelSelected = new SimpleBooleanProperty(false);
        isToolbarVisible = new SimpleBooleanProperty(true);
        toolbarPosition = "top";
        isThumbViewSelected = new SimpleBooleanProperty(false);
        thumbnailsPosition = "bottom";
        isChBackgroundSelected = new SimpleBooleanProperty(false);
    }

    public static Settings getSettingsInstance() {
        if (settings == null) {
            settings = new Settings();
        }
        return settings;
    }

    public Map<String, String> getDifferencesInSettings() {
        Map<String, String> diffs = new HashMap<>();
        Settings defaultSettings = new Settings();
        if (!settings.getLanguage().equals(defaultSettings.getLanguage()))
            diffs.put("language", settings.getLanguage());
        if (!settings.getThemeName().equals(defaultSettings.getThemeName()))
            diffs.put("theme", settings.getThemeName());
        if (settings.isMenuVisible() != defaultSettings.isMenuVisible())
            diffs.put("menu", String.valueOf(settings.isMenuVisible()));
        if (settings.isStatusbarVisible() != defaultSettings.isStatusbarVisible())
            diffs.put("statusbar", String.valueOf(settings.isStatusbarVisible()));
        if (settings.isInfoPanelSelected() != defaultSettings.isInfoPanelSelected())
            diffs.put("info_panel", String.valueOf(settings.isInfoPanelSelected()));
        if (settings.isToolbarVisible() != defaultSettings.isToolbarVisible())
            diffs.put("toolbar", String.valueOf(settings.isToolbarVisible()));
        if (!settings.getToolbarPosition().equals(defaultSettings.getToolbarPosition()))
            diffs.put("toolbar_position", settings.getToolbarPosition());
        if (settings.isThumbViewSelected() != defaultSettings.isThumbViewSelected())
            diffs.put("thumbnails", String.valueOf(settings.isThumbViewSelected()));
        if (!settings.getThumbnailsPosition().equals(defaultSettings.getThumbnailsPosition()))
            diffs.put("thumbnails_position", settings.getThumbnailsPosition());
        if (settings.isChBackgroundSelected() != defaultSettings.isChBackgroundSelected())
            diffs.put("checked_bg", String.valueOf(settings.isChBackgroundSelected()));
        return diffs;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public boolean isMenuVisible() {
        return isMenuVisible.get();
    }

    public BooleanProperty isMenuVisibleProperty() {
        return isMenuVisible;
    }

    public void setMenuVisible(boolean isMenuVisible) {
        this.isMenuVisible.set(isMenuVisible);
    }

    public boolean isStatusbarVisible() {
        return isStatusbarVisible.get();
    }

    public BooleanProperty isStatusbarVisibleProperty() {
        return isStatusbarVisible;
    }

    public void setStatusbarVisible(boolean isStatusbarVisible) {
        this.isStatusbarVisible.set(isStatusbarVisible);
    }

    public boolean isInfoPanelSelected() {
        return isInfoPanelSelected.get();
    }

    public BooleanProperty isInfoPanelSelectedProperty() {
        return isInfoPanelSelected;
    }

    public void setInfoPanelSelected(boolean isInfoPanelSelected) {
        this.isInfoPanelSelected.set(isInfoPanelSelected);
    }

    public boolean isToolbarVisible() {
        return isToolbarVisible.get();
    }

    public BooleanProperty isToolbarVisibleProperty() {
        return isToolbarVisible;
    }

    public void setToolbarVisible(boolean isToolbarVisible) {
        this.isToolbarVisible.set(isToolbarVisible);
    }

    public String getToolbarPosition() {
        return toolbarPosition;
    }

    public void setToolbarPosition(String toolbarPosition) {
        this.toolbarPosition = toolbarPosition;
    }

    public boolean isThumbViewSelected() {
        return isThumbViewSelected.get();
    }

    public BooleanProperty isThumbViewSelectedProperty() {
        return isThumbViewSelected;
    }

    public void setThumbViewSelected(boolean isThumbViewSelected) {
        this.isThumbViewSelected.set(isThumbViewSelected);
    }

    public String getThumbnailsPosition() {
        return thumbnailsPosition;
    }

    public void setThumbnailsPosition(String thumbnailsPosition) {
        this.thumbnailsPosition = thumbnailsPosition;
    }

    public boolean isChBackgroundSelected() {
        return isChBackgroundSelected.get();
    }

    public BooleanProperty isChBackgroundSelectedProperty() {
        return isChBackgroundSelected;
    }

    public void setChBackgroundSelected(boolean isChBackgroundSelected) {
        this.isChBackgroundSelected.set(isChBackgroundSelected);
    }
}
