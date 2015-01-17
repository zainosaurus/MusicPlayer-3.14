import java.util.*;
import java.io.*;

public class MusicManager {
    private List<Song> playlist;
    private String playFileLoc;
    public MusicManager() {
        playlist = new ArrayList<Song>();
    }
    
    public MusicManager(String txt){
        playlist = new ArrayList<Song>();
        playFileLoc = txt;
        load(txt);
    }
    
    //Accessors
    public List<Song> getPlaylist() {
        return playlist;
    }
    
    //manager loading a playlist
    public void load(String txt) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(txt));
            int numSongs = Integer.parseInt(br.readLine().trim());
            for (int i=0;i<numSongs;i++){
                String songName = br.readLine().trim();
                if (!playlist.contains(songName)){
                    playlist.add(new Song(songName));
                }
            }
        }
        catch (IOException e){
            System.out.println("Error loading some file. X: Not all of playlist loaded");
        }
        catch (NumberFormatException e){
            System.out.println("File is not properly formatted. Check relative humidity.");
        }
    }
    
    //adding a song
    public void addTrack(Song song) {
        boolean alreadyAdded = false;
        for (int i=0;i<playlist.size()&&!alreadyAdded;i++){
            if (song.equals(playlist.get(i))){
                alreadyAdded = true;
            }
        }
        if (!alreadyAdded)
            playlist.add(song);
    }
    
    public void removeTrack(Song song) {
        for (int i=0;i<playlist.size();i++){
            if (song.equals(playlist.get(i))){
                playlist.remove(i);
            }
        }
    }
    
    //assuming that playlist is saved in a textfile
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
    
}
