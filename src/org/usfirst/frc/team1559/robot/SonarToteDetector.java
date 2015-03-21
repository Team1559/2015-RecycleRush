package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.AnalogInput;

public class SonarToteDetector {

	AnalogInput sonarL;
	AnalogInput sonarR;
	
	public SonarToteDetector(){
		sonarL = new AnalogInput(Wiring.LEFT_TOTE_DETECTOR_SONAR);
		sonarR = new AnalogInput(Wiring.RIGHT_TOTE_DETECTOR_SONAR);
		
	}
	
	public double getLeftFeet(){
		return sonarL.getAverageVoltage() * 9.3;
	}
	
	public double getLeftInches(){
		return getLeftFeet() * 12;
	}
	
	public double getRightFeet(){
		return sonarR.getAverageVoltage() * 9.3;
	}
	
	public double getRightInches(){
		return getRightFeet() * 12;
	}
	
	public double getCorrection(){
		
		double drive = 0.0;
		
		if((Math.abs(getLeftInches() - getRightInches()) > -.5) && (Math.abs(getLeftInches() - getRightInches()) < .5)){
			//they're roughly equal
			return 0.0;
		} else if(getLeftInches() < getRightInches()){
			return -.25;
		} else if(getLeftInches() > getRightInches()){
			return .25;
		}
		
		return drive;
		
	}
	
	public boolean toteInRange(){
		if(getCorrection() == 0){
			return getRightInches() < Wiring.GATHER_RANGE;
		} else {
			return false;
		}
	}
	
}
