/**
 * Plays a song in a separate thread
 */
import java.io.*;
import javax.sound.sampled.*;
import java.util.*;

public  class Play implements Runnable {
	private Song currentSong = null;

	public  void setSong(Song song) {
		currentSong = song;
	}

	public void run() {

	}
}