package com.termproject;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Motor extends JLabel{
	//public int horizontalLoc = -1455;
	private float speed=10;
	private int time=60;
	private double meter=0;
	private static final long serialVersionUID = 2308190075178678463L;
	private int imgs_index=7;
	private final String[] imgs = {"images/motor_left_7.png","images/motor_left_6.png","images/motor_left_5.png","images/motor_left_4.png","images/motor_left_3.png","images/motor_left_2.png","images/motor_left_1.png","images/motor_center.png","images/motor_right_1.png","images/motor_right_2.png","images/motor_right_3.png","images/motor_right_4.png","images/motor_right_5.png","images/motor_right_6.png","images/motor_right_7.png"};
	private final String[] crashimgs= {"images/motor_left_fall_11.png","images/motor_left_fall_10.png","images/motor_left_fall_9.png","images/motor_left_fall_8.png","images/motor_left_fall_7.png","images/motor_left_fall_6.png","images/motor_left_fall_5.png","images/motor_left_fall_4.png","images/motor_left_fall_3.png","images/motor_left_fall_2.png","images/motor_left_fall_1.png","images/motor_right_fall_1.png","images/motor_right_fall_2.png","images/motor_right_fall_3.png","images/motor_right_fall_4.png","images/motor_right_fall_5.png","images/motor_right_fall_6.png","images/motor_right_fall_7.png","images/motor_right_fall_8.png","images/motor_right_fall_9.png","images/motor_right_fall_10.png","images/motor_right_fall_11.png"};
	private ImageIcon motorImg = new ImageIcon(imgs[imgs_index]);
	private JLabel speedLabel = new JLabel("Speed " + getSpeed() + " KM/H");
	private JLabel meterLabel = new JLabel("Traveled " + getMeter() + " M");
	private JLabel timeLabel = new JLabel("Time "+ getTime());
	private int xLoc=0;
	
	public Motor() {
		super();
		this.setIcon(motorImg);
		this.setSize(200, 200);
		
		getSpeedLabel().setForeground(Color.YELLOW);
		getSpeedLabel().setFont(new Font("Algerian", Font.BOLD, 20));
		
		getTimeLabel().setForeground(Color.YELLOW);
		getTimeLabel().setFont(new Font("Algerian", Font.BOLD, 20));
		
		getMeterLabel().setForeground(Color.YELLOW);
		getMeterLabel().setFont(new Font("Algerian", Font.BOLD, 20));
		
		this.setVerticalAlignment(SwingConstants.BOTTOM);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		
		
		
	}
	
	public void animation(Background road) {
		Thread th1 = new Thread() {
			public void run() {
				
				try {
					while(road.getFlag()==0 && !road.getGameFinished()) {
						try {
							Thread.sleep(50);
						} catch (InterruptedException e3) {
							// TODO Auto-generated catch block
							e3.printStackTrace();
						}
						while(!road.getPauseflag()) {
						
						while(imgs_index<imgs.length && imgs_index>=0 && Game.getCheckLR()!=0) {
							Thread.sleep(50);
							
							motorImg= new ImageIcon(imgs[imgs_index]);
							setIcon(motorImg);
							
							if(Game.getCheckLR()==1) {
								imgs_index++;
								
							}
							if(Game.getCheckLR()==2) {
								imgs_index--;
								
							}
						}
						while(Game.getCheckLR()==0) {
							Thread.sleep(50);
							
							motorImg= new ImageIcon(imgs[imgs_index]);
							setIcon(motorImg);

							if(imgs_index < 7)
								imgs_index++;
							if(imgs_index > 7)
								imgs_index--;
						}
						
						if(imgs_index>14)
							imgs_index=12;
					
					
						if(imgs_index<0)
							imgs_index=2;
					
						
					}	}
					if(road.getFlag()==1) {
						
						for(int i=11;i<22;i++) {
							
							
							motorImg= new ImageIcon(crashimgs[i]);
							
							setIcon(motorImg);
							
							Thread.sleep(100);
						}
					}
					if(road.getFlag()==-1) {
						
						for(int i=10;i>-1;i--) {
							
							
							motorImg= new ImageIcon(crashimgs[i]);
							
							setIcon(motorImg);
							
							Thread.sleep(100);
						}
					}
					
					
						
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		};
		th1.start();
	}
	
	public void speed(Background road) {
		Thread th2 = new Thread() {
			public void run() {
				
				while(road.getFlag()==0 && !road.getGameFinished()) {
					float volume = 0.45f;
					 AudioInputStream audioInputStream = null;
					try {
						audioInputStream = AudioSystem.getAudioInputStream(new File("images/sound.wav").getAbsoluteFile());
					} catch (UnsupportedAudioFileException | IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} 
				          
				        // create clip reference 
				        Clip clip = null;
						try {
							clip = AudioSystem.getClip();
						} catch (LineUnavailableException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} 
				          
				        // open audioInputStream to the clip 
				        try {
							clip.open(audioInputStream);
						} catch (LineUnavailableException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} 
				          
				        clip.loop(Clip.LOOP_CONTINUOUSLY); 
				        
				        FloatControl gainControl = 
				        	    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				        float range = gainControl.getMaximum() - gainControl.getMinimum();
				        float gain = (range * volume) + gainControl.getMinimum();
				        gainControl.setValue(gain);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					while(!road.getPauseflag()) {
						try {
							
							Thread.sleep(300);
							volume= (float) (0.45f + speed/250f);
							gain = (range * volume) + gainControl.getMinimum();
							if(gain>6)
								gain = 6;
					        gainControl.setValue(gain);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(Game.getCheckUD()==1 && getSpeed()<250) {
							setSpeed(getSpeed() + 8);
							volume+=0.015;
						}
						if(Game.getCheckUD()==-1 && getSpeed() > 30) {
							setSpeed(getSpeed() - 15);
							
						}
						if(Game.getCheckUD()==2 && getSpeed() > 30) {
							setSpeed(getSpeed() - 6);
							
						}
						if(volume<0.07)
							volume=(float) 0.07;
						
						getSpeedLabel().setText("Speed " + getSpeed() + " KM/H");
					}
					clip.close();
				}
				
			}
		};
		th2.start();
	}
	public void time(Background road) {
		Thread th4 = new Thread() {
			public void run() {
				while(road.getFlag()==0 && !road.getGameFinished()) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
						while(!road.getPauseflag()) {
						try {
							
							Thread.sleep(1000);
							setTime(getTime() - 1);
							setMeter(getMeter() + getSpeed());
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(getTime()<0) {
							road.setPauseflag(true);
							road.setFlag(2);
							init();
							
							Game.gameRestart();
						}
						getTimeLabel().setText("Time " + getTime());
						getMeterLabel().setText("Traveled " + getMeter()/1000 + " KM");
					
					}
				}
				
				
			}
		};
		th4.start();
	}
	
	public void init() {
		setSpeed(10);
		setTime(60);
		setMeter(0);
		imgs_index=7;
		
		motorImg = new ImageIcon(imgs[imgs_index]);
		
		setSpeedLabel(new JLabel("Speed " + getSpeed() + " KM/H"));
		setMeterLabel(new JLabel("Traveled " + getMeter() + " M"));
		setTimeLabel(new JLabel("Time "+ getTime()));
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public double getMeter() {
		return meter;
	}

	public void setMeter(double meter) {
		this.meter = meter;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public JLabel getSpeedLabel() {
		return speedLabel;
	}

	public void setSpeedLabel(JLabel speedLabel) {
		this.speedLabel = speedLabel;
	}

	public JLabel getTimeLabel() {
		return timeLabel;
	}

	public void setTimeLabel(JLabel timeLabel) {
		this.timeLabel = timeLabel;
	}

	public JLabel getMeterLabel() {
		return meterLabel;
	}

	public void setMeterLabel(JLabel meterLabel) {
		this.meterLabel = meterLabel;
	}

	public int getXLoc() {
		return xLoc;
	}

	public void setXLoc(int horizontalLoc) {
		this.xLoc = horizontalLoc;
	}
	
	
}
