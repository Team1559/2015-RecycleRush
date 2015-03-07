package org.usfirst.frc.team1559.robot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Playback {

	File f;
	FileReader fr;
	BufferedReader br;
	int lines;
	String command;
	double x, y;
	boolean done;
	int lifterLevel;
	double xComp, yComp;
	Pedometer ped;
	double autoSpeed;
	Lifter lifter;
	
	public Playback(){
		 try {
	        	f = new File("/home/lvuser/Output.txt");
				if(!f.exists()){
		        	f.createNewFile();
		        }
				fr = new FileReader(f);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        br = new BufferedReader(fr);
	        
	        lines = 0;
	        command = "DEFAULT";
	        x = 0.0;
	        y = 0.0;
	        //it's ok the nasty try-catch is gone
	        done = true;
	        
	        lifterLevel = 0;
	        xComp = 0.0;
	        yComp = 0.0;
	        ped = new Pedometer();
	        lifter = new Lifter();
	}
	
	 public void playback(){
	    	//ew
	    	if(done){
		    	try {
					command = br.readLine();
					done = false;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//end ew
	    	}
			
			if(command.equals("<<START>>")){
				System.out.println(lines + ". We are at the starting position.");
			} else if(command.contains("[MOVE]")){
				//code for decoding x and y values
			    x = Double.valueOf(command.substring(command.indexOf("X")+1, command.indexOf("Y")-1));
			    y = Double.valueOf(command.substring(command.indexOf("Y")+1));
				System.out.println(lines + ". Move " + x + " inches in x. Move " + y + " inches in Y.");
				
				if((x > 0) && (y > 0)){
					
					if((ped.getX() < x)){
						xComp = autoSpeed * 1;
					} else {
						xComp = 0;
					}
					
					if(ped.getY() < y){
						yComp = autoSpeed * 1;
					} else {
						yComp = 0;
					}
					
//					md.drive(xComp, yComp, 0, g.getAngle());
					if((xComp >= x) && (yComp >= y)){
						done = true;
						ped.reset();
					}
					
				} else if((x < 0) && (y > 0)){
					
					if((ped.getX() > x)){
						xComp = autoSpeed * -1;
					} else {
						xComp = 0;
					}
					
					if(ped.getY() < y){
						yComp = autoSpeed * 1;
					} else {
						yComp = 0;
					}
					
//					md.drive(xComp, yComp, 0, g.getAngle());
					if((xComp >= x) && (yComp >= y)){
						done = true;
						ped.reset();
					}
					
				} else if((x > 0) && (y < 0)){
					
					if((ped.getX() < x)){
						xComp = autoSpeed * 1;
					} else {
						xComp = 0;
					}
					
					if(ped.getY() > y){
						yComp = autoSpeed * -1;
					} else {
						yComp = 0;
					}
					
//					md.drive(xComp, yComp, 0, g.getAngle());
					if((xComp >= x) && (yComp >= y)){
						done = true;
						ped.reset();
					}
					
				} else if((x < 0) && (y > 0)){
					
					if((ped.getX() > x)){
						xComp = autoSpeed * -1;
					} else {
						xComp = 0;
					}
					
					if(ped.getY() < y){
						yComp = autoSpeed * 1;
					} else {
						yComp = 0;
					}
					
//					md.drive(xComp, yComp, 0, g.getAngle());
					if((xComp >= x) && (yComp >= y)){
						done = true;
						ped.reset();
					}
					
				}
				
			} else if(command.contains("[GATHER]")){
				int totes;
				String s = command.substring(command.indexOf((" "))).trim();
				totes = Integer.valueOf(s);
				System.out.println(lines + ". Gather " + totes + " tote(s)");
				if(lifterLevel > totes){			
					lifter.move(totes);		
					lifterLevel --;
					done = true;
				} else {			
					lifter.move(totes);
					lifterLevel++;
					done = true;
				}
			} else {
				System.out.println("STOP");
			}
			lines++;
			
			// this is a comment
			// ayy lmao yeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee The Pizza is Agressive oh $%^%#$
			
	    }
	
}
