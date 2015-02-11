
package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//testing test test
public class Robot extends IterativeRobot {
 
	Joystick joy, joy2;
	Talon lf, lr, rf, rr;
	Talon rightGatherer;
	Talon leftGatherer;
	Solenoid in;
	Solenoid out;
	MecanumDrive md;
	Lifter lifter;
	Gyro g;
	int count;
    boolean pressed;
    boolean x;
    boolean y;
    boolean z;
    Pixy pixy;
    PixyPacket pp;
    int halfBand = Wiring.PIXY_HALF_BAND;
    PixyController p;
    Arduino arduino;
    double sonarInch;
	MaxSonar sonar;
	double wallDist;
	int timesRun = 0;
	int state = 0;
	Wings wing;
	Compressor c;
	boolean firstHome;
	Pedometer ped;
	
    public void robotInit() {
        //drive system
    	joy = new Joystick(Wiring.JOYSTICK_1);
    	joy2 = new Joystick(Wiring.JOYSTICK_2);
    	lf = new Talon(Wiring.LEFT_FRONT_MOTOR_ID); //backwards
    	lr = new Talon(Wiring.LEFT_REAR_MOTOR_ID); //backwards
    	rf = new Talon(Wiring.RIGHT_FRONT_MOTOR_ID);
    	rr = new Talon(Wiring.RIGHT_REAR_MOTOR_ID);
    	md = new MecanumDrive(joy, lf, lr, rf, rr, g);
    	lifter = new Lifter(Wiring.LIFTER_JAGUAR_VALUE);
    	firstHome = true;
    	g = new Gyro(Wiring.GYRO_ID);
    	count = 0;
    	sonar = new MaxSonar(1);
    	wing = new Wings();
    	c = new Compressor();
    	c.start();
    	
    	leftGatherer = new Talon(4);
    	rightGatherer = new Talon(5);
    	in = new Solenoid(0);
    	out = new Solenoid(1);
    	
        //pixy stuff
        pixy = new Pixy();
        p = new PixyController(pixy);
        pp = new PixyPacket();
        
        ped = new Pedometer();

        //arduino stuff
        arduino = new Arduino(0,1,2);
    }
    public void disabledInit(){
    	
    }


    public void autonomousInit(){
    	
    	g.reset();
    	count = 1;
    	
    }
    
    
    public void autonomousPeriodic() {
    	md.drive(p.autoCenter(), 0, 0, g.getAngle());
    	SmartDashboard.putDouble("Error for Pixy", p.error);
    	SmartDashboard.putDouble("Crate Ratio", p.objRatio);
//
//    	wallDist = sonar.getInches();
//    	int toWall = 60;
//    	System.out.println("Count: "+count);
//    	System.out.println(wallDist);
//    	switch(count)
//    	{
//    	
//    	case 1:
//    		if(timesRun > 0) {
//    			//Drive
////    			lifter.goHome();
//    		}
////    		lifter.liftTote(1);
//    		count = 2;
//    		break;
//    	case 2:
//    		if(wallDist>toWall-25)
//    		{
//        		rd.mecanumDrive_Cartesian(-.75, -.2, Wiring.STUPID_CHASSIS_SIDEWAYS, g.getAngle());
//    		}
//    		else
//    			count = 3;
//    		break;
//    	case 3:
//    		wallDist = sonar.getInches();
//    		if(wallDist<toWall)
//    		{
//        		rd.mecanumDrive_Cartesian(.5, -.35, Wiring.STUPID_CHASSIS_SIDEWAYS, g.getAngle());
//    		}
//    		else
//    			count = 4;
//    		break;
//    	case 4:
//    		rd.mecanumDrive_Cartesian(p.autoCenter(), 0, Wiring.STUPID_CHASSIS_SIDEWAYS, g.getAngle());
//    		if(timesRun == 2){
//    			count = 5;
//    		}
//    		else{
//    			count = 1;
//    			timesRun++;
//    		}
//    		break;
//    	case 5:
//    		wallDist = sonar.getInches();
//    		if (wallDist<100)
//    		{
//    			rd.mecanumDrive_Cartesian(.5, 0, Wiring.STUPID_CHASSIS_CORRECTION, g.getAngle());
//    		}
//    		else
//    			count = 6;
//    		break;
//    	case 6:
////    		lifter.goHome();
//    		count = 7;
//    		break;
//    	case 7:
//    		rd.mecanumDrive_Cartesian(0, .5, Wiring.STUPID_CHASSIS_CORRECTION, g.getAngle());
//    		count = 8;
//    	case 8:
//    		//Pretty Lights
//    		break;
//    	}
    }
    
    public void teleopInit(){
    	
    	g.reset();
    	
    }


