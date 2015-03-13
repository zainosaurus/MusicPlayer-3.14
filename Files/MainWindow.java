import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class MainWindow extends JFrame implements ActionListener {
    private Player player;
    private MusicManager manager;
    private ArrayList<Song> playlist;

    // variables for search bar panel
    private JTextField searchBox;
    private JButton searchButton;
    private JComboBox searchOptionSelector;
    private final String[] searchOptions = {"Title", "Artist", "Album"};

    // variables for song list area
    private JScrollPane songList;
    private JList<String> songs;
    private String[] songNames = {"[--------Uninitialized--------]"};

    // variables for menu
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem loadPlaylistOption;
    private JMenuItem savePlaylistOption;

    // variables for songActionArea
    private JButton playPause;
    private JButton stop;
    private JButton forward;
    private JButton rewind;
    private JButton info;
    
    //private JCheckBox shuffle;
    //private JCheckBox loopMode;
    private JButton shuffle;
    private JButton loopMode;
    
    // variables for newer features
    private JLabel imageDisplay;
    private JSlider volume;
    private JSlider songProgress;
    
    /**
     * Constructor
     */
    public MainWindow(String title, MusicManager man){
        super(title);
        manager = man;
        player = new Player(this);
        
        //ugly command
        //updateSongs(manager.getPlaylist());
        
        // elements must be drawn in order:
        // but first, init properties
        initMenu();
        initSearchArea();
        initSongList();
        initSongActionArea();
        enablePlayer();
        
        // frame specs
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setMinimumSize(new Dimension(450, 350));
        setLocation(300, 250);
        setResizable(false); //for now
        
        // draw and render
        setLayout(new GridBagLayout());
        drawItems(this.getContentPane());
        setVisible(true);
    }

    /**
     * Song action area (play/stop button etc)
     */
    
    //source: http://en.wikipedia.org/wiki/Geometric_Shapes#Compact_chart
    //http://en.wikipedia.org/wiki/Block_Elements#Compact_table
    private final String RIGHT_TRIANGLE = "\u25B6", LEFT_TRIANGLE = "\u25C0", SQUARE = "\u25A0", RECTANGLE = "\u275A";
    public final String PLAY_TEXT = RIGHT_TRIANGLE + " ";
    public final String PAUSE_TEXT = RECTANGLE+RECTANGLE;
    public final String STOP_TEXT = SQUARE;
    public final String FORWARD_TEXT = RIGHT_TRIANGLE+RIGHT_TRIANGLE;
    public final String REWIND_TEXT = LEFT_TRIANGLE+LEFT_TRIANGLE;
    
    public void setPlayPauseText(String s){
        if (playPause != null){
            playPause.setText(s);
        }
    }
    
    private void initSongActionArea() {
        // initialize
        volume = new JSlider(0, 150, 100);
        songProgress = new JSlider();
        imageDisplay = new JLabel();
        
        playPause = new JButton(PLAY_TEXT);
        stop = new JButton(STOP_TEXT);
        forward = new JButton(FORWARD_TEXT);
        rewind = new JButton(REWIND_TEXT);
        info = new JButton("Info");
        shuffle = new JButton("[S]F");
        //loopMode = new JButton("Play current only");
        loopMode = new JButton("[L]0");

        // more properties
        playPause.setFont(new Font("Arial Unicode", Font.BOLD, 20));
        stop.setFont(new Font("Arial Unicode", Font.PLAIN, 15));
        forward.setFont(new Font("Arial Unicode", Font.PLAIN, 20));
        rewind.setFont(new Font("Arial Unicode", Font.PLAIN, 20));
        
        // action commands
        playPause.setActionCommand("togglePlay");
        stop.setActionCommand("stop");
        info.setActionCommand("showInfo");
        forward.setActionCommand("forward");
        rewind.setActionCommand("rewind");
        shuffle.setActionCommand("toggleShuffle");
        loopMode.setActionCommand("toggleLoopMode");
        //loopMode.setActionCommand("togglePlayMode");
        
        // action listeners
        playPause.addActionListener(this);
        stop.addActionListener(this);
        info.addActionListener(this);
        forward.addActionListener(this);
        rewind.addActionListener(this);
        shuffle.addActionListener(this);
        //loopMode.addActionListener(this);
        loopMode.addActionListener(this);
        
        // disabling features
        playPause.setEnabled(false);
        stop.setEnabled(false);
        forward.setEnabled(false);
        rewind.setEnabled(false);
        info.setEnabled(false);
        shuffle.setEnabled(false);
        //loopMode.setEnabled(false);
        loopMode.setEnabled(false);
        songProgress.setEnabled(false);
    }

    /**
     * Menu
     */
    private void initMenu() {
        // initialize
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        
        loadPlaylistOption = new JMenuItem("Load Playlist");
        savePlaylistOption = new JMenuItem("Save Playlist");
        loadPlaylistOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        // action commands
        loadPlaylistOption.setActionCommand("load");
        savePlaylistOption.setActionCommand("save");

        // action listeners
        loadPlaylistOption.addActionListener(this);
        savePlaylistOption.addActionListener(this);

        // adding elements
        fileMenu.add(loadPlaylistOption);
        fileMenu.add(savePlaylistOption);
        menuBar.add(fileMenu);
        
        setJMenuBar(menuBar);
    }

    /**
     * Search area
     */
    private void initSearchArea() {
        // initialize
        searchBox = new JTextField(15);
        searchOptionSelector = new JComboBox<String>(searchOptions);
        searchButton = new JButton("Search");

        // spec
        searchOptionSelector.setSelectedIndex(0);

        // action commands
        searchBox.setActionCommand("search");
        searchButton.setActionCommand("search");

        // action listeners
        searchBox.addActionListener(this);
        searchButton.addActionListener(this);
    }

    /**
     * Song list
     */
    private void initSongList() {
        // initialize
        songs = new JList<String>(songNames);
        songList = new JScrollPane(songs);

        // spec
        songs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    private void enablePlayer() {
        //start enabling features
        if (songs.getModel().getSize() > 0){
            songs.setSelectedIndex(0);
            loopMode.setEnabled(true);
            playPause.setEnabled(true);
            
            if (songs.getModel().getSize() > 1){
                shuffle.setEnabled(true);
                forward.setEnabled(true);
                rewind.setEnabled(true);
            }
        }
    }
    
    // draw the items onto a panel
    private void drawItems(Container panel) {
        GridBagConstraints c = new GridBagConstraints();
        // add search toolbar
        c.fill = GridBagConstraints.BOTH;
        c.ipadx = c.ipady = 0;
        
        //add searchbar
        c.weighty = 0.0;
        c.weightx = 0.0;
        c.gridx = 0; c.gridy = 0; c.gridwidth = 10;
        panel.add(searchBox, c);
        
        //c.weightx = 0.3;
        c.gridx = 10; c.gridy = 0; c.gridwidth = 3;
        panel.add(searchOptionSelector, c);
        
        //c.weightx = 0.3;
        c.gridx = 13; c.gridy = 0; c.gridwidth = 3;
        panel.add(searchButton, c);
        
        // add songList and coverArtDisplayer
        c.weighty = 1.0;
        //c.weightx = 0.6;
        c.gridx = 0; c.gridy = 1; c.gridwidth = 10; c.gridheight = 6;
        panel.add(songList, c);
        
        //c.weightx = 0.3;
        c.gridx = 10; c.gridy = 1; c.gridwidth = 6; c.gridheight = 6;
        panel.add(imageDisplay, c);
        
        //add slider
        c.weightx = 1;
        c.weighty = 0.1;
        c.gridx = 0; c.gridy = 7; c.gridwidth = 16; c.gridheight = 1;
        panel.add(songProgress, c);
        
        //add last row
        c.weightx = 0.0;
        c.weighty = 0.0;
        c.ipady = 15;
        c.gridx = 0; c.gridy = 8; c.gridwidth = 2; c.gridheight = 1;
        panel.add(shuffle, c);
        c.gridx = 2; c.gridy = 8; c.gridwidth = 2;
        panel.add(loopMode, c);
        c.gridx = 4; c.gridy = 8; c.gridwidth = 2;
        panel.add(rewind, c);
        c.gridx = 6; c.gridy = 8; c.gridwidth = 2;
        panel.add(playPause, c);
        c.gridx = 8; c.gridy = 8; c.gridwidth = 2;
        panel.add(stop, c);
        c.gridx = 10; c.gridy = 8; c.gridwidth = 2;
        panel.add(forward, c);
        c.gridx = 12; c.gridy = 8; c.gridwidth = 3;
        c.ipadx = 20;
        panel.add(volume, c);
        c.gridx = 15; c.gridy = 8; c.gridwidth = 1;
        panel.add(info, c);
    }

    /**
     * Updates the playlist and list of song names
     */
    private void updateSongs(ArrayList<Song> list) {
        playlist = list;
        try {
            songNames = new String[list.size()];

            for (int i = 0; i < list.size(); i++) {
                songNames[i] = list.get(i).getTitle();
            }

            songs.setListData(songNames);
        }
        catch(NullPointerException noArray) {
            new NotificationWindow("Note", "You need to load a playlist before being able to play songs.");
        }
    }


    /**
     * Updates the list of song names and playlist (with only one song)
     */
    private void updateSongs(Song song) {
        // playlist
        playlist = new ArrayList<Song>();
        playlist.add(song);

        // song names
        songNames = new String[1];
        songNames[0] = song.getTitle();

        songs.setListData(songNames);
    }
    
    /**
     * Action Performed method
     */
    public void actionPerformed(ActionEvent evt) {
        String command = evt.getActionCommand();
        int selectedIndex;
        
        // Search
        if (command.equals("search")) {
            if (searchOptionSelector.getSelectedIndex() == 0) {
                ArrayList<String> results = new ArrayList<String>();
                // search and update the list with the results.
                // if the search field is blank, reset the list with all songs by updating songs from MusicManager
            }
        }
        // Load
        else if (command.equals("load")) {
            manager.load("songlist.txt");
            updateSongs(manager.getPlaylist());
            enablePlayer();
        }
        // Save
        else if (command.equals("save")) {
            manager.save();
        }
        // Play / Pause
        // if not playing then stop should be disabled 
        // if playing then pause and stop should be enabled
        // if pause then play should show
        else if (command.equals("togglePlay")) {
            //if text is play button
            if (playPause.getText().equals(PLAY_TEXT)){
                //set text
                playPause.setText(PAUSE_TEXT);
                // play all?
                if (loopMode.isSelected()) {
                    // shuffle?
                    if (shuffle.isSelected()) {
                        //player.shuffle(playlist.toArray(/*new Song[playlist.size()]*/));
                        //player.shuffle(playlist.toArray());
                    } else {
                        player.play(playlist.toArray(new Song[playlist.size()]));
                    }
                } else if (songs.getSelectedIndex() != -1) {
                    player.play(playlist.get(songs.getSelectedIndex()));
                }
            }
            //if pause
            else {
                playPause.setText(PLAY_TEXT);
                player.pause();
            }
            stop.setEnabled(true);
        }
        // Stop
        else if (command.equals("stop")) {
            player.stop();
            playPause.setText(PLAY_TEXT);
            stop.setEnabled(false);
        }
        // Forward
        else if (command.equals("forward")) {
            selectedIndex = songs.getSelectedIndex();
            if (selectedIndex == songs.getModel().getSize()-1){
                selectedIndex = 0;
            }
            else{
                selectedIndex++;
            }
            songs.setSelectedIndex(selectedIndex);
            
            player.stop();
            //if pause displayed, start new song from beginning
            if (playPause.getText().equals(PAUSE_TEXT)){
                player.play(playlist.get(songs.getSelectedIndex()));
            }
        }
        // Rewind
        else if (command.equals("rewind")) {
            selectedIndex = songs.getSelectedIndex();
            if (selectedIndex == 0){
                selectedIndex = songs.getModel().getSize()-1;
            }
            else{
                selectedIndex--;
            }
            songs.setSelectedIndex(selectedIndex);
            
            player.stop();
            if (playPause.getText().equals(PAUSE_TEXT)){
                player.play(playlist.get(songs.getSelectedIndex()));
            }
        }
        // Get Info
        else if (command.equals("info")) {
            Song curSong = playlist.get(songs.getSelectedIndex());
            String infoString = String.format("Title: %s\nArtist: %s\nAlbum: %s\n", curSong.getTitle(), curSong.getArtist(), curSong.getAlbum());
            new NotificationWindow("About "+curSong.getTitle(), infoString);
        }
    }
}










