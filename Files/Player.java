
import sun.audio.*;
import java.io.*;
import java.util.*;

class Test {
  
  MusicManager manager;
  
  public Test(MusicManager man) {
    manager = man;
  }
  
  public void playIt() {
    try {
      InputStream in = new FileInputStream("Xenoblade_Music_-_Gaur_Plains.wav");
      AudioStream as = new AudioStream(in);
      AudioPlayer.player.start(as);
      
    } catch (Exception iox) {
      System.out.println("You dingus");
    }
    
  }
  
}
