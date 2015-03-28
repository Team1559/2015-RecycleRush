package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//testing test test
public class Robot extends IterativeRobot {

	//Robot Components
	PowerDistributionPanel pdp;
	Talon lf, lr, rf, rr;
	Solenoid gatherIn, gatherOut;
	MecanumDrive md;
	Lifter lifter;
	Pixy pixy;
	PixyPacket pp;
	PixyController p;
	Arduino arduino;
	IRSensor irSensor;
	MaxSonar sonar;
	Wings wing;
	Compressor c;
	Pedometer ped;
	Ramp rampX;
	Ramp rampY;
	Gatherer gather;
	DebounceButton dbb;
	Pincers pincers;
	
	//Other Software Components
	Autonomous auto;
	
	//Controllers
	Joystick pilotXY, pilotR, copilot;
	
	//Other random ints, doubles, booleans and such
	int count;
	boolean pressed;
	int mode;
	boolean once;
	boolean flag;
	int num;
	int step;
	int avgMotorSpeed = 0;

	public void robotInit() {
					
		//Controllers
		pilotXY = new Joystick(Wiring.JOYSTICK_1);
		pilotR = new Joystick(1);
		copilot = new Joystick(2);
				
		//Robot Components
		lf = new Talon(Wiring.LEFT_FRONT_MOTOR_ID); // backwards
		lr = new Talon(Wiring.LEFT_REAR_MOTOR_ID); // backwards
		rf = new Talon(Wiring.RIGHT_FRONT_MOTOR_ID);
		rr = new Talon(Wiring.RIGHT_REAR_MOTOR_ID);
		md = new MecanumDrive(pilotR, lf, lr, rf, rr);
		lifter = new Lifter();
		sonar = new MaxSonar(Wiring.SONAR_ANALOG_ID);
		wing = new Wings();
		c = new Compressor();
		c.start();
		gather = new Gatherer();
		irSensor = new IRSensor();
		pixy = new Pixy(this);
		p = new PixyController();
		pdp = new PowerDistributionPanel();
		ped = new Pedometer();
		dbb = new DebounceButton(copilot, Wiring.BOX_EASY_BUTTON);
		arduino = new Arduino(4);
		rampX = new Ramp();
		rampY = new Ramp();
		
		//Other software things
		auto = new Autonomous(Wiring.BCD_PORTS, gather, wing, lifter, irSensor, sonar, arduino, md, pixy, p);
		
		//random ints, booleans, doubles, floats, etc.
		count = 0;
		step = 0;
		once = true;
		flag = true;		
		mode = -1;
		pincers = new Pincers();
	}

	public void disabledInit() {
		arduino.writeSequence(4);
		flag = true;
		once = true;
		pixy.interrupt();
	}

	public void autonomousInit() {

		count = 1;
		if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue) {
			arduino.writeAlliance(1);
		} else {
			arduino.writeAlliance(0);
		}
		arduino.writeSequence(1);
		// mode = autoMode.read();
		md.resetGyro();
		mode = 0;
		num = 0;
		once = true;
		flag = true;
		System.out.print("I got here");
		System.out.println("...but not here");
	}

	/* no comment */

	/////////////////////////////////////well commented

	public void autonomousPeriodic() {

		/*
		 * 
		 * THIS CODE ASSUMES THAT THE ROBOT WILL START WITH THE GATHERER ABOVE
		 * HOME!!!!!!!!!!!
		 */
		
		lifter.run();		
		auto.startAutonomous();
		SmartDashboard.putNumber("LIFTER CURRENT", lifter.motor.getOutputCurrent());
//		PixyPacket pkt = pixy.getPacket();
//		PixyDriveValues pd = p.autoCenter(pkt);
//		md.drivePID(pd.driveX, 0, 0);
//		SmartDashboard.putNumber("X Correction Pixy",pd.driveX);
		

	}

	public void teleopInit() {
		arduinoControls();
		md.resetGyro();
		arduino.writeSequence(5);
	}

	public void teleopPeriodic() {
//		md.drivePIDToteCenter(pilotXY.getX(),
//				pilotXY.getY(), pilotR.getX());
		md.drivePIDToteCenter(rampX.rampMotorValue(pilotXY.getX()), rampY.rampMotorValue(pilotXY.getY()), pilotR.getX());
		
		if (pilotXY.getRawButton(Wiring.RESET_GYRO_BUT)) {
			md.resetGyro();
		}
		
		avgMotorSpeed = (int) (Math.abs((lf.get()) + Math.abs(lr.get()) + Math.abs(rf.get()) + Math.abs(rr.get())) * 15/68.5);
		arduino.writeElevatorPos(avgMotorSpeed);
		wingControls();
		lifterControls();
		gathererControls();
//		pincerControls();
		
		SmartDashboard.putNumber("SONAR_VALUE", sonar.getInches());
		SmartDashboard.putBoolean("HAS_TOTE", irSensor.hasTote());
		SmartDashboard.putDouble("IR VALUE", irSensor.getVoltage());

	}
	
