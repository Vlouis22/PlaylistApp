package com.example.playlistapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import okhttp3.*;


public class HelloApplication extends Application {
    // Need a default playlist => download 10 songs from Youtube and add it to the project so that user can have a default playlist if they haven't create a playlist themselves

    // need test cases for every thing, even for the api => research how to create test cases for maven java

    // test deleting from an empty linkedlist

    // removing a song from the playlist should also remove the image from the image list (possibly a doubly linked list also)
    // because when displaying a song using an index, we should use that same index for the song, otherwise it won't work


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Playlist App");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}