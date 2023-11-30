package com.example.playlistapp;

public class Song {
    String title;
    String previewUrl;
    String imageUrl;
    String artist;

    public Song(String title, String previewUrl, String imageUrl, String artist){
        this.title = title;
        this.previewUrl = previewUrl;
        this.imageUrl = imageUrl;
        this.artist = artist;
    }
}
