
package org.usfirst.frc.team1559.robot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import edu.wpi.first.wpilibj.AnalogInput;
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
 
	Joystick pilot, copilot;
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
//    Arduino arduino;
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
	File f;
	FileReader fr;
	BufferedReader br;
	String command;
	int lines;
	double x;
	double y;
	int lifterLevel;
	boolean done;
	double xComp;
	double yComp;
	double autoSpeed = Wiring.AUTO_SPEED;
	Gatherer gather;
	
    public void robotInit() {
        //drive system
    	pilot = new Joystick(Wiring.JOYSTICK_1);
    	copilot = new Joystick(Wiring.JOYSTICK_2);
    	lf = new Talon(Wiring.LEFT_FRONT_MOTOR_ID); //backwards
    	lr = new Talon(Wiring.LEFT_REAR_MOTOR_ID); //backwards
    	rf = new Talon(Wiring.RIGHT_FRONT_MOTOR_ID);
    	rr = new Talon(Wiring.RIGHT_REAR_MOTOR_ID);
    	md = new MecanumDrive(pilot, lf, lr, rf, rr);
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

        //arduino stuff
//        arduino = new Arduino(0,1,2);
        
        //record/playback stuff
        //sorry about the nasty try-catch
        
        try {
        	f = new File("/home/lvuser/Output.txt");
			if(!f.exists()){
	        	f.createNewFile();
	        }
			fr = new FileReader(f);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        br = new BufferedReader(fr);
        
        lines = 0;
        command = "DEFAULT";
        x = 0.0;
        y = 0.0;
        //it's ok the nasty try-catch is gone
        done = true;
        
        lifterLevel = 0;
        xComp = 0.0;
        yComp = 0.0;
        
    }
    public void disabledInit(){
    	
    }


    public void autonomousInit(){
    	
    	count = 1;
    	
    }
    
    
    public void autonomousPeriodic() {
    	PixyPacket pkt = pixy.getPacket();
//    	md.drive(p.autoCenter(pkt), -p.autoCenter(pkt), 0, g.getAngle());
    	SmartDashboard.putDouble("Error for Pixy", p.error);
    	SmartDashboard.putDouble("Crate Ratio", p.objRatio);
//    	SmartDashboard.putDouble("Gyro angle", g.getAngle());
    	
    	//playback();
    	
    	switch (count){
    	case 1:
    		wing.release();
    		lifter.moveUp(1);
    		count++;
    		break;
    	case 2:
    		gather.gatherIn();
    		try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		gather.stopGather(); //need ir to stop
    		count++;
    		break;
    	case 3:
    		lifter.goHome();
    		try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		wing.latch();
    		try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		lifter.moveUp(1);
    		count++;
    		break;
    	case 4:
    		
    		
    		break;
    		
    	}
    	
    	
    	
//    	SmartDashboard.putDouble("Crate center", pp.X);
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
    
    public void playback(){
    	//ew
    	if(done){
	    	try {
				command = br.readLine();
				done = false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//end ew
    	}
		
		if(command.equals("<<START>>")){
			System.out.println(lines + ". We are at the starting position.");
		} else if(command.contains("[MOVE]")){
			//code for decoding x and y values
		    x = Double.valueOf(command.substring(command.indexOf("X")+1, command.indexOf("Y")-1));
		    y = Double.valueOf(command.substring(command.indexOf("Y")+1));
			System.out.println(lines + ". Move " + x + " inches in x. Move " + y + " inches in Y.");
			
			if((x > 0) && (y > 0)){
				
				if((ped.getX() < x)){
					xComp = autoSpeed * 1;
				} else {
					xComp = 0;
				}
				
				if(ped.getY() < y){
					yComp = autoSpeed * 1;
				} else {
					yComp = 0;
				}
				
//				md.drive(xComp, yComp, 0, g.getAngle());
				if((xComp >= x) && (yComp >= y)){
					done = true;
					ped.reset();
				}
				
			} else if((x < 0) && (y > 0)){
				
				if((ped.getX() > x)){
					xComp = autoSpeed * -1;
				} else {
					xComp = 0;
				}
				
				if(ped.getY() < y){
					yComp = autoSpeed * 1;
				} else {
					yComp = 0;
				}
				
//				md.drive(xComp, yComp, 0, g.getAngle());
				if((xComp >= x) && (yComp >= y)){
					done = true;
					ped.reset();
				}
				
			} else if((x > 0) && (y < 0)){
				
				if((ped.getX() < x)){
					xComp = autoSpeed * 1;
				} else {
					xComp = 0;
				}
				
				if(ped.getY() > y){
					yComp = autoSpeed * -1;
				} else {
					yComp = 0;
				}
				
//				md.drive(xComp, yComp, 0, g.getAngle());
				if((xComp >= x) && (yComp >= y)){
					done = true;
					ped.reset();
				}
				
			} else if((x < 0) && (y > 0)){
				
				if((ped.getX() > x)){
					xComp = autoSpeed * -1;
				} else {
					xComp = 0;
				}
				
				if(ped.getY() < y){
					yComp = autoSpeed * 1;
				} else {
					yComp = 0;
				}
				
//				md.drive(xComp, yComp, 0, g.getAngle());
				if((xComp >= x) && (yComp >= y)){
					done = true;
					ped.reset();
				}
				
			}
			
		} else if(command.contains("[GATHER]")){
			int totes;
			String s = command.substring(command.indexOf((" "))).trim();
			totes = Integer.valueOf(s);
			System.out.println(lines + ". Gather " + totes + " tote(s)");
			if(lifterLevel > totes){			
				lifter.moveDown(totes);		
				lifterLevel --;
				done = true;
			} else {			
				lifter.moveUp(totes);
				lifterLevel++;
				done = true;
			}
		} else {
			System.out.println("STOP");
		}
		lines++;
    }
    
    public void teleopInit(){
    	
    }


    public void teleopPeriodic() {
//    	SmartDashboard.putDouble("Gyro angle", g.getAngle());
//    	rd.mecanumDrive_Cartesian(joy.getX(), joy.getY(), joy.getRawAxis(4), g.getAngle());
//    	System.out.println(g.getAngle());
    	md.drivePID(pilot.getX(), pilot.getY(), pilot.getRawAxis(4));
//    	System.out.println("X " + ped.getX());
//    	System.out.println("Y " + ped.getY());
//    	
    	SmartDashboard.putDouble("Lifter Current", lifter.getOutputCurrent());
    	
    	//md.drive(joy.getX(), joy.getY(), joy.getRawAxis(4), g.getAngle());
    	if(pilot.getRawButton(XBoxController.BUTTON_A)){
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
	    	if(pilot.getRawButton(XBoxController.BUTTON_RB)){    		
	    		gather.gatherIn();
	    	} else if(pilot.getRawButton(XBoxController.BUTTON_LB)){
	    		gather.gatherOut();
	    	} else {
	    		gather.stopGather();
	    	}
    	} else {
    		gather.stopGather();
    	}
    	
    }
    
    public void wingControls(){
    	
    	if(pilot.getRawButton(XBoxController.BUTTON_B)){
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
        
    	if(firstHome){
	        if(!lifter.getReverseLimitOK() && lifter.hardLimit){
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
