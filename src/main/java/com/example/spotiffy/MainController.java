package com.example.spotiffy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Callback;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainController {
    @FXML
    private ListView<String> musicListView;
    @FXML
    private Label currentlyPlayingLabel;
    @FXML
    private TextField searchField;
    @FXML
    private Label limitMessageLabel;
    @FXML
    private Button previousButton;
    @FXML
    private Button skipButton;
    @FXML
    private Button buyPremiumButton;
    @FXML
    private Label alreadyBoughtPremiumLabel;

    private MediaPlayer mediaPlayer;
    private List<String> musicFiles;
    private int currentSongIndex = 0;
    private ObservableList<String> observableMusicFiles;

    private int skipCount = 0;
    private int previousCount = 0;
    private boolean isPremiumUser = false;
    private LocalDateTime resetTime;

    @FXML
    public void initialize() {
        loadMusicFiles();
        observableMusicFiles = FXCollections.observableArrayList(musicFiles);
        musicListView.setItems(observableMusicFiles);
        musicListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> playSelectedMusic(newValue));
        resetTime = LocalDateTime.now().plusHours(1);

        musicListView.setFocusTraversable(false);

        musicListView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> listView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            String[] parts = item.split(" \\(by ");
                            if (parts.length == 2) {
                                setText(parts[0] + "\n(by " + parts[1]);
                            } else {
                                setText(item);
                            }
                        }
                    }
                };
            }
        });
    }

    private void loadMusicFiles() {
        musicFiles = new ArrayList<>();
        File musicDir = new File(Objects.requireNonNull(getClass().getResource("/audio")).getFile());
        if (musicDir.exists() && musicDir.isDirectory()) {
            for (File file : Objects.requireNonNull(musicDir.listFiles())) {
                if (file.isFile() && file.getName().endsWith(".m4a")) {
                    String fileName = file.getName().substring(0, file.getName().length() - 4);
                    musicFiles.add(fileName);
                }
            }
        }
    }

    private void playSelectedMusic(String musicFileName) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        if (musicFileName != null) {
            currentSongIndex = musicFiles.indexOf(musicFileName);
            String musicFilePath = Objects.requireNonNull
                    (getClass().getResource("/audio/" + musicFileName + ".m4a")).toExternalForm();
            Media media = new Media(musicFilePath);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            currentlyPlayingLabel.setText("Currently Playing: " + musicFileName);
        }
    }

    @FXML
    private void handlePlay() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    @FXML
    private void handlePause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    @FXML
    private void handleRestart() {
        if (mediaPlayer != null) {
            mediaPlayer.seek(mediaPlayer.getStartTime());
            mediaPlayer.play();
        }
    }

    @FXML
    private void resetSkipLimit() {
        skipCount = 0;
        resetTime = LocalDateTime.now().plusHours(1);
        limitMessageLabel.setVisible(false);
    }

    @FXML
    private boolean checkLimitReached(int count) {
        if (isPremiumUser) {
            return false;
        }

        if (LocalDateTime.now().isAfter(resetTime)) {
            resetSkipLimit();
        }

        if (count >= 3) {
            limitMessageLabel.setText("In order to listen to unlimited songs, buy Spotiffy Premium.");
            limitMessageLabel.setVisible(true);
            previousButton.setDisable(true);
            skipButton.setDisable(true);
            return true;
        }
        return false;
    }

    @FXML
    private void handleSkip() {
        if (checkLimitReached(skipCount)) {
            return;
        }
        skipCount++;
        if (musicFiles.isEmpty()) {
            return;
        }
        currentSongIndex = (currentSongIndex + 1) % musicFiles.size();
        String nextSong = musicFiles.get(currentSongIndex);
        musicListView.getSelectionModel().select(currentSongIndex);
        playSelectedMusic(nextSong);
    }

    @FXML
    private void handlePrevious() {
        if (checkLimitReached(previousCount)) {
            return;
        }
        previousCount++;
        if (musicFiles.isEmpty()) {
            return;
        }
        currentSongIndex = (currentSongIndex - 1 + musicFiles.size()) % musicFiles.size();
        String previousSong = musicFiles.get(currentSongIndex);
        musicListView.getSelectionModel().select(currentSongIndex);
        playSelectedMusic(previousSong);
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().toLowerCase();
        if (searchText.isEmpty()) {
            musicListView.setItems(observableMusicFiles);
            return;
        }

        ObservableList<String> filteredList = FXCollections.observableArrayList();
        for (String song : musicFiles) {
            if (song.toLowerCase().contains(searchText)) {
                filteredList.add(song);
            }
        }
        musicListView.setItems(filteredList);
    }

    @FXML
    private void switchToStart() {
        stopMusic();
        try {
            Main.showStartView();
        } catch (Exception e) {
            e.getCause();
        }
    }

    @FXML
    private void handleBuyPremium() {
        if (isPremiumUser) {
            alreadyBoughtPremiumLabel.setVisible(true);
        } else {
            stopMusic();
            try {
                Main.showPremiumView();
            } catch (Exception e) {
                e.getCause();
            }
        }
    }

    private void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer = null;
        }
    }


    public void setPremiumUser(boolean isPremiumUser) {
        this.isPremiumUser = isPremiumUser;
        alreadyBoughtPremiumLabel.setVisible(isPremiumUser);
        buyPremiumButton.setVisible(!isPremiumUser);
    }
}