package com.github.marnow955.yourview;

import com.github.marnow955.yourview.controllers.MainController;
import com.github.marnow955.yourview.settings.Settings;
import com.github.marnow955.yourview.settings.SettingsReader;
import com.sun.javafx.PlatformUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    private static final String appName = "YourView - 1.0-SNAPSHOT";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Settings settings = Settings.getSettingsInstance();
        if (new File(getUserDataDirectory() + "settings.properties").exists())
            SettingsReader.getSettingsFromFile(settings, getUserDataDirectory() + "settings.properties");
        CustomTooltipBehavior.updateTooltipBehavior(300, 5000, 200, false);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        loader.setResources(ResourceBundle.getBundle("bundles.lang", new Locale(settings.getLanguage())));
        Parent root = loader.load();
        MainController controller = loader.getController();
        controller.setStage(primaryStage);
        controller.setupView();
        controller.loadSettings();
        Scene scene = new Scene(root);
        String theme = settings.getThemeName();
        scene.getStylesheets().add(getClass().getResource("/styles/MainView_" + theme + ".css").toExternalForm());
        primaryStage.getIcons().addAll(getIcons());
        primaryStage.setTitle("Your View");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private ArrayList<Image> getIcons() {
        ArrayList<Image> icons = new ArrayList<>();
        icons.add(new Image(getClass().getResource("/images/icon16x16.png").toExternalForm()));
        icons.add(new Image(getClass().getResource("/images/icon32x32.png").toExternalForm()));
        icons.add(new Image(getClass().getResource("/images/icon64x64.png").toExternalForm()));
        icons.add(new Image(getClass().getResource("/images/icon256x256.png").toExternalForm()));
        return icons;
    }

    public static String getUserDataDirectory() {
        String dir = System.getProperty("user.home");
        if (PlatformUtil.isWindows())
            dir += File.separator + "AppData" + File.separator + "Local" + File.separator + appName;
        else if (PlatformUtil.isUnix())
            dir += File.separator + ".config" + File.separator + appName;
        File dirFile = new File(dir);
        if (dirFile.exists() && dirFile.isDirectory())
            return dir + File.separator;
        else {
            boolean success = dirFile.mkdir();
            if (success)
                return dir + File.separator;
        }
        return "";
    }

}
