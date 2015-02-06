package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Servo;

public class Wings {
	Servo servo;
	
	public Wings()
	{
		servo = new Servo(0);
	}
	
	public void latch()
	{
		 servo.setAngle(60);
    	
	}
	
	public void release()
	{
		 servo.setAngle(0);
    	
	}
}
