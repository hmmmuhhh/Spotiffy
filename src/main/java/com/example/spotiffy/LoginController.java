package com.example.spotiffy;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label messageLabel;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        String validationMessage = validateInputs(username, password);
        if (validationMessage != null) {
            messageLabel.setText(validationMessage);
            return;
        }

        if (UserStore.login(username, password)) {
            switchToMain();
        } else {
            messageLabel.setText("Invalid username or password.");
        }
    }

    private String validateInputs(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return "All fields must be filled out.";
        }

        if (!isValidUsername(username)) {
            return "Invalid username. Username must be at least 3 characters long and contain at least one number.";
        }

        if (!isValidPassword(password)) {
            return "Invalid password. Password must be at least 5 characters long,\ncontain at least one number and one special character.";
        }

        return null;
    }

    private boolean isValidUsername(String username) {
        return username.length() >= 3 && username.matches(".*\\d.*");
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 5 && password.matches(".*\\d.*") && password.matches(".*\\W.*");
    }

    @FXML
    private void switchToStart() {
        try {
            Main.showStartView();
        } catch (Exception e) {
            e.getCause();
        }
    }

    private void switchToMain() {
        try {
            Main.showMainView();
        } catch (Exception e) {
            e.getCause();
        }
    }
}
