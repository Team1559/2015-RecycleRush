
package org.usfirst.frc.team1559.robot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
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
 
	Joystick pilotXY, pilotR, copilot;
	Talon lf, lr, rf, rr;
	Solenoid in;
	Solenoid out;
	MecanumDrive md;
	Lifter lifter;
	int count;
    boolean pressed;
    Pixy pixy;
    PixyPacket pp;
    int halfBand = Wiring.PIXY_HALF_BAND;
    PixyController p;
    Arduino arduino;
    double sonarInch;
    IRSensor irSensor;
	MaxSonar sonar;
	double wallDist;
	int timesRun = 0;
	int state = 0;
	Wings wing;
	Compressor c;
	boolean firstHome;
	Pedometer ped;
	
	//record / playback functions
	String command;
	int lifterLevel;
	Gatherer gather;
	DebounceButton dbb;
	
	
    public void robotInit() {
        //drive system
    	pilotXY = new Joystick(Wiring.JOYSTICK_1);
    	pilotR = new Joystick(1);
    	copilot = new Joystick(2);
    	lf = new Talon(Wiring.LEFT_FRONT_MOTOR_ID); //backwards
    	lr = new Talon(Wiring.LEFT_REAR_MOTOR_ID); //backwards
    	rf = new Talon(Wiring.RIGHT_FRONT_MOTOR_ID);
    	rr = new Talon(Wiring.RIGHT_REAR_MOTOR_ID);
    	md = new MecanumDrive(pilotR, lf, lr, rf, rr);
    	lifter = new Lifter(Wiring.LIFTER_JAGUAR_VALUE);
    	firstHome = true;
    	count = 0;
    	sonar = new MaxSonar(Wiring.SONAR_ID);
    	wing = new Wings();
    	c = new Compressor();
    	c.start();
    	gather = new Gatherer();
    	in = new Solenoid(Wiring.GATHER_ARMS_IN);
    	out = new Solenoid(Wiring.GATHER_ARMS_OUT);
    	
    	irSensor = new IRSensor();
    	
        //pixy stuff
        pixy = new Pixy();
        p = new PixyController(pixy);
        
        ped = new Pedometer();

        dbb = new DebounceButton(copilot, 1);
        
        arduino = new Arduino(4);
        //arduino stuff
//        arduino = new Arduino(0,1,2);
        
        //record/playback stuff
        //sorry about the nasty try-catch
        
    }
    public void disabledInit(){
    	arduino.writeSequence(4);
    }


    public void autonomousInit(){
    	
    	count = 1;
    	if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue){
        	arduino.writeAlliance(1);
        }
        else {
        	arduino.writeAlliance(0);
        }
    	
    }
    
    /* no comment */
    
    
    /////////////////////////////////////well commented
    //m'comment *tips IDE*
    //dank meme
    
    public void autonomousPeriodic() {
//    	PixyPacket pkt = pixy.getPacket();
////    	md.drive(p.autoCenter(pkt), -p.autoCenter(pkt), 0, g.getAngle());
//    	SmartDashboard.putDouble("Error for Pixy", p.error);
//    	SmartDashboard.putDouble("Crate Ratio", p.objRatio);
////    	SmartDashboard.putDouble("Gyro angle", g.getAngle());
//    	
//    	
//    	switch (count){
//    	case 1:
//    		wing.release();
//    		lifter.moveUp(1);
//    		count++;
//    		break;
//    	case 2:
//    		gather.gatherIn();
//    		try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//    		gather.stopGather(); //need ir to stop
//    		count++;
//    		break;
//    	case 3:
//    		lifter.goHome();
//    		try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//    		wing.latch();
//    		try {
//				Thread.sleep(500);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//    		lifter.moveUp(1);
//    		count++;
//    		break;
//    	case 4:
//    		
//    		
//    		break;
//    		
//    	}
//    	
//    	
//    	
////    	SmartDashboard.putDouble("Crate center", pp.X);
////
////    	wallDist = sonar.getInches();
////    	int toWall = 60;
////    	System.out.println("Count: "+count);
////    	System.out.println(wallDist);
////    	switch(count)
////    	{
////    	
////    	case 1:
////    		if(timesRun > 0) {
////    			//Drive
//////    			lifter.goHome();
////    		}
//////    		lifter.liftTote(1);
////    		count = 2;
////    		break;
////    	case 2:
////    		if(wallDist>toWall-25)
////    		{
////        		rd.mecanumDrive_Cartesian(-.75, -.2, Wiring.STUPID_CHASSIS_SIDEWAYS, g.getAngle());
////    		}
////    		else
////    			count = 3;
////    		break;
////    	case 3:
////    		wallDist = sonar.getInches();
////    		if(wallDist<toWall)
////    		{
////        		rd.mecanumDrive_Cartesian(.5, -.35, Wiring.STUPID_CHASSIS_SIDEWAYS, g.getAngle());
////    		}
////    		else
////    			count = 4;
////    		break;
////    	case 4:
////    		rd.mecanumDrive_Cartesian(p.autoCenter(), 0, Wiring.STUPID_CHASSIS_SIDEWAYS, g.getAngle());
////    		if(timesRun == 2){
////    			count = 5;
////    		}
////    		else{
////    			count = 1;
////    			timesRun++;
////    		}
////    		break;
////    	case 5:
////    		wallDist = sonar.getInches();
////    		if (wallDist<100)
////    		{
////    			rd.mecanumDrive_Cartesian(.5, 0, Wiring.STUPID_CHASSIS_CORRECTION, g.getAngle());
////    		}
////    		else
////    			count = 6;
////    		break;
////    	case 6:
//////    		lifter.goHome();
////    		count = 7;
////    		break;
////    	case 7:
////    		rd.mecanumDrive_Cartesian(0, .5, Wiring.STUPID_CHASSIS_CORRECTION, g.getAngle());
////    		count = 8;
////    	case 8:
////    		//Pretty Lights
////    		break;
////    	}
    }
    
   
    
    public void teleopInit(){
    	arduino.writeSequence(1);
    	if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue){
        	arduino.writeAlliance(1);
        }
        else {
        	arduino.writeAlliance(0);
        }
    }


    public void teleopPeriodic() {
//    	SmartDashboard.putDouble("Gyro angle", g.getAngle());
//    	rd.mecanumDrive_Cartesian(joy.getX(), joy.getY(), joy.getRawAxis(4), g.getAngle());
//    	System.out.println(g.getAngle());
//    	md.drivePID(pilotXY.getX(), pilotXY.getY(), pilotR.getZ());
    	md.drivePIDToteCenter(pilotXY.getX(), pilotXY.getY(), pilotR.getTwist());
    	
//    	System.out.println("X " + ped.getX());
//    	System.out.println("Y " + ped.getY());
//    	
    	SmartDashboard.putDouble("Lifter Current", lifter.getOutputCurrent());
    	
    	//md.drive(joy.getX(), joy.getY(), joy.getRawAxis(4), g.getAngle());
    	if(pilotR.getRawButton(XBoxController.BUTTON_A)){
    		md.resetGyro();
    	}
	
		
    	SmartDashboard.putDouble("IRSENSOR VALUE", irSensor.getVoltage());
    	
//        arduinoControls()
    	
        wingControls();
        lifterControls();
        gathererControls();
    	
    }
    
    public void gathererControls(){
    	
    	if(lifter.getPosition() > Wiring.GATHERER_HEIGHT){
	    	if(pilotXY.getRawButton(4)){    		
	    		gather.gatherIn();
	    	} else if(pilotXY.getRawButton(5)){
	    		gather.gatherOut();
//	    	} else if(pilot2.getRawAxis(3) > 0){
//	    		gather.rotateRight(pilot2.getRawAxis(3));
//	    	} else if(pilot2.getRawAxis(2) > 0){
//	    		gather.rotateLeft(pilot2.getRawAxis(2));
	    	} else {
	    		gather.stopGather();	    		
	    	}
    	} else {
    		gather.stopGather();
   	}
    	
    }
    
    public void wingControls(){
    	
    	if(pilotXY.getRawButton(3)){
    		wing.latch();
    	} else {
    		wing.release();
    	}
    	
    }
    
    public void testPeriodic() {
        lifter.set(copilot.getAxis(AxisType.kY));
        System.out.println("Encoder Pos. " + (lifter.getPosition() - lifter.getHome()));
//        System.out.println("Encoder Spd." + lifter.getSpeed());
    }


    public void lifterControls(){

//    	if (lifter.getPosition()<=lifter.tgtHeight-.5 && lifter.getPosition()>=lifter.tgtHeight+.5)
//    		lifter.configForwardLimit(lifter.tgtHeight);
    	if(dbb.getRelease()){
    		lifter.set(0);
    	}
    	if(firstHome){
	        if(!lifter.getReverseLimitOK() && lifter.hardLimit){
	        	if (lifter.getSpeed() == 0){
	        		lifter.setHome();
	        		firstHome = false;
	        		lifterLevel = 0;
	        		Wiring.GATHERER_HEIGHT = lifter.getHome()+25;
	        	}	        	
	        }
    	} else {
    		if(copilot.getRawButton(2)){
    			if (lifter.getSpeed() == 0){
	        		lifter.setHome();
	        		firstHome = false;
	        		lifterLevel = 0;
	        		Wiring.GATHERER_HEIGHT = lifter.getHome()+25;
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
    	
        if(!copilot.getRawButton(1)){
        	
        	
	        if(copilot.getRawButton(7) || copilot.getRawButton(8))
	        { 
	        	if(!pressed){
	        		if (lifter.getPosition()>lifter.tgtHeight1)
	        			lifter.moveDown(1);
	        		else
	        			lifter.moveUp(1);
	            }
	        	pressed = true;
	        } else if(copilot.getRawButton(9) || copilot.getRawButton(10)) {
	        	if(!pressed){
	        		if (lifter.getPosition()>lifter.tgtHeight2)
	        			lifter.moveDown(2);
	        		else
	        			lifter.moveUp(2);
	        	}
	        	pressed = true;
	        } else if(copilot.getRawButton(11) || copilot.getRawButton(12)) {
	        	if (!pressed){
	        		state++;
	//        		lifter.moveUp(3);
	        		lifter.disableSoftPositionLimits();
	        		lifter.set(lifter.UP);
	        	}
	        	pressed = true;
	        } else if(copilot.getRawButton(5) || copilot.getRawButton(6)) {
	        	if (!pressed){
		            lifter.goHome();
	        	}
	        	pressed = true;
	        } else {
	            pressed = false;
	        }
        } else {        	
        	lifter.disableSoftPositionLimits();
        	lifter.set(copilot.getRawAxis(1));           	        	
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
