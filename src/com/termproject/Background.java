package com.termproject;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Background extends JLabel{

	private static final long serialVersionUID = 1L;
	private int turn;
	private static int horizontalLoc=-1455;
	private static long points=0;
	private int flag=0;
	private boolean pauseflag=false;
	private boolean gameFinished=false;
	private File scores = new File("Scores.txt");
	private boolean aiflag=true;
	public Background(ImageIcon img,int width,int height) {
		super();
		this.setIcon(img);
		this.setSize(width, height);
	}
	
	

	
	
	public void mvroad(Motor motor) {
		Thread th3 = new Thread() {
			public void run() {
				
				points=0;
				int i=0,j =0,f=0,d=0,g=0,h=0;
				boolean point=false;
				while(getFlag()==0 && !getGameFinished()) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					while(!getPauseflag()) {
						try {
							
							setLocation(getHorizontalLoc(),193);
							Thread.sleep((long) (3500/ motor.getSpeed()));
							
							
							if(motor.getMeter()>5000 && j<82) {
								turn=-2;
								Thread.sleep((long) (280-motor.getSpeed()));
								ImageIcon img= new ImageIcon("images/leftTurn("+j%82+").png");
								setIcon(img);
								setHorizontalLoc(getHorizontalLoc() - 10);
								j++;
							}
							
							else if(motor.getMeter()>12000 && d<5) {
								turn=0;
								Thread.sleep((long) (280-motor.getSpeed()));
								ImageIcon img= new ImageIcon("images/roadpoint("+d%5+").png");
								setIcon(img);
								
								d++;
								if(d==5) {
									motor.setTime(motor.getTime() + 60);
								}
							
							}
							else if(motor.getMeter()>27000 && g<5) {
								turn=0;
								Thread.sleep((long) (280-motor.getSpeed()));
								ImageIcon img= new ImageIcon("images/roadpoint("+g%5+").png");
								setIcon(img);
								g++;
								if(g==5) {
									motor.setTime(motor.getTime() + 60);
								}
								
								
							}
						
							
							else if(motor.getMeter()>17000 && f<82) {
								turn=2;
								Thread.sleep((long) (280-motor.getSpeed()));
								ImageIcon img= new ImageIcon("images/rightTurn("+f%82+").png");
								setIcon(img);
								setHorizontalLoc(getHorizontalLoc() + 10);
								f++;
							}
							else if(motor.getMeter()>35000 && h<5) {
								turn=0;
								Thread.sleep((long) (290-motor.getSpeed()));
								ImageIcon img= new ImageIcon("images/roadfinish("+h%5+").png");
								setIcon(img);
								setHorizontalLoc(getHorizontalLoc() + 10);
								h++;
							}
							else if(point){
								turn=0;
								ImageIcon img= new ImageIcon("images/roadsign("+i%5+").png");
								setIcon(img);
								}
							else {
								turn=0;
								ImageIcon img= new ImageIcon("images/road("+i%5+").png");
								setIcon(img);
								
								
							}
								
							if(h==5) {
								Random randomGenerator = new Random();
								points=(long) (motor.getMeter()+(motor.getSpeed()*(randomGenerator.nextInt(25)+1)));
								FileWriter fr = null;
								try {
									fr = new FileWriter(scores, true);
								} catch (IOException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
								BufferedWriter br = new BufferedWriter(fr);
								PrintWriter pr = new PrintWriter(br);
								
								
								
								pr.println(Game.getLoggedinUser() + ": " + Background.points);
								pr.close();
								pr.close();
								try {
									br.close();
									fr.close();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								
								
								setGameFinished(true);
								setPauseflag(true);
								
							}
							i++;
							if(i%6==5)
								point=true;
							if(i%11==10)
								point=false;
							
							if(Game.getCheckLR()==1) {
								
								motor.setXLoc(motor.getXLoc()-6);
								setHorizontalLoc(getHorizontalLoc() - 6);
								
							}
							if(Game.getCheckLR()==2) {
								
								motor.setXLoc(motor.getXLoc()+6);
								setHorizontalLoc(getHorizontalLoc() + 6);
								
							}
							if(getHorizontalLoc() < -1930 ) {
							
								setFlag(1);
								
								setPauseflag(true);
								Thread.sleep(3000);
								//Game.gover=true;
								setHorizontalLoc(-1455);
								Game.gameRestart();
								
							}
							else if(getHorizontalLoc() > -1030 ) {
							
								setFlag(-1);
								setPauseflag(true);
								Thread.sleep(3000);
								Game.gameRestart();
								
							}
							
							if(motor.getSpeed()<10)
								motor.setSpeed(10);
							
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					
					
					}
				}
			}
			
			
		};
		th3.start();
	}
	
	
	public void mvbg() {
		Thread th5 = new Thread() {
			public void run() {
				int horizontalLoc = -1455;
				
					try {
						
						while(getFlag()==0 && !getGameFinished()) {
							try {
								Thread.sleep(50);
							} catch (InterruptedException e3) {
								// TODO Auto-generated catch block
								e3.printStackTrace();
							}
							while(!getPauseflag()) {
							Thread.sleep(10);
							
						if(Game.getCheckLR()==1) {
							
							Thread.sleep(50);
							horizontalLoc-=2;
							setLocation(horizontalLoc,0);
						}
						if(Game.getCheckLR()==2) {
							
							Thread.sleep(50);
							horizontalLoc+=2;
							setLocation(horizontalLoc,0);
						}
						
							horizontalLoc+=turn;
						}
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
			
			
		};
		th5.start();
	}





	public boolean getPauseflag() {
		return pauseflag;
	}





	public void setPauseflag(boolean pauseflag) {
		this.pauseflag = pauseflag;
	}





	public int getFlag() {
		return flag;
	}





	public void setFlag(int flag) {
		this.flag = flag;
	}





	public boolean getGameFinished() {
		return gameFinished;
	}





	public void setGameFinished(boolean gameFinished) {
		this.gameFinished = gameFinished;
	}





	public int getHorizontalLoc() {
		return horizontalLoc;
	}





	public void setHorizontalLoc(int hLoc) {
		horizontalLoc = hLoc;
	}





	public boolean getAiflag() {
		return aiflag;
	}





	public void setAiflag(boolean aiflag) {
		this.aiflag = aiflag;
	}

}