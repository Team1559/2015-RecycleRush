package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.AnalogInput;

public class IRSensor {

	AnalogInput ir;
	double normalDist;
	
	public IRSensor(){
		
		ir = new AnalogInput(2);
		normalDist = 0.0;
	}
	
	public boolean hasTote(){
		if(ir.getVoltage() > normalDist){
			return true;
		} else {
			return false;
		}
	}
	
	public double getVoltage(){
		return ir.getAverageVoltage();
	}
	
}
