
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
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//testing test test
public class Robot extends IterativeRobot {
 
	
	PowerDistributionPanel pdp;
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
	Ramp ramp;
	
	//record / playback functions
	String command;
	int lifterLevel;
	Gatherer gather;
	DebounceButton dbb;
	BCDSwitch autoMode;
	int mode;
	int ticks;
	boolean once;
	boolean flag;
	int num;
	
	
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
    	
    	autoMode = new BCDSwitch(6, 7, 8, 9);
    	
    	once = true;
    	flag = true;
    	
    	irSensor = new IRSensor();
    	mode = -1;
    	
    	ticks = 0;
    	
        //pixy stuff
        pixy = new Pixy();
        p = new PixyController(pixy);
        
        pdp = new PowerDistributionPanel();
        
        ped = new Pedometer();

        dbb = new DebounceButton(copilot, 1);
        
        arduino = new Arduino(4);
        
        ramp = new Ramp();
        //arduino stuff
//        arduino = new Arduino(0,1,2);
        
        //record/playback stuff
        //sorry about the nasty try-catch
        
    }
    public void disabledInit(){
    	arduino.writeSequence(4);
    	flag = true;
    	once = true;
    }


    public void autonomousInit(){
    	
    	count = 1;
    	if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue){
        	arduino.writeAlliance(1);
        }
        else {
        	arduino.writeAlliance(0);
        }
    	
