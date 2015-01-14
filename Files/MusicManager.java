// CLASS MusicManager - HOLDS AND MANAGES ALL SONGS IN THE LIBRARY

import java.util.*;
import java.io.*;

class MusicManager {

	// Holds all the songs in the library
  	private ArrayList<Song> music;

/// *** ACCESSORS

  public Song[] getMusic() {
    Song[] temp = new Song[music.size()];
    for (int i = 0; i < temp.length; i++) {
      temp[i] = music.get(i);
    }
    return temp;
  }

/// *** CONSTRUCTORS

  	// Blank Constructor
  	public MusicManager() {
    music = new ArrayList<Song>();
  }

	// Automatically imports the library from the txt file
	public MusicManager(String filename) {
		music = new ArrayList<Song>();
		import(filename);
	}

/// *** METHODS

	// Searches by Track Name
	public Song[] searchByTitle(String name) {
		// An ArrayList is used in case there is more than one track with the same name
		ArrayList<Song> matches = new ArrayList<Song>();

		for (int i = 0; i < music.size(); i++) {
			if (music.get(i).getTitle().equals(name)) {
				matches.add(music.get(i));
			}
		}

		return matches.toArray(new Song[matches.size()]);
	}

	// Searches by Artist
	public Song[] searchByArtist(String artist) {
		// An ArrayList is used in case there is more than one track with the same name
		ArrayList<Song> matches = new ArrayList<Song>();

		for (int i = 0; i < music.size(); i++) {
			if (music.get(i).getArtist().equals(artist)) {
				matches.add(music.get(i));
			}
		}

		return matches.toArray(new Song[matches.size()]);
	}

	// Returns a Song array sorted by song title
	public Song[] sortByTitle() {
		Song[] sortedSongs = music.toArray(new Song[music.size()]);

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
		return sortedSongs();
	}

	// Returns a Song array sorted by song artist
	public Song[] sortByTitle() {
		Song[] sortedSongs = music.toArray(new Song[music.size()]);

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
		return sortedSongs();
	}

	// Returns a Song array sorted by song Album
	public Song[] sortByTitle() {
		Song[] sortedSongs = music.toArray(new Song[music.size()]);

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
		return sortedSongs();
	}

	// Adds a Song to the list
	public void addTrack(Song track) {
		music.add(track);
	}

	// Removes a Song from the list
	public void removeTrack(Song track) {
		music.remove(track);
	}

	// Clears the library
	public void clearList() {
		music.clear();
	}

	// Exports the current library to the specified file
	public void exportLibrary(String filename) {
	}

	// Imports a library from the specified txt file
	public void importLibrary(String filename) {
	}
}









