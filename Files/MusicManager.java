/**
 * MusicManager Class
 * Manages the playlist and perform front-end actions for the user
 */

import java.util.*;
import java.io.*;

public class MusicManager {
    private ArrayList<Song> playlist;
//     private Player player;
	 private int currentSongIndex;

	 /**
	  * Constructor
	  */
    public MusicManager() {
        playlist = new ArrayList<Song>();
        // player = new Player(this);
    }


    //Accessors
    public ArrayList<Song> getPlaylist() {
        return playlist;
    }

    /**
     * Loading the playlist to use from a txt
     */
    public void load(String txt) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(txt));
            // required vars
            String title, artist, album, filePath, cover;

            // looping till the songname is null (end of file)
            while ((title = br.readLine()) != null) {
            	// init song specs
            	artist = br.readLine();
            	album = br.readLine();
            	filePath = br.readLine();
            	cover = br.readLine();

            	// add song
            	addTrack(new Song(title, artist, album, filePath, cover));
            	currentSongIndex = 0;
				// blank line
            	br.readLine();
            }
        }
        catch (IOException e){
            new NotificationWindow("Fatal Error", "Error loading some file. X: Not all of playlist loaded");
        }
        catch (NumberFormatException e){
            new NotificationWindow("Fatal Error", "File is not properly formatted. Check relative humidity.");
        }
    }

    /**
     * Adding a song
     */
    public void addTrack(Song song) {
	    playlist.add(song);
    }

    /**
     * removing a song
     */
    public void removeTrack(Song song) {
		playlist.remove(song);
    }

    /**
     * Saving playlist
     */
    public void save() {
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter("play.list"));
            bw.write((int)playlist.size());
            bw.newLine();
            for (int i=0;i<playlist.size();i++){
                bw.write(playlist.get(i).toString());
                bw.newLine();
            }
        }
        catch (IOException e){
            System.out.println("Error: Playlist was not saved. Sorry.");
        }
    }

    /**
     * Searches by track name
     */
    public Song[] searchByTitle(String name) {
        // An List is used in case there is more than one track with the same name
        ArrayList<Song> matches = new ArrayList<Song>();

        for (int i = 0; i < playlist.size(); i++) {
            if (playlist.get(i).getTitle().equals(name)) {
                matches.add(playlist.get(i));
            }
        }

        return matches.toArray(new Song[matches.size()]);
    }

    /**
     * Searches by artist
     */
    public Song[] searchByArtist(String artist) {
        // An ArrayList is used in case there is more than one track with the same name
        ArrayList<Song> matches = new ArrayList<Song>();

        for (int i = 0; i < playlist.size(); i++) {
            if (playlist.get(i).getArtist().equals(artist)) {
                matches.add(playlist.get(i));
            }
        }
        return matches.toArray(new Song[matches.size()]);
    }

    /**
     * Returns a song array sorted by song title
     */
    public Song[] sortByTitle() {
        Song[] sortedSongs = playlist.toArray(new Song[playlist.size()]);

        // Bubble sort
        boolean sorted = false;
        while(!sorted) {
            sorted = true;
            for (int i = 0; i < sortedSongs.length - 1; i++) {
                if (sortedSongs[i].getTitle().compareTo(sortedSongs[i+1].getTitle()) > 0) {
                    // switch
                    Song temp = sortedSongs[i];
                    sortedSongs[i] = sortedSongs[i + 1];
                    sortedSongs[i + 1] = temp;
                    sorted = false;
                }
            }
        }
        return sortedSongs;
    }

    /**
     * Returns a Song array sorted by song artist
     */
    public Song[] sortByArtist() {
        Song[] sortedSongs = playlist.toArray(new Song[playlist.size()]);

        // Bubble sort
        boolean sorted = false;
        while(!sorted) {
            sorted = true;
            for (int i = 0; i < sortedSongs.length - 1; i++) {
                if (sortedSongs[i].getArtist().compareTo(sortedSongs[i+1].getArtist()) > 0) {
                    // switch
                    Song temp = sortedSongs[i];
                    sortedSongs[i] = sortedSongs[i + 1];
                    sortedSongs[i + 1] = temp;
                    sorted = false;
                }
            }
        }
        return sortedSongs;
    }

    /**
     * Returns a Song array sorted by song Album
     */
    public Song[] sortByAlbum() {
        Song[] sortedSongs = playlist.toArray(new Song[playlist.size()]);

        // Bubble sort
        boolean sorted = false;
        while(!sorted) {
            sorted = true;
            for (int i = 0; i < sortedSongs.length - 1; i++) {
                if (sortedSongs[i].getAlbum().compareTo(sortedSongs[i+1].getAlbum()) > 0) {
                    // switch
                    Song temp = sortedSongs[i];
                    sortedSongs[i] = sortedSongs[i + 1];
                    sortedSongs[i + 1] = temp;
                    sorted = false;
                }
            }
        }
        return sortedSongs;
    }

    /**
     * Clears the library
     */
    public void clearList() {
        playlist.clear();
    }

    /**
     * Exports the current library to the specified file
     */
    public void exportLibrary(String filename) {
    }

    /**
     * Imports a library from the specified txt file
     */
    public void importLibrary(String filename) {
    }
}
