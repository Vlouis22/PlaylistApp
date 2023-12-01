package com.example.playlistapp;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Controller implements Initializable {

    // add light mode and dark mode

    @FXML
    private Pane pane;
    @FXML
    private Label songLabel;

    @FXML
    private Label prevSongLabel;

    @FXML
    private Button addNewSongButton;

    @FXML
    private TextField newPlaylistText;

    @FXML
    private Label nextSongLabel;
    @FXML
    private Slider volumeSlider;

    @FXML
    private Label prevArtistLabel;

    @FXML
    private Label nextArtistLabel;

    @FXML
    private Label previous;

    @FXML
    private Label next;

    @FXML
    private Label currentArtistLabel;
    @FXML
    private ProgressBar songProgressBar;
    @FXML
    private TextField songField;
    private Media media;

    @FXML
    private Label currentPlaylistText;
    private MediaPlayer mediaPlayer;
    public Button previousButton, nextButton, pauseButton, playButton, deleteButton, newPlaylistButton;

    private Timer timer;
    private boolean running;
    private int songNumber;

    private TimerTask task;
    private Playlist newPlaylist;

    private ArrayList<Playlist> playlistArray;

    @FXML
    ImageView myImageView;
    @FXML
    ImageView myprevImageView;

    @FXML
    ImageView mynextImageView;

    @FXML
    MenuBar choosePlaylistMenu;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String previewUrl = "https://cdns-preview-8.dzcdn.net/stream/c-8b7aaf57e2aa777e6c64da14ad741754-5.mp3";
        songNumber = 0;
        media = new Media(previewUrl);
        mediaPlayer = new MediaPlayer(media);
        volumeSlider.setVisible(false);
        playlistArray = new ArrayList<>();
        newPlaylist = new Playlist("Default Playlist");
        currentPlaylistText.setText("Default Playlist");

        songLabel.setText("24K Magic");
        Image newImage = new Image("https://e-cdns-images.dzcdn.net/images/artist/7f3c0956357c326b2db2cf436f1dc8c5/500x500-000000-80-0-0.jpg");
        myImageView.setImage(newImage);
        mynextImageView.setImage(newImage);
        myprevImageView.setImage(newImage);
        currentArtistLabel.setText("Bruno Mars");

        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mediaPlayer.setVolume(volumeSlider.getValue()*0.01);
            }
        });
    }

    public void playMedia() {
        beginTimer();
        pauseMedia();
        if (newPlaylist.getSize() != 0) {
            if (songNumber > newPlaylist.getSize()) {
                songNumber = 1;
            } else if (songNumber < 1) {
                songNumber = newPlaylist.getSize();
            }

            setDisplay();

            String url = newPlaylist.playSong(songNumber - 1).toString();
            media = new Media(url);
            mediaPlayer = new MediaPlayer(media);
            this.mediaPlayer.play();

        }
        else{
            unvisible();
        }
    }

    public void setDisplay(){
        System.out.println("Song number: " + songNumber);
        System.out.println("Playlist size: "+newPlaylist.getSize());
        System.out.println();


        getCurrentImage();
        getPreviousImage();
        getNextImage();

        songLabel.setText(newPlaylist.getCurrentLabel(songNumber));
        prevSongLabel.setText(newPlaylist.getPrevLabel(songNumber));
        nextSongLabel.setText(newPlaylist.getNextLabel(songNumber));
        currentArtistLabel.setText(newPlaylist.getCurrentArtist(songNumber));
        prevArtistLabel.setText(newPlaylist.getPrevArtist(songNumber));
        nextArtistLabel.setText(newPlaylist.getNextArtist(songNumber));
        currentPlaylistText.setText(newPlaylist.name);

        visible();
    }

    public void visible(){
        songLabel.setVisible(true);
        prevSongLabel.setVisible(true);
        nextSongLabel.setVisible(true);
        currentArtistLabel.setVisible(true);
        prevArtistLabel.setVisible(true);
        nextArtistLabel.setVisible(true);
        previous.setVisible(true);
        next.setVisible(true);
    }

    public void unvisible(){
        songLabel.setVisible(false);
        prevSongLabel.setVisible(false);
        nextSongLabel.setVisible(false);
        currentArtistLabel.setVisible(false);
        prevArtistLabel.setVisible(false);
        nextArtistLabel.setVisible(false);
    }
    public void pauseMedia(){
        //cancelTimer();
        this.mediaPlayer.stop();
    }

    public void showVolumeSlider(){
        volumeSlider.setVisible(true);
        showMenu();
    }

    public void hideVolumeSlider(){
        volumeSlider.setVisible(false);
        hideMenu();
    }

    public void previousMedia(){
        this.mediaPlayer.stop();
        if(running){
            cancelTimer();
        }
        songNumber--;
        playMedia();
        beginTimer();
    }

    public void deleteMedia(){
        if (newPlaylist.getSize() > 0) {
            newPlaylist.removeSong(newPlaylist.getCurrentSong(songNumber));
            playMedia();
            beginTimer();
        }
        else{
            unvisible();
            System.out.println("No songs to delete");
        }
    }

    public void nextMedia(){
        this.mediaPlayer.stop();
        if(running){
            cancelTimer();
        }
        songNumber++;
        playMedia();
    }

    // timer for the song progress bar
    public void beginTimer() {
        timer = new Timer();
        task = new TimerTask() {
            public void run() {
                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();
                songProgressBar.setProgress(current/end);

                if (current / end == 1) {
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    public void cancelTimer() {
        running = false;
        timer.cancel();
    }

    public void getPreviousImage(){
        String imageUrl = newPlaylist.getPrevImage(songNumber);
        Image myImage = new Image(imageUrl);
        myprevImageView.setImage(myImage);
    }

    public void getCurrentImage(){
        String imageUrl = newPlaylist.getCurrentImage(songNumber);
        Image myImage = new Image(imageUrl);
        myImageView.setImage(myImage);
    }

    public void getNextImage(){
        String imageUrl = newPlaylist.getNextImage(songNumber);
        Image myImage = new Image(imageUrl);
        mynextImageView.setImage(myImage);
    }
    // Api Call => Gets music from Deezer and allows you to search and play any music
    public void getSong(String songName) {
        OkHttpClient client = new OkHttpClient();
        String stringUrl = "https://deezerdevs-deezer.p.rapidapi.com/search?q=";
        stringUrl += songName;
        Request request = new Request.Builder()
                .url(stringUrl)
                .get()
                .addHeader("X-RapidAPI-Key", "ff047da37amsh4dcf829642fb1dep1e3e34jsnd9d42c0e673d")
                .addHeader("X-RapidAPI-Host", "deezerdevs-deezer.p.rapidapi.com")
                .build();

        try {
            Response response = client.newCall(request).execute();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body().string());
            JsonNode dataArray = jsonNode.path("data");

            for (JsonNode track : dataArray) {
                String title = track.path("title").asText();
                String previewUrl = track.path("preview").asText();
                String imageUrl = track.path("artist").path("picture_big").asText();
                String artist = track.path("artist").path("name").asText();
                Song song = new Song(title, previewUrl, imageUrl, artist);
                newPlaylist.addSong(song);
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSong() {
        mediaPlayer.stop();
        if (songField.getText() != null) {
            getSong(songField.getText());
            songField.clear();
        }
        playMedia();
        pauseMedia();
    }

    public void addPlaylist() {
        String playlistName = newPlaylistText.getText();
        MenuItem newMenuItem = new MenuItem(playlistName);
        Playlist newPlaylist = new Playlist(playlistName);
        playlistArray.add(newPlaylist);
        newPlaylistText.clear();
        Menu playlistMenu = (Menu) choosePlaylistMenu.getMenus().get(0);
        newMenuItem.setOnAction(event -> handlePlaylistSelection(event)); // Set the event handler
        playlistMenu.getItems().add(newMenuItem);

        if (playlistArray.size() > 1) {
            int playlistIndex = playlistArray.size() - 1;
            playlistArray.get(playlistIndex).displayPlaylist();
            newPlaylist = playlistArray.get(playlistIndex);
            System.out.println("Switched to playlist: " + newPlaylist);
        }
        playMedia();
    }

    public void handlePlaylistSelection(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        Playlist selectedPlaylist = getPlaylistByName(menuItem.getText());
        if (selectedPlaylist != null) {
            newPlaylist = selectedPlaylist;
            System.out.println("Changed playlist to: " + newPlaylist);
        }
    }

    private Playlist getPlaylistByName(String playlistName) {
        for (Playlist playlist : playlistArray) {
            if (playlist.getPlaylistName().equals(playlistName)) {
                newPlaylist = playlist;
                System.out.println(newPlaylist.getSize());
                return playlist;
            }
        }
        return null;
    }

    private void hideMenu(){
        newPlaylistButton.setVisible(false);
        newPlaylistText.setVisible(false);
        songField.setVisible(false);
        addNewSongButton.setVisible(false);
    }
    private void showMenu(){
        newPlaylistButton.setVisible(true);
        newPlaylistText.setVisible(true);
        songField.setVisible(true);
        addNewSongButton.setVisible(true);
    }
}