package com.github.marnow955.yourview;

import com.github.marnow955.yourview.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Settings settings = Settings.getSettingsInstance();
        if (new File("settings.properties").exists())
            SettingsReader.getSettingsFromFile(settings, "settings.properties");
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
        primaryStage.getIcons().add(new Image(getClass().getResource("/images/YV_logo.png").toExternalForm()));
        primaryStage.setTitle("Your View");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

}
