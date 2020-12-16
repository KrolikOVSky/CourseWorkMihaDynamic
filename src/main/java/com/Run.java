package com;

import com.frontEnd.MainWindow;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Run extends Application {
    public static void run(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) {
        Global.primaryStage.setScene(Global.mainScene);
        Global.primaryStage.setResizable(false);
        Global.primaryStage.setWidth(800);
        Global.primaryStage.setHeight(600);
        Global.primaryStage.setTitle("Program to work with database of books");
        Global.primaryStage.getIcons().add(new Image("/com/images/icon.png"));
        MainWindow mainWindow = new MainWindow();
        Global.setScene(mainWindow.getMainBoxOfElements());
        Global.primaryStage.show();
    }
}
