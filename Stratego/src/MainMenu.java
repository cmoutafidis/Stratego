import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class MainMenu extends JFrame{
	
	private int counter = 0;
	private double overallVol = 100, musicVol = 100, effectsVol = 100;
	private File[] listOfFiles;
	private String fileNameToLoad;
	private JTextField username, overallText, musicText, effectsText;
	private JTable table;
    private JPasswordField password, passwordCheck;
    private JLabel loginLabel, outcomeLabel;
    private Player player1, player2;
    private JFrame frame;
    
    private JPanel panel = new JPanel();
	private JList list = new JList();
	private JButton button = new JButton();
	private ArrayList<Pawn> pawns = new ArrayList<Pawn>();
	private int[] freqs = new int[12];
	private DefaultListModel model = new DefaultListModel();
	private Position[][] positions = new Position[10][10];
	private static int vo = 0;
	private ArrayList<Pawn> bluepawns;
	private Position[][] pos = new Position[10][10];
	private String[] array = {"B", "F", "10", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	
	public MainMenu(){
		
		bluepawns = new ArrayList<Pawn>();
		
		for(int i=1;i<11;i++){
			switch(i){
			case 1:
			case 9:
			case 10:
				bluepawns.add(new Movable(Integer.toString(i),"BLUE",1));
				break;
			case 8:
				for(int k=0;k<2;k++){
					bluepawns.add(new Movable(Integer.toString(i),"BLUE",1));
				}
				break;
			case 7:
				for(int k=0;k<3;k++){
					bluepawns.add(new Movable(Integer.toString(i),"BLUE",1));
				}
				break;
			case 4:
			case 5:
			case 6:
				for(int k=0;k<4;k++){
					bluepawns.add(new Movable(Integer.toString(i),"BLUE",1));
				}
				break;
			case 3:
				for(int k=0;k<5;k++){
					bluepawns.add(new Movable(Integer.toString(i),"BLUE",1));
				}
				break;
			case 2:
				for(int k=0;k<8;k++){
					bluepawns.add(new Movable(Integer.toString(i),"BLUE",9));
				}
				break;
			}
		}
		bluepawns.add(new Immovable("F","BLUE"));
		for(int k=0;k<6;k++){
			bluepawns.add(new Immovable("B","BLUE"));
		}
		
		main();
		
	    setExtendedState(JFrame.MAXIMIZED_BOTH); 
	    setUndecorated(true);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setVisible(true);
	}
	
	public MainMenu(ArrayList<Pawn> pawnNames, boolean empty, int[] replicas, Position[][] pos){
		PawnList(pawnNames,empty,replicas,pos);
	}
	
	public void login(){
		setLayout(new BorderLayout(1,1));
	    setContentPane(new JLabel(new ImageIcon("backrounds/backround1.jpg")));
	    setLayout(new BorderLayout(0,0));
	    
	    JLabel create = new JLabel("Create Account");
	    JLabel exit = new JLabel("Exit");
	    loginLabel = new JLabel("Login player " + (counter+1));
	    outcomeLabel = new JLabel();
	    username = new JTextField("Username");
	    password = new JPasswordField("Password");
	    
	    //visible content
	    password.setEchoChar((char)0);
	    
	    //border
	    Border border = BorderFactory.createLineBorder(Color.BLACK);
	    username.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    password.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    create.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    exit.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    
	    //alignment
	    loginLabel.setHorizontalAlignment(JTextField.CENTER);
	    outcomeLabel.setHorizontalAlignment(JTextField.CENTER);
	    username.setHorizontalAlignment(JTextField.CENTER);
	    password.setHorizontalAlignment(JTextField.CENTER);
	    create.setHorizontalAlignment(JTextField.CENTER);
	    exit.setHorizontalAlignment(JTextField.CENTER);
	    
	    //font
	    loginLabel.setFont(loginLabel.getFont().deriveFont(35f));
	    outcomeLabel.setFont(outcomeLabel.getFont().deriveFont(35f));
	    username.setFont(username.getFont().deriveFont(35f));
	    password.setFont(password.getFont().deriveFont(35f));
	    create.setFont(create.getFont().deriveFont(35f));
	    exit.setFont(exit.getFont().deriveFont(35f));
	    
	    //start listeners
	    username.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (username.getText().equals("Username")) {
	                username.setText("");
	            }
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
				    e.consume();
				    PlayerList pl = PlayerList.getInstance();
				    String user = username.getText().trim().replaceAll("\\s+", "");
				    String pass = password.getText();
				    int i = pl.checkUser(user, pass);
				    if(counter==1){
				    	if(user.equals(player1.getName())){
				    		i=3;
				    	}
				    }
				    switch(i){
				    	case 0:
				    		outcomeLabel.setText("Wrong Username");
				    		break;
				    	case 1:
				    		outcomeLabel.setText("Wrong Password");
				    		break;
				    	case 2:
				    		counter++;
				    		outcomeLabel.setText("");
				    		loginLabel.setText("Login player " + (counter+1));
				    		if(counter==1){
				    			player1 = pl.getPlayer(user);
				    		}
				    		else{
				    			player2 = pl.getPlayer(user);
				    		}
				    		username.setText("Username");
				    		password.setText("Password");
				    		password.setEchoChar((char)0);
				    		break;
				    	case 3:
				    		outcomeLabel.setText("Already logged in");
				    		break;
				    }
				    if(counter==2){
				    	counter=0;
				    	main();
				    }
				    }
			}
			@Override
			public void keyReleased(KeyEvent arg0) {	
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
	    });
	    password.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(password.getText().equals("Password")){
					password.setText("");
					password.setEchoChar('*');
				}
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
				    e.consume();
				    PlayerList pl = PlayerList.getInstance();
				    String user = username.getText().trim().replaceAll("\\s+", "");
				    String pass = password.getText();
				    int i = pl.checkUser(user, pass);
				    if(counter==1){
				    	if(user.equals(player1.getName())){
				    		i=3;
				    	}
				    }
				    switch(i){
				    	case 0:
				    		outcomeLabel.setText("Wrong Username");
				    		break;
				    	case 1:
				    		outcomeLabel.setText("Wrong Password");
				    		break;
				    	case 2:
				    		counter++;
				    		outcomeLabel.setText("");
				    		loginLabel.setText("Login player " + (counter+1));
				    		if(counter==1){
				    			player1 = pl.getPlayer(user);
				    		}
				    		else{
				    			player2 = pl.getPlayer(user);
				    		}
				    		username.setText("Username");
				    		password.setText("Password");
				    		password.setEchoChar((char)0);
				    		break;
				    	case 3:
				    		outcomeLabel.setText("Already logged in");
				    		break;
				    }
				    if(counter==2){
				    	counter=0;
				    	main();
				    }
				    }
			}
			@Override
			public void keyReleased(KeyEvent arg0) {	
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
	    });
	    exit.addMouseListener(new MouseAdapter() {
			
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	try {
					InputStream in = new FileInputStream("music/button.wav");
	            	AudioStream as = new AudioStream(in);         
	            	AudioPlayer.player.start(as); 
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	
            }
        });
	    exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	PlayerList pl = PlayerList.getInstance();
            	pl.savePlayers();
                System.exit(0);
            }
        });
	    create.addMouseListener(new MouseAdapter() {
			
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	try {
					InputStream in = new FileInputStream("music/button.wav");
	            	AudioStream as = new AudioStream(in);         
	            	AudioPlayer.player.start(as); 
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	
            }
        });
	    create.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                create();
            }
        });
	    username.addFocusListener(new FocusListener() {

	        public void focusGained(FocusEvent e) {
	            if (username.getText().equals("Username")) {
	                username.setText("");
	            }
	        }

	        public void focusLost(FocusEvent e) {
	            if ("".equalsIgnoreCase(username.getText().trim())) {
	                username.setText("Username");
	            }
	        }});	
	    password.addFocusListener(new FocusListener() {

	        public void focusGained(FocusEvent e) {
	        	password.setEchoChar('*');
	            if (password.getText().equals("Password")) {
	                password.setText("");
	                password.setEchoChar('*');
	            }
	        }

	        public void focusLost(FocusEvent e) {
	            if ("".equalsIgnoreCase(password.getText().trim())) {
	                password.setEchoChar((char)0);
	                password.setText("Password");
	            }
	        }});
	    //end listeners
	    
	    GridLayout grid = new GridLayout(0,1);
		grid.setVgap(20);
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBackground(new Color(230,230,0,80));
		p1.add(exit, BorderLayout.CENTER);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.setBackground(new Color(230,230,0,80));
		p2.add(create, BorderLayout.CENTER);
		
		//add labels to panels
		JPanel pane = new JPanel();
		pane.setBorder(new EmptyBorder(250, 430, 250, 430));
		pane.setLayout(grid);
		pane.add(loginLabel);
		pane.add(username);
		pane.add(password);
		pane.add(outcomeLabel);
		pane.add(p2);
		pane.add(p1);
		pane.setOpaque(false);
		add(pane, BorderLayout.CENTER);
	    
	    SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void create(){
		setLayout(new BorderLayout(1,1));
	    setContentPane(new JLabel(new ImageIcon("backrounds/backround1.jpg")));
	    setLayout(new BorderLayout(0,0));
	    
	    JLabel createLabel = new JLabel("Create Account");
	    outcomeLabel = new JLabel();
	    JLabel login = new JLabel("Back to login");
	    username = new JTextField("Username");
	    password = new JPasswordField("Password");
	    passwordCheck = new JPasswordField("Confirm Password");
	    
	    //visible content
	    password.setEchoChar((char)0);
	    passwordCheck.setEchoChar((char)0);
	    
	    //border
	    Border border = BorderFactory.createLineBorder(Color.BLACK);
	    username.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    password.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    passwordCheck.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    login.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    
	    //alignment
	    username.setHorizontalAlignment(JTextField.CENTER);
	    password.setHorizontalAlignment(JTextField.CENTER);
	    passwordCheck.setHorizontalAlignment(JTextField.CENTER);
	    login.setHorizontalAlignment(JTextField.CENTER);
	    createLabel.setHorizontalAlignment(JTextField.CENTER);
	    outcomeLabel.setHorizontalAlignment(JTextField.CENTER);
	    
	    //font
	    username.setFont(username.getFont().deriveFont(35f));
	    password.setFont(password.getFont().deriveFont(35f));
	    passwordCheck.setFont(passwordCheck.getFont().deriveFont(35f));
	    login.setFont(login.getFont().deriveFont(35f));
	    createLabel.setFont(createLabel.getFont().deriveFont(35f));
	    outcomeLabel.setFont(outcomeLabel.getFont().deriveFont(35f));
	    
	    //start listeners
	    username.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(username.getText().equals("Username")){
					username.setText("");
				}
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
				    e.consume();
				    
				    PlayerList pl = PlayerList.getInstance();
				    String user = username.getText().trim().replaceAll("\\s+", "");
				    String pass = password.getText();
				    String passCheck = passwordCheck.getText();
				    
				    if(!user.equals("") && !pass.equals("") && !passCheck.equals("")) {
				    	if(!pl.userExists(user)){
				    		if(pass.equals(passCheck)){
				    			pl.addUser(user, pass);
				    			outcomeLabel.setText("User " + user + " created");
				    			username.setText("Username");
				    			password.setText("Password");
				    			passwordCheck.setText("Confirm Password");
				    			password.setEchoChar((char)0);
				    		    passwordCheck.setEchoChar((char)0);
				    		}
				    		else{
				    			outcomeLabel.setText("Passwords don't match");
				    		}
				    	}
				    	else{
				    		outcomeLabel.setText("User Already Exists");
				    	}
				    }
				    else{
				    	outcomeLabel.setText("Blank fields");
				    }
				    
				    
				    }
			}
			@Override
			public void keyReleased(KeyEvent arg0) {	
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
	    });
	    password.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(password.getText().equals("Password")){
					password.setText("");
					password.setEchoChar('*');
				}
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
				    e.consume();
				    
				    PlayerList pl = PlayerList.getInstance();
				    String user = username.getText().trim().replaceAll("\\s+", "");
				    String pass = password.getText();
				    String passCheck = passwordCheck.getText();
				    
				    if(!user.equals("") && !pass.equals("") && !passCheck.equals("")) {
				    	if(!pl.userExists(user)){
				    		if(pass.equals(passCheck)){
				    			pl.addUser(user, pass);
				    			outcomeLabel.setText("User " + user + " created");
				    			username.setText("Username");
				    			password.setText("Password");
				    			passwordCheck.setText("Confirm Password");
				    			password.setEchoChar((char)0);
				    		    passwordCheck.setEchoChar((char)0);
				    		}
				    		else{
				    			outcomeLabel.setText("Passwords don't match");
				    		}
				    	}
				    	else{
				    		outcomeLabel.setText("User Already Exists");
				    	}
				    }
				    else{
				    	outcomeLabel.setText("Blank fields");
				    }
				    }
			}
			@Override
			public void keyReleased(KeyEvent arg0) {	
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
	    });
	    passwordCheck.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(passwordCheck.getText().equals("Confirm Password")){
					passwordCheck.setText("");
					passwordCheck.setEchoChar('*');
				}
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
				    e.consume();
				    
				    PlayerList pl = PlayerList.getInstance();
				    String user = username.getText().trim().replaceAll("\\s+", "");
				    String pass = password.getText();
				    String passCheck = passwordCheck.getText();
				    
				    if(!user.equals("") && !pass.equals("") && !passCheck.equals("")) {
				    	if(!pl.userExists(user)){
				    		if(pass.equals(passCheck)){
				    			pl.addUser(user, pass);
				    			outcomeLabel.setText("User " + user + " created");
				    			username.setText("Username");
				    			password.setText("Password");
				    			passwordCheck.setText("Confirm Password");
				    			password.setEchoChar((char)0);
				    		    passwordCheck.setEchoChar((char)0);
				    		}
				    		else{
				    			outcomeLabel.setText("Passwords don't match");
				    		}
				    	}
				    	else{
				    		outcomeLabel.setText("User Already Exists");
				    	}
				    }
				    else{
				    	outcomeLabel.setText("Blank fields");
				    }
				    
				    
				    }
			}
			@Override
			public void keyReleased(KeyEvent arg0) {	
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
	    });
	    login.addMouseListener(new MouseAdapter() {
			
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	try {
					InputStream in = new FileInputStream("music/button.wav");
	            	AudioStream as = new AudioStream(in);         
	            	AudioPlayer.player.start(as); 
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	
            }
        });
	    login.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                login();
            }
        });
	    username.addFocusListener(new FocusListener() {

	        public void focusGained(FocusEvent e) {
	            if (username.getText().equals("Username")) {
	                username.setText("");
	            }
	        }

	        public void focusLost(FocusEvent e) {
	            if ("".equalsIgnoreCase(username.getText().trim())) {
	                username.setText("Username");
	            }
	        }});	
	    password.addFocusListener(new FocusListener() {

	        public void focusGained(FocusEvent e) {
	            password.setEchoChar('*');
	            if (password.getText().equals("Password")) {
	                password.setText("");
	            }
	        }

	        public void focusLost(FocusEvent e) {
	            if ("".equalsIgnoreCase(password.getText().trim())) {
	                password.setEchoChar((char)0);
	                password.setText("Password");
	            }
	        }});
	    passwordCheck.addFocusListener(new FocusListener() {

	        public void focusGained(FocusEvent e) {
	            passwordCheck.setEchoChar('*');
	            if (passwordCheck.getText().equals("Confirm Password")) {
	                passwordCheck.setText("");
	            }
	        }

	        public void focusLost(FocusEvent e) {
	            if ("".equalsIgnoreCase(passwordCheck.getText().trim())) {
	                passwordCheck.setEchoChar((char)0);
	                passwordCheck.setText("Confirm Password");
	            }
	        }});
	    //end listeners
	    
	    GridLayout grid = new GridLayout(0,1);
		grid.setVgap(20);
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBackground(new Color(230,230,0,80));
		p1.add(login, BorderLayout.CENTER);
		
		//add labels to panels
		JPanel pane = new JPanel();
		pane.setBorder(new EmptyBorder(200, 430, 200, 430));
		pane.setLayout(grid);
		pane.add(createLabel);
		pane.add(username);
		pane.add(password);
		pane.add(passwordCheck);
		pane.add(outcomeLabel);
		pane.add(p1);
		pane.setOpaque(false);
		add(pane, BorderLayout.CENTER);
	    
	    SwingUtilities.updateComponentTreeUI(this);
	    
	}
	
	public void main(){
		setLayout(new BorderLayout(1,1));
	    setContentPane(new JLabel(new ImageIcon("backrounds/Menu_BG.png")));
	    setLayout(new BorderLayout(0,0));
	    
	    //define labels
		JLabel newGame = new JLabel("New Game"), 
			   loadGame = new JLabel("Load Game"), 
			   leaderboard = new JLabel("Leaderboard"),
			   options = new JLabel("Options"), 
			   exit = new JLabel("Exit");
		
		//border
	    Border border = BorderFactory.createLineBorder(Color.BLACK);
	    newGame.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    loadGame.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    leaderboard.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    options.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    exit.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    
	    //alignment
	    newGame.setHorizontalAlignment(JTextField.CENTER);
	    loadGame.setHorizontalAlignment(JTextField.CENTER);
	    leaderboard.setHorizontalAlignment(JTextField.CENTER);
	    options.setHorizontalAlignment(JTextField.CENTER);
	    exit.setHorizontalAlignment(JTextField.CENTER);
	    
	    //font
	    newGame.setFont(newGame.getFont().deriveFont(35f));
	    loadGame.setFont(loadGame.getFont().deriveFont(35f));
	    leaderboard.setFont(leaderboard.getFont().deriveFont(35f));
	    options.setFont(options.getFont().deriveFont(35f));
	    exit.setFont(exit.getFont().deriveFont(35f));
		
		//start add listener to labels
		newGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                PawnList(bluepawns,true,freqs,pos);
            }
        });
		newGame.addMouseListener(new MouseAdapter() {
			
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	try {
					InputStream in = new FileInputStream("music/button.wav");
	            	AudioStream as = new AudioStream(in);         
	            	AudioPlayer.player.start(as); 
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	
            }
        });
		loadGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                load();
            }
        });
		loadGame.addMouseListener(new MouseAdapter() {
			
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	try {
					InputStream in = new FileInputStream("music/button.wav");
	            	AudioStream as = new AudioStream(in);         
	            	AudioPlayer.player.start(as); 
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	
            }
        });
		leaderboard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                leaderboard();
            }
        });
		leaderboard.addMouseListener(new MouseAdapter() {
			
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	try {
					InputStream in = new FileInputStream("music/button.wav");
	            	AudioStream as = new AudioStream(in);         
	            	AudioPlayer.player.start(as); 
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	
            }
        });
		options.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                options();
            }
        });
		options.addMouseListener(new MouseAdapter() {
			
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	try {
					InputStream in = new FileInputStream("music/button.wav");
	            	AudioStream as = new AudioStream(in);         
	            	AudioPlayer.player.start(as); 
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	
            }
        });
		exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	System.exit(0);
            }
        });
		exit.addMouseListener(new MouseAdapter() {
			
			
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	try {
					InputStream in = new FileInputStream("music/button.wav");
	            	AudioStream as = new AudioStream(in);         
	            	AudioPlayer.player.start(as); 
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	
            }
        });
		//end add listeners
		
		GridLayout grid = new GridLayout(0,1);
		grid.setVgap(50);
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBackground(new Color(230,230,0,80));
		p1.add(newGame, BorderLayout.CENTER);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.setBackground(new Color(230,230,0,80));
		p2.add(loadGame, BorderLayout.CENTER);
		
		JPanel p3 = new JPanel(new BorderLayout());
		p3.setBackground(new Color(230,230,0,80));
		p3.add(leaderboard, BorderLayout.CENTER);
		
		JPanel p4 = new JPanel(new BorderLayout());
		p4.setBackground(new Color(230,230,0,80));
		p4.add(options, BorderLayout.CENTER);
		
		JPanel p5 = new JPanel(new BorderLayout());
		p5.setBackground(new Color(230,230,0,80));
		p5.add(exit, BorderLayout.CENTER);
		
		//add labels to panels
		JPanel pane = new JPanel();
		pane.setBorder(new EmptyBorder(250, 420, 100, 420));
		pane.setLayout(grid);
		pane.add(p1);
		pane.add(p2);
		pane.add(p3);
		pane.add(p4);
		pane.add(p5);
		pane.setOpaque(false);
		add(pane, BorderLayout.CENTER);
		
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	private void options(){
		setLayout(new BorderLayout(1,1));
	    setContentPane(new JLabel(new ImageIcon("backrounds/Options_BG.png")));
	    setLayout(new BorderLayout(0,0));
	    
	    String resolutions[] = {"Fullscreen", "1920x1080", "1366x768", "1280x1024", "1280x800", "800x600"};
	    
	    JLabel res = new JLabel("Resolution"),
	    	   overall = new JLabel("Overall"),
	    	   music = new JLabel("Music"),
	    	   effects = new JLabel("Effects"),
	    	   back = new JLabel("Back");
	    
	    overallText = new JTextField(overallVol + "%");
	    musicText = new JTextField(musicVol + "%");
	    effectsText = new JTextField(effectsVol + "%");
	    
	    JComboBox<String> cb=new JComboBox<String>(resolutions);
	    
	    //border
	    Border border = BorderFactory.createLineBorder(Color.BLACK);
	    cb.setBorder(BorderFactory.createCompoundBorder(border, 
	                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    overallText.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    musicText.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    effectsText.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    back.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    
	    //alignment
	    DefaultListCellRenderer dlcr = new DefaultListCellRenderer(); 
	    dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER); 
	    cb.setRenderer(dlcr); 
	    overallText.setHorizontalAlignment(JTextField.CENTER);
	    musicText.setHorizontalAlignment(JTextField.CENTER);
	    effectsText.setHorizontalAlignment(JTextField.CENTER);
	    res.setHorizontalAlignment(JTextField.CENTER);
	    overall.setHorizontalAlignment(JTextField.CENTER);
	    music.setHorizontalAlignment(JTextField.CENTER);
	    effects.setHorizontalAlignment(JTextField.CENTER);
	    back.setHorizontalAlignment(JTextField.CENTER);
	    
	    //foreground
	    res.setForeground(Color.YELLOW);
	    overall.setForeground(Color.YELLOW);
	    music.setForeground(Color.YELLOW);
	    effects.setForeground(Color.YELLOW);
	    cb.setForeground(Color.YELLOW);
	    overallText.setForeground(Color.YELLOW);
	    musicText.setForeground(Color.YELLOW);
	    effectsText.setForeground(Color.YELLOW);
	    
	    //backround
	    cb.setBackground(Color.LIGHT_GRAY);
	    overallText.setBackground(Color.LIGHT_GRAY);
	    musicText.setBackground(Color.LIGHT_GRAY);
	    effectsText.setBackground(Color.LIGHT_GRAY);
	    
	    //font
	    cb.setFont(cb.getFont().deriveFont(35f));
	    overallText.setFont(overallText.getFont().deriveFont(35f));
	    musicText.setFont(musicText.getFont().deriveFont(35f));
	    effectsText.setFont(effectsText.getFont().deriveFont(35f));
	    res.setFont(effectsText.getFont().deriveFont(40f));
	    overall.setFont(effectsText.getFont().deriveFont(40f));
	    music.setFont(effectsText.getFont().deriveFont(40f));
	    effects.setFont(effectsText.getFont().deriveFont(40f));
	    back.setFont(effectsText.getFont().deriveFont(35f));
	    
	    //start listeners
	    back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	String overall = overallText.getText().trim().replaceAll("\\s+", "").replaceAll("%", "");
            	String music = musicText.getText().trim().replaceAll("\\s+", "").replaceAll("%", "");
            	String effects = effectsText.getText().trim().replaceAll("\\s+", "").replaceAll("%", "");
            	if(overall.matches("\\d*\\.?\\d+")){
            		if(Double.parseDouble(overall)<=100){
            			overallVol = Double.parseDouble(overall);
            		}
            	}
            	if(music.matches("\\d*\\.?\\d+")){
            		if(Double.parseDouble(music)<=100){
            			musicVol = Double.parseDouble(music);
            		}
            	}
            	if(effects.matches("\\d*\\.?\\d+")){
            		if(Double.parseDouble(effects)<=100){
            			effectsVol = Double.parseDouble(effects);
            		}
            	}
                main();
            }
        });
	    back.addMouseListener(new MouseAdapter() {
			
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	try {
					InputStream in = new FileInputStream("music/button.wav");
	            	AudioStream as = new AudioStream(in);         
	            	AudioPlayer.player.start(as); 
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	
            }
        });
	    //end listeners
	    
	    GridLayout grid = new GridLayout(0,1);
		grid.setVgap(20);
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBackground(new Color(230,230,0,80));
		p1.add(back, BorderLayout.CENTER);
		
		//add labels to panels
		JPanel pane = new JPanel();
		pane.setBorder(new EmptyBorder(90, 420, 90, 420));
		pane.setLayout(grid);
		pane.add(res);
		pane.add(cb);
		pane.add(overall);
		pane.add(overallText);
		pane.add(music);
		pane.add(musicText);
		pane.add(effects);
		pane.add(effectsText);
		pane.add(p1);
		pane.setOpaque(false);
		add(pane, BorderLayout.CENTER);
	    
	    SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void leaderboard(){
		frame = this;
		setLayout(new BorderLayout(1,1));
	    setContentPane(new JLabel(new ImageIcon("backrounds/Leaderboard_BG.png")));
	    setLayout(new BorderLayout(0,0));
	    
	    PlayerList pl = PlayerList.getInstance();
	    
	    JLabel back = new JLabel("Back");
	    JLabel leaderboard = new JLabel("Leaderboard");
	    
	    DefaultTableModel model = new DefaultTableModel(); 
	    table = new JTable(model); 
	    
	    table.disable();
	    table.setGridColor(Color.BLACK);
	    table.setBackground(new Color(230,230,230,80));
	    
	    leaderboard.setForeground(Color.YELLOW);
	    
	    model.addColumn("Name"); 
	    model.addColumn("Wins"); 
	    model.addColumn("Losses"); 
	    model.addColumn("Win Ratio"); 
	    model.addColumn("Fastest Round"); 
	    
	    JTableHeader header = table.getTableHeader();
	    header.setBackground(new Color(230,230,230,80));
	    header.setForeground(Color.YELLOW);
	    
	    for(Player p : pl.getList()){
	        Object[] rowData = new Object[]{
	            p.getName(),
	            p.getWins(), 
	            p.getLosses(), 
	            p.getWinRation(),
	            p.getFastestRound()
	            };
	        model.addRow(rowData);
	    }
	    
	    //font
	    back.setFont(back.getFont().deriveFont(35f));
	    table.setFont(table.getFont().deriveFont(35f));
	    leaderboard.setFont(leaderboard.getFont().deriveFont(50f));
	    header.setFont(leaderboard.getFont().deriveFont(20f));
	    table.setRowHeight(50);
	    
	    //foreground
	    table.setForeground(Color.BLACK);
	    header.setForeground(Color.BLACK);
	    
	    //border
	    Border border = BorderFactory.createLineBorder(Color.BLACK);
	    back.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    table.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    header.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    
	    //alignment
	    back.setHorizontalAlignment(JTextField.CENTER);
	    leaderboard.setHorizontalAlignment(JTextField.CENTER);
	    
	    //start listener
	    back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                main();
            }
        });
	    back.addMouseListener(new MouseAdapter() {
			
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	try {
					InputStream in = new FileInputStream("music/button.wav");
	            	AudioStream as = new AudioStream(in);         
	            	AudioPlayer.player.start(as); 
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	
            }
        });
	    //end listener
	    
	    JPanel leaderPanel = new JPanel();
		leaderPanel.setBorder(new EmptyBorder(150, 0, 0, 0));
		leaderPanel.add(leaderboard);
		leaderPanel.setOpaque(false);
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBackground(new Color(230,230,0,80));
		p1.add(back, BorderLayout.CENTER);
	    
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 0, 100, 900));
		panel.add(p1);
		panel.setOpaque(false);
		
	    GridLayout grid = new GridLayout(0,1);
		grid.setVgap(20);
		
		JScrollPane scroll = new JScrollPane(table);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		scroll.setBorder(new EmptyBorder(0, 0, 0, 0));
		
		JScrollBar s = scroll.getVerticalScrollBar();
		s.addAdjustmentListener(new AdjustmentListener(){

			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				SwingUtilities.updateComponentTreeUI(frame);
			}
			
		});
		
		//add labels to panels
		JPanel pane = new JPanel();
		pane.setBorder(new EmptyBorder(50, 250, 30, 250));
		pane.setLayout(grid);
		pane.setOpaque(false);
		pane.add(scroll);
		add(pane, BorderLayout.CENTER);
		add(panel, BorderLayout.PAGE_END);
		add(leaderPanel, BorderLayout.PAGE_START);
		
	    
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void load(){
		frame = this;
		setLayout(new BorderLayout(1,1));
	    setContentPane(new JLabel(new ImageIcon("backrounds/Load Game_BG.png")));
	    setLayout(new BorderLayout(0,0));
	    
	    fileNameToLoad = null;
	    
	    JLabel back = new JLabel("Back");
	    JLabel delete = new JLabel("Delete game");
	    JLabel load = new JLabel("Load game");
	    
	    File folder = new File("saves");
	    listOfFiles = folder.listFiles();
	    ArrayList<String> files = new ArrayList<String>();
	    String s;
	    
	    for (int i = 0; i < listOfFiles.length; i++) {
	    	s = listOfFiles[i].getName().replaceAll("GAME ", "").replaceAll(".ser", "").replaceAll("-", " ");
	    	if(s.contains(player1.getName()) && s.contains(player2.getName())){
	    		files.add(s);
	    	}
	    }
	    
	    NonEditableModel model = new NonEditableModel(); 
	    table = new JTable(model);
	    table.setTableHeader(null);
	    
	    table.setGridColor(Color.BLACK);
	    ((DefaultTableCellRenderer)table.getDefaultRenderer(Object.class)).setOpaque(false);
	    table.setForeground(Color.BLACK);
	    table.setBackground(new Color(230, 230, 230, 80));
	    
	    //font
	    back.setFont(back.getFont().deriveFont(35f));
	    delete.setFont(back.getFont().deriveFont(35f));
	    load.setFont(back.getFont().deriveFont(35f));
	    table.setFont(table.getFont().deriveFont(35f));
	    table.setRowHeight(50);
	    
	    model.addColumn(""); 
	    
	    for(String str : files){
	        Object[] rowData = new Object[]{
	            str
	            };
	        model.addRow(rowData);
	    }
	    
	    //border
	    Border border = BorderFactory.createLineBorder(Color.BLACK);
	    back.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    delete.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    load.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    table.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    
	    //alignment
	    back.setHorizontalAlignment(JTextField.CENTER);
	    delete.setHorizontalAlignment(JTextField.CENTER);
	    load.setHorizontalAlignment(JTextField.CENTER);
	    
	    //start listener
	    back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                main();
            }
        });
	    back.addMouseListener(new MouseAdapter() {
			
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	try {
					InputStream in = new FileInputStream("music/button.wav");
	            	AudioStream as = new AudioStream(in);         
	            	AudioPlayer.player.start(as); 
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	
            }
        });
	    table.addMouseListener(new MouseAdapter() {
	        public void mousePressed(MouseEvent me) {
	            JTable table =(JTable) me.getSource();
	            Point p = me.getPoint();
	            int row = table.rowAtPoint(p);
	            if (me.getClickCount() == 2) {
	                fileNameToLoad = listOfFiles[row].getName();
	                //open game
	            }
	        }
	    });
	    delete.addMouseListener(new MouseAdapter() {
	        public void mousePressed(MouseEvent me) {
	        	if(fileNameToLoad!=null){
	        		File file = new File("saves/" + fileNameToLoad);
		        	file.delete();
		        	deleted();
	        	}
	        }
	    });
	    delete.addMouseListener(new MouseAdapter() {
			
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	if(fileNameToLoad!=null){
            		try {
    					InputStream in = new FileInputStream("music/button.wav");
    	            	AudioStream as = new AudioStream(in);         
    	            	AudioPlayer.player.start(as); 
    				} catch (FileNotFoundException e1) {
    					e1.printStackTrace();
    				} catch (IOException e1) {
    					e1.printStackTrace();
    				}
            	}
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	
            }
        });
	    //end listener
	    
	    JPanel p1 = new JPanel(new BorderLayout());
		p1.setBackground(new Color(230,230,0,80));
		p1.add(delete, BorderLayout.CENTER);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.setBackground(new Color(230,230,0,80));
		p2.add(load, BorderLayout.CENTER);
		
		JPanel p3 = new JPanel(new BorderLayout());
		p3.setBackground(new Color(230,230,0,80));
		p3.add(back, BorderLayout.CENTER);
	    
	    JPanel panel2 = new JPanel();
		panel2.setBorder(new EmptyBorder(0, 0, 0, 200));
		panel2.add(p1);
		panel2.setOpaque(false);
	    
	    JPanel panel3 = new JPanel();
		panel3.setBorder(new EmptyBorder(0, 0, 100, 0));
		panel3.add(panel2);
		panel3.add(p2);
		panel3.setOpaque(false);
	    
	    JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(100, 0, 0, 900));
		panel.add(p3);
		panel.setOpaque(false);
		
	    GridLayout grid = new GridLayout(0,1);
		grid.setVgap(20);
		
		
		JScrollPane scroll = new JScrollPane(table);
		scroll.setOpaque(false);
		scroll.getViewport().setOpaque(false);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		
		JScrollBar scr = scroll.getVerticalScrollBar();
		scr.addAdjustmentListener(new AdjustmentListener(){

			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				SwingUtilities.updateComponentTreeUI(frame);
			}
			
		});
		
		//add labels to panels
		JPanel pane = new JPanel();
		pane.setBorder(new EmptyBorder(100, 250, 50, 250));
		pane.setLayout(grid);
		pane.setOpaque(false);
		pane.add(scroll);
		add(pane, BorderLayout.CENTER);
		add(panel, BorderLayout.PAGE_START);
		add(panel3, BorderLayout.PAGE_END);
	    
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void deleted(){
		setLayout(new BorderLayout(1,1));
	    setContentPane(new JLabel(new ImageIcon("backrounds/Load Game_BG.png")));
	    setLayout(new BorderLayout(0,0));
	    
	    JLabel message = new JLabel("File " + fileNameToLoad.replaceAll("GAME ", "").replaceAll(".ser", "").replaceAll("-", " ") + " was successfully deleted");
		JLabel ok = new JLabel("OK");
		
		//font
	    ok.setFont(ok.getFont().deriveFont(35f));
	    message.setFont(message.getFont().deriveFont(35f));
		
		//border
	    Border border = BorderFactory.createLineBorder(Color.BLACK);
	    ok.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
		
		//alignment
	    ok.setHorizontalAlignment(JTextField.CENTER);
	    message.setHorizontalAlignment(JTextField.CENTER);
		
		//start listener
		ok.addMouseListener(new MouseAdapter() {
	        public void mousePressed(MouseEvent me) {
	        	load();
	        }
	    });
		ok.addMouseListener(new MouseAdapter() {
			
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	try {
					InputStream in = new FileInputStream("music/button.wav");
	            	AudioStream as = new AudioStream(in);         
	            	AudioPlayer.player.start(as); 
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	
            }
        });
	    //end listener
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(0, 0, 300, 0));
		panel.add(ok);
		panel.setOpaque(false);
		
		GridLayout grid = new GridLayout(0,1);
		grid.setVgap(20);
		
		//add labels to panels
		JPanel pane = new JPanel();
		pane.setBorder(new EmptyBorder(400, 250, 50, 250));
		pane.setLayout(grid);
		pane.setOpaque(false);
		pane.add(message);
		add(pane, BorderLayout.CENTER);
		add(panel, BorderLayout.PAGE_END);
	    
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void PawnList(ArrayList<Pawn> pawnNames, boolean empty, int[] replicas, Position[][] pos) {
		int i = 0;
		int j = 0;
		int counter = 0;
		Pawn somePawn = new Pawn("","");
		
		if(vo==0){
			for(i=0;i<10;i++){
				for(j=0;j<10;j++){
					positions[i][j] = new Position();
					positions[i][j].addPawnToPosition(somePawn);
					pos[i][j] = new Position();
					pos[i][j].addPawnToPosition(somePawn);
					positions[i][j].addPawnToPosition(pos[i][j].getPawnOnPosition());
				}
			}
		}
		else{
			for(i=0;i<10;i++){
				for(j=0;j<10;j++){
					positions[i][j] = new Position();
					positions[i][j].addPawnToPosition(somePawn);
					positions[i][j].addPawnToPosition(pos[i][j].getPawnOnPosition());
				}
			}
		}
		Collections.sort(pawnNames);
		ArrayList<String> pawnTypes = new ArrayList<String>();
		
		for(Pawn pawn : pawnNames){
			pawnTypes.add(pawn.getPawnType());
		}
		
		HashSet<String> noMultiples = new HashSet<String>(pawnTypes);
		
		for(String name : noMultiples){
			model.addElement(name);
		}
		pawns.addAll(pawnNames);
		list.setModel(model);
		
		ButtonListener listener = new ButtonListener();
		button.addActionListener(listener);
		i=0;
		if(empty){
			for(String name : noMultiples) {
				counter = Collections.frequency(pawnTypes, name);
				freqs[i] = counter;
				i++;
			}
		}
		else{
			for(i=0;i<12;i++){
				freqs[i] = replicas[i];
			}
		}
		
		for(int k=0;k<12;k++){
			if(freqs[k]==0){
				for(int p=0;p<model.getSize();p++){
					if(model.getElementAt(p).equals(array[k])){
						model.removeElementAt(p);
					}
				}
			}
		}
		
		vo++;
		this.setContentPane(panel);
		this.panel.add(list);
		this.panel.add(button);
		this.setVisible(true);
		this.setSize(300, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void closeFrame(){
		super.dispose();
	}
	
	class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			int i = 0;
			Pawn selectedPawn = null;
			if(list.getSelectedValue()!=null){
				
				String selected = (String)list.getSelectedValue();
				
				for(int j=0;j<12;j++){
					if(array[j].equals(selected)){
						i = j;
					}
				}
				
				if(freqs[i]==0){
					
				}
				else{
					for(Pawn pawn : pawns){
						if(pawn.getPawnType().equals(selected)){
							selectedPawn = pawn;
						}
					}
					new TheBoard(selectedPawn,pawns,freqs,positions);
					closeFrame();
				}
			}
			
		}
	}
	
	public class NonEditableModel extends DefaultTableModel {

	    NonEditableModel() {
	        super();
	    }

	    @Override
	    public boolean isCellEditable(int row, int column) {
	        return false;
	    }
	}
}
