package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class SonarMovement {

	Talon leftFront, rightFront, leftBack, rightBack;
	final double MAXDISTANCE = 7.5;
	double distance = 0.0;
	final double LEGDISTANCE = 4.0;
	final double DIAGDISTANCE = LEGDISTANCE * Math.sqrt(2);
	double forwardDistance; // temp
	final int NO = 0, LEFT = 1, RIGHT = 2;
	SonarStereo sonarStereo;
	MecanumDrive drive;
	int sequence;
	boolean decisionMade = false;
	Encoder pedometerX, pedometerY;
	int decision = NO;

	enum Direction {
		LEFT, RIGHT;
	}

	public SonarMovement(Talon leftFront, Talon rightFront, Talon leftBack, Talon rightBack, SonarStereo sonarStereo, Encoder pedometerX, Encoder pedometerY) {
		drive = new MecanumDrive(leftFront, leftBack, rightFront, rightBack);
		this.sonarStereo = sonarStereo;
		sequence = 0;
	}

	public void avoid() {
		sonarStereo.periodic();
        if(!decisionMade)
             decision = decide();
         react(decision);
	}

	public int decide() {
		if (isDetecting()) {
			if (sonarStereo.right.getFeet() < sonarStereo.left.getFeet()) {
				distance = getDistance();
				System.out.println("Left!");
				decisionMade = true;
				return LEFT;
			} else {
				distance = getDistance();
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
			isoscelesSequence(Direction.LEFT);
			break;
		case RIGHT:
			isoscelesSequence(Direction.RIGHT);
			break;
		default:
			moveForward();
			break;
		}
	}

	public void isoscelesSequence(Direction dir) { // out then in (45 deg)
		if (dir == Direction.LEFT) {
			switch (sequence) {
			case 0:
				System.out.println("Stage 0: Setup");
				resetPedometers();
				sequence = 1;
				break;
			case 1:
				System.out.println("Stage 1: Go out (left)");
				diagLeft();
				if (distanceTraveled() <= -diagonalDistance()) {
					sequence = 2;
					resetPedometers();
				}
				break;
			case 2:
				System.out.println("Stage 2: Go back (right)");
				diagRight();
				if (distanceTraveled() >= diagonalDistance()) 
					sequence = 3;
				break;
			case 3:
				System.out.println("Stage 3: Preparing for another obstacle");
				resetPedometers();
				sequence = 0;
				break;
			default:
				System.out.println("Stage -1: You broke it");
				sequence = 0;
				break;
			}
		} else if (dir == Direction.RIGHT) {
			switch (sequence) {
			case 0:
				System.out.println("Stage 0: Setup");
				resetPedometers();
				sequence = 1;
				break;
			case 1:
				System.out.println("Stage 1: Go out (right)");
				diagRight();
				if (distanceTraveled() >= diagonalDistance()) {
					sequence = 2;
					resetPedometers();
				}
				break;
			case 2:
				System.out.println("Stage 2: Go back (left)");
				diagLeft();
				if (getYFeet() <= 0) 
					sequence = 3;
				break;
			case 3:
				System.out.println("Stage 3: Preparing for another obstacle");
				resetPedometers();
				sequence = 0;
				break;
			default:
				System.out.println("Stage -1: You broke it");
				sequence = 0;
				break;
			}
		} else {
			System.err.println("ERROR! Not a valid direction, somehow.");
		}
	}

	public void scaleneSequence(Direction dir) { // out at variable angle until perp to object, then back at a 45
		if (dir == Direction.LEFT) {
			switch (sequence) {
			case 0:
				System.out.println("Stage 0: Setup");
				resetPedometers();
				sequence = 1;
				break;
			case 1:
				System.out.println("Stage 1: Go out (left)");
				drive.drive(-(0.25 * distance) / LEGDISTANCE, 0.25, 0, 0);
				if (yDistance() <= -LEGDISTANCE) sequence = 2;
				break;
			case 2:
				System.out.println("Stage 2: Go back (right)");
				drive.drive(0.25, 0.25, 0, 0);
				if (yDistance() >= 0) sequence = 0;
				break;
			}
		} else if (dir == Direction.RIGHT) {
			switch (sequence) {
			case 0:
				System.out.println("Stage 0: Setup");
				resetPedometers();
				sequence = 1;
				break;
			case 1:
				System.out.println("Stage 1: Go out (right)");
				drive.drive((0.25 * distance) / LEGDISTANCE, 0.25, 0, 0);
				if (yDistance() >=LEGDISTANCE) sequence = 2;
				break;
			case 2:
				System.out.println("Stage 2: Go back (left)");
				drive.drive(0.25, 0.25, 0, 0);
				if (yDistance() <= 0) sequence = 0;
				break;
			}
		} else {
			System.err.println("ERROR! Not a valid direction, somehow.");
		}
	}

	public void trapezoidSequence(Direction dir) { // out at 45 to fixed distance, forward until perp, then back at 45
		if (dir == Direction.LEFT) {
			switch (sequence) {
			case 0:
				System.out.println("Stage 0: Setup");
				resetPedometers();
				sequence = 1;
				break;
			case 1:
				System.out.println("Stage 1: Go out (left)");
				diagLeft();
				if (getYFeet() <= -LEGDISTANCE) sequence = 2;
				break;
			case 2:
				System.out.println("Stage 2: Forward");
				forwardDistance = distance -LEGDISTANCE;
				resetPedometers();
				moveForward();
				if (getXFeet() >= forwardDistance) sequence = 3;
				break;
			case 3:
				System.out.println("Stage 3: Go back (right)");
				diagRight();
				if (getYFeet() >= 0) sequence = 4;
				break;
			case 4:
				System.out.println("Stage 4: Preparing for another obstacle");
				resetPedometers();
				sequence = 0;
				break;
			default:
				System.out.println("Stage -1: You broke it");
				sequence = 0;
				break;
			}
		} else if (dir == Direction.RIGHT) {
			switch (sequence) {
			case 0:
				System.out.println("Stage 0: Setup");
				resetPedometers();
				sequence = 1;
				break;
			case 1:
				System.out.println("Stage 1: Go out (right)");
				diagRight();
				if (getYFeet() >=LEGDISTANCE) sequence = 2;
				break;
			case 2:
				System.out.println("Stage 2: Forward");
				forwardDistance = distance -LEGDISTANCE;
				resetPedometers();
				moveForward();
				if (getXFeet() >= forwardDistance) sequence = 3;
				break;
			case 3:
				System.out.println("Stage 3: Go back (left)");
				diagLeft();
				if (getYFeet() <= 0) sequence = 4;
				break;
			case 4:
				System.out.println("Stage 4: Preparing for another obstacle");
				resetPedometers();
				sequence = 0;
				break;
			default:
				System.out.println("Stage -1: You broke it");
				sequence = 0;
				break;
			}
		} else {
			System.err.println("ERROR! Not a valid direction, somehow.");
		}
	}

	public void moveForward() { //actualy right
		drive.drive(.25, .0, 0, 0);
	}

	public void diagLeft() { 
		drive.drive(.25, .25, 0, 0);
	}

	public void diagRight() {
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

	public double distanceTraveled() { //pythag pedometers
		return Math.sqrt(Math.pow(xDistance(), 2) + Math.pow(yDistance(), 2));
	}
	
	public double diagonalDistance() { //the diagonal distance needed to travel
		return getDistance() * Math.sqrt(2);
	}
	
	public boolean isDetecting() {
		return sonarStereo.right.getFeet() < MAXDISTANCE || sonarStereo.left.getFeet() < MAXDISTANCE;
	}
}