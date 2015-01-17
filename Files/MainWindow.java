import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MainWindow extends JFrame implements ActionListener {
    private JPanel panel = new JPanel();
    private Player player;
    public MainWindow(){
        /*frame = new JFrame("MusicPlayer v3.14");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);*/
        this("");
    }
    public MainWindow(String title){
        super(title);
        loadPlayer();
    }
    
    private void loadPlayer(){
        player = new Player(new MusicManager());
        
        //for debugging purposes
        player.getManager().addTrack(new Song("Kappa.wav"));
        player.getManager().addTrack(new Song("Song Name.wav"));
        player.play();
        player.getManager().removeTrack(new Song("Song Name.wav"));
        player.play();
    }
    
    /**
    * Action Performed method that disposes of the notification window
    */
    public void actionPerformed(ActionEvent evt) {
        dispose();
    }
}