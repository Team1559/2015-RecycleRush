package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Servo;

public class Wings {
	Servo servo;
	double angle = 0.0;
	
	public Wings()
	{
		servo = new Servo(0);
		angle = servo.getAngle();
	}
	
	public void latch()
	{
		 servo.setAngle(angle + 60);
    	
	}
	
	public void release()
	{
		 servo.setAngle(angle);
    	
	}
}
