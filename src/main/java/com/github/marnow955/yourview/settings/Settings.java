package com.github.marnow955.yourview.settings;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashMap;
import java.util.Map;

public class Settings {

    private static Settings settings = null;

    private StringProperty language;
    private StringProperty themeName;
    private BooleanProperty isMenuVisible;
    private BooleanProperty isStatusbarVisible;
    private BooleanProperty isInfoPanelSelected;
    private BooleanProperty isToolbarVisible;
    private StringProperty toolbarPosition;
    private BooleanProperty isThumbViewSelected;
    private StringProperty thumbnailsPosition;
    private BooleanProperty isChBackgroundSelected;
    private BooleanProperty isNavigationBarVisible;
    private StringProperty navigationBarPosition;

    private Settings() {
        language = new SimpleStringProperty("en");
        themeName = new SimpleStringProperty("dark");
        isMenuVisible = new SimpleBooleanProperty(true);
        isStatusbarVisible = new SimpleBooleanProperty(false);
        isInfoPanelSelected = new SimpleBooleanProperty(false);
        isToolbarVisible = new SimpleBooleanProperty(true);
        toolbarPosition = new SimpleStringProperty("top");
        isThumbViewSelected = new SimpleBooleanProperty(false);
        thumbnailsPosition = new SimpleStringProperty("bottom");
        isChBackgroundSelected = new SimpleBooleanProperty(false);
        isNavigationBarVisible = new SimpleBooleanProperty(true);
        navigationBarPosition = new SimpleStringProperty("center");
    }

    public static Settings getSettingsInstance() {
        if (settings == null) {
            settings = new Settings();
        }
        return settings;
    }

    public Settings getDefaultSettings() {
        return new Settings();
    }

    public Settings removeListeners() {
        Settings tmp = new Settings();
        tmp.setLanguage(settings.getLanguage());
        tmp.setThemeName(settings.getThemeName());
        tmp.setMenuVisible(settings.isMenuVisible());
        tmp.setStatusbarVisible(settings.isStatusbarVisible());
        tmp.setInfoPanelSelected(settings.isInfoPanelSelected());
        tmp.setToolbarVisible(settings.isToolbarVisible());
        tmp.setToolbarPosition(settings.getToolbarPosition());
        tmp.setThumbViewSelected(settings.isThumbViewSelected());
        tmp.setThumbnailsPosition(settings.getThumbnailsPosition());
        tmp.setChBackgroundSelected(settings.isChBackgroundSelected());
        tmp.setNavigationBarVisible(settings.isNavigationBarVisible());
        tmp.setNavigationBarPosition(settings.getNavigationBarPosition());
        settings = tmp;
        return settings;
    }

    Map<String, String> getDifferencesFromDefaults() {
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
        if (settings.isNavigationBarVisible() != defaultSettings.isNavigationBarVisible())
            diffs.put("navigation_bar", String.valueOf(settings.isNavigationBarVisible()));
        if (!settings.getNavigationBarPosition().equals(defaultSettings.getNavigationBarPosition()))
            diffs.put("navigation_bar_position", settings.getNavigationBarPosition());
        return diffs;
    }

    public Map<String, String> getSettingsAsMap() {
        Map<String, String> settingsMap = new HashMap<>();
        settingsMap.put("language", settings.getLanguage());
        settingsMap.put("theme", settings.getThemeName());
        settingsMap.put("menu", String.valueOf(settings.isMenuVisible()));
        settingsMap.put("statusbar", String.valueOf(settings.isStatusbarVisible()));
        settingsMap.put("info_panel", String.valueOf(settings.isInfoPanelSelected()));
        settingsMap.put("toolbar", String.valueOf(settings.isToolbarVisible()));
        settingsMap.put("toolbar_position", settings.getToolbarPosition());
        settingsMap.put("thumbnails", String.valueOf(settings.isThumbViewSelected()));
        settingsMap.put("thumbnails_position", settings.getThumbnailsPosition());
        settingsMap.put("checked_bg", String.valueOf(settings.isChBackgroundSelected()));
        settingsMap.put("navigation_bar", String.valueOf(settings.isNavigationBarVisible()));
        settingsMap.put("navigation_bar_position", settings.getNavigationBarPosition());
        return settingsMap;
    }

