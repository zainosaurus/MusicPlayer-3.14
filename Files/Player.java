/**
 * Player class
 * Manages the playlist (back-end) and plays the music as requested by the MainWindow class
 */
 
import java.io.*;
import javax.sound.sampled.*;
import java.util.*;

public class Player {
    private ArrayList<Song> originalPList;
    private ArrayList<Song> playlist;
    private Clip clip;
    // used for shuffle method
    // instead of doing this consider making a shuffle() method which rearranges the plist - this should be in the musicmanager i think
    private Random songChooser;
    private int randomSong;
    private boolean[] played;
    // a thread which plays all the songs in the playlist
    private Thread playThread, resumeThread;
    private boolean finishedPlaying = true;
    
    private MainWindow parent; //just for some features
    
    /**
    * Constructor
    */
    public Player(){
        originalPList = new ArrayList<Song>();
        playlist = new ArrayList<Song>();
        initializePlayThread();
    }
    
    public Player(MainWindow parent){
        this();
        this.parent = parent;
    }
    
    // initilizes the thread (this is ugly)
    private void initializePlayThread(){
        playThread = new Thread(){
            public void run(){
                finishedPlaying = false;
                try {
                    //play each song in playlist
                    for (Song s : playlist) {
                        File in = new File(s.getFilePath());
                        AudioInputStream as = AudioSystem.getAudioInputStream(in);
                        clip = AudioSystem.getClip();
                        clip.open(as);
                        initializeResumeThread();
                        resumeThread.start();
                    }
                }
                //these cases occur when stop or play are (re)requested anyway
                catch (NullPointerException npe){
                }
                catch (Exception iox) {
                    new NotificationWindow("Fatal Error", "Error playing file.");
                    iox.printStackTrace();
                }
            }
        };
    }
    
    private void initializeResumeThread(){
        resumeThread = new Thread(){
            public void run() {
                try{
                    clip.start();
                    do {
                        Thread.sleep(500);
                        
                        //if finished, force break out after setting UI button's text
                        if (clip.getMicrosecondPosition() >= clip.getMicrosecondLength())
                        {
                            finishedPlaying = true;
                            parent.setPlayPauseText(parent.PLAY_TEXT);
                            break;
                        }
                    } while (clip.isRunning());
                }
                catch (InterruptedException|NullPointerException e){
                    //e.printStackTrace();
                }
            }
        };
    }
    
    /**
    * Sets the playlist (list of songs)
    */
    public void setPlaylist(ArrayList<Song> plist) {
        originalPList = plist;
        playlist = plist;
    }
    
    /**
    * Sets the playlist (Single song)
    */
    public void setPlaylist(Song sng) {
        playlist.clear();
        playlist.add(sng);
    }
    
    
    /**
    * This is where the class performs its sworn duty and runs(). aka plays the playlist
    */
    public void run() {
        if (clip!=null && !finishedPlaying){
            initializeResumeThread();
            resumeThread.start();
        }
        else{
            initializePlayThread();
            playThread.start();
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
            resumeThread.interrupt();
            playThread.interrupt();
            clip.stop();
            reset();
        }
        catch (NullPointerException ex) {
        }
    }
    
    /**
    * Resets the clip so that the current file it holds is discarded
    */
    public void reset() {
        clip = null;
    }
    
    /**
    * Plays a single song
    */
    public void play(Song song) {
        playlist.clear();
        playlist.add(song);
        run();
    }
    
    /**
    * Plays a bunch of songs
    */
    public void play(Song[] plist){
        playlist.clear();
        Collections.addAll(playlist, plist);
        run();
    }
    
    // /**
    //  * Shuffles a song array. doesn't repeat songs and stops once all songs have been played
    //  */
    //  public void shuffle(Song[] playlist){
        
        //        // array keeps track of al the songs which have been played so that they don't repeat
        //        played = new boolean[playlist.length];
        //        for (int i = 0; i < played.length; i++) {
            //             played[i] = false;
        //        }
        
        //        songChooser = new Random(playlist.length);
        
        //        try {
            //            for (int i=0;i<playlist.length;i++){
                //             // choosing a random index which hasn't been played
                //             do {
                    //                  randomSong = songChooser.nextInt();
                //             } while (!played[randomSong]);
                
                //               //play each song in playlist
                //               File in = new File(playlist[randomSong].getFilePath());
                //               AudioInputStream as = AudioSystem.getAudioInputStream(in);
                //               clip = AudioSystem.getClip();
                //               clip.open(as);
                //               clip.start();
                //             // set status to played
                //             played[randomSong] = true;
                
                //                // prevents all songs from starting at once
                //                do {
                    //                    Thread.sleep(500);
                //                } while (clip.isRunning());
            //            }
        //        }
        //        catch (Exception iox) {
            //            new NotificationWindow("Fatal Error", "Error playing file. Check for file or governmental corruption.");
            //            System.out.println(iox.toString());
        //        }
    //    }
    
    
}
