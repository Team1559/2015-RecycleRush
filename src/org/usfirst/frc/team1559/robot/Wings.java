package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;

public class Wings {
	Servo servo;
	Joystick joy;
	
	public Wings()
	{
		servo = new Servo(0);
		joy = new Joystick(0);
	}
	
	public void latch()
	{
		 servo.setAngle(60);
    	
	}
	
	public void release()
	{
		 servo.setAngle(0);
    	
	}
	
	public void wingsControl() {
		
    	if (joy.getRawButton(3))
    	{
    		latch();
    	}
    	
    	if (!joy.getRawButton(3))
    	{
    		release();
    	}
	}
}