//    	mode = autoMode.read();
    	mode = 0;
    	num = 0;
    	once = true;
    	flag = true;
    	
    }
    
    /* no comment */
    
    
    /////////////////////////////////////well commented
    //m'comment *tips IDE*
    //dank meme
    
    public void autonomousPeriodic() {
    	
    	/*
    	 * 
    	 * THIS CODE ASSUMES THAT THE ROBOT WILL START WITH THE GATHERER ABOVE HOME!!!!!!!!!!!
    	 * 
    	 */
    	
    	switch(mode){
    	
    	default:
    		mode = 0;
    	break;
    	case 0:
    		switch(num){
    		default:
    			num = 0;
    		break;
    	
    		case 0:    		    
    			wing.release();
		    	if(irSensor.hasTote()){
		    		num++;
		    	} else {
		    		gather.gatherIn();
		    	}		    	
    		break;
    		case 1: 
    			if(once){
    				System.out.println("MODE " + num);
    				gather.stopGather();
        			lifter.goHome();/* You're drunk. */
        			once = false;
    			}    			
    			
    			if(!lifter.getReverseLimitOK()){
    				lifter.setHome();
    				Timer.delay(.25);
    				lifter.moveUp(1);
    				num++;
    			}
			break;
    		case 2:
    			if(sonar.getInches() <= 120){
    				md.drivePID(1, -.25, 0);
    				System.out.println("TRYING TO MOVE!!!! " + sonar.getInches());
    			} else {
    				num++;
    			}
    		break;
    		case 3:
    			md.drivePID(0, 0, 0);
//    			lifter.goHome();
//    			wing.latch();
    			arduino.writeSequence(2);
    		break;
    		
    	}
    	break;
    	
    	}
    	
    	
    	
//    	PixyPacket pkt = pixy.getPacket();
////    	md.drive(p.autoCenter(pkt), -p.autoCenter(pkt), 0, g.getAngle());
//    	SmartDashboard.putDouble("Error for Pixy", p.error);
//    	SmartDashboard.putDouble("Crate Ratio", p.objRatio);
////    	SmartDashboard.putDouble("Gyro angle", g.getAngle());
    }
    
   
    
    public void teleopInit(){
    	arduinoControls(); 
    }


    public void teleopPeriodic() {
//    	SmartDashboard.putDouble("Gyro angle", g.getAngle());
//    	rd.mecanumDrive_Cartesian(joy.getX(), joy.getY(), joy.getRawAxis(4), g.getAngle());
//    	System.out.println(g.getAngle());
//    	md.drivePID(pilotXY.getX(), pilotXY.getY(), pilotR.getZ());
    	
    	
//    	md.drivePIDToteCenter(pilotXY.getX(), pilotXY.getY(), pilotR.getZ());
    	
//    	System.out.println("X " + ped.getX());
//    	System.out.println("Y " + ped.getY());
//    	
//    	SmartDashboard.putNumber("Lifter Current", pdp.getCurrent(13));
    	
    	//md.drive(joy.getX(), joy.getY(), joy.getRawAxis(4), g.getAngle());
    	if(pilotR.getRawButton(Wiring.RESET_GYRO_BUT)){
    		md.resetGyro();
    	}    	
           	
        wingControls();
        lifterControls();
        gathererControls();
    	
    }
    
    public void arduinoControls(){
    	
    	arduino.writeSequence(1);
    	if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue){
        	arduino.writeAlliance(1);
        }
        else {
        	arduino.writeAlliance(0);
        }
    	
    }
    
    public void gathererControls(){
    	
    	if(lifter.getPosition() > Wiring.GATHERER_HEIGHT){
	    	if(pilotXY.getRawButton(Wiring.GATHER_IN_BUT)){    		
	    		gather.gatherIn();
	    	} else if(pilotXY.getRawButton(Wiring.GATHER_OUT_BUT)){
	    		gather.gatherOut();
	    	} else if(pilotXY.getRawButton(Wiring.PILOT_ROTATE_RIGHT)){
	    		gather.rotateRight();
	    	} else if(pilotXY.getRawButton(Wiring.PILOT_ROTATE_LEFT)){
	    		gather.rotateLeft();
	    	} else {
	    		gather.stopGather();	    		
	    	}
    	} else {
    		gather.stopGather();
   	}
    	
    }
    
    public void wingControls(){
    	
    	if(pilotXY.getRawButton(Wiring.WINGS_BUT)){
    		wing.latch();
    	} else {
    		wing.release();
    	}
    	
    }
    
    public void testPeriodic() {
//        lifter.set(copilot.getAxis(AxisType.kY));
//        System.out.println("Encoder Pos. " + (lifter.getPosition() - lifter.getHome()));
//        System.out.println("Encoder Spd." + lifter.getSpeed());
    	
    	System.out.println("HAS TOTE: " + irSensor.hasTote());
    	System.out.println("IR VOLTAGE: " + irSensor.getVoltage());
    	
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
    		if(copilot.getRawButton(Wiring.COPILOT_RED_LIGHT)){
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
    	
        if(!copilot.getRawButton(Wiring.COPILOT_TRIGGER)){
        	
        	
	        if(copilot.getRawButton(Wiring.COPILOT_T3) || copilot.getRawButton(Wiring.COPILOT_T4))
	        { 
	        	if(!pressed){
	        		if (lifter.getPosition()>lifter.tgtHeight1)
	        			lifter.moveDown(1);
	        		else
	        			lifter.moveUp(1);
	            }
	        	pressed = true;
	        } else if(copilot.getRawButton(Wiring.COPILOT_T5) || copilot.getRawButton(Wiring.COPILOT_T6)) {
	        	if(!pressed){
	        		if (lifter.getPosition()>lifter.tgtHeight2)
	        			lifter.moveDown(2);
	        		else
	        			lifter.moveUp(2);
	        	}
	        	pressed = true;
	        } else if(copilot.getRawButton(Wiring.COPILOT_T7) || copilot.getRawButton(Wiring.COPILOT_T8)) {
	        	if (!pressed){
	        		state++;
	//        		lifter.moveUp(3);
	        		lifter.disableSoftPositionLimits();
	        		lifter.set(lifter.UP);
	        	}
	        	pressed = true;
	        } else if(copilot.getRawButton(Wiring.COPILOT_T1) || copilot.getRawButton(Wiring.COPILOT_T2)) {
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
}
