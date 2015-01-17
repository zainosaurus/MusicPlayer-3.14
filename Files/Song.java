public class Song {
    private String title;
    private String artist;
    private String album;
    private String filepath;
    public Song(String path) {
        filepath = path;
    }
    //this is to be used if reading from text file, since trying to extract info from
    //song file itself would take a while
    public Song(String title, String artist, String album, String path) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.filepath = path;
    }
    
    //gets and default methods
    public String getFilePath(){
        return filepath;
    }
    
    public String toString(){
        return title;
    }
    
    public boolean equals(Song other){
        return this.filepath == other.filepath;
    }
}
