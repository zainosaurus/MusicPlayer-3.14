import java.io.*;
import javax.sound.sampled.*;

public class Player {
    private MusicManager manager;
    private Clip clip;
    public Player(MusicManager man){
        manager = man;
    }
    public Player(){
    }
    
    //accessor
    public MusicManager getManager(){
        return manager;
    }
    
    public void play(){
        try {
            //use this method of playing audio files; sun.audio.* is dead
            for (int i=0;i<manager.getPlaylist().size();i++){
                //play each song in playlist
                File in = new File(manager.getPlaylist().get(i).getFilePath());
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
            System.out.println("Error playing file. Check for file or governmental corruption.");
            System.out.println(iox.toString());
        }
    }
    
    public void stop(){
        clip.stop();
    }
}