//	public void pincerControls(){
//		
//		if(pilotR.getRawButton(2)){
//			pincers.rightCan();
//		} else {
//			pincers.dontRightCan();
//		}
//		
//	}
	
	public void arduinoControls() {

		arduino.writeSequence(1);
		if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue) {
			arduino.writeAlliance(1);
		} else {
			arduino.writeAlliance(0);
		}

	}

	public void gathererControls() {
		//used to use getEncoderPosition, but it would've returned un-home-adjusted values, so I changed it to getCurrentPosition() 
		if (lifter.getRelativePosition() > Wiring.GATHERER_HEIGHT) {
			if (pilotXY.getRawButton(Wiring.GATHER_IN_BUT)) {
				gather.gatherIn();
			} else if (pilotXY.getRawButton(Wiring.GATHER_OUT_BUT)) {
				gather.gatherOut();
			} else if (pilotXY.getRawButton(Wiring.PILOT_ROTATE_RIGHT)) {
				gather.rotateRight();
			} else if (pilotXY.getRawButton(Wiring.PILOT_ROTATE_LEFT)) {
				gather.rotateLeft();
			} else if(pilotR.getRawButton(2)){
				gather.solenoidsIn();
			} else {
				gather.stopGather();
			}
		} else {
			gather.stopGather();
		}

	}

	public void wingControls() {

		if (pilotR.getRawButton(Wiring.WINGS_BUT)) {
			wing.latch();
		} else {
			wing.release();
		}

	}

	public void testPeriodic() {

//		System.out.println(auto.getBCDValue());
		System.out.println(sonar.getInches());
		md.drivePIDToteCenter(rampX.rampMotorValue(pilotXY.getX()), rampY.rampMotorValue(pilotXY.getY()), pilotR.getX());
		arduino.writeSequence(1);
	}

	public void lifterControls() {

		/*
		 * DON'T PLAY WITH THIS!!!! IT COULD GET DANGEROUS IF YOU DO!!
		 */
		
		if (dbb.getRelease()) {
			lifter.stop();
		}

		lifter.run();
		
		if (!copilot.getRawButton(Wiring.BOX_EASY_BUTTON)) {

			if (copilot.getRawButton(Wiring.BOX_LVL1)) {
				if (!pressed) {
					lifter.move(1);
				}
				pressed = true;
			} else if (copilot.getRawButton(Wiring.BOX_CRUISING)) {
				if (!pressed) {
					lifter.cruisingHeight();
				}
				pressed = true;
			} else if (copilot.getRawButton(Wiring.BOX_LVL2)) {
				if (!pressed) {
					lifter.move(2);
				}
				pressed = true;
			} else if (copilot.getRawButton(Wiring.BOX_LVL3)) {
				if (!pressed) {
					lifter.move(3);
				}
				pressed = true;
			} else if (copilot.getRawButton(Wiring.BOX_HOME)) {
				if (!pressed) {
					lifter.goHome();
				}
				pressed = true;
			} else {
				pressed = false;
			}
		} else {
			lifter.set(copilot.getRawAxis(Wiring.BOX_OVERRIDE_AXIS));
		}
	}

}
