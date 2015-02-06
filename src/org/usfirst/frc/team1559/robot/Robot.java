
package org.usfirst.frc.team1559.robot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
//	RobotDrive rd;
//	Lifter lifter;
	MecanumDrive md;
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
	int positions;
	FileWriter fw;
	int lines;
	String command;
	BufferedWriter bw;
	double moveX;
	double moveY;
	
	double desiredAngle;
	
	Encoder pedX;
	Encoder pedY;
	
	File f;
	FileReader fr;
	BufferedReader br;
	
	boolean ready;
	
    public void robotInit() {
        //drive system
    	
    	joy = new Joystick(Wiring.JOYSTICK_1);
    	lf = new Talon(Wiring.LEFT_FRONT_MOTOR_ID); //backwards
    	lr = new Talon(Wiring.LEFT_REAR_MOTOR_ID); //backwards
    	rf = new Talon(Wiring.RIGHT_FRONT_MOTOR_ID);
    	rr = new Talon(Wiring.RIGHT_REAR_MOTOR_ID);
//    	rd = new RobotDrive(lf, lr, rf, rr);
//    	rd.setInvertedMotor(MotorType.kFrontLeft, true);
//    	rd.setInvertedMotor(MotorType.kRearLeft, true);
//    	rd.setMaxOutput(.5);
//    	lifter = new Lifter(Wiring.LIFTER_JAGUAR_VALUE);
    	g = new Gyro(Wiring.GYRO_ID);
    	count = 0;
    	sonar = new MaxSonar(0);
    	pedX = new Encoder(1, 2);
    	pedY = new Encoder(3, 4);
    	recordCase = 0;
    	pressed = false;
    	
    	//file reading
    	f = new File("/home/lvuser/Output.txt");
    	positions = 0;
    	lines = 1;
    	command = "DEFAULT_VALUE";
    	try {
    		if(!f.exists()){
    			f.createNewFile();
    		}
			fw = new FileWriter(f);
			fr = new FileReader(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	bw = new BufferedWriter(fw);
    	br = new BufferedReader(fr);
    	moveX = 0.0;
    	moveY = 0.0;
    	ready = true;
    	   	
    	//gyro pid
    	desiredAngle = 0.0;
    	
    	md = new MecanumDrive(joy, lf, lr, rf, rr, g);
    	
    }


    public void autonomousInit(){

    	
    	
    }
    
    
    public void autonomousPeriodic() {
   
    	if(ready){
	    	try {
	    		ready = false;
				command = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(command.equals("<<START>>")){
				System.out.println(lines + ". We are at the starting position.");
			} else if(command.contains("[MOVE]")){
				//code for decoding x and y values
			    moveX = Double.valueOf(command.substring(command.indexOf("X")+1, command.indexOf("Y")-1));
			    moveY = Double.valueOf(command.substring(command.indexOf("Y")+1));
	//			System.out.println(lines + ". Move " + x + " inches in x. Move " + y + " inches in Y.");
			    pedX.reset();
			    pedY.reset();
			} else if(command.contains("[GATHER]")){
				int totes;
				String s = command.substring(command.indexOf((" "))).trim();
				totes = Integer.valueOf(s);
				System.out.println(lines + ". Gather " + totes + " tote(s)");
			} else {
				System.out.println("STOP");
			}
			lines++;
    	} else {
    		
    	}
    }
    
    public void teleopInit(){
    	
    	g.reset();
    	
    }

    public void correction(){
    	
    	
    	
    }

    public void teleopPeriodic() {
        
//    	rd.mecanumDrive_Cartesian(joy.getX(), joy.getY(), joy.getRawAxis(4), g.getAngle());
    	md.drive(joy.getX(), joy.getY(), joy.getRawAxis(4));
    	
    	
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

    	System.out.println("     4       |BTN / FUNCTION \n"
    			         + "	 |	     |1   / Record position, Next move \n"
    					 + " 2 - # - 3   |2   / Gather 1 tote   \n"
    					 + "     |       |3   / Gather 2 totes  \n"
    					 + "     1       |4   / Gather 3 totes\n"
    					 + "             |R1   / Finish");
    	
    	    	
    }
    
    public void testPeriodic() {
    
    	if(positions == 0){
    		System.out.println(">>Place the robot in its initial location for Autonomous, then press\nbutton 1 on the controller...");
    		if(joy.getRawButton(1)){
    			pedX.reset();
        		pedY.reset();
        		System.out.println("Move the Robot to its next location.");
        		try {
					bw.write("<<START>>");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		testInit(); //legal?
        		positions++;
    		}
    	} if(positions > 0) {
    		if(joy.getRawButton(1) && !pressedRec){
    			System.out.print("RECORDING POSITION " + positions + "...");
        		double x = pedX.getDistance();
        		double y = pedY.getDistance();
        		pedX.reset();
        		pedY.reset();
        		try {
					bw.write(positions + " [MOVE] X" + x + " Y" + y);
					bw.newLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		System.out.println("DONE. Move the robot to the next position");
        		testInit();
        		positions++;
        		pressedRec = true;
        	} else if(joy.getRawButton(2) && !pressedRec){
        		pedX.reset();
    			pedY.reset();
        		System.out.print("Gathering 1 tote...");
        		System.out.println("Please place the tote on the robot as if it was gathered.");
        		System.out.println("Gathering operation recorded");
        		try {
					bw.write("[GATHER] 1");
					bw.newLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		testInit();
        		pressedRec = true;
        		positions++;
        	} else if(joy.getRawButton(3) && !pressedRec){
        		pedX.reset();
    			pedY.reset();
        		System.out.print("Gathering 2 totes...");
        		System.out.println("Please place the tote on the robot as if it was gathered.");
        		System.out.println("Gathering operation recorded");
        		try {
					bw.write("[GATHER] 2");
					bw.newLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		testInit();
        		pressedRec = true;
        		positions++;
        	} else if(joy.getRawButton(4) && !pressedRec){
        		pedX.reset();
    			pedY.reset();
        		System.out.print("Gathering 3 totes...");
        		System.out.println("Please place the tote on the robot as if it was gathered.");
        		System.out.println("Gathering operation recorded");
        		try {
					bw.write("[GATHER] 3");
					bw.newLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		testInit();
        		pressedRec = true;
        		positions++;
        	} else if(joy.getRawButton(5) && !pressedRec){
        		try {
        			bw.write("<<STOP>>");
					fw.close();
					bw.close();
					System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
							+ ">>Finished recording positions.");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	} else {
        		pressedRec = false;
        	}
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
//    	rd.mecanumDrive_Cartesian(p.autoCenter(), 0, -.023, g.getAngle());

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
