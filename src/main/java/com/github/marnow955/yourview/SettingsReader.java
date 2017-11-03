package com.github.marnow955.yourview;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class SettingsReader {

    public static Settings getSettingsFromFile(Settings settings, String fileName) {
        Properties properties = new Properties();
        File settingsFile = new File(fileName);
        try {
            InputStream inputStream = new FileInputStream(settingsFile);
            properties.load(inputStream);
            inputStream.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        if (properties.containsKey("language"))
            settings.setLanguage(properties.getProperty("language"));
        if (properties.containsKey("theme"))
            settings.setThemeName(properties.getProperty("theme"));
        if (properties.containsKey("menu"))
            settings.setMenuVisible(Boolean.parseBoolean(properties.getProperty("menu")));
        if (properties.containsKey("statusbar"))
            settings.setStatusbarVisible(Boolean.parseBoolean(properties.getProperty("statusbar")));
        if (properties.containsKey("info_panel"))
            settings.setInfoPanelSelected(Boolean.parseBoolean(properties.getProperty("info_panel")));
        if (properties.containsKey("toolbar"))
            settings.setToolbarVisible(Boolean.parseBoolean(properties.getProperty("toolbar")));
        if (properties.containsKey("toolbar_position"))
            settings.setToolbarPosition(properties.getProperty("toolbar_position"));
        if (properties.containsKey("thumbnails"))
            settings.setThumbViewSelected(Boolean.parseBoolean(properties.getProperty("thumbnails")));
        if (properties.containsKey("thumbnails_position"))
            settings.setThumbnailsPosition(properties.getProperty("thumbnails_position"));
        if (properties.containsKey("checked_bg"))
            settings.setChBackgroundSelected(Boolean.parseBoolean(properties.getProperty("checked_bg")));
        return settings;
    }
}
