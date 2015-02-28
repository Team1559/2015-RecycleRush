package org.usfirst.frc.team1559.robot;


import java.util.Arrays;

public class Ramp {

	double[] primaryBuffer;
	double[] secondaryBuffer;
	int primaryCell;
	int secondaryCell;
	
	public Ramp(){
		
		primaryBuffer = new double[10];
		secondaryBuffer = new double[4];
		
		//this happens automatically, but I like wasting CPU cycles :)
		Arrays.fill(primaryBuffer, 0.0);
		Arrays.fill(secondaryBuffer, 0.0);
		primaryCell = 0;
		secondaryCell = 0;
		
	}
	
	public double rampMotorValue(double joystickInput){
		
		double ramp = 0.0;
		
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
