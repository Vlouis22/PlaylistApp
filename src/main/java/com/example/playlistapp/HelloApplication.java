package com.example.playlistapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

import okhttp3.*;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        String css = Objects.requireNonNull(this.getClass().getResource("application.css")).toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.setTitle("Playlist App");
        stage.show();


        scene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                Controller controller = loader.getController();
                controller.addSong();
                controller.addPlaylist();
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}