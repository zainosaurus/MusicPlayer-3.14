
import java.util.*;

class MusicManager {
  
  private ArrayList music;
  
  public MusicManager() {
    music = new ArrayList();
  }
  
  //Accessors
  public Song[] getMusic() {
    Song[] temp = new Song[music.size()];
    
    for (int i = 0; i < temp.length; i++) {
      temp[i] = (Song)music.get(i);
    }
    
    return temp;
  }
  
  
  
  
  
  
  
  public void load() {
    
  }
  
  public void save() {
    
  }
  
}
