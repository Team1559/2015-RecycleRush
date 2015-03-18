package org.usfirst.frc.team1559.robot;


import java.util.Arrays;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Ramp {

	double[] primaryBuffer;
	double[] secondaryBuffer;
	int primaryCell;
	int secondaryCell;
	double delta;
	double previousValue;
	
	public Ramp(){
		
		primaryBuffer = new double[Wiring.PRIMARY_BUFFER_LENGTH];
		secondaryBuffer = new double[Wiring.SECONDARY_BUFFER_LENGTH];
		
		//this happens automatically, but I like wasting CPU cycles :)
		Arrays.fill(primaryBuffer, 0.0);
		Arrays.fill(secondaryBuffer, 0.0);
		primaryCell = 0;
		secondaryCell = 0;
		previousValue = 0;
	}
	
	public double rampMotorValue(double joystickInput){
		
		double ramp = 0.0;
		if(previousValue != 0){
			delta = ((joystickInput - previousValue)/previousValue) * 100;
		} else {
			delta = joystickInput * 100;
		}
		
		SmartDashboard.putNumber("Drive delta", delta);
		
		if(primaryCell == primaryBuffer.length){
			primaryCell = 0;
		}
		
		if(secondaryCell == secondaryBuffer.length){
			secondaryCell = 0;
		}
		
		primaryBuffer[primaryCell] = joystickInput;
		secondaryBuffer[secondaryCell] = takeAverage(primaryBuffer);

			ramp = takeAverage(secondaryBuffer);
			
		primaryCell++;
		secondaryCell++;
		
		joystickInput = previousValue;
		return ramp;
		
	}
	
	public double takeAverage(double[] array){		
		double sum = 0.0;
		
		for(double value : array){
			sum += value;
		}
		
		return sum / array.length;
	}
	
}
