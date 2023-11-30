package com.example.playlistapp;

import java.util.LinkedList;

public class Playlist {

    LinkedList<Song> songs;
    String name;

    public Playlist(String name){
        songs = new LinkedList<>();
        this.name = name;
    }

    public void addSong(Song song)
    {
        songs.add(song);
    }

    public void removeSong(Song song){
        if (songs.contains(song)){
            songs.pop();
        }
    }

    public void displayPlaylist(){
        for(Song song: songs){
            System.out.println(song.title);
        }
    }

    public String playSong(int songNumber){
        return songs.get(songNumber).previewUrl;
    }

    public Song getCurrentSong(int songNumber){
        return songs.get(songNumber-1);
    }

    public String getCurrentImage(int songNumber){
        return songs.get(songNumber-1).imageUrl;
    }

    public String getPrevImage(int songNumber){
        songNumber = songNumber - 1;
        if (songNumber > songs.size()) {
            songNumber = 1;
        } else if (songNumber < 1) {
            songNumber = songs.size();
        }
        return songs.get(songNumber-1).imageUrl;
    }
    public String getNextImage(int songNumber){
        songNumber = songNumber + 1;
        if (songNumber > songs.size()) {
            songNumber = 1;
        } else if (songNumber < 1) {
            songNumber = songs.size();
        }
        return songs.get(songNumber-1).imageUrl;
    }

    public String getCurrentLabel(int songNumber){
        return songs.get(songNumber-1).title;
    }

    public String getPrevLabel(int songNumber){
        songNumber = songNumber - 1;
        if (songNumber > songs.size()) {
            songNumber = 1;
        } else if (songNumber < 1) {
            songNumber = songs.size();
        }
        return songs.get(songNumber-1).title;
    }
    public String getNextLabel(int songNumber){
        songNumber = songNumber + 1;
        if (songNumber > songs.size()) {
            songNumber = 1;
        } else if (songNumber < 1) {
            songNumber = songs.size();
        }
        return songs.get(songNumber-1).title;
    }

    public String getCurrentArtist(int songNumber){
        return songs.get(songNumber-1).artist;
    }

    public String getPrevArtist(int songNumber){
        songNumber = songNumber - 1;
        if (songNumber > songs.size()) {
            songNumber = 1;
        } else if (songNumber < 1) {
            songNumber = songs.size();
        }
        return songs.get(songNumber-1).artist;
    }
    public String getNextArtist(int songNumber){
        songNumber = songNumber + 1;
        if (songNumber > songs.size()) {
            songNumber = 1;
        } else if (songNumber < 1) {
            songNumber = songs.size();
        }
        return songs.get(songNumber-1).artist;
    }

    public String getPlaylistName(){
        return this.name;
    }

    public int getSize(){
        return songs.size();
    }
}

