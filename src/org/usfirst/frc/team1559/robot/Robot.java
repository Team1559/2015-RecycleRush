
package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//testing test test
public class Robot extends IterativeRobot {
 
	Joystick joy;
	Talon lf, lr, rf, rr;
	RobotDrive rd;
	Gyro g;
	int count;
    boolean pressed;
//    Lifter lifter;
    boolean x;
    boolean y;
    boolean z;
    Pixy pixy;
    int halfBand = Wiring.PIXY_HALF_BAND;
    PixyController p;
    Arduino arduino;
    Talon leftMotor; //pixy motors?
    Talon rightMotor;//pixy motors?
    double sonarInch;
	MaxSonar sonar;
	
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
    	g = new Gyro(Wiring.GYRO_ID);
    	count = 0;
    	sonar = new MaxSonar(0);
    	

        //lifter stuff
//        lifter = new Lifter(Wiring.LIFTER_JAGUAR_VALUE);
        pressed = false;

//        //pixy stuff
//        pixy = new Pixy();
//        leftMotor = new Talon(6);
//        rightMotor = new Talon(8);
//        p = new PixyController(pixy);

        //arduino stuff
        arduino = new Arduino(0,1,2);
    }


    public void autonomousInit(){
    	
    	g.reset();
    	count = 0;
    	
    }
    
    public void autonomousPeriodic() {
    	
//    	if(count < 75){ // drives straight
//    		rd.mecanumDrive_Cartesian(0, 1, -.023, g.getAngle());
//    	}
//    	In Progress:
//    	//Gather Here
//    	else if(count<150)
//    	{
//    		rd.mecanumDrive_Cartesian(.5, -.5, -.023, g.getAngle());
//    	}
//    	else if(count<230)
//    	{
//    		rd.mecanumDrive_Cartesian(-1, -.5, -.023, g.getAngle());
//    	}
//    	else	//	ReSeT aT nExT tOtE
//    		count = 0;
    	if (sonar.getInches()>20)
    	{
    		rd.mecanumDrive_Cartesian(-.5, -.5, -.023, g.getAngle());
    	}
    	else if (sonar.getInches()<=10)
    	{
    		rd.mecanumDrive_Cartesian(.5, -.5, -.023, g.getAngle());
    	}
//    	count++;
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
    	
    	
    }
    

    public void testPeriodic() {
    
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

        //put pixy stuff in here, please.

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
