module com.example.playlistapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires okhttp3;
    requires javafx.media;
    requires com.fasterxml.jackson.databind;

    opens com.example.playlistapp to javafx.fxml;
    exports com.example.playlistapp;
}