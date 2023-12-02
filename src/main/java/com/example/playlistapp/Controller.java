package com.example.playlistapp;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Controller implements Initializable {
    public AnchorPane myPane;

    // todo
    // add light mode and dark mode
    // play next song automatically
    // add splash background to playlist name

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
    public Button previousButton, nextButton, pauseButton, playButton, deleteButton;
    public Button newPlaylistButton, shuffleButton, deletePlaylistButton;

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
        playlistArray.add(newPlaylist);
        running = false;

        MenuItem newMenuItem = new MenuItem("Default Playlist");
        newMenuItem.setOnAction(this::switchPlaylist);
        Menu playlistMenu = (Menu) choosePlaylistMenu.getMenus().get(0);
        playlistMenu.getItems().add(newMenuItem);

        myPane = new AnchorPane();
        setDisplay();
        //setBackgroundIMG(new Image("https://e-cdns-images.dzcdn.net/images/artist/7432efa1fc1d9a1c5a7049512792b9fc/500x500-000000-80-0-0.jpg"));


        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
            }
        });
    }

    public void playMedia() {
        mediaPlayer.stop();
        if (newPlaylist.getSize() != 0) {
            if (songNumber > newPlaylist.getSize()) {
                songNumber = 1;
            } else if (songNumber < 1) {
                songNumber = newPlaylist.getSize();
            }

            String url = newPlaylist.playSong(songNumber - 1).toString();
            media = new Media(url);
            mediaPlayer = new MediaPlayer(media);
            beginTimer();
            this.mediaPlayer.play();

        } else {
            unvisible();
        }
        setDisplay();
    }

    public void setDisplay() {
        if (newPlaylist.getSize() != 0) {
            if(songNumber > newPlaylist.getSize()){
                songNumber = 1;
            }
            else if (songNumber < 1) {
                songNumber = newPlaylist.getSize();
            }

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
            showMenu();

        } else {
            unvisible();

            songLabel.setText("There's Nothing Holding Me Back");
            prevSongLabel.setText("Lose Yourself");
            nextSongLabel.setText("Mask Off");

            Image shawnImg = new Image("https://e-cdns-images.dzcdn.net/images/artist/7432efa1fc1d9a1c5a7049512792b9fc/500x500-000000-80-0-0.jpg");
            Image FutureImg = new Image("https://e-cdns-images.dzcdn.net/images/artist/f24795f13743283ddc49c871bc96e836/500x500-000000-80-0-0.jpg");
            Image EminemImg = new Image("https://e-cdns-images.dzcdn.net/images/artist/19cc38f9d69b352f718782e7a22f9c32/500x500-000000-80-0-0.jpg");

            myImageView.setImage(shawnImg);
            mynextImageView.setImage(FutureImg);
            myprevImageView.setImage(EminemImg);

            currentArtistLabel.setText("Shawn Mendes");
            nextArtistLabel.setText("Future");
            prevArtistLabel.setText("Eminem");

            System.out.println(newPlaylist.getPlaylistName());
            currentPlaylistText.setText(newPlaylist.getPlaylistName());

            visible();
            showMenu();

        }
    }


    public void visible() {
        songLabel.setVisible(true);
        prevSongLabel.setVisible(true);
        nextSongLabel.setVisible(true);
        currentArtistLabel.setVisible(true);
        prevArtistLabel.setVisible(true);
        nextArtistLabel.setVisible(true);
        previous.setVisible(true);
        next.setVisible(true);
        showMenu();
    }

    public void unvisible() {
        songLabel.setVisible(false);
        prevSongLabel.setVisible(false);
        nextSongLabel.setVisible(false);
        currentArtistLabel.setVisible(false);
        prevArtistLabel.setVisible(false);
        nextArtistLabel.setVisible(false);
        hideMenu();
    }

    public void pauseMedia() {
        if(newPlaylist.getSize()>0) {
            this.mediaPlayer.stop();
            running = false;
            cancelTimer();
        }
    }

    public void showVolumeSlider() {
        volumeSlider.setVisible(true);
        showMenu();
    }

    public void hideVolumeSlider() {
        volumeSlider.setVisible(false);
        hideMenu();
    }

    public void previousMedia() {
        if(newPlaylist.getSize()>0) {
            this.mediaPlayer.stop();
            cancelTimer();
            songNumber--;
            beginTimer();
            playMedia();
        }
    }

    public void deleteMedia() {
        mediaPlayer.stop();
        if (newPlaylist.getSize() > 0) {
            newPlaylist.removeSong(newPlaylist.getCurrentSong(songNumber));
            beginTimer();
            playMedia();
            setDisplay();
        } else {
            unvisible();
            System.out.println("No songs to delete");
        }
    }

    public void nextMedia() {
        if(newPlaylist.getSize()>0) {
            cancelTimer();
            this.mediaPlayer.stop();
            if (running) {
                cancelTimer();
            }
            songNumber++;
            playMedia();
            beginTimer();
        }
    }

    // timer for the song progress bar
    public void beginTimer() {
        timer = new Timer();
        task = new TimerTask() {
            public void run() {
                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();
                songProgressBar.setProgress(current / end);
                if (Math.abs(current / end - 1.0) < 0.0001) {
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

    public void getPreviousImage() {
        String imageUrl = newPlaylist.getPrevImage(songNumber);
        Image myImage = new Image(imageUrl);
        myprevImageView.setImage(myImage);
    }

    public void getCurrentImage() {
        String imageUrl = newPlaylist.getCurrentImage(songNumber);
        Image myImage = new Image(imageUrl);
        myImageView.setImage(myImage);

    }

    public void getNextImage() {
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
        if (songField.getText() != null) {
            getSong(songField.getText());
            songField.clear();
            setDisplay();
        }
    }

    public void addPlaylist() {
        String playlistName = newPlaylistText.getText();
        if(!Objects.equals(playlistName, "") || Objects.isNull(playlistName)) {
            System.out.println("playlist name: "+playlistName);
            System.out.println(Objects.equals(playlistName, ""));
            MenuItem newMenuItem = new MenuItem(playlistName);

            newMenuItem.setOnAction(this::switchPlaylist);
            newPlaylist = new Playlist(playlistName);
            playlistArray.add(newPlaylist);
            newPlaylistText.clear();

            Menu playlistMenu = (Menu) choosePlaylistMenu.getMenus().get(0);
            newMenuItem.setOnAction(this::handlePlaylistSelection);
            playlistMenu.getItems().add(newMenuItem);

            if (playlistArray.size() > 0) {
                int playlistIndex = playlistArray.size() - 1;
                playlistArray.get(playlistIndex).displayPlaylist();
                newPlaylist = playlistArray.get(playlistIndex);
                System.out.println("Switched to playlist: " + newPlaylist.name);
            }
        }
        showMenu();
        setDisplay();
        playMedia();
    }

    public void deletePlaylist() {
        pauseMedia();
        String playlistName = newPlaylistText.getText();
        if (playlistArray.size() > 1) {
            Menu playlistMenu = (Menu) choosePlaylistMenu.getMenus().get(0);
            int length = playlistArray.size() - 1;
            playlistMenu.getItems().remove(length);
            playlistArray.remove(length);
            newPlaylist = playlistArray.get(0);
            setDisplay();
            showMenu();
        }
        playMedia();
    }

    public void switchPlaylist(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        String selectedPlaylistName = menuItem.getText();
        Playlist selectedPlaylist = getPlaylistByName(selectedPlaylistName);

        if (selectedPlaylist != null) {
            this.newPlaylist = selectedPlaylist;
            playMedia();
        }

        setDisplay();
        showMenu();
        showVolumeSlider();
    }

    public void handlePlaylistSelection(ActionEvent event) {
        MenuItem menuItem = (MenuItem) event.getSource();
        Playlist selectedPlaylist = getPlaylistByName(menuItem.getText());
        if (selectedPlaylist != null) {
            newPlaylist = selectedPlaylist;
            setDisplay();
            showMenu();
        } else {
            setDisplay();
            showVolumeSlider();
            showMenu();

        }
    }

    private Playlist getPlaylistByName(String playlistName) {
        for (Playlist playlist : playlistArray) {
            if (playlist.getPlaylistName().equals(playlistName)) {
                newPlaylist = playlist;
                return playlist;
            }
        }
        return null;
    }

    private void hideMenu() {
        newPlaylistButton.setVisible(false);
        newPlaylistText.setVisible(false);
        songField.setVisible(false);
        addNewSongButton.setVisible(false);
        addNewSongButton.setVisible(false);

    }

    private void showMenu() {

        addNewSongButton.setVisible(true);
        newPlaylistButton.setVisible(true);
        newPlaylistText.setVisible(true);
        songField.setVisible(true);
        addNewSongButton.setVisible(true);

    }

    @FXML
    private void shufflePlaylist() {
        newPlaylist.shuffle();
        pauseMedia();
        setDisplay();
        nextMedia();
        playMedia();
        showMenu();
    }

    /*

    public void setBackgroundIMG(Image image) {
        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,  // repeat X
                BackgroundRepeat.NO_REPEAT,  // repeat Y
                BackgroundPosition.CENTER,   // position
                new BackgroundSize(
                        100,   // width  = 100%
                        100,   // height = 100%
                        true,  // width is percentage
                        true,  // height is percentage
                        true,  // contain image within bounds
                        true   // cover all of Region content area
                )
        );

        Background background = new Background(backgroundImage);
        this.myPane.setBackground(background);
    }

     */
}