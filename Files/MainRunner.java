/*
 * The main entry point of the application.
 */
import javax.swing.*;

public class MainRunner {
    public static void main(String[] args) {
    	// Don't touch
    	try {
            // Set System L&F
        UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
    	}
    	catch (Exception e) {}

        MainWindow wind = new MainWindow("MusicPlayer-3.14", new MusicManager());
    }
}
