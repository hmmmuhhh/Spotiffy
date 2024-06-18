package com.example.spotiffy;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class StartController {
    public Button jokeButton;
    public Label Title;
    public Label Subtitle;
    public Button loginButton;
    public Button signupButton;
    @FXML
    private Label jokeText;

    @FXML
    private void onLoginButtonClick() {
        try {
            Main.showLoginView();
        } catch (Exception e) {
            e.getCause();
        }
    }

    @FXML
    private void onSignupButtonClick() {
        try {
            Main.showSignupView();
        } catch (Exception e) {
            e.getCause();
        }
    }

    public void onJokeButtonClick() {
        jokeButton.setVisible(false);

        jokeText.setText("There's nothing here, just wanted to add a random button, click 'sign up' now :]");
        jokeText.setVisible(true);
    }
}