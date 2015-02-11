package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MecanumDrive {

	Talon leftFront, leftRear;
	Talon rightFront, rightRear;
	Joystick joy;
	Gyro g;
	
	//use these to set
	double lf;
	double lr;
	double rf;
	double rr;
	double desiredAngle;
	double gyroAngle;
	double correctionAngle;
	double maxSpeed;
	
	double kP, kI, kD;
	double i;
	double prevAngle;
	
	
	
	public MecanumDrive(Joystick j, Talon lf, Talon lr, Talon  rf, Talon rr, Gyro i){
		
		joy = j;
		leftFront = lf;
		leftRear = lr;
		rightFront = rf;
		rightRear = rr;
		g = i;
				
		this.lf = 0.0; //accesses the double values for driving calculations
		this.lr = 0.0;
		this.rf = 0.0;
		this.rr = 0.0;
		
		resetGyro();
//		desiredAngle = g.getAngle();
		correctionAngle = 0.0;
		
		maxSpeed = .5;
		
		smartInit();
//		prevAngle = g.getAngle();
		
		kP = .2;
		kI = .2;
		kD = .2;
		
	}	
	
	public void resetGyro(){
		
//		g.reset();
		
	}
	
	public void smartInit(){
		
		SmartDashboard.putDouble("kP", kP);
		SmartDashboard.putDouble("kI", kI);
		SmartDashboard.putDouble("kD", kD);
		
	}
	
	public void smartGet(){
		
		kP = SmartDashboard.getDouble("kP");
		kI = SmartDashboard.getDouble("kI");
		kD = SmartDashboard.getDouble("kD");
		
	}
	
	public double wrap(double r){
		return (r > desiredAngle) ? 360 - (r - desiredAngle) : desiredAngle - r;
	}
	
	public void drivePID(double x, double y, double rotation){
		
		smartGet();
		
		gyroAngle = g.getAngle();
		
		if(Math.abs(rotation) > .02){
			desiredAngle = gyroAngle;
		} else {
			double p = wrap(gyroAngle);
			i += wrap(gyroAngle) * .01;
			double d = g.getRate();
			rotation = ((kP * p)/360) + ((kI * i)/360) + ((kD * d)/360);
		}
		
		double xIn = x;
        double yIn = y;
        
		double wheelSpeeds[] = new double[4];
        wheelSpeeds[0] = xIn + yIn + rotation;
        wheelSpeeds[1] = -xIn + yIn - rotation;
        wheelSpeeds[2] = -xIn + yIn + rotation;
        wheelSpeeds[3] = xIn + yIn - rotation;
		normalize(wheelSpeeds);
		
		i = g.getAngle();
	}
	
	public void drive(double x, double y, double rotation){
//		
//		desiredAngle += rotation;
//		
//		if(desiredAngle > gyroAngle+1){
//			correctionAngle-=0.01;
//		} else if(desiredAngle < gyroAngle-1){
//			correctionAngle+=0.01;
//		} else {
//			correctionAngle = 0.0;
//		}
//		
//		rotation += correctionAngle;
		
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
        leftFront.set(-wheelSpeeds[0] * maxSpeed);
        rightFront.set(wheelSpeeds[1] * maxSpeed);
        leftRear.set(-wheelSpeeds[2] * maxSpeed);
        rightRear.set(wheelSpeeds[3] * maxSpeed);
		
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
