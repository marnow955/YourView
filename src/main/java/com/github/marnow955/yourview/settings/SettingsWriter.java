package com.github.marnow955.yourview.settings;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

public class SettingsWriter {

    public static void writeSettingsToFile(Settings settings, String fileName) {
        try {
            Properties properties = new Properties();
            properties.putAll(settings.getDifferencesFromDefaults());
            File settingsFile = new File(fileName);
            if (properties.isEmpty()) {
                if (settingsFile.exists() && settingsFile.isFile())
                    settingsFile.delete();
            } else {
                OutputStream outputStream = new FileOutputStream(settingsFile);
                properties.store(outputStream, "YourView Settings");
                outputStream.close();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

}
