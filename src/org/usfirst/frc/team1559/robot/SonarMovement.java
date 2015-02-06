package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Victor;

public class SonarMovement {

	private double distance; // 0.5 ft - 6 ft
	final double maxDist = 6;
	final double minDist = 0.5;
	private int location;
	final int LEFTDECISION = 0;
	final int RIGHTDECISION = 1;
	final double maxVarMotor = 0.25;
	final double minVarMotor = -0.50;
	final double DISTANCEPROP = (maxVarMotor - minVarMotor) / (maxDist - minDist);
	final double SCALEDDISTANCE = ((distance - minDist) * DISTANCEPROP) + minVarMotor;
	Victor leftMotor;
	Victor rightMotor;
	int counter = 0;
	final int delay = 10;
	SonarStereo sonarStereo;
	
	public SonarMovement(Victor motorLeft, Victor motorRight, SonarStereo sonarStereo) {
		leftMotor = motorLeft;
		rightMotor = motorRight;
		this.sonarStereo = sonarStereo;
		
	}

	public double getDistance() { return distance; }
	public int getLocation() { return location; }

	public int decide() {
		if (location > 0) return LEFTDECISION;
		else return RIGHTDECISION;
	}

	public void respond(int decision) {
		if (decision == LEFTDECISION) {
			turnLeft();
		}
		else if (decision == RIGHTDECISION) {
			turnRight();
		}
		else {
			System.out.println("Make a decision, yo.");
		}
	}
	
	public void turnRight() {
		switch(counter++) {
		case 0:
			leftMotor.set(.75);
			rightMotor.set(SCALEDDISTANCE);
			break;
		case delay:
			leftMotor.set(SCALEDDISTANCE);
			rightMotor.set(.75);
			break;
		case delay * 2:
			leftMotor.set(.75);
			rightMotor.set(.75);
			break;
		case delay * 3:
			leftMotor.set(0);
			rightMotor.set(0);
			break;
		case delay * 4:
			counter = 0;
			break;
		}
	}
	
	public void turnLeft() {
		
		switch(counter++) {
		case 0:
			leftMotor.set(SCALEDDISTANCE);
			rightMotor.set(.75);
			break;
		case delay:
			leftMotor.set(.75);
			rightMotor.set(SCALEDDISTANCE);
			break;
		case delay * 2:
			leftMotor.set(.75);
			rightMotor.set(.75);
			break;
		case delay * 3:
			leftMotor.set(0);
			rightMotor.set(0);
			break;
		}
	}
	
	public void enableTurn() {
		counter = 0;
	}
	
	public void moveForward() {
		leftMotor.set(.25);
		rightMotor.set(.25);
	}
	public void stop() {
		// EXCLUSIVELY FOR TESTING
		if(sonarStereo.left.getFeet() < 2.5 && sonarStereo.right.getFeet() < 2.5) {
			leftMotor.set(0);
			rightMotor.set(0);
		} else {
			moveForward();
		}
	}
	
	public void stopOneSonar() {
		// EXCLUSIVELY FOR TESTING
		if(sonarStereo.left.getFeet() < 2.5 && sonarStereo.right.getFeet() < 2.5 ) {
			leftMotor.set(0);
			rightMotor.set(0);
		} else {
			moveForward();
		}
	}
}
