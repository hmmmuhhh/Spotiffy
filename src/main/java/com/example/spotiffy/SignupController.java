package com.example.spotiffy;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignupController {
    public Label messageLabel;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    @FXML
    private void handleSignup() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        String validationMessage = validateInputs(firstName, lastName, email, username, password);
        if (validationMessage != null) {
            errorLabel.setText(validationMessage);
            return;
        }

        if (UserStore.signup(firstName, lastName, email, username, password)) {
            errorLabel.setVisible(false);
            messageLabel.setText("Signup successful! Return to the startup page to Log in!");
        } else {
            errorLabel.setText("Username already taken or email invalid.");
        }
    }

    private String validateInputs(String firstName, String lastName, String email, String username, String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            return "All fields must be filled out.";
        }

        if (!isValidUsername(username)) {
            return "Username must be at least 3 characters long and contain at least 1 number.";
        }

        if (!isValidPassword(password)) {
            return "Password must be at least 5 characters long and contain at least 1 number and 1 special character.";
        }

        if (!isValidEmail(email)) {
            return "Invalid email format!";
        }

        return null;
    }

    private boolean isValidUsername(String username) {
        return username.length() >= 3 && username.matches(".*\\d.*");
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 5 && password.matches(".*\\d.*") && password.matches(".*\\W.*");
    }

    private boolean isValidEmail(String email) {
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}");
    }

    @FXML
    private void switchToStart() {
        try {
            Main.showStartView();
        } catch (Exception e) {
            e.getCause();
        }
    }
}
