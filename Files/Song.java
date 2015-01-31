public class Song {

    private String title;
    private String artist;
    private String album;
    private String filepath;
    private String cover_img_path;

    /**
     * Constructor
     */
    public Song(String name, String art, String alb, String file, String cover) {
        title = name;
        artist = art;
        album = alb;
        filepath = file;
        cover_img_path = cover;
    }

    // cheap constructor for debugging
//     public Song(String path) {
//         filepath = path;
//     }

    /// *** ACCESSORS
    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getFilePath() {
        return filepath;
    }

    public String getCoverImg() {
        return cover_img_path;
    }

    /// *** MUTATORS
    public void setTitle(String value) {
        title = value;
    }

    public void setArtist(String value) {
        artist = value;
    }

    public void setAlbum(String value) {
        album = value;
    }

    public void setFilePath(String value) {
        filepath = value;
    }

    public void setCoverImg(String value) {
        cover_img_path = value;
    }

    public String toString(){
        return title;
    }

    //comparison
    public boolean equals(Song other){
        return this.filepath == other.filepath;
    }
}


