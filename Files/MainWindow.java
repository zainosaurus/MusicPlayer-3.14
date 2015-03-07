import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class MainWindow extends JFrame implements ActionListener {
    private Player player;
    private MusicManager manager;
    private ArrayList<Song> playlist;

    // variables for search bar panel
    private JPanel searchPanel;
    private JTextField searchBox;
    private JButton searchButton;
    private JComboBox searchOptionSelector;
    private String[] searchOptions = {"Title", "Artist", "Album"};

    // variables for song list area
    private JPanel songListPanel;
    private JScrollPane songList;
    private JList<String> songs;
    private String[] songNames = {"Uninitialized"};

    // variables for menu
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem loadPlaylistOption;
    private JMenuItem savePlaylistOption;

    // variables for songActionArea
    private JPanel songActionPanel;
    private JPanel checkBoxPanel;
    private JButton play;
    private JButton stop;
    private JCheckBox shuffle;
    private JCheckBox playAll;
    private JButton pause;
    private JButton info;

	/**
	 * Constructor
	 */
    public MainWindow(String title, MusicManager man){
        super(title);
        manager = man;
        player = new Player(manager);
		  updateSongs(manager.getPlaylist());

        // search area
        drawSearchArea();

        // song list
        drawSongList();

        // menu
        drawMenu();

        // song action area
        drawSongActionArea();

		  // frame specs
		  setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 300);
        setResizable(true);
        setVisible(true);
    }

    /**
     * Song action area (play/stop button etc)
     */
    public void drawSongActionArea() {
        // initialize
        songActionPanel = new JPanel(new FlowLayout());
        checkBoxPanel = new JPanel();
        play = new JButton("Play");
        stop = new JButton("Stop");
        pause = new JButton("Pause");
        shuffle = new JCheckBox("Shuffle");
        info = new JButton("Song Info");
        playAll = new JCheckBox("Play All");

        // spec
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.PAGE_AXIS));

        // action commands
        play.setActionCommand("play");
        stop.setActionCommand("stop");
        pause.setActionCommand("pause");
        info.setActionCommand("info");

        // action listeners
        play.addActionListener(this);
        stop.addActionListener(this);
        pause.addActionListener(this);
        info.addActionListener(this);

        // adding elements
        checkBoxPanel.add(playAll);
        checkBoxPanel.add(shuffle);
        songActionPanel.add(checkBoxPanel);
        songActionPanel.add(play);
        songActionPanel.add(pause);
        songActionPanel.add(stop);
        songActionPanel.add(info);

        add(songActionPanel);
    }


    /**
     * Menu
     */
    public void drawMenu() {
        // initialize
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        loadPlaylistOption = new JMenuItem("Load Playlist");
        savePlaylistOption = new JMenuItem("Save Playlist");

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
    public void drawSearchArea() {
		// initialize
		searchPanel = new JPanel(new FlowLayout());
		searchBox = new JTextField(15);
		searchOptionSelector = new JComboBox(searchOptions);
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
		searchPanel.add(searchBox);
		searchPanel.add(searchOptionSelector);
		searchPanel.add(searchButton);

		add(searchPanel);
    }

	/**
	 * Song list
	 */
	public void drawSongList() {
		// initialize
		songListPanel = new JPanel(new FlowLayout());
		songs = new JList<String>(songNames);
		songList = new JScrollPane(songs);

		// spec
		songs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Adding elements
		songListPanel.add(songList);

		add(songListPanel);

	}

// 	 private void loadPlayer(){
//         player = new Player(new MusicManager());
//
//         //for debugging purposes
//         player.getManager().addTrack(new Song("Kappa.wav"));
//         player.getManager().addTrack(new Song("Song Name.wav"));
//         player.play();
//         player.getManager().removeTrack(new Song("Song Name.wav"));
//         player.play();
//     }

	/**
	 * Updates the playlist and list of song names
	 */
    public void updateSongs(ArrayList<Song> list) {
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
   	public void updateSongs(Song song) {
   	   // playlist
   	   playlist = new ArrayList<>();
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

        // Search
        if (command.equals("search")) {
            if (searchOptionSelector.getSelectedIndex() == 0) {
               // search and update the list with the results.
               // if the search field is blank, reset the list with all songs by updating songs from MusicManager
        	   }
        }

        // Load
        else if (command.equals("load")) {
            manager.load("songlist.txt");
            updateSongs(manager.getPlaylist());
        }
        // Save
        else if (command.equals("save")) {
            manager.save();
        }
        // play
        else if (command.equals("play")) {
            // play all?
            if (playAll.isSelected()) {
                // shuffle?
                if (shuffle.isSelected()) {
                    player.shuffle(playlist.toArray(new Song[playlist.size()]));
                } else {
                    player.play(playlist.toArray(new Song[playlist.size()]));
                    //System.out.println(playlist.get(0));
                    //System.out.println(playlist.get(1));
                }
            } else if (songs.getSelectedIndex() != -1) {
                player.play(playlist.get(songs.getSelectedIndex()));
            } else {
                new NotificationWindow("Dumbass Alert", "Listen here, you little shit, did you just try to play a song without selecting it? I'm not a fucking mind-reader. Select a song before playing it please.");
            }
        }
        // stop
        else if (command.equals("stop")) {
            player.stop();
        }
        // pause
        else if (command.equals("pause")) {
            player.pause();
        }
        // info
    }
}










