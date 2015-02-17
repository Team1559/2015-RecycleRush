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
	double gAngle = 0.0;
	
	
	
	public MecanumDrive(Joystick j, Talon lf, Talon lr, Talon  rf, Talon rr){
		
		joy = j;
		leftFront = lf;
		leftRear = lr;
		rightFront = rf;
		rightRear = rr;
		g = new Gyro(Wiring.GYRO_ID);
				
		this.lf = 0.0; //accesses the double values for driving calculations
		this.lr = 0.0;
		this.rf = 0.0;
		this.rr = 0.0;
		
		resetGyro();
//		desiredAngle = g.getAngle();
		correctionAngle = 0.0;
		
		maxSpeed = Wiring.MAX_SPEED;
		
//		prevAngle = g.getAngle();
		
		kP = .03;
		kI = .01;
		kD = 0;//.2;
		smartInit();
		
	}	
	
	public void resetGyro(){
		
		g.reset();
		desiredAngle = 0.0;
		
	}
	
	public void smartInit(){
		
		SmartDashboard.putDouble("kP", kP*1000);
		SmartDashboard.putDouble("kI", kI*1000);
		SmartDashboard.putDouble("kD", kD*1000);
		
	}
	
	public void smartGet(){
		
		kP = SmartDashboard.getDouble("kP")/1000;
		kI = SmartDashboard.getDouble("kI")/1000;
		kD = SmartDashboard.getDouble("kD")/1000;
		
	}
	
	
	public double wrap(double r){
		//return (r > desiredAngle) ? 360 - (r - desiredAngle) : desiredAngle - r;	
		return (r > 180) ? wrap(r - 360) : (r <= -180) ? wrap(r + 360) : r;
//		if (Math.abs(r) >= 180) {
//			r = r % 360;
//			return (r > 180) ? r - 360 : r;
//		} else {
//			return r;
//		}
		
		//looks like the gyro is +-180, so I copied the code from last year, and modified it for angles instead of radians
	}
	
public void drivePIDToteCenter(double x, double y, double rotationClockwise){
		
		//fix center of rotation
		//FRONT 10%
		//READ 100%
		
		x = Math.abs(x) * x;
		y = Math.abs(y) * y;
		rotationClockwise = Math.abs(rotationClockwise) * rotationClockwise;
		
		double wrappedGyro = wrap(g.getAngle());
		
		smartGet();
//		System.out.println("P" + kP + " I" + kI + " D" + kD);
		
		if(joy.getPOV(0) != -1){
			desiredAngle = joy.getPOV(0);
			System.out.println("GOT A POV");
		}
		
		gyroAngle = g.getAngle();
		
		if(Math.abs(rotationClockwise) > .1){
			desiredAngle = wrappedGyro;
			i = 0;
		} else {
			SmartDashboard.putDouble("Desired", desiredAngle);
			double delta = wrappedGyro - desiredAngle;
			SmartDashboard.putDouble("un-Delta Angle", delta);
			delta = -wrap(delta);
			SmartDashboard.putDouble("Adjusted Delta", delta);
			i += delta * .01;
			double d = g.getRate();
			rotationClockwise = (kP * delta) + (kI * i) + (kD * d);
		}
		
		double xIn = x;
        double yIn = -y;
        
//        double rotated[] = rotateVector(xIn, yIn, gyroAngle);
//        xIn = rotated[0];
//        yIn = rotated[1];
        
		double wheelSpeeds[] = new double[4];
        wheelSpeeds[0] = xIn + yIn + (rotationClockwise * .1);
        wheelSpeeds[1] = -xIn + yIn - (rotationClockwise * .1);
        wheelSpeeds[2] = -xIn + yIn + (rotationClockwise);
        wheelSpeeds[3] = xIn + yIn - rotationClockwise;
		normalize(wheelSpeeds);
		
		leftFront.set(-wheelSpeeds[0] * maxSpeed);
        rightFront.set(wheelSpeeds[1] * maxSpeed);
        leftRear.set(-wheelSpeeds[2] * maxSpeed);
        rightRear.set(wheelSpeeds[3] * maxSpeed);
	}
	
	public void drivePID(double x, double y, double rotationClockwise){
		
		//fix center of rotation
		//FRONT 10%
		//READ 100%
		
		x = Math.abs(x) * x;
		y = Math.abs(y) * y;
		rotationClockwise = Math.abs(rotationClockwise) * rotationClockwise;
		
		double wrappedGyro = wrap(g.getAngle());
		
		smartGet();
//		System.out.println("P" + kP + " I" + kI + " D" + kD);
		
		if(joy.getPOV(0) != -1){
			desiredAngle = joy.getPOV(0);
			System.out.println("GOT A POV");
		}
		
		gyroAngle = g.getAngle();
		
		if(Math.abs(rotationClockwise) > .1){
			desiredAngle = wrappedGyro;
			i = 0;
		} else {
			SmartDashboard.putDouble("Desired", desiredAngle);
			double delta = wrappedGyro - desiredAngle;
			SmartDashboard.putDouble("un-Delta Angle", delta);
			delta = -wrap(delta);
			SmartDashboard.putDouble("Adjusted Delta", delta);
			i += delta * .01;
			double d = g.getRate();
			rotationClockwise = (kP * delta) + (kI * i) + (kD * d);
		}
		
		double xIn = x;
        double yIn = -y;
        
//        double rotated[] = rotateVector(xIn, yIn, gyroAngle);
//        xIn = rotated[0];
//        yIn = rotated[1];
        
		double wheelSpeeds[] = new double[4];
        wheelSpeeds[0] = xIn + yIn + rotationClockwise;
        wheelSpeeds[1] = -xIn + yIn - rotationClockwise;
        wheelSpeeds[2] = -xIn + yIn + rotationClockwise;
        wheelSpeeds[3] = xIn + yIn - rotationClockwise;
		normalize(wheelSpeeds);
		
		leftFront.set(-wheelSpeeds[0] * maxSpeed);
        rightFront.set(wheelSpeeds[1] * maxSpeed);
        leftRear.set(-wheelSpeeds[2] * maxSpeed);
        rightRear.set(wheelSpeeds[3] * maxSpeed);
	}
	
	public void drive(double x, double y, double rotation){
//		
		gAngle = g.getAngle();
		
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
		
		gyroAngle = gAngle;
		
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
