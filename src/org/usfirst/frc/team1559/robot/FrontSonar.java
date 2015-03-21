package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.AnalogInput;

public class FrontSonar {

	AnalogInput sonar;
	
	public FrontSonar(){
		sonar = new AnalogInput(Wiring.TINYSONAR);
	}
	
	public double getFeet(){
		return sonar.getVoltage() * 9.3;
	}
	
	public double getInches(){
		return getFeet() / 12;
	}
	
}
