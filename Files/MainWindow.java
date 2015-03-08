import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class MainWindow extends JFrame implements ActionListener {
    private Player player;
    private MusicManager manager;
    private ArrayList<Song> playlist;
    private JPanel mainPanel;

    // variables for search bar panel
    private JTextField searchBox;
    private JButton searchButton;
    private JComboBox searchOptionSelector;
    private final String[] searchOptions = {"Title", "Artist", "Album"};

    // variables for song list area
    private JScrollPane songList;
    private JList<String> songs;
    private String[] songNames = {"[--------Uninitialized----------------Uninitialized---------]"};

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
    
    private JCheckBox shuffle;
    private JCheckBox playAll;
    //private JButton loopMode;
    
    /**
     * Constructor
     */
    public MainWindow(String title, MusicManager man){
        super(title);
        manager = man;
        player = new Player(this);
        //ugly command
        updateSongs(manager.getPlaylist());
        
        //use only one panel for adding stuff on
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        
        // elements must be drawn in order:
        // but first, init properties
        initMenu();
        initSearchArea();
        initSongList();
        initSongActionArea();
        enablePlayer();
        
        drawItems(mainPanel);

        // frame specs
        setLayout(new FlowLayout());
        add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 300);
        setResizable(false); //do this for now as it looks terrible when resized
        setVisible(true);
    }

    /**
     * Song action area (play/stop button etc)
     */
    
    //source: http://en.wikipedia.org/wiki/Geometric_Shapes#Compact_chart
    //http://en.wikipedia.org/wiki/Block_Elements#Compact_table
    private final String RIGHT_TRIANGLE = "\u25B6", LEFT_TRIANGLE = "\u25C0", SQUARE = "\u25A0", RECTANGLES = "\u275A\u275A";
    public final String PLAY_TEXT = RIGHT_TRIANGLE;
    public final String PAUSE_TEXT = RECTANGLES;
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
        playPause = new JButton(PLAY_TEXT);
        stop = new JButton(STOP_TEXT);
        forward = new JButton(FORWARD_TEXT);
        rewind = new JButton(REWIND_TEXT);
        info = new JButton("Info");
        shuffle = new JCheckBox("Shuffle");
        //loopMode = new JButton("Play current only");
        playAll = new JCheckBox("Play All");

        // more properties
        playPause.setFont(new Font("Arial Unicode", Font.BOLD, 20));
        stop.setFont(new Font("Arial Unicode", Font.PLAIN, 16));
        forward.setFont(new Font("Arial Unicode", Font.PLAIN, 16));
        rewind.setFont(new Font("Arial Unicode", Font.PLAIN, 16));
        
        // action commands
        playPause.setActionCommand("togglePlay");
        stop.setActionCommand("stop");
        info.setActionCommand("showInfo");
        forward.setActionCommand("forward");
        rewind.setActionCommand("rewind");
        shuffle.setActionCommand("toggleShuffle");
        //loopMode.setActionCommand("toggleLoopMode");
        playAll.setActionCommand("togglePlayMode");
        
        // action listeners
        playPause.addActionListener(this);
        stop.addActionListener(this);
        info.addActionListener(this);
        forward.addActionListener(this);
        rewind.addActionListener(this);
        shuffle.addActionListener(this);
        //loopMode.addActionListener(this);
        playAll.addActionListener(this);
        
        // disabling features
        playPause.setEnabled(false);
        stop.setEnabled(false);
        forward.setEnabled(false);
        rewind.setEnabled(false);
        info.setEnabled(false);
        shuffle.setEnabled(false);
        //loopMode.setEnabled(false);
        playAll.setEnabled(false);
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
        //searchPanel = new JPanel(new FlowLayout());
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

        // adding elements
        //searchPanel.add(searchBox);
        //searchPanel.add(searchOptionSelector);
        //searchPanel.add(searchButton);

        //add(searchPanel);
    }

    /**
     * Song list
     */
    private void initSongList() {
        // initialize
        //songListPanel = new JPanel(new FlowLayout());
        songs = new JList<String>(songNames);
        songList = new JScrollPane(songs);

        // spec
        songs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Adding elements
        //songListPanel.add(songList);

        //add(songListPanel);
    }
    
    private void enablePlayer() {
        //start enabling features
        if (songs.getModel().getSize() > 0){
            songs.setSelectedIndex(0);
            playAll.setEnabled(true);
            playPause.setEnabled(true);
            
            if (songs.getModel().getSize() > 1){
                shuffle.setEnabled(true);
                forward.setEnabled(true);
                rewind.setEnabled(true);
            }
        }
    }
    
    //draw the items onto a panel
    private void drawItems(JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        //add search toolbar
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 9;
        panel.add(searchBox);
        
        c.gridx = 8;
        c.gridy = 0;
        c.gridwidth = 3;
        panel.add(searchOptionSelector);
        
        c.gridx = 11;
        c.gridy = 0;
        c.gridwidth = 3;
        panel.add(searchButton);
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
                if (playAll.isSelected()) {
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










