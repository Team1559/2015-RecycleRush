package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Pincers {

	Solenoid in;
	Solenoid out;
	
	public Pincers(){
		
		in = new Solenoid(5);
		out = new Solenoid(4);
		
	}
	
	public void rightCan(){
		
		in.set(false);
		out.set(true);
		System.out.println("RIGHTING CAN!");
		
	}
	
	public void dontRightCan(){
		
		in.set(true);
		out.set(false);
		
	}
	
}
