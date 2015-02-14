package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;

public class MecanumDrive {

	Talon leftFront, leftRear;
	Talon rightFront, rightRear;
	Joystick joy;
	
	//use these to set
	double lf;
	double lr;
	double rf;
	double rr;
	
	
	public MecanumDrive(Talon lf, Talon lr, Talon  rf, Talon rr){
		
		leftFront = lf;
		leftRear = lr;
		rightFront = rf;
		rightRear = rr;
				
		this.lf = 0.0; //accesses the double values for driving calculations
		this.lr = 0.0;
		this.rf = 0.0;
		this.rr = 0.0;
	}
	
	/*
	 * Use for autonomous mode, or directly moving the chassis
	 * 
	 * Parameters:
	 * double speed:
	 * 		scaled speed between -1.0 and 1.0
	 * 		i.e. 1.0 is max forward
	 * 			-1.0 is max reverse
	 * 
	 * double angle:
	 * 		angle between -360 and 360. Used for translating at an angle
	 * 		i.e. 45 moves the chassis at a 45 degree angle, maintaining orientation
	 * 			
	 * double distance:
	 * 		distance in inches, actual distance is measured by pedometer unit -?
	 * 
	 * boolean closedLoop:
	 * 		should be true most of the time, checks Gyro angle against desired angle
	 * 		TRUE: do check
	 * 		FALSE: you don't speak English, don't check
	 */
	
	public void drive(double x, double y, double rotation, double gyroAngle){
		
		double xIn = x;
        double yIn = y;
        // Negate y for the joystick.
        yIn = -yIn;
        // Compenstate for gyro angle.
        double rotated[] = rotateVector(xIn, yIn, gyroAngle);
        xIn = rotated[0];
        yIn = rotated[1];

        double wheelSpeeds[] = new double[4];
        wheelSpeeds[0] = xIn + yIn + rotation;
        wheelSpeeds[1] = -xIn + yIn - rotation;
        wheelSpeeds[2] = -xIn + yIn + rotation;
        wheelSpeeds[3] = xIn + yIn - rotation;

        normalize(wheelSpeeds);
        leftFront.set(-wheelSpeeds[0]);
        rightFront.set(wheelSpeeds[1]);
        leftRear.set(-wheelSpeeds[2]);
        rightRear.set(wheelSpeeds[3]);
		
	}
	
	 protected static double[] rotateVector(double x, double y, double angle) {
	        double cosA = Math.cos(angle * (3.14159 / 180.0));
	        double sinA = Math.sin(angle * (3.14159 / 180.0));
	        double out[] = new double[2];
	        out[0] = x * cosA - y * sinA;
	        out[1] = x * sinA + y * cosA;
	        return out;
	    }
	 
	    protected static void normalize(double wheelSpeeds[]) {
	        double maxMagnitude = Math.abs(wheelSpeeds[0]);
	        int i;
	        for (i=1; i<4; i++) {
	            double temp = Math.abs(wheelSpeeds[i]);
	            if (maxMagnitude < temp) maxMagnitude = temp;
	        }
	        if (maxMagnitude > 1.0) {
	            for (i=0; i<4; i++) {
	                wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude;
	            }
	        }
	    }
	

	/*
	 * Set each Talon to an arbitrary value...or something preconfigured...or whatever...
	 * Math is done before this method is called. 
	 */
		
}
