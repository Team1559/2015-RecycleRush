package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class SonarMovement {

	Talon leftFront, rightFront, leftBack, rightBack;
	final double MAXDISTANCE = 7.5;
	double distanceFromObject = 0.0;
	final double DIAGDISTANCE = 6.0;
	final double LEGDISTANCE = DIAGDISTANCE * Math.sqrt(2);
	double forwardDistance; // temp
	final int NO = 0, LEFT = 1, RIGHT = 2;
	SonarStereo sonarStereo;
	MecanumDrive drive;
	int sequence;
	boolean decisionMade = false;
	Encoder pedometerX;
	Encoder pedometerY;

	public SonarMovement(Talon leftFront, Talon rightFront, Talon leftBack,
			Talon rightBack, SonarStereo sonarStereo, Encoder pedometerX, Encoder pedometerY) {
		drive = new MecanumDrive(leftFront, leftBack, rightFront, rightBack);
		this.sonarStereo = sonarStereo;
		sequence = 0;
	}

	public boolean isDetecting() {
		return sonarStereo.right.getFeet() < MAXDISTANCE
				|| sonarStereo.left.getFeet() < MAXDISTANCE;
	}

	public int decide() {
		if (isDetecting()) {
			if (sonarStereo.right.getFeet() < sonarStereo.left.getFeet()) {
				System.out.println("Left!");
				decisionMade = true;
				return LEFT;
			} else {
				System.out.println("Right!");
				decisionMade = true;
				return RIGHT;
			}
		} else {
			System.out.println("No decision");
			return NO;
		}
	}

	public void react(int decision) {
		switch (decision) {
		case NO:
			moveForward();
			break;
		case LEFT:
			leftSequence();
			break;
		case RIGHT:
			rightSequence();
			break;
		default:
			moveForward();
			break;
		}
	}

	public void leftSequence() {
		switch (sequence) {
		case 0:
			System.out.println("Stage 0: Setup");
			resetPedometers();
			sequence = 1;
			break;
		case 1:
			System.out.println("Stage 1: Go out (left)");
			diagLeft();
			if (getYFeet() <= -LEGDISTANCE)
				sequence = 2;
			break;
		case 2:
			System.out.println("Stage 2: Forward");
			forwardDistance = distanceFromObject - LEGDISTANCE;
			resetPedometers();
			moveForward();
			if (getXFeet() >= forwardDistance)
				sequence = 3;
			break;
		case 3:
			System.out.println("Stage 3: Go back (right)");
			diagRight();
			if (getYFeet() >= 0)
				sequence = 4;
			break;
		case 4:
			System.out.println("Stage 4: Preparing for another obstacle");
			resetPedometers();
			sequence = 0;
		default:
			System.out.println("Stage -1: You broke it");
			sequence = 0;
			break;
		}
	}
	
	public void rightSequence() {
		switch (sequence) {
		case 0:
			System.out.println("Stage 0: Setup");
			resetPedometers();
			sequence = 1;
			break;
		case 1:
			System.out.println("Stage 1: Go out (right)");
			diagRight();
			if (getYFeet() >= LEGDISTANCE)
				sequence = 2;
			break;
		case 2:
			System.out.println("Stage 2: Forward");
			forwardDistance = distanceFromObject - LEGDISTANCE;
			resetPedometers();
			moveForward();
			if (getXFeet() >= forwardDistance)
				sequence = 3;
			break;
		case 3:
			System.out.println("Stage 3: Go back (left)");
			diagLeft();
			if (getYFeet() <= 0)
				sequence = 4;
			break;
		case 4:
			System.out.println("Stage 4: Preparing for another obstacle");
			resetPedometers();
			sequence = 0;
		default:
			System.out.println("Stage -1: You broke it");
			sequence = 0;
			break;
		}
	}

	public void moveForward() { // actually moving sideways
		drive.drive(.25, .0, 0, 0);
	}

	public void diagLeft() { // actually going forwardsish
		drive.drive(.25, .25, 0, 0);
	}

	public void diagRight() { // actually going backwardsish
		drive.drive(.25, -.25, 0, 0);
	}

	public void disable() { // periodic
		sequence = 0;
		resetPedometers();
		decisionMade = false;
	}

	public double getDistance() { // Gets lesser distance
		if (sonarStereo.left.getFeet() > sonarStereo.right.getFeet()) {
			return sonarStereo.right.getFeet();
		} else {
			return sonarStereo.left.getFeet();
		}
	}

	public double diagonalDistance() { // diagonal distance needed to travel
		return Math.sqrt(2) * getDistance();
	}

	public double xDistance() {
		return pedometerX.get(); // add pedometer distance
	}

	public double yDistance() {
		return pedometerY.get();
	}

	public void resetPedometers() {
		pedometerX.reset();
		pedometerY.reset();
	}

	public double getXFeet() {
		return pedometerX.get() / 2700;
	}

	public double getYFeet() {
		return pedometerY.get() / 2700;
	}

	public double distanceTraveled() {
		return Math.sqrt(Math.pow(xDistance(), 2) + Math.pow(yDistance(), 2));
	}
}