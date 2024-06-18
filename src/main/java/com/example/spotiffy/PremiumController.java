package com.example.spotiffy;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class PremiumController {

    @FXML
    private TextField cardNumberField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField cvvField;
    @FXML
    private Button purchaseButton;
    @FXML
    private Label thankYouMessage;

//    private MainController mainController;
    @FXML
    public void initialize() {
        cardNumberField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        nameField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        emailField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        cvvField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
    }

    private void validateForm() {
        boolean isCardNumberValid = cardNumberField.getText().matches("\\d{16}");
        boolean isNameValid = !nameField.getText().trim().isEmpty();
        boolean isEmailValid = emailField.getText().endsWith("@gmail.com");
        boolean isCvvValid = cvvField.getText().matches("\\d{3}");

        purchaseButton.setDisable(!(isCardNumberValid && isNameValid && isEmailValid && isCvvValid));
    }

    @FXML
    private void handlePurchase() {
        thankYouMessage.setText("Thank you for purchasing Spotiffy Premium! Returning to app..");
        thankYouMessage.setVisible(true);

        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
        pause.setOnFinished(event -> goToMainView());
        pause.play();
    }

    private void goToMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main-view.fxml"));
            Parent root = loader.load();

            MainController mainController = loader.getController();
            mainController.setPremiumUser(true);

            Stage stage = (Stage) purchaseButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.getCause();
        }
    }
}