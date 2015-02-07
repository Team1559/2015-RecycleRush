package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

public class Gatherer {
	
	Solenoid sole;
	Talon lefttalon;
	Talon righttalon;
	Joystick joy;
	
	
	public Gatherer()
	{
		sole = new Solenoid(0);
		lefttalon = new Talon(4);
		righttalon = new Talon(5);
	}
	
	public void PushPiston()
	{
		sole.set(true);
	}
	
	public void PullPiston()
	{
		sole.set(false);
	}
	
	public void PullTote()
	{
		
		if (joy.getRawButton(6))
		{
			righttalon.set(.75);
		}
		
		if (joy.getRawButton(5))
		{
			lefttalon.set(.75);
		}
	}

}
