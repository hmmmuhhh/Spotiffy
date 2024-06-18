package com.example.spotiffy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    private static Stage primaryStage;
    @Override

    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        stage.setTitle("Spotiffy :] - totally not a scam");
        showStartView();
    }

    public static void showStartView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/start-view.fxml"));
        Scene startScene = new Scene(fxmlLoader.load(), 500, 500);
        primaryStage.setScene(startScene);
        primaryStage.show();
    }

    public static void showSignupView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/signup-view.fxml"));
        Scene signupScene = new Scene(fxmlLoader.load(), 300, 500);
        primaryStage.setScene(signupScene);
        primaryStage.show();
    }

    public static void showLoginView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/login-view.fxml"));
        Scene loginScene = new Scene(fxmlLoader.load(), 300, 400);
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void showMainView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/main-view.fxml"));
        Scene mainScene = new Scene(fxmlLoader.load(), 600, 700);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void showPremiumView() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/premium-view.fxml"));
        Scene premiumScene = new Scene(fxmlLoader.load(), 400, 400);
        primaryStage.setScene(premiumScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

