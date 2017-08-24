package com.github.marnow955.yourview;

import com.github.marnow955.yourview.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties properties = loadProperties();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
        loader.setResources(ResourceBundle.getBundle("bundles.lang", new Locale(properties.getProperty("language"))));
        Parent root = loader.load();
        MainController controller = loader.getController();
        controller.setStageAndSetupView(primaryStage);
        Scene scene = new Scene(root);
        String theme = properties.getProperty("theme");
        scene.getStylesheets().add(getClass().getResource("/styles/MainView_" + theme + ".css").toExternalForm());
        primaryStage.getIcons().add(new Image(getClass().getResource("/images/YV_logo.png").toExternalForm()));
        primaryStage.setTitle("Your View");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private Properties loadProperties() {
        Properties properties = new Properties();
        InputStream inputStream = getClass().getResourceAsStream("/default_config.properties");
        try {
            properties.load(inputStream);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
