package com.termproject;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Game  implements KeyListener,ActionListener {
	private static final int SCALE = 1,WIDTH = 900 * SCALE, HEIGHT = SCALE *WIDTH / 16 * 9;
	private Motor motor;
	private Background road;
	private Background bg;
	
	private JLayeredPane paneB;
	private JLabel loginLabel;
	private ImageIcon img = new ImageIcon("images/road(0).png");
	private boolean loggedin = false;
	
	private static JFrame frame;
	private int i=0;
	private String username = "",passwordd = "";
	private static String loggedinUser ="";
	
	
	public Game(String string) {
		frame = new JFrame(string);
	}


	/**
	 * 
	 */
	private File file = new File("User.txt");
	private File  scores = new File("Scores.txt");
	
	private static int checkLR;
	private static int checkUD;

	public void keyPressed(KeyEvent event) {
		
		String whichKey=KeyEvent.getKeyText(event.getKeyCode());
		if(whichKey.compareTo("Left")==0)
		{
			
			setCheckLR(2);

		}else if(whichKey.compareTo("Right")==0){
			setCheckLR(1);

		}else if(whichKey.compareTo("Up")==0){
			setCheckUD(1);

		}else if(whichKey.compareTo("Down")==0){
			setCheckUD(-1);
		}else{		
		}	
		
		
		
	}
	
	
	
	
	

	
	@SuppressWarnings("deprecation")
	public void firstWindow() {
		
		JMenuBar menuBar = new JMenuBar();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setSize(WIDTH, HEIGHT + menuBar.getHeight());
		frame.setLocationRelativeTo(null);
		
		frame.setJMenuBar(menuBar);
		
		
		JMenu first = new JMenu("Game");
		menuBar.add(first);
		JMenuItem start = new JMenuItem("Start");
		first.add(start);
		start.setActionCommand("Start");
		start.addActionListener(this);
		JMenuItem pause = new JMenuItem("Pause");
		first.add(pause);
		pause.setActionCommand("Pause");
		pause.addActionListener(this);
		JMenuItem restart = new JMenuItem("Restart");
		first.add(restart);
		restart.setActionCommand("Restart");
		restart.addActionListener(this);
		JMenu second = new JMenu("User");
		menuBar.add(second);
		JMenuItem register = new JMenuItem("Register");
		second.add(register);
		register.setActionCommand("Register");
		register.addActionListener(this);
		JMenuItem login = new JMenuItem("Login");
		second.add(login);
		login.setActionCommand("Login");
		login.addActionListener(this);
		JMenuItem quit = new JMenuItem("Quit");
		quit.setActionCommand("Quit");
		quit.addActionListener(this);
		menuBar.add(quit);
		
		
		JLabel hangon = new JLabel(new ImageIcon("images/hangon.png"));
		hangon.setBounds(0, 0, frame.getWidth(), frame.getHeight());
		
		loginLabel = new JLabel("Not Logged In    Login to Play");
		loginLabel.setForeground(Color.YELLOW);
		loginLabel.setFont(new Font("Algerian", Font.BOLD, 15));
		loginLabel.setBounds(10, 10, 300, 20);
		
		paneB = new JLayeredPane();
		paneB.add(loginLabel, new Integer(2));
		paneB.add(hangon, new Integer(1));
		paneB.setBounds(0,0,900,507);
		frame.add(paneB);
		frame.setVisible(true);
	}
	
	
	
	@SuppressWarnings("deprecation")
	public void createGame() {
		JMenuBar menuBar = new JMenuBar();
		
		
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setSize(WIDTH, HEIGHT + menuBar.getHeight());
		frame.setLocationRelativeTo(null);
		
		
		
		JMenu first = new JMenu("Game");
		menuBar.add(first);
		
		JMenuItem start = new JMenuItem("Start");
		first.add(start);
		
		start.setActionCommand("Start");
		start.addActionListener(this);
		
		JMenuItem pause = new JMenuItem("Pause");
		first.add(pause);
		pause.setActionCommand("Pause");
		pause.addActionListener(this);
		
		JMenuItem restart = new JMenuItem("Restart");
		first.add(restart);
		restart.setActionCommand("Restart");
		restart.addActionListener(this);
				
		JMenuItem quit = new JMenuItem("Quit");
		quit.setActionCommand("Quit");
		quit.addActionListener(this);
		menuBar.add(quit);
		
	
		
		
		motor = new Motor();
		motor.getSpeedLabel().setBounds(700, 0, 180, 24);
		motor.getTimeLabel().setBounds(400,0,140,24);
		motor.setLocation((frame.getWidth()-motor.getWidth())/2,(frame.getHeight()-motor.getHeight())-15);
		motor.getMeterLabel().setBounds(0, 0, 250, 24);
		
		road = new Background(img,3824,314);
		road.setLocation(-1455,193);
		
		
		//AiPlayer ai = new AiPlayer();
		//ai.tryUser(road);
		
		bg = new Background(new ImageIcon("images/Bakgg.png"),3824,506);
		bg.setLocation(-1800,0);
		
		
		road.mvroad(motor);
		motor.animation(road);
		motor.time(road);
		motor.speed(road);
		bg.mvbg();
		
		paneB = new JLayeredPane();
		//paneB.add(ai,new Integer(4));
		paneB.add(motor,new Integer(4));
		paneB.add(road, new Integer(3));
		paneB.add(bg,new Integer(1));
		paneB.add(motor.getSpeedLabel(), new Integer(2));
		paneB.add(motor.getMeterLabel(), new Integer(2));
		paneB.add(motor.getTimeLabel(), new Integer(2));
		paneB.setBounds(0, 0, 3824, 506);
		
		frame.setJMenuBar(menuBar);
		frame.add(paneB);
		frame.addKeyListener(this);
		frame.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		
		Game game = new Game("Hang on");
		
		game.firstWindow();
		
		
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		String whichKey=KeyEvent.getKeyText(e.getKeyCode());
		if(whichKey.compareTo("Right")==0 || whichKey.compareTo("Left")==0){
			setCheckLR(0);
		}
		if(whichKey.compareTo("Up")==0 || whichKey.compareTo("Down")==0){
			setCheckUD(2);
			setCheckLR(0);
		}
		
		
	}
	
	
	
	public static void gameRestart() {
		setCheckLR(10);
		setCheckUD(10);
		
		frame.dispose();
		Game game = new Game("Hang on");
		
		game.createGame();
		
	}


 	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
 			
			String command = e.getActionCommand();
			System.out.println(command);
			if(command.equals("Start")){
				if(loggedin==true)
					gameRestart();
				
			}
			if(command.equals("Restart")){
				gameRestart();
			}
			if(command.equals("Pause")){
				i++;
				if(i%2==1) {
					System.out.println(road.getFlag());
					road.setPauseflag(true);
					System.out.println("true " + i);
				}
				else {
					System.out.println(road.getFlag());
					road.setPauseflag(false);
					System.out.println("false " + i);
				}
			}
			
			if(command.equals("Register")){
				
				FileWriter fr = null;
				try {
					fr = new FileWriter(file, true);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				BufferedWriter br = new BufferedWriter(fr);
				PrintWriter pr = new PrintWriter(br);
				
			
				
				JLabel label_login = new JLabel("Username:");
				JTextField login = new JTextField();
				 
				JLabel label_password = new JLabel("Password:");
				JPasswordField password = new JPasswordField();
				 
				Object[] array = { label_login,  login, label_password, password };
				 
				int res = JOptionPane.showConfirmDialog(null, array, "Register", 
				        JOptionPane.OK_CANCEL_OPTION,
				        JOptionPane.PLAIN_MESSAGE);
				if (res == JOptionPane.OK_OPTION) {
				    pr.println(login.getText().trim() + "/" + new String(password.getPassword())+"/");
				}
				pr.close();
				try {
					br.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					fr.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			if(command.equals("Login")){
				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader(file));
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 

				JLabel label_login = new JLabel("Username:");
				JTextField login = new JTextField();
				
				JLabel label_password = new JLabel("Password:");
				JPasswordField password = new JPasswordField();
				 
				Object[] array = { label_login,  login, label_password, password };
				 
				int res = JOptionPane.showConfirmDialog(null, array, "Login", 
				        JOptionPane.OK_CANCEL_OPTION,
				        JOptionPane.PLAIN_MESSAGE);
				
				if (res == JOptionPane.OK_OPTION) {
				   username=new String(login.getText().trim());
				   passwordd=new String(password.getPassword());
				}
				  
				  String st; 
				  try {
					while ((st = br.readLine()) != null) {
					   i =st.indexOf('/');
					   int j =st.lastIndexOf('/');
					    
					    if(username.equals(st.substring(0, i))) {
					    	System.out.println("fiyu");
					    	if(passwordd.equals(st.substring(i+1, j))) {
					    		loginLabel.setText("Welcome " + username);
					    		loggedin=true;
					    		setLoggedinUser(username);
					    	}
					    	else
					    		System.out.println(st);
					    	
					    }
					  }
					br.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			
			
			if(command.equals("Quit")){
				
				
				
				
				System.exit(0);
			
				 
				}
				
			}



	public static int getCheckLR() {
		return checkLR;
	}



	public static void setCheckLR(int checkLR) {
		Game.checkLR = checkLR;
	}



	public static int getCheckUD() {
		return checkUD;
	}



	public static void setCheckUD(int checkUD) {
		Game.checkUD = checkUD;
	}



	public File getScores() {
		return scores;
	}



	public void setScores(File scores) {
		this.scores = scores;
	}



	



	public static String getLoggedinUser() {
		return loggedinUser;
	}



	public static void setLoggedinUser(String loggedinUser) {
		Game.loggedinUser = loggedinUser;
	}
 	
 	
			
			
		
}


	
	

	

