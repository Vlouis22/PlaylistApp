package com.example.playlistapp;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPlaylist {

    Song song1 = new Song("All of me", "allofmesong.mp3", "song1image.png", "John Legend");
    Song song2 = new Song("In My Blood", "inmyblood.mp3", "song2image.png", "Shawn Mendes");
    Song song3 = new Song("One Call Away", "onecallaway.mp3", "song3image.png", "Charlie Puth");
    Song song4 = new Song("Thinking Out Loud", "thinkingoutloud.mp3", "song4image.png", "Ed Sheeran");
    Song song5 = new Song("Mask Off", "maskoff.mp3", "song5image.png", "Future");

    @Test
    public void testAddSong01(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song1);
        favoritePlaylist.addSong(song2);
        assertEquals(2, favoritePlaylist.getSize());
    }

    @Test
    public void testAddSong02(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song1);
        favoritePlaylist.addSong(song2);
        favoritePlaylist.addSong(song3);
        assertEquals(3, favoritePlaylist.getSize());
    }

    @Test
    public void testRemoveSong01(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song1);
        favoritePlaylist.addSong(song2);
        favoritePlaylist.removeSong(song3);
        assertEquals(2, favoritePlaylist.getSize());
    }

    @Test
    public void testRemoveSong02(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song1);
        favoritePlaylist.addSong(song2);
        favoritePlaylist.removeSong(song3);
        favoritePlaylist.removeSong(song4);
        favoritePlaylist.removeSong(song5);
        assertEquals(2, favoritePlaylist.getSize());
    }

    @Test
    public void testplaySong01(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song1);
        favoritePlaylist.addSong(song2);
        favoritePlaylist.addSong(song3);
        assertEquals("onecallaway.mp3", favoritePlaylist.playSong(2));
    }

    @Test
    public void testplaySong02(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song1);
        favoritePlaylist.addSong(song3);
        favoritePlaylist.addSong(song5);
        assertEquals("allofmesong.mp3", favoritePlaylist.playSong(0));
    }

    @Test
    public void testgetCurrentSong01(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song1);
        favoritePlaylist.addSong(song2);
        favoritePlaylist.addSong(song3);
        assertEquals(song3, favoritePlaylist.getCurrentSong(3));
    }

    @Test
    public void testgetCurrentSong02(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song1);
        favoritePlaylist.addSong(song2);
        favoritePlaylist.addSong(song3);
        favoritePlaylist.addSong(song4);
        favoritePlaylist.addSong(song5);
        assertEquals(song2, favoritePlaylist.getCurrentSong(2));
    }

    @Test
    public void testgetCurrentImage01(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song2);
        favoritePlaylist.addSong(song3);
        favoritePlaylist.addSong(song5);
        assertEquals("song5image.png", favoritePlaylist.getCurrentImage(3));
    }

    @Test
    public void testgetCurrentImage02(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song4);
        favoritePlaylist.addSong(song5);
        favoritePlaylist.addSong(song4);
        assertEquals("song4image.png", favoritePlaylist.getCurrentImage(1));
    }

    @Test
    public void testgetPrevImage01(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song2);
        favoritePlaylist.addSong(song3);
        favoritePlaylist.addSong(song5);
        assertEquals("song3image.png", favoritePlaylist.getPrevImage(3));
    }

    @Test
    public void testgetPrevImage02(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song2);
        favoritePlaylist.addSong(song3);
        favoritePlaylist.addSong(song5);
        favoritePlaylist.addSong(song4);
        assertEquals("song2image.png", favoritePlaylist.getPrevImage(2));
    }

    @Test
    public void testgetNextImage01(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song2);
        favoritePlaylist.addSong(song3);
        favoritePlaylist.addSong(song5);
        favoritePlaylist.addSong(song4);
        assertEquals("song2image.png", favoritePlaylist.getNextImage(10));
    }

    @Test
    public void testgetNextImage02(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song2);
        favoritePlaylist.addSong(song3);
        favoritePlaylist.addSong(song5);
        favoritePlaylist.addSong(song4);
        assertEquals("song4image.png", favoritePlaylist.getNextImage(-10));
    }

    @Test
    public void testgetCurrentLabel01(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song5);
        favoritePlaylist.addSong(song4);
        favoritePlaylist.addSong(song4);
        assertEquals("Mask Off", favoritePlaylist.getCurrentLabel(1));
    }

    @Test
    public void testgetCurrentLabel02(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song3);
        favoritePlaylist.addSong(song5);
        favoritePlaylist.addSong(song5);
        favoritePlaylist.addSong(song4);
        assertEquals("Thinking Out Loud", favoritePlaylist.getCurrentLabel(10));
    }
    @Test
    public void testgetPrevLabel01(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song3);
        favoritePlaylist.addSong(song5);
        favoritePlaylist.addSong(song4);
        assertEquals("Thinking Out Loud", favoritePlaylist.getPrevLabel(-10000000));
    }

    @Test
    public void testgetPrevLabel02(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song3);
        favoritePlaylist.addSong(song5);
        favoritePlaylist.addSong(song1);
        favoritePlaylist.addSong(song4);
        assertEquals("One Call Away", favoritePlaylist.getPrevLabel(10000000));
    }

    @Test
    public void testgetNextLabel01(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song3);
        favoritePlaylist.addSong(song5);
        favoritePlaylist.addSong(song4);
        assertEquals("Mask Off", favoritePlaylist.getNextLabel(1));
    }

    @Test
    public void testgetNextLabel02(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song3);
        favoritePlaylist.addSong(song5);
        favoritePlaylist.addSong(song4);
        assertEquals("Thinking Out Loud", favoritePlaylist.getNextLabel(2));
    }

    @Test
    public void testgetCurrentArtist01(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song2);
        favoritePlaylist.addSong(song3);
        favoritePlaylist.addSong(song5);
        favoritePlaylist.addSong(song4);
        assertEquals("Future", favoritePlaylist.getCurrentArtist(3));
    }

    @Test
    public void testgetCurrentArtist02(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song2);
        favoritePlaylist.addSong(song3);
        favoritePlaylist.addSong(song5);
        favoritePlaylist.addSong(song5);
        assertEquals("Future", favoritePlaylist.getCurrentArtist(4));
    }

    @Test
    public void testgetPrevArtist01(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song2);
        favoritePlaylist.addSong(song3);
        favoritePlaylist.addSong(song5);
        favoritePlaylist.addSong(song5);
        assertEquals("Future", favoritePlaylist.getPrevArtist(1));
    }

    @Test
    public void testgetPrevArtist02(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song1);
        favoritePlaylist.addSong(song2);
        favoritePlaylist.addSong(song5);
        favoritePlaylist.addSong(song2);
        assertEquals("Future", favoritePlaylist.getPrevArtist(4));
    }

    @Test
    public void testgetNextArtist01(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song1);
        favoritePlaylist.addSong(song2);
        favoritePlaylist.addSong(song1);
        favoritePlaylist.addSong(song2);
        assertEquals("John Legend", favoritePlaylist.getNextArtist(4));
    }

    @Test
    public void testgetNextArtist02(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.removeSong(song1);
        favoritePlaylist.addSong(song2);
        favoritePlaylist.addSong(song1);
        favoritePlaylist.addSong(song1);
        assertEquals("Shawn Mendes", favoritePlaylist.getNextArtist(4));
    }

    @Test
    public void testgetPlaylistName01(){
        Playlist favoritePlaylist = new Playlist("favorite Playlist");
        assertEquals("favorite Playlist", favoritePlaylist.getPlaylistName());
    }

    @Test
    public void testgetPlaylistName02(){
        Playlist favoritePlaylist = new Playlist("Default Playlist");
        assertEquals("Default Playlist", favoritePlaylist.getPlaylistName());
    }

    @Test
    public void testgetSize01(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song1);
        favoritePlaylist.addSong(song2);
        favoritePlaylist.removeSong(song3);
        favoritePlaylist.removeSong(song4);
        favoritePlaylist.removeSong(song5);
        favoritePlaylist.addSong(song1);
        favoritePlaylist.addSong(song2);
        favoritePlaylist.removeSong(song3);
        favoritePlaylist.removeSong(song4);
        favoritePlaylist.removeSong(song5);
        assertEquals(4, favoritePlaylist.getSize());
    }

    @Test
    public void testgetSize02(){
        Playlist favoritePlaylist = new Playlist("favoritePlaylist");
        favoritePlaylist.addSong(song1);
        favoritePlaylist.addSong(song2);
        favoritePlaylist.addSong(song3);
        favoritePlaylist.removeSong(song2);
        favoritePlaylist.removeSong(song2);
        favoritePlaylist.removeSong(song3);
        assertEquals(1, favoritePlaylist.getSize());
    }
}