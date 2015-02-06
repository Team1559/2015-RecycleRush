
package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.AxisType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//testing test test
public class Robot extends IterativeRobot {
 
	Joystick joy, joy2;
	Talon lf, lr, rf, rr;
	RobotDrive rd;
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
	
    public void robotInit() {
        //drive system
    	joy = new Joystick(Wiring.JOYSTICK_1);
    	joy2 = new Joystick(1);
    	lf = new Talon(Wiring.LEFT_FRONT_MOTOR_ID); //backwards
    	lr = new Talon(Wiring.LEFT_REAR_MOTOR_ID); //backwards
    	rf = new Talon(Wiring.RIGHT_FRONT_MOTOR_ID);
    	rr = new Talon(Wiring.RIGHT_REAR_MOTOR_ID);
    	rd = new RobotDrive(lf, lr, rf, rr);
    	rd.setInvertedMotor(MotorType.kFrontLeft, true);
    	rd.setInvertedMotor(MotorType.kRearLeft, true);
    	rd.setMaxOutput(.5);
    	lifter = new Lifter(Wiring.LIFTER_JAGUAR_VALUE);
    	g = new Gyro(Wiring.GYRO_ID);
    	count = 0;
    	sonar = new MaxSonar(0);

//        //pixy stuff
        pixy = new Pixy();
        p = new PixyController(pixy);
        pp = new PixyPacket();

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
    	
//    	if(count < 75){ // drives straight
//    		rd.mecanumDrive_Cartesian(0, 1, Wiring.STUPID_CHASSIS_CORRECTION, g.getAngle());
//    	}
//    	In Progress:
//    	pixyControls();
//    	//Gather Here
//    	else if(count<150)
//    	{
//    		rd.mecanumDrive_Cartesian(.5, -.5, Wiring.STUPID_CHASSIS_CORRECTION, g.getAngle());
//    	}
//    	else if(count<230)
//    	{
//    		rd.mecanumDrive_Cartesian(-1, -.5, Wiring.STUPID_CHASSIS_CORRECTION, g.getAngle());
//    	}
//    	else	//	ReSeT aT nExT tOtE
//    		count = 0;

    	wallDist = sonar.getInches();
    	int toWall = 60;
    	System.out.println("Count: "+count);
    	System.out.println(wallDist);
    	switch(count)
    	{
    	
    	case 1:
    		if(timesRun > 0) {
    			//Drive
//    			lifter.goHome();
    		}
//    		lifter.liftTote(1);
    		count = 2;
    		break;
    	case 2:
    		if(wallDist>toWall-25)
    		{
        		rd.mecanumDrive_Cartesian(-.75, -.2, Wiring.STUPID_CHASSIS_SIDEWAYS, g.getAngle());
    		}
    		else
    			count = 3;
    		break;
    	case 3:
    		wallDist = sonar.getInches();
    		if(wallDist<toWall)
    		{
        		rd.mecanumDrive_Cartesian(.5, -.35, Wiring.STUPID_CHASSIS_SIDEWAYS, g.getAngle());
    		}
    		else
    			count = 4;
    		break;
    	case 4:
    		rd.mecanumDrive_Cartesian(p.autoCenter(), 0, Wiring.STUPID_CHASSIS_SIDEWAYS, g.getAngle());
    		if(timesRun == 2){
    			count = 5;
    		}
    		else{
    			count = 1;
    			timesRun++;
    		}
    		break;
    	case 5:
    		wallDist = sonar.getInches();
    		if (wallDist<100)
    		{
    			rd.mecanumDrive_Cartesian(.5, 0, Wiring.STUPID_CHASSIS_CORRECTION, g.getAngle());
    		}
    		else
    			count = 6;
    		break;
    	case 6:
//    		lifter.goHome();
    		count = 7;
    		break;
    	case 7:
    		rd.mecanumDrive_Cartesian(0, .5, Wiring.STUPID_CHASSIS_CORRECTION, g.getAngle());
    		count = 8;
    	case 8:
    		//Pretty Lights
    		break;
    	}
    	
    	
//    	if (sonar.getInches()>20)
//    	{
//    		rd.mecanumDrive_Cartesian(-.5, -.5, Wiring.STUPID_CHASSIS_CORRECTION, g.getAngle());
//    	}
//    	else if (sonar.getInches()<=10)
//    	{
//    		rd.mecanumDrive_Cartesian(.5, -.5, Wiring.STUPID_CHASSIS_CORRECTION, g.getAngle());
//    	}
//    	count++;
    }
    
    public void teleopInit(){
    	
    	g.reset();
    	
    }


    public void teleopPeriodic() {
        
//    	rd.mecanumDrive_Cartesian(joy.getX(), joy.getY(), joy.getRawAxis(4), g.getAngle());
//    	System.out.println(g.getAngle());
    	
//    	if(joy.getRawButton(1)){
//    		g.reset();
//    	}
//    	SmartDashboard.putDouble("Voltage", sonar.getVoltage());    
//    	SmartDashboard.putDouble("Feets", sonar.getFeet());
//    	SmartDashboard.putDouble("Inches", sonar.getInches());
        SmartDashboard.putNumber("Relative Pos.", lifter.getRelative());//lifter.getPosition() - lifter.getHome());
        SmartDashboard.putNumber("Encoder Spd.", lifter.getSpeed());
		SmartDashboard.putBoolean("BottomLimit", lifter.getForwardLimitOK());
		SmartDashboard.putNumber("Encoder Pos.", lifter.getPosition());
        lifterControls();
        if(!lifter.getForwardLimitOK()){
        	lifter.setHome();
        }
//        arduinoControls();
    	
    	
    }
    

    public void testPeriodic() {
        lifter.set(joy2.getAxis(AxisType.kY));
        SmartDashboard.putNumber("Encoder Pos.", lifter.getPosition() - lifter.getHome());
        SmartDashboard.putNumber("Encoder Spd.", lifter.getSpeed());
//        if(joy.getRawButton(4))
//        { 
//        	if(!pressed){
//            setToteHeight(1);
//            pressed = true;
//            }
//        	pressed = true;
//        } else if(joy.getRawButton(3)) {
//        	if(!pressed){
//	            setToteHeight(2);
//	            pressed = true;
//        	}
//        	pressed = true;
//        } else if(joy.getRawButton(5)) {
//        	if (!pressed){
//	            setToteHeight(3);
//	            pressed = true;
//        	}
//        	pressed = true;
//        } else if(joy.getRawButton(2)) {
//        	if (!pressed){
//	            lifter.goHome();
//	            pressed = true;
//        	}
//        	pressed = true;
//        } else {
//            pressed = false;
//        }
//    }
//    
//    public void arduinoControls(){
//
//        if (joy.getRawButton(1)){
//            arduino.Write(1);
//        }
//        else if (joy.getRawButton(2)){
//            arduino.Write(2);
//        }
//        else if (joy.getRawButton(3)){
//            arduino.Write(3);
//        }
//        else if (joy.getRawButton(4)){
//            arduino.Write(4);
//        }
//        else if(joy.getRawButton(5)){
//            arduino.Write(5);
//        }
//        else if(joy.getRawButton(6)){
//            arduino.Write(6);
//        }
//        else {
//            arduino.Write(0);
//        }

    }

    public void pixyControls(){

        //put pixy stuff in here, please. OK CODY
    	rd.mecanumDrive_Cartesian(p.autoCenter(), 0, -.023, g.getAngle());
    	

    }

    public void lifterControls(){


        
        if(joy.getRawButton(4))
        { 
        	if(!pressed){
            lifter.setToteHeight(1);
            pressed = true;
            }
        	pressed = true;
        } else if(joy.getRawButton(3)) {
        	if(!pressed){
	            lifter.setToteHeight(2);
	            pressed = true;
        	}
        	pressed = true;
        } else if(joy.getRawButton(5)) {
        	if (!pressed){
	            lifter.setToteHeight(3);
	            pressed = true;
        	}
        	pressed = true;
        } else if(joy.getRawButton(2)) {
        	if (!pressed){
	            lifter.goHome();
	            pressed = true;
        	}
        	pressed = true;
        } else {
            pressed = false;
        }
    }
//    public void setToteHeight(int numTotes) // 1 TOTE = 1'
//	{
//		double Herrow = (numTotes * 25) + lifter.getRelative();
//		
//		if (lifter.getRelative() > Herrow){
//			lifter.configForwardLimit(Herrow);
//			lifter.move(lifter.DOWN);
//			System.out.println("Going down");
//		}
//		else{
//			lifter.configReverseLimit(Herrow);
//			lifter.move(lifter.UP);
//			System.out.println("Going up");
//			System.out.println(Herrow);
//		}
//	}
}
