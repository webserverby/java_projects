package com.faenko.mavenjavafxcosts.start;

import com.faenko.mavenjavafxcosts.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Класс для запуска приложения
 *
 * @author Artem Faenko
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/main.fxml"));
        Parent fxmlMain = fxmlLoader.load();
        MainController mainController = fxmlLoader.getController();
        mainController.setMainStage(primaryStage);

        primaryStage.setTitle("Коммунальные расходы");
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(450);
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(fxmlMain, 450, 600));
        primaryStage.getScene().getStylesheets().add(0, "/styles/style.css");
        primaryStage.show();
    }

}
