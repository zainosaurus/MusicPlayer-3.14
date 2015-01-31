import java.io.*;
import javax.sound.sampled.*;
import java.util.Random;

public class Player {
    private MusicManager manager;
    private Clip clip;
    // used for shuffle method
    private Random songChooser;
    private int randomSong;
    private boolean[] played;

    /**
     * Constructor
     */
    public Player(MusicManager man){
        manager = man;
    }

    //accessor
    public MusicManager getManager(){
        return manager;
    }

    /**
     * Plays a single song
     */
    public void play(Song song) {
    	try {
    		File in = new File(song.getFilePath());
        	AudioInputStream as = AudioSystem.getAudioInputStream(in);
        	clip = AudioSystem.getClip();
        	clip.open(as);
        	clip.start();
    	}
    	catch (Exception iox) {
    		new NotificationWindow("Fatal Error", "Error playing file. Check for file or governmental corruption.");
            System.out.println(iox.toString());
    	}
    }

	/**
	 * Plays a song array straight down the list
	 */
    public void play(Song[] playlist){
        try {
            //use this method of playing audio files; sun.audio.* is dead
            for (int i=0;i<playlist.length;i++){
                //play each song in playlist
                File in = new File(playlist[i].getFilePath());
                AudioInputStream as = AudioSystem.getAudioInputStream(in);
                clip = AudioSystem.getClip();
                clip.open(as);
                clip.start();
                do {
                    Thread.sleep(500);
                } while (clip.isRunning());
            }
        }
        catch (Exception iox) {
            new NotificationWindow("Fatal Error", "Error playing file. Check for file or governmental corruption.");
            System.out.println(iox.toString());
        }
    }

	/**
	 * Shuffles a song array. doesn't repeat songs and stops once all songs have been played
	 */
	 public void shuffle(Song[] playlist){

	 	  // array keeps track of al the songs which have been played so that they don't repeat
	 	  played = new boolean[playlist.length];
	 	  for (int i = 0; i < played.length; i++) {
	 		   played[i] = false;
	 	  }

	 	  songChooser = new Random(playlist.length);

        try {
            for (int i=0;i<playlist.length;i++){
            	// choosing a random index which hasn't been played
            	do {
						randomSong = songChooser.nextInt();
            	} while (!played[randomSong]);

               //play each song in playlist
               File in = new File(playlist[randomSong].getFilePath());
               AudioInputStream as = AudioSystem.getAudioInputStream(in);
               clip = AudioSystem.getClip();
               clip.open(as);
               clip.start();
            	// set status to played
            	played[randomSong] = true;

                // prevents all songs from starting at once
                do {
                    Thread.sleep(500);
                } while (clip.isRunning());
            }
        }
        catch (Exception iox) {
            new NotificationWindow("Fatal Error", "Error playing file. Check for file or governmental corruption.");
            System.out.println(iox.toString());
        }
    }

    /**
     * Resumes playback after being paused
     */
    public void continuePlaying() {
    	try {
    		clip.start();
    	}
    	catch(NullPointerException ex) {
			// call play method for the song
    	}
    }


	/**
	 * Pauses the track
	 */
	public void pause() {
		try {
			clip.stop();
		}
		catch (NullPointerException ex){}
	}

	/**
	 * Stops the currently playing track
	 */
    public void stop(){
		try {
        	clip.stop();
        	reset();
        }
        catch (NullPointerException ex) {}
    }

    /**
     * Resets the clip so that the current file it holds is discarded
     */
    public void reset() {
    	clip = null;
    }
}