    public void setSettingsFromMap(Map<String, String> userSettingsAsMap) {
        settings.setLanguage(userSettingsAsMap.get("language"));
        settings.setThemeName(userSettingsAsMap.get("theme"));
        settings.setMenuVisible(Boolean.parseBoolean(userSettingsAsMap.get("menu")));
        settings.setStatusbarVisible(Boolean.parseBoolean(userSettingsAsMap.get("statusbar")));
        settings.setInfoPanelSelected(Boolean.parseBoolean(userSettingsAsMap.get("info_panel")));
        settings.setToolbarVisible(Boolean.parseBoolean(userSettingsAsMap.get("toolbar")));
        settings.setToolbarPosition(userSettingsAsMap.get("toolbar_position"));
        settings.setThumbViewSelected(Boolean.parseBoolean(userSettingsAsMap.get("thumbnails")));
        settings.setThumbnailsPosition(userSettingsAsMap.get("thumbnails_position"));
        settings.setChBackgroundSelected(Boolean.parseBoolean(userSettingsAsMap.get("checked_bg")));
        settings.setNavigationBarVisible(Boolean.parseBoolean(userSettingsAsMap.get("navigation_bar")));
        settings.setNavigationBarPosition(userSettingsAsMap.get("navigation_bar_position"));
    }

    public String getLanguage() {
        return language.get();
    }

    public StringProperty getLanguageProperty() {
        return language;
    }

    void setLanguage(String language) {
        this.language.set(language);
    }

    public String getThemeName() {
        return themeName.get();
    }

    public StringProperty getThemeNameProperty() {
        return themeName;
    }

    void setThemeName(String themeName) {
        this.themeName.set(themeName);
    }

    public boolean isMenuVisible() {
        return isMenuVisible.get();
    }

    public BooleanProperty isMenuVisibleProperty() {
        return isMenuVisible;
    }

    void setMenuVisible(boolean isMenuVisible) {
        this.isMenuVisible.set(isMenuVisible);
    }

    public boolean isStatusbarVisible() {
        return isStatusbarVisible.get();
    }

    public BooleanProperty isStatusbarVisibleProperty() {
        return isStatusbarVisible;
    }

    void setStatusbarVisible(boolean isStatusbarVisible) {
        this.isStatusbarVisible.set(isStatusbarVisible);
    }

    public boolean isInfoPanelSelected() {
        return isInfoPanelSelected.get();
    }

    public BooleanProperty isInfoPanelSelectedProperty() {
        return isInfoPanelSelected;
    }

    void setInfoPanelSelected(boolean isInfoPanelSelected) {
        this.isInfoPanelSelected.set(isInfoPanelSelected);
    }

    public boolean isToolbarVisible() {
        return isToolbarVisible.get();
    }

    public BooleanProperty isToolbarVisibleProperty() {
        return isToolbarVisible;
    }

    void setToolbarVisible(boolean isToolbarVisible) {
        this.isToolbarVisible.set(isToolbarVisible);
    }

    public String getToolbarPosition() {
        return toolbarPosition.get();
    }

    public StringProperty getToolbarPositionProperty() {
        return toolbarPosition;
    }

    void setToolbarPosition(String toolbarPosition) {
        this.toolbarPosition.set(toolbarPosition);
    }

    public boolean isThumbViewSelected() {
        return isThumbViewSelected.get();
    }

    public BooleanProperty isThumbViewSelectedProperty() {
        return isThumbViewSelected;
    }

    void setThumbViewSelected(boolean isThumbViewSelected) {
        this.isThumbViewSelected.set(isThumbViewSelected);
    }

    public String getThumbnailsPosition() {
        return thumbnailsPosition.get();
    }

    public StringProperty getThumbnailsPositionProperty() {
        return thumbnailsPosition;
    }

    void setThumbnailsPosition(String thumbnailsPosition) {
        this.thumbnailsPosition.set(thumbnailsPosition);
    }

    public boolean isChBackgroundSelected() {
        return isChBackgroundSelected.get();
    }

    public BooleanProperty isChBackgroundSelectedProperty() {
        return isChBackgroundSelected;
    }

    void setChBackgroundSelected(boolean isChBackgroundSelected) {
        this.isChBackgroundSelected.set(isChBackgroundSelected);
    }

    public boolean isNavigationBarVisible() {
        return isNavigationBarVisible.get();
    }

    public BooleanProperty isNavigationBarVisibleProperty() {
        return isNavigationBarVisible;
    }

    void setNavigationBarVisible(boolean isNavigationBarVisible) {
        this.isNavigationBarVisible.set(isNavigationBarVisible);
    }

    public String getNavigationBarPosition() {
        return navigationBarPosition.get();
    }

    public StringProperty getNavigationBarPositionProperty() {
        return navigationBarPosition;
    }

    void setNavigationBarPosition(String navigationBarPosition) {
        this.navigationBarPosition.set(navigationBarPosition);
    }
}
