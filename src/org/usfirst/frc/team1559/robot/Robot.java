
package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Talon;

//testing test test
public class Robot extends IterativeRobot {
 
	Joystick joy;
	Talon lf, lr, rf, rr;
	RobotDrive rd;
//	Lifter lifter;
	Gyro g;
	int count;
    boolean pressed;
    boolean x;
    boolean y;
    boolean z;
    Pixy pixy;
    int halfBand = Wiring.PIXY_HALF_BAND;
    PixyController p;
    Arduino arduino;
    double sonarInch;
	MaxSonar sonar;
	double wallDist;
	int recordCase;
	boolean pressedRec;
	
	Encoder pedX;
	Encoder pedY;
	
    public void robotInit() {
        //drive system
    	joy = new Joystick(Wiring.JOYSTICK_1);
    	lf = new Talon(Wiring.LEFT_FRONT_MOTOR_ID); //backwards
    	lr = new Talon(Wiring.LEFT_REAR_MOTOR_ID); //backwards
    	rf = new Talon(Wiring.RIGHT_FRONT_MOTOR_ID);
    	rr = new Talon(Wiring.RIGHT_REAR_MOTOR_ID);
    	rd = new RobotDrive(lf, lr, rf, rr);
    	rd.setInvertedMotor(MotorType.kFrontLeft, true);
    	rd.setInvertedMotor(MotorType.kRearLeft, true);
    	rd.setMaxOutput(.5);
//    	lifter = new Lifter(Wiring.LIFTER_JAGUAR_VALUE);
    	g = new Gyro(Wiring.GYRO_ID);
    	count = 0;
    	sonar = new MaxSonar(0);
    	pedX = new Encoder(1, 2);
    	pedY = new Encoder(3, 4);
    	recordCase = 0;
    	pressed = false;
    }


    public void autonomousInit(){

    	
    	
    }
    
    
    public void autonomousPeriodic() {
   
    }
    
    public void teleopInit(){
    	
    	g.reset();
    	
    }


    public void teleopPeriodic() {
        
    	rd.mecanumDrive_Cartesian(joy.getX(), joy.getY(), joy.getRawAxis(4), g.getAngle());
//    	System.out.println(g.getAngle());
    	
    	if(joy.getRawButton(1)){
    		g.reset();
    	}
    	
//        lifterControls();
//        arduinoControls();
//        pixyControls();
    	
    	System.out.println("PED_X: " + pedX.getDistance() + " PED_Y: " + pedY.getDistance());
    	
    }
    

    //USE FOR RECORDING AUTONOMOUS
    public void testInit(){
    	
    	System.out.println("===RECORD/PLAY FUNCTION===");
    	System.out.println("CONTROLS:");
    	System.out.println("     4       |BTN / FUNCTION \n"
    			         + "	 |	     |1   / Start Move \n"
    					 + " 2 - # - 3   |2   / Stop Move   \n"
    					 + "     |       |3   / Next Move  \n"
    					 + "     1       |4   / Stop Recording, Save");
    	
    	    	
    }
    
    public void testPeriodic() {
    
    	if(joy.getRawButton(1) && !pressedRec){
    		System.out.println("BTN1: Start Recording move...");
    		pressedRec = true;
    	} else if(joy.getRawButton(2) && !pressedRec){
    		System.out.println("BTN2: Stopped Recording move...");
    		pressedRec = true;
    	} else if(joy.getRawButton(3) && !pressedRec){
    		System.out.println("BTN3: Next Move...");
    		pressedRec = true;
    	} else if(joy.getRawButton(3) && !pressedRec){
    		System.out.println("BTN4: Stopped recording, saving persistence file...");
    		pressedRec = true;
    	}
    	
    }
    
    public void arduinoControls(){

        if (joy.getRawButton(1)){
            arduino.Write(1);
        }
        else if (joy.getRawButton(2)){
            arduino.Write(2);
        }
        else if (joy.getRawButton(3)){
            arduino.Write(3);
        }
        else if (joy.getRawButton(4)){
            arduino.Write(4);
        }
        else if(joy.getRawButton(5)){
            arduino.Write(5);
        }
        else if(joy.getRawButton(6)){
            arduino.Write(6);
        }
        else {
            arduino.Write(0);
        }

    }

    public void pixyControls(){

        //put pixy stuff in here, please. OK CODY
    	rd.mecanumDrive_Cartesian(p.autoCenter(), 0, -.023, g.getAngle());

    }

//    public void lifterControls(){
//
//        SmartDashboard.putNumber("Encoder Pos.", lifter.getPosition() - lifter.getHome());
//        SmartDashboard.putNumber("Encoder Spd.", lifter.getSpeed());
//        
//        if(joy.getRawButton(10) && !pressed) {
//            lifter.liftTote(1);
//            pressed = true;
//        }
//        else if(joy.getRawButton(11) && !pressed) {
//            lifter.liftTote(2);
//            pressed = true;
//        }
//        else if(joy.getRawButton(7) && !pressed) {
//            lifter.goHome();
//        }
//        else {
//            pressed = false;
//        }
//
//    }
    
}