    public void teleopPeriodic() {
        
//    	rd.mecanumDrive_Cartesian(joy.getX(), joy.getY(), joy.getRawAxis(4), g.getAngle());
//    	System.out.println(g.getAngle());
    	md.drive(joy2.getX(), joy2.getY(), joy2.getRawAxis(4), g.getAngle());
    	
    	System.out.println("X " + ped.getX());
    	System.out.println("Y " + ped.getY());
    	
    	if(joy2.getRawButton(XBoxController.BUTTON_A)){
    		g.reset();
    	}
//    	SmartDashboard.putDouble("Voltage", sonar.getVoltage());    
//    	SmartDashboard.putDouble("Feets", sonar.getFeet());
//    	SmartDashboard.putDouble("Inches", sonar.getInches());
//    	System.out.println("Target Height " + lifter.tgtHeight);
//		System.out.println("Encoder Pos." +  lifter.getPosition());
//		System.out.println("Home Pos. " + lifter.getHome());
		
		if(firstHome){
	        if(!lifter.getReverseLimitOK() && lifter.hardLimit){
	        	if (lifter.getSpeed() == 0){
	        		lifter.setHome();
	        		firstHome = false;
	        	}	        	
	        }
    	}
		
        if (lifter.movingDown)
        {
        	if (lifter.getPosition()<= lifter.tgtHeight)
        	{
        		lifter.stop();
        	}
        	
        }
//        arduinoControls();
        wingControls();
        lifterControls();
        gathererControls();
    	
    }
    
    public void gathererControls(){
    	
    	if(lifter.getPosition() > Wiring.GATHERER_HEIGHT){
	    	if(joy2.getRawButton(XBoxController.BUTTON_RB)){    		
	    		rightGatherer.set(-.25);
	    		leftGatherer.set(.25);
	    		in.set(true);
	    		out.set(false);
	    	} else if(joy2.getRawButton(XBoxController.BUTTON_LB)){
	    		rightGatherer.set(.5);
	    		leftGatherer.set(-.5);
	    		in.set(true);
	    		out.set(false);
	    	} else {
	    		rightGatherer.set(0);
	    		leftGatherer.set(0);
	    		in.set(false);
	    		out.set(true);
	    	}
    	} else {
    		out.set(true);
    		in.set(false);
    		rightGatherer.set(0);
    		leftGatherer.set(0);
    	}
    	
    }
    
    public void wingControls(){
    	
    	if(joy.getRawButton(7)){
    		wing.latch();
    	} else {
    		wing.release();
    	}
    	
    }
    
    public void testPeriodic() {
        lifter.set(joy2.getAxis(AxisType.kY));
        System.out.println("Encoder Pos. " + (lifter.getPosition() - lifter.getHome()));
//        System.out.println("Encoder Spd." + lifter.getSpeed());
    }

    public void pixyControls(){

        //put pixy stuff in here, please. OK CODY
//    	rd.mecanumDrive_Cartesian(p.autoCenter(), 0, -.023, g.getAngle());
    	

    }

    public void lifterControls(){

//    	if (lifter.getPosition()<=lifter.tgtHeight-.5 && lifter.getPosition()>=lifter.tgtHeight+.5)
//    		lifter.configForwardLimit(lifter.tgtHeight);
        
        if(joy.getRawButton(4))
        { 
        	if(!pressed){
        		if (lifter.getPosition()>lifter.tgtHeight1)
        			lifter.moveDown(1);
        		else
        			lifter.moveUp(1);
            }
        	pressed = true;
        } else if(joy.getRawButton(3)) {
        	if(!pressed){
        		if (lifter.getPosition()>lifter.tgtHeight2)
        			lifter.moveDown(2);
        		else
        			lifter.moveUp(2);
        	}
        	pressed = true;
        } else if(joy.getRawButton(5)) {
        	if (!pressed){
        		state++;
        		lifter.moveUp(3);
        	}
        	pressed = true;
        } else if(joy.getRawButton(2)) {
        	if (!pressed){
	            lifter.goHome();
        	}
        	pressed = true;
        } else {
            pressed = false;
        }
    }
    	// Hide all this as backup
//    	if(joy.getRawButton(4))
//        { 
//        	if(!pressed){
//        		state--;
//            lifter.setToteHeight(1);
//            }
//        	pressed = true;
//        } else if(joy.getRawButton(3)) {
//        	if(!pressed){
//	            lifter.setToteHeight(2);
//        	}
//        	pressed = true;
//        } else if(joy.getRawButton(5)) {
//        	if (!pressed){
//        		state++;
//	            lifter.setToteHeight(3);
//        	}
//        	pressed = true;
//        } else if(joy.getRawButton(2)) {
//        	if (!pressed){
//	            lifter.goHome();
//        	}
//        	pressed = true;
//        } else {
//            pressed = false;
//        }
//    }
	}
