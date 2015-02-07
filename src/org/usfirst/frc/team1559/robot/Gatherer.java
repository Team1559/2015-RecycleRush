package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Gatherer {
	
	Solenoid sole;
	
	public Gatherer()
	{
		sole = new Solenoid(0);
	}
	
	public void Push()
	{
		sole.set(true);
	}
	
	public void Pull()
	{
		sole.set(false);
	}

}
