package com.termproject;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class AiPlayer extends JLabel{
	
	private static final long serialVersionUID = 1L;
	private int xLoc;
	private int yLoc;
	private String[] imgs = {"images/ai_player(0).png","images/ai_player(1).png","images/ai_player(2).png","images/ai_player(3).png","images/ai_player(4).png","images/ai_player(5).png"};
	private ImageIcon img = new ImageIcon(imgs[5]);
	
	public AiPlayer() {
		super();
		
		
	}
	
	
	public void tryUser(Background road) {
		Thread aiTh = new Thread() {
			public void run() {
				xLoc=0;;
				yLoc=0;
				while(true) {
					
				if(road.getAiflag()) {
					try {
						Thread.sleep(400);
						if(road.getHorizontalLoc()>xLoc) {
							xLoc-=3;
							
						}
						if(road.getHorizontalLoc()<xLoc) {
							xLoc+=3;
						}
						
						img = new ImageIcon(imgs[yLoc%6]);
						
						setSize(img.getIconWidth(),img.getIconHeight());
						setIcon(img);
						setBounds(road.getHorizontalLoc() + 1750 + xLoc+(8*yLoc),300-(10*yLoc),img.getIconWidth(),img.getIconHeight());
						yLoc++;
						
						
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
				}
					
					
				}
			}
		};
		aiTh.start();
		
	}
	
	public void moveAi() {
		
	}

	
	
	
	public int getyLoc() {
		return yLoc;
	}

	public void setyLoc(int yLoc) {
		this.yLoc = yLoc;
	}
}
