import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
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
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
import javax.swing.plaf.basic.BasicBorders;
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
    private JLabel loginLabel, outcomeLabel, outcome;
    private Player player1, player2;
    private JFrame frame;
    
    private JPanel panel = new JPanel();
	private JList list = new JList();
	private JButton button = new JButton();
	private ArrayList<Pawn> pawns = new ArrayList<Pawn>();
	private ArrayList<Integer> freqs;
	private DefaultListModel model = new DefaultListModel();
	/**
	 * 
	 */
	private Position[][] positions = new Position[10][10];
	private static int vo;
	private ArrayList<Pawn> bluepawns;
	private Position[][] pos = new Position[10][10];
	private ArrayList<String> array;
	
	private JPanel contentPane = new JPanel();
	private HashMap<String,JButton> btnB = new HashMap<String,JButton>();
	private Pawn thePawn;
	private ArrayList<Integer> frequencies;
	private static int countTurn;
	private String color;
	
	private static Movable attacker = new Movable("","",0);
	private static boolean blueIsOK = false;
	private static boolean redIsOK = false;
	private int blocksToMove = 1;
	
	public MainMenu(){
		
		array = new ArrayList<String>();
		array.add("B");
		array.add("F");
		array.add("10");
		array.add("1");
		array.add("2");
		array.add("3");
		array.add("4");
		array.add("5");
		array.add("6");
		array.add("7");
		array.add("8");
		array.add("9");
		
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
		
		login();
		
	    setExtendedState(JFrame.MAXIMIZED_BOTH); 
	    setUndecorated(true);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setVisible(true);
	}
	
	public MainMenu(ArrayList<Pawn> pawnNames, boolean empty, int[] replicas){
		PawnList(pawnNames,empty);
	}
	
	public void login(){
		setLayout(new BorderLayout(1,1));
	    setContentPane(new JLabel(new ImageIcon("backrounds/backround1.jpg")));
	    setLayout(new BorderLayout(0,0));
	    
	    JLabel create = new JLabel("Create Account");
	    JLabel login = new JLabel("Login");
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
	    login.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    exit.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    
	    //alignment
	    loginLabel.setHorizontalAlignment(JTextField.CENTER);
	    outcomeLabel.setHorizontalAlignment(JTextField.CENTER);
	    username.setHorizontalAlignment(JTextField.CENTER);
	    password.setHorizontalAlignment(JTextField.CENTER);
	    create.setHorizontalAlignment(JTextField.CENTER);
	    login.setHorizontalAlignment(JTextField.CENTER);
	    exit.setHorizontalAlignment(JTextField.CENTER);
	    
	    //font
	    loginLabel.setFont(loginLabel.getFont().deriveFont(35f));
	    outcomeLabel.setFont(outcomeLabel.getFont().deriveFont(35f));
	    username.setFont(username.getFont().deriveFont(35f));
	    password.setFont(password.getFont().deriveFont(35f));
	    create.setFont(create.getFont().deriveFont(35f));
	    login.setFont(login.getFont().deriveFont(35f));
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
		
		JPanel p3 = new JPanel(new BorderLayout());
		p3.setBackground(new Color(230,230,0,80));
		p3.add(login, BorderLayout.CENTER);
		
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
		pane.add(p3);
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
	    JLabel create = new JLabel("Create");
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
	    create.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    login.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    
	    //alignment
	    username.setHorizontalAlignment(JTextField.CENTER);
	    password.setHorizontalAlignment(JTextField.CENTER);
	    passwordCheck.setHorizontalAlignment(JTextField.CENTER);
	    create.setHorizontalAlignment(JTextField.CENTER);
	    login.setHorizontalAlignment(JTextField.CENTER);
	    createLabel.setHorizontalAlignment(JTextField.CENTER);
	    outcomeLabel.setHorizontalAlignment(JTextField.CENTER);
	    
	    //font
	    username.setFont(username.getFont().deriveFont(35f));
	    password.setFont(password.getFont().deriveFont(35f));
	    passwordCheck.setFont(passwordCheck.getFont().deriveFont(35f));
	    create.setFont(create.getFont().deriveFont(35f));
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
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.setBackground(new Color(230,230,0,80));
		p2.add(create, BorderLayout.CENTER);
		
		//add labels to panels
		JPanel pane = new JPanel();
		pane.setBorder(new EmptyBorder(200, 430, 200, 430));
		pane.setLayout(grid);
		pane.add(createLabel);
		pane.add(username);
		pane.add(password);
		pane.add(passwordCheck);
		pane.add(p2);
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
                PawnList(bluepawns,true);
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
            	PlayerList pl = PlayerList.getInstance();
            	pl.savePlayers();
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
	    JLabel overall = new JLabel("Overall"),
	    	   music = new JLabel("Music"),
	    	   effects = new JLabel("Effects"),
	    	   back = new JLabel("Back"),
	    	   apply = new JLabel("Apply");
	    
	    overallText = new JTextField(overallVol + "%");
	    musicText = new JTextField(musicVol + "%");
	    effectsText = new JTextField(effectsVol + "%");
	    //border
	    Border border = BorderFactory.createLineBorder(Color.BLACK);
	    overallText.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    musicText.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    effectsText.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    apply.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    back.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    
	    //alignment
	    DefaultListCellRenderer dlcr = new DefaultListCellRenderer(); 
	    dlcr.setHorizontalAlignment(DefaultListCellRenderer.CENTER);  
	    overallText.setHorizontalAlignment(JTextField.CENTER);
	    musicText.setHorizontalAlignment(JTextField.CENTER);
	    effectsText.setHorizontalAlignment(JTextField.CENTER);
	    overall.setHorizontalAlignment(JTextField.CENTER);
	    music.setHorizontalAlignment(JTextField.CENTER);
	    effects.setHorizontalAlignment(JTextField.CENTER);
	    apply.setHorizontalAlignment(JTextField.CENTER);
	    back.setHorizontalAlignment(JTextField.CENTER);
	    
	    //foreground
	    overall.setForeground(Color.YELLOW);
	    music.setForeground(Color.YELLOW);
	    effects.setForeground(Color.YELLOW);
	    overallText.setForeground(Color.YELLOW);
	    musicText.setForeground(Color.YELLOW);
	    effectsText.setForeground(Color.YELLOW);
	    
	    //backround
	    overallText.setBackground(Color.LIGHT_GRAY);
	    musicText.setBackground(Color.LIGHT_GRAY);
	    effectsText.setBackground(Color.LIGHT_GRAY);
	    
	    //font
	    overallText.setFont(overallText.getFont().deriveFont(35f));
	    musicText.setFont(musicText.getFont().deriveFont(35f));
	    effectsText.setFont(effectsText.getFont().deriveFont(35f));
	    overall.setFont(overall.getFont().deriveFont(40f));
	    music.setFont(music.getFont().deriveFont(40f));
	    effects.setFont(effects.getFont().deriveFont(40f));
	    apply.setFont(apply.getFont().deriveFont(35f));
	    back.setFont(back.getFont().deriveFont(35f));
	    
	    //start listeners
	    apply.addMouseListener(new MouseAdapter() {
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
	    apply.addMouseListener(new MouseAdapter() {
			
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
	    //end listeners
	    
	    GridLayout grid = new GridLayout(0,1);
		grid.setVgap(20);
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBackground(new Color(230,230,0,80));
		p1.add(back, BorderLayout.CENTER);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.setBackground(new Color(230,230,0,80));
		p2.add(apply, BorderLayout.CENTER);
		
		//add labels to panels
		JPanel pane = new JPanel();
		pane.setBorder(new EmptyBorder(90, 420, 90, 420));
		pane.setLayout(grid);
		pane.add(overall);
		pane.add(overallText);
		pane.add(music);
		pane.add(musicText);
		pane.add(effects);
		pane.add(effectsText);
		pane.add(p2);
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
	    table.setBackground(new Color(180, 180, 180, 255));
	    
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
	            fileNameToLoad = listOfFiles[row].getName();
	            if (me.getClickCount() == 2) {
	            	FileInputStream streamIn;
					try {
						streamIn = new FileInputStream("saves/" + fileNameToLoad);
						ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
						Game game = Game.getInstance();
		        	    game.setInstance((Game) objectinputstream.readObject());
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					outcome = new JLabel("Game Started");
					countTurn = -5;
					FinalBoard();
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
	    load.addMouseListener(new MouseAdapter() {
	        public void mousePressed(MouseEvent me) {
	        	if(fileNameToLoad!=null){
	        		FileInputStream streamIn;
					try {
						streamIn = new FileInputStream("saves/" + fileNameToLoad);
						ObjectInputStream objectinputstream = new ObjectInputStream(streamIn);
						Game game = Game.getInstance();
		        	    game.setInstance((Game) objectinputstream.readObject());
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					outcome = new JLabel("Game Started");
					countTurn = -5;
					FinalBoard();
	        	}
	        }
	    });
	    load.addMouseListener(new MouseAdapter() {
			
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
	
	public void nextTurn(){
		setLayout(new BorderLayout(1,1));
	    setContentPane(new JLabel(new ImageIcon("backrounds/Gameplay_BG.png")));
	    setLayout(new BorderLayout(0,0));
	    
	    Game game = Game.getInstance();
	    
	    JLabel message = new JLabel("Next player's turn");
		JLabel ok = new JLabel("OK");
		message.setForeground(Color.YELLOW);
		ok.setForeground(Color.YELLOW);
		
		//font
	    ok.setFont(ok.getFont().deriveFont(35f));
	    message.setFont(message.getFont().deriveFont(50f));
		
		//border
	    Border border = BorderFactory.createLineBorder(Color.YELLOW);
	    ok.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
		
		//alignment
	    ok.setHorizontalAlignment(JTextField.CENTER);
	    message.setHorizontalAlignment(JTextField.CENTER);
		
		//start listener
		ok.addMouseListener(new MouseAdapter() {
	        public void mousePressed(MouseEvent me) {
	        	FinalBoard();
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
	
	public void PawnList(ArrayList<Pawn> pawnNames, boolean empty) {
		
		array = new ArrayList<String>();
		array.add("B");
		array.add("F");
		array.add("10");
		array.add("1");
		array.add("2");
		array.add("3");
		array.add("4");
		array.add("5");
		array.add("6");
		array.add("7");
		array.add("8");
		array.add("9");
		
		contentPane = new JPanel();
		frame = this;
		frequencies = new ArrayList<Integer>();
		freqs = new ArrayList<Integer>();
		vo = 0;
		countTurn = 1;
		color = "BLUE";
		
		setLayout(new BorderLayout(1,1));
	    setContentPane(new JLabel(new ImageIcon("backrounds/Gameplay_BG.png")));
	    setLayout(new BorderLayout(0,0));
	    
		int i = 0;
		int j = 0;
		int counter = 0;
		Pawn somePawn = new Pawn("","");
		Game game = Game.getInstance();
		game.setPlayers(player1, player2);
		
		positions = game.getBoard();
		
		if(vo==0){
			for(i=0;i<10;i++){
				for(j=0;j<10;j++){
					positions[i][j] = new Position();
					positions[i][j].addPawnToPosition(somePawn);
				}
			}
		}
		
		Collections.sort(bluepawns);
		ArrayList<String> pawnTypes = new ArrayList<String>();
		
		for(Pawn pawn : pawnNames){
			pawnTypes.add(pawn.getPawnType());
		}
		
		HashSet<String> noMultiples = new HashSet<String>(pawnTypes);
		
		model = new DefaultListModel();
		
		if(vo==0){
			for(String name : noMultiples){
				ImageIcon icon = new ImageIcon(getClass().getResource("/images/" + color + name + ".png"));
			    Image img = icon.getImage() ;  
			    Image newimg = img.getScaledInstance(75, 75,  java.awt.Image.SCALE_SMOOTH ) ;  
			    icon = new ImageIcon( newimg );
				model.addElement(icon);
			}
		}
		pawns = pawnNames;
		list.setModel(model);
		
		i=0;
		for(String name : noMultiples) {
			counter = Collections.frequency(pawnTypes, name);
			freqs.add(counter);
		}
		
		frequencies = freqs;
		
		panel.setLayout(new FlowLayout());
		panel.add(list);
		panel.setBorder(new EmptyBorder(10,0,0,0));
		
		vo++;
		setLayout(new BorderLayout());
		add(panel, BorderLayout.EAST);
		panel.setOpaque(false);
		
		frequencies = freqs;
		
		JButton button;
		this.contentPane.setLayout(new GridLayout(10, 10));
		
		ButtonListener listener1 = new ButtonListener();
		for(i=0;i<10;i++){
			for(j=0;j<10;j++){  //ekana tis 2 for mia. Kanei new button, prosthetei listener kai to vazei sto hashmap
				button = new JButton();//kai sto contentPane.
				String colorr = positions[i][j].getPawnOnPosition().getPawnColor();
				String type = positions[i][j].getPawnOnPosition().getPawnType();
				
				button.addActionListener(listener1);
				btnB.put("B" + i + j, button);
				contentPane.add(button);
				
				if(countTurn<13){
					if(i<6)
						button.setEnabled(false);
					else{
						if(!colorr.equals("")){
							button.setEnabled(false);
							ImageIcon icon = new ImageIcon(getClass().getResource("/images/" + thePawn.getPawnColor() + thePawn.getPawnType() + ".png"));
						    Image img = icon.getImage() ;  
						    Image newimg = img.getScaledInstance(button.getWidth(), button.getHeight(),  java.awt.Image.SCALE_SMOOTH ) ;  
						    icon = new ImageIcon( newimg );
						    button.setIcon(icon);
						}
					}
				}
				else if(countTurn<25){
					color = "RED";
					if(i>3){
						if(i>3){
							if(!colorr.equals("")){
								button.setBackground(Color.CYAN);
							}
							button.setEnabled(false);
						}
						button.setEnabled(false);
					}
					else{
						if(!colorr.equals("")){
							button.setEnabled(false);
							ImageIcon icon = new ImageIcon(getClass().getResource("/images/" + thePawn.getPawnColor() + thePawn.getPawnType() + ".png"));
						    Image img = icon.getImage() ;  
						    Image newimg = img.getScaledInstance(button.getWidth(), button.getHeight(),  java.awt.Image.SCALE_SMOOTH ) ;  
						    icon = new ImageIcon( newimg );
						    button.setIcon(icon);
						}
					}
				}
			}
		}
		contentPane.setBorder(new EmptyBorder(20, 50, 0, 50));
		add(contentPane, BorderLayout.CENTER);
		contentPane.setOpaque(false);
		
		JLabel exit = new JLabel("EXIT");
		
		//font
	    exit.setFont(exit.getFont().deriveFont(35f));
		
		//border
	    Border border = BorderFactory.createLineBorder(Color.BLACK);
	    exit.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
		
		//alignment
	    exit.setHorizontalAlignment(JTextField.CENTER);
		
		//start listener
		exit.addMouseListener(new MouseAdapter() {
	        public void mousePressed(MouseEvent me) {
	        	main();
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
	    //end listener
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBackground(new Color(230,230,0,80));
		p1.add(exit, BorderLayout.WEST);
		add(p1, BorderLayout.PAGE_END);
		
		JPanel panel2 = new JPanel();
		panel2.setBorder(new EmptyBorder(0, 0, 10, 800));
		panel2.add(p1);
		panel2.setOpaque(false);
	    
		add(panel2, BorderLayout.PAGE_END);
		
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	class ButtonListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Position[][] pos = Game.getInstance().getBoard();
			
			int p = list.getSelectedIndex();
			Pawn selectedPawn = null;
			
			if(p!=-1){
				
				if(countTurn>12){
					pawns = new ArrayList<Pawn>();
					for(int i=1;i<11;i++){
						switch(i){
						case 1:
						case 9:
						case 10:
							pawns.add(new Movable(Integer.toString(i),"RED",1));
							break;
						case 8:
							for(int k=0;k<2;k++){
								pawns.add(new Movable(Integer.toString(i),"RED",1));
							}
							break;
						case 7:
							for(int k=0;k<3;k++){
								pawns.add(new Movable(Integer.toString(i),"RED",1));
							}
							break;
						case 4:
						case 5:
						case 6:
							for(int k=0;k<4;k++){
								pawns.add(new Movable(Integer.toString(i),"RED",1));
							}
							break;
						case 3:
							for(int k=0;k<5;k++){
								pawns.add(new Movable(Integer.toString(i),"RED",1));
							}
							break;
						case 2:
							for(int k=0;k<8;k++){
								pawns.add(new Movable(Integer.toString(i),"RED",9));
							}
							break;
						}
					}
					pawns.add(new Immovable("F","RED"));
					for(int k=0;k<6;k++){
						pawns.add(new Immovable("B","RED"));
					}
				}
				
				for(Pawn pawn : pawns){
					if(pawn.getPawnType().equals(array.get(p))){
						selectedPawn = pawn;
					}
				}
				thePawn = selectedPawn;
				
				//detects which button was pressed	
				int i;
				int y = 0;
				int j = 0;
				boolean OK = false;
				
				for(String key : btnB.keySet()){
					if(arg0.getSource().equals(btnB.get(key))){
						if(countTurn<25){
							
							int index = 0;
							for(int count=0;count<array.size();count++){
								if(array.get(count).equals(thePawn.getPawnType())){
									index = count;
								}
							}
							frequencies.get(index);
							frequencies.set(index, (frequencies.get(index)-1));
							
							ImageIcon icon = new ImageIcon(getClass().getResource("/images/" + thePawn.getPawnColor() + thePawn.getPawnType() + ".png"));
						    Image img = icon.getImage() ;  
						    Image newimg = img.getScaledInstance( btnB.get(key).getWidth(), btnB.get(key).getHeight(),  java.awt.Image.SCALE_SMOOTH ) ;  
						    icon = new ImageIcon( newimg );
						    btnB.get(key).setIcon(icon);
							char[] abc = key.toCharArray();
							int xz = abc[1]-48,yz = abc[2]-48;
							positions[xz][yz].addPawnToPosition(thePawn);
							btnB.get(key).setEnabled(false);//apotrepei to xrhsth apo to na topo8ethsei se ena button ola ta pionia idias aksias tou
							
							if(frequencies.get(index)==0){
								if(countTurn!=12){
									if(countTurn!=24){
										model.removeElementAt(index);
										array.remove(index);
										countTurn++;
										frequencies.remove(index);
									}
									else{
										model.removeElementAt(index);
										array.remove(index);
										countTurn++;
										frequencies.remove(index);
										countTurn = 0;
										outcome = new JLabel("Game Started");
										FinalBoard();
									}
								}
								else{
									model.removeElementAt(index);
									array.remove(index);
									countTurn++;
									frequencies.remove(index);
									color = "RED";
									
									array.add("B");
									array.add("F");
									array.add("10");
									array.add("1");
									array.add("2");
									array.add("3");
									array.add("4");
									array.add("5");
									array.add("6");
									array.add("7");
									array.add("8");
									array.add("9");
									
									Collections.sort(bluepawns);
									ArrayList<String> pawnTypes = new ArrayList<String>();
									
									for(Pawn pawn : bluepawns){
										pawnTypes.add(pawn.getPawnType());
									}
									
									HashSet<String> noMultiples = new HashSet<String>(pawnTypes);
									
									for(String name : noMultiples) {
										counter = Collections.frequency(pawnTypes, name);
										freqs.add(counter);
									}
									
									for(String name : noMultiples){
										icon = new ImageIcon(getClass().getResource("/images/" + color + name + ".png"));
									    img = icon.getImage() ;  
									    newimg = img.getScaledInstance(75, 75,  java.awt.Image.SCALE_SMOOTH ) ;  
									    icon = new ImageIcon( newimg );
										model.addElement(icon);
									}
									
									String s;
									JButton b;
									for(int row=0;row<10;row++){
										for(int col=0;col<10;col++){
											s = "B" + row + col;
											btnB.get(s).setEnabled(true);
											b = btnB.get(s);
											
											String colorr = positions[row][col].getPawnOnPosition().getPawnColor();
											String type = positions[row][col].getPawnOnPosition().getPawnType();
											
											if(row>3){
												if(row>3){
													if(!colorr.equals("")){
														b.setBackground(Color.CYAN);
														b.setIcon(null);
													}
													b.setEnabled(false);
												}
												b.setEnabled(false);
											}
										}
									}
									
									SwingUtilities.updateComponentTreeUI(frame);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void FinalBoard(){
		
		frame = this;
		
		setLayout(new BorderLayout(1,1));
	    setContentPane(new JLabel(new ImageIcon("backrounds/Gameplay_BG.png")));
	    setLayout(new BorderLayout(0,0));
		
		contentPane = new JPanel();
		pawns = new ArrayList<Pawn>();
		
		//alignment
	    outcome.setHorizontalAlignment(JTextField.CENTER);
	    
	    //font
	    outcome.setFont(loginLabel.getFont().deriveFont(35f));
		
		blocksToMove = 1;
		int i,j;
		Pawn somePawn = new Pawn("","");
		
		Game game = Game.getInstance();
		Position[][] pos = game.getBoard();
		game.firstSave();
		
		if(!game.gameStatus()){
			positions = new Position[10][10];
			btnB = new HashMap<String,JButton>();
			for(i=0;i<10;i++){
				for(j=0;j<10;j++){
					if(pos[i][j].getPawnOnPosition().getPawnType().equals("")){
						somePawn = new Pawn("","");
						positions[i][j] = new Position();
						positions[i][j].addPawnToPosition(somePawn);
						positions[i][j].setAccess(true);
					}
					else{
						positions[i][j] = new Position();
						positions[i][j].addPawnToPosition(pos[i][j].getPawnOnPosition());
						positions[i][j].setAccess(pos[i][j].getAccess());
					}
				}
			}
			
			positions[4][2].setAccess(false);
			positions[4][2].getPawnOnPosition().setPawnType("L");
			positions[4][3].setAccess(false);
			positions[4][3].getPawnOnPosition().setPawnType("L");
			positions[5][2].setAccess(false);
			positions[5][2].getPawnOnPosition().setPawnType("L");
			positions[5][3].setAccess(false);
			positions[5][3].getPawnOnPosition().setPawnType("L");
			positions[4][6].setAccess(false);
			positions[4][6].getPawnOnPosition().setPawnType("L");
			positions[4][7].setAccess(false);
			positions[4][7].getPawnOnPosition().setPawnType("L");
			positions[5][6].setAccess(false);
			positions[5][6].getPawnOnPosition().setPawnType("L");
			positions[5][7].setAccess(false);
			positions[5][7].getPawnOnPosition().setPawnType("L");
			
			if(countTurn==-5){
				countTurn = game.getPlayersTurn();
			}
			else{
				countTurn++;
			}
			JButton button;
			contentPane.setLayout(new GridLayout(10, 10));
			ButtonListener1 listener = new ButtonListener1();
			
			for(i=0;i<10;i++){
				for(j=0;j<10;j++){  //ekana tis 2 for mia. Kanei new button, prosthetei listener kai to vazei sto hashmap
					button = new JButton();//kai sto contentPane.
					button.addActionListener(listener);
					btnB.put("B" + i + j, button);
					contentPane.add(button);
				}
			}
			this.setLayout(new BorderLayout());
			contentPane.setBorder(new EmptyBorder(20, 50, 0, 220));
			this.add(contentPane, BorderLayout.CENTER);
			contentPane.setOpaque(false);
			
			SwingUtilities.updateComponentTreeUI(this);
			
			for(i=0;i<10;i++){
				for(j=0;j<10;j++){
					if(positions[i][j].getAccess()){
						if(positions[i][j].getPawnOnPosition().getPawnColor().equals("BLUE")){
							if(countTurn%2==1){
								JButton b = btnB.get("B" + i + j);
								ImageIcon icon = new ImageIcon(getClass().getResource("/images/" + positions[i][j].getPawnOnPosition().getPawnColor() + positions[i][j].getPawnOnPosition().getPawnType() + ".png"));
							    Image img = icon.getImage() ;  
							    Image newimg = img.getScaledInstance(b.getWidth(), b.getHeight(),  java.awt.Image.SCALE_SMOOTH ) ;  
							    icon = new ImageIcon( newimg );
							    b.setIcon(icon);
							}
							else{
								JButton b = btnB.get("B" + i + j);
								b.setBackground(Color.CYAN);
								b.setIcon(null);
							}
							
						}
						else if(positions[i][j].getPawnOnPosition().getPawnColor().equals("RED")){
							if(countTurn%2==0){
								JButton b = btnB.get("B" + i + j);
								ImageIcon icon = new ImageIcon(getClass().getResource("/images/" + positions[i][j].getPawnOnPosition().getPawnColor() + positions[i][j].getPawnOnPosition().getPawnType() + ".png"));
							    Image img = icon.getImage() ;  
							    Image newimg = img.getScaledInstance(b.getWidth(), b.getHeight(),  java.awt.Image.SCALE_SMOOTH ) ;  
							    icon = new ImageIcon( newimg );
							    b.setIcon(icon);
							}
							else{
								JButton b = btnB.get("B" + i + j);
								b.setBackground(Color.RED);
								b.setIcon(null);
							}
						}
					}
					else{
						JButton b = btnB.get("B" + i + j);
						b.setEnabled(false);
						b.setBackground(Color.BLUE);
					}
				}
			}
		}
		else{
			JButton button;
			contentPane.setLayout(new GridLayout(10, 10));
			ButtonListener1 listener = new ButtonListener1();
			
			for(i=0;i<10;i++){
				for(j=0;j<10;j++){  //ekana tis 2 for mia. Kanei new button, prosthetei listener kai to vazei sto hashmap
					button = new JButton();//kai sto contentPane.
					button.addActionListener(listener);
					btnB.put("B" + i + j, button);
					contentPane.add(button);
				}
			}
			this.setLayout(new BorderLayout());
			contentPane.setBorder(new EmptyBorder(20, 50, 0, 170));
			this.add(contentPane, BorderLayout.CENTER);
			contentPane.setOpaque(false);
			SwingUtilities.updateComponentTreeUI(this);
			
			for(i=0;i<10;i++){
				for(j=0;j<10;j++){
					btnB.get("B" + i + j).setEnabled(false);
					JButton b = btnB.get("B" + i + j);
					b.setBackground(null);
					if(positions[i][j].getPawnOnPosition()!=null){
						if(!positions[i][j].getPawnOnPosition().getPawnColor().equals("")){
							ImageIcon icon = new ImageIcon(getClass().getResource("/images/" + positions[i][j].getPawnOnPosition().getPawnColor() + positions[i][j].getPawnOnPosition().getPawnType() + ".png"));
						    Image img = icon.getImage() ;  
						    Image newimg = img.getScaledInstance(b.getWidth(), b.getHeight(),  java.awt.Image.SCALE_SMOOTH ) ;  
						    icon = new ImageIcon( newimg );
						    b.setIcon(icon);
						}
					}
				}
				btnB.get("B" + "4" + "2").setBackground(Color.BLUE);
				btnB.get("B" + "4" + "3").setBackground(Color.BLUE);
				btnB.get("B" + "4" + "6").setBackground(Color.BLUE);
				btnB.get("B" + "4" + "7").setBackground(Color.BLUE);
				
				btnB.get("B" + "5" + "2").setBackground(Color.BLUE);
				btnB.get("B" + "5" + "3").setBackground(Color.BLUE);
				btnB.get("B" + "5" + "6").setBackground(Color.BLUE);
				btnB.get("B" + "5" + "7").setBackground(Color.BLUE);
			}
		}
		
		JLabel exit = new JLabel("EXIT");
		JLabel save = new JLabel("SAVE");
		
		//font
	    exit.setFont(exit.getFont().deriveFont(35f));
	    save.setFont(save.getFont().deriveFont(35f));
	    
	    //border
	    Border border = BorderFactory.createLineBorder(Color.BLACK);
	    exit.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
	    save.setBorder(BorderFactory.createCompoundBorder(border, 
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
		
		//alignment
	    exit.setHorizontalAlignment(JTextField.CENTER);
	    save.setHorizontalAlignment(JTextField.CENTER);
		
		//start listener
		exit.addMouseListener(new MouseAdapter() {
	        public void mousePressed(MouseEvent me) {
	        	main();
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
		save.addMouseListener(new MouseAdapter() {
	        public void mousePressed(MouseEvent me) {
	        	Game game = Game.getInstance();
	        	game.saveGame(countTurn%2);
	        	outcome.setText("Game " + game.getName().replace("saves/GAME ", "").replace(".ser", "") + " saved");
	        }
	    });
		save.addMouseListener(new MouseAdapter() {
			
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
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBackground(new Color(230,230,0,80));
		p1.add(exit, BorderLayout.CENTER);
		
		JPanel p2 = new JPanel(new BorderLayout());
		p2.setBackground(new Color(230,230,0,80));
		p2.add(save, BorderLayout.CENTER);
		
		JPanel p3 = new JPanel(new BorderLayout());
		p3.setBackground(new Color(230,230,200,80));
		p3.add(outcome, BorderLayout.CENTER);
		
		JPanel panel1 = new JPanel();
		panel1.setBorder(new EmptyBorder(0, 0, 10, 100));
		panel1.add(p1);
		panel1.setOpaque(false);
		
		JPanel panel2 = new JPanel();
		panel2.setBorder(new EmptyBorder(0, 0, 10, 100));
		panel2.add(p2);
		panel2.setOpaque(false);
		
		JPanel panel4 = new JPanel();
		panel4.setBorder(new EmptyBorder(0, 0, 10, 100));
		panel4.add(p3);
		panel4.setOpaque(false);
		
		JPanel panel3 = new JPanel();
		panel3.setBorder(new EmptyBorder(0, 0, 10, 0));
		panel3.add(panel1);
		panel3.add(panel2);
		panel3.add(panel4);
		panel3.setOpaque(false);
		
		add(panel3, BorderLayout.PAGE_END);
		
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public boolean blockManager(String key, Movable attacker){
	    ArrayList<String> blocks = new ArrayList<String>();
	    boolean OK=false;
	    int i,j;
	    char fcoords[] = key.toCharArray();
	    i=fcoords[1]-48;
	    j=fcoords[2]-48;
	    int k=i-1,l=j-1;
	    BasicBorders.ButtonBorder border = new BasicBorders.ButtonBorder(Color.YELLOW,Color.YELLOW,Color.YELLOW,Color.YELLOW);
	    String thiscolor = attacker.getPawnColor();
	    String othercolor;
	    int left = 0;
	    int right = 9;
	    int up = 0;
	    int down = 9;
	    
	    if(thiscolor.equals("BLUE"))
	    	othercolor = "RED";
	    else
	    	othercolor = "BLUE";
	    
	    if(attacker.getBlocksPawnCanMove()==1){
	        blocks.add("B" + k + j);
	        k+=2;
	        blocks.add("B" + k + j);
	        blocks.add("B" + i + l);
	        l+=2;
	        blocks.add("B" + i + l);
	    }
	    else{
	        if(i!=0){
	        	for(k=i-1;k!=0;k--){
	        		if(!positions[k][j].getPawnOnPosition().getPawnType().equals("")){
	        			up = k;
	        			break;
	        		}
	        	}
	        }
	        
	        if(i!=9){
	        	for(k=i+1;k<10;k++){
	        		if(!positions[k][j].getPawnOnPosition().getPawnType().equals("")){
	        			down = k;
	        			break;
	        		}
	        	}
	        }
	        
	        if(j!=0){
	        	for(l=j-1;l!=0;l--){
	        		if(!positions[i][l].getPawnOnPosition().getPawnType().equals("")){
	        			left = l;
	        			break;
	        		}
	        	}
	        }
	        
	        if(j!=9){
	        	for(l=j+1;l<10;l++){
	        		if(!positions[i][l].getPawnOnPosition().getPawnType().equals("")){
	        			right = l;
	        			break;
	        		}
	        	}
	        }
	        
	        for(k=up;k<=down;k++){
	            blocks.add("B" + k + j);
	            for(l=left;l<=right;l++){
		        	blocks.add("B" + i + l);
		    	}
	        }
	    }
	    
	    for(String akey : btnB.keySet()){
	    	if(blocks.contains(akey)){
	    		char[] fcoords2 = akey.toCharArray();
		       	if(positions[fcoords2[1]-48][fcoords2[2]-48].getPawnOnPosition().getPawnColor().equals(thiscolor)
		       			||positions[fcoords2[1]-48][fcoords2[2]-48].getPawnOnPosition().getPawnType().equals("L")){
		       		btnB.get(akey).setEnabled(false);
		       	}
		       	else{
		       		btnB.get(akey).setEnabled(true);
		       		btnB.get(akey).setBorder(border);
		       		OK=true;
		       	}
		    }
	    	
	        else{
	        	btnB.get(akey).setEnabled(false);
	        }
	        	
	    }
	   
	    return OK;
	}
	//listener
	class ButtonListener1 implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int x = 0;
			String playerRED = "RED";
			String playerBLUE = "BLUE";
			boolean OK = false;
			
			if(!blueIsOK&&!redIsOK){
				for(String key : btnB.keySet()){
					char[] fcoords = key.toCharArray();
					if(arg0.getSource().equals(btnB.get(key))){
						//EDW KSEKINA H PARTIDA  
						//POLY APLA:EFOSON KSEKINA PANTA O BLUE TYPOS OTAN O GYROS EINAI MONOS(TO countTurn) TOTE 8A PREPEI NA EPILEKSEI 8ESH
						//OPOU EXEI DIKO TOY PIONI, GIA NA KSEKINHSEI O GYROS, DHLADH TO PRVTO BUTTON POU EPILEGETAI NA EINAI DIKO TOY. TA IDIA
						//ISXYOUN KAI GIA TO RED TYPO, ME TH DIAFORA OTI O GYROS PREPEI NA EINAI ZYGOS ARITHMOS
						if(countTurn%2==1){
							if(positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition().getPawnColor().equals("BLUE")){
								for(String key2 : btnB.keySet()){
									char[] fcoords2 = key2.toCharArray();
									if(positions[fcoords2[1]-48][fcoords2[2]-48].getPawnOnPosition().getPawnColor().equals("BLUE")
									||positions[fcoords2[1]-48][fcoords2[2]-48].getPawnOnPosition().getPawnType().equals("L")){//ME L SYMBOLIZOUME TH LIMNH
										btnB.get(key2).setEnabled(false);
									}
									else{
										btnB.get(key2).setEnabled(true);
									}
								}
								if(!positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition().getPawnType().equals("B")&&
										!positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition().getPawnType().equals("F")){
									if(positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition().getPawnType().equals("2")){
										blocksToMove = 9;
									}
									attacker = new Movable(positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition().getPawnType(),"BLUE",blocksToMove);
									OK = blockManager(key,attacker);
									if(!OK){
										for(String akey : btnB.keySet()){
											btnB.get(akey).setEnabled(true);
										}
									}
									else{
										positions[fcoords[1]-48][fcoords[2]-48].removePawnFromPosition();
										blueIsOK=true;
										
									}
								}
								else{
									for(String akey : btnB.keySet()){
										btnB.get(akey).setEnabled(true);
									}
								}
							}
							else{
							}
						}
						else{
							if(positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition().getPawnColor().equals("RED")){
								for(String key2 : btnB.keySet()){
									char[] fcoords2 = key.toCharArray();
									if(positions[fcoords2[1]-48][fcoords2[2]-48].getPawnOnPosition().getPawnColor().equals("RED")
									||positions[fcoords2[1]-48][fcoords2[2]-48].getPawnOnPosition().getPawnType().equals("L"))//ME L SYMBOLIZOUME TH LIMNH
										btnB.get(key2).setEnabled(false);
									else{
										btnB.get(key2).setEnabled(true);
									}
								}
								if(!positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition().getPawnType().equals("B")&&
										!positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition().getPawnType().equals("F")){
									if(positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition().getPawnType().equals("2"))
										blocksToMove = 9;
									attacker = new Movable(positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition().getPawnType(),"RED",blocksToMove);
									OK = blockManager(key,attacker);
									if(!OK){
										for(String akey : btnB.keySet()){
											btnB.get(akey).setEnabled(true);
										}
									}
									else{
										positions[fcoords[1]-48][fcoords[2]-48].removePawnFromPosition();
										redIsOK=true;
									}
								}
								else{
									for(String akey : btnB.keySet()){
										btnB.get(akey).setEnabled(true);
									}
								}
							}
							else{
							}
						}
					}
				}
			}
			else{
				if(blueIsOK){
					for(String key : btnB.keySet()){
						if(arg0.getSource().equals(btnB.get(key))){
							char[] fcoords = key.toCharArray();
							int i=fcoords[1]-48;
							int j=fcoords[2]-48;
							Pawn enemy = new Pawn("","");
							if(positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition().getPawnColor().equals("RED")){
								enemy = positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition();
								x = attacker.attackEnemyPawn(enemy);
								switch(x){
								case 1:
									positions[i][j].addPawnToPosition(attacker);
									outcome = new JLabel(attacker.getPawnColor() + attacker.getPawnType() + " Won " + enemy.getPawnColor() + enemy.getPawnType() + ".");
									nextTurn();
									break;
								case 0:
									positions[i][j].removePawnFromPosition();
									outcome = new JLabel("Both " + attacker.getPawnColor() + attacker.getPawnType() + " and " + enemy.getPawnColor() + enemy.getPawnType() + " lost.");
									nextTurn();
									break;
								case 2:
									outcome = new JLabel(Game.getInstance().getPlayer1().getName() + " Wins");
									Game.getInstance().setBoard(positions);
									Game game = Game.getInstance();
									game.endGame();
									nextTurn();
									Game.getInstance().getPlayer1().setFastestRound(game.getTime());
									break;
								default:
									outcome = new JLabel(attacker.getPawnColor() + attacker.getPawnType() + " Lost to " + enemy.getPawnColor() + enemy.getPawnType() + ".");
									nextTurn();
									break;
								}
							}
							else{
								positions[i][j].addPawnToPosition(attacker);
								Game.getInstance().setBoard(positions);
								nextTurn();
							}
							attacker = new Movable("","",0);
						}
					}
					Game.getInstance().setBoard(positions);
					blueIsOK=false;
				}
				else if(redIsOK){
					for(String key : btnB.keySet()){
						if(arg0.getSource().equals(btnB.get(key))){
							char[] fcoords = key.toCharArray();
							int i=fcoords[1]-48;
							int j=fcoords[2]-48;
							Pawn enemy = new Pawn("","");
							if(positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition().getPawnColor().equals("BLUE")){
								enemy = positions[fcoords[1]-48][fcoords[2]-48].getPawnOnPosition();
								x = attacker.attackEnemyPawn(enemy);
								switch(x){
								case 1:
									positions[i][j].addPawnToPosition(attacker);
									outcome = new JLabel(attacker.getPawnColor() + attacker.getPawnType() + " Won " + enemy.getPawnColor() + enemy.getPawnType() + ".");
									nextTurn();
									break;
								case 0:
									positions[i][j].removePawnFromPosition();
									outcome = new JLabel("Both " + attacker.getPawnColor() + attacker.getPawnType() + " and " + enemy.getPawnColor() + enemy.getPawnType() + " lost.");
									nextTurn();
									break;
								case 2:
									outcome = new JLabel(Game.getInstance().getPlayer2().getName() + " Wins");
									Game.getInstance().setBoard(positions);
									Game game = Game.getInstance();
									game.endGame();
									nextTurn();
									break;
								default:
									outcome = new JLabel(attacker.getPawnColor() + attacker.getPawnType() + " Lost to " + enemy.getPawnColor() + enemy.getPawnType() + ".");
									nextTurn();
									break;
								}
							}
							else{
								positions[i][j].addPawnToPosition(attacker);
								Game.getInstance().setBoard(positions);
								nextTurn();
							}
							attacker = new Movable("","",0);
						}
					}
					redIsOK=false;
					Game.getInstance().setBoard(positions);
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
