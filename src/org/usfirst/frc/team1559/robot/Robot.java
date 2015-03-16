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

	public void robotInit() {
		
		//Other software things
		auto = new Autonomous(Wiring.BCD_PORTS, gather, wing, lifter, irSensor);
		
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
		pixy = new Pixy();
		p = new PixyController(pixy);
		pdp = new PowerDistributionPanel();
		ped = new Pedometer();
		dbb = new DebounceButton(copilot, 1);
		arduino = new Arduino(4);
		rampX = new Ramp();
		rampY = new Ramp();
		
		
		
		//random ints, booleans, doubles, floats, etc.
		count = 0;
		step = 0;
		once = true;
		flag = true;		
		mode = -1;
	}

	public void disabledInit() {
		arduino.writeSequence(4);
		flag = true;
		once = true;
	}

	public void autonomousInit() {

		count = 1;
		if (DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue) {
			arduino.writeAlliance(1);
		} else {
			arduino.writeAlliance(0);
		}

		// mode = autoMode.read();
		mode = 0;
		num = 0;
		once = true;
		flag = true;

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

		switch (mode) {

		default:
			mode = 0;
			break;
		case 0:
			switch (num) {
			default:
				num = 0;
				break;

			case 0:
				wing.release();
				if (irSensor.hasTote()) {
					num++;
				} else {
					gather.gatherIn();
				}
				break;
			case 1:
				if (once) {
					System.out.println("MODE " + num);
					gather.stopGather();
					lifter.goHome();/* You're drunk. */
					once = false;
				}

				if (lifter.bottomLimit()) {
					lifter.setHome();
					Timer.delay(.25);
					lifter.move(1);
					num++;
				}
				break;
			case 2:
				if (sonar.getInches() <= 120) {
					md.drivePID(1, -.25, 0);
					System.out.println("TRYING TO MOVE!!!! "
							+ sonar.getInches());
				} else {
					num++;
				}
				break;
			case 3:
				md.drivePID(0, 0, 0);
				// lifter.goHome();
				// wing.latch();
				arduino.writeSequence(2);
				break;

			}
			break;

		}

		// PixyPacket pkt = pixy.getPacket();
		// // md.drive(p.autoCenter(pkt), -p.autoCenter(pkt), 0, g.getAngle());
		// SmartDashboard.putDouble("Error for Pixy", p.error);
		// SmartDashboard.putDouble("Crate Ratio", p.objRatio);
		// // SmartDashboard.putDouble("Gyro angle", g.getAngle());
	}

	public void teleopInit() {
		arduinoControls();
		md.resetGyro();
	}

	public void teleopPeriodic() {
		md.drivePIDToteCenter(pilotXY.getX(),
				pilotXY.getY(), pilotR.getX());
		
		if (pilotXY.getRawButton(Wiring.RESET_GYRO_BUT)) {
			md.resetGyro();
		}
		
		wingControls();
		lifterControls();
		gathererControls();

	}
	
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

		System.out.println("HAS TOTE: " + irSensor.hasTote());
		System.out.println("IR VOLTAGE: " + irSensor.getVoltage());

	}

	public void lifterControls() {

		/*
		 * DON'T PLAY WITH THIS!!!! IT COULD GET DANGEROUS IF YOU DO!!
		 */
		
		if (dbb.getRelease()) {
			lifter.stop();
		}

		lifter.run();
		
		if (!copilot.getRawButton(Wiring.COPILOT_TRIGGER)) {

			if (copilot.getRawButton(Wiring.COPILOT_T3)
					|| copilot.getRawButton(Wiring.COPILOT_T4)) {
				if (!pressed) {
					lifter.move(1);
				}
				pressed = true;
			} else if (copilot.getRawButton(Wiring.COPILOT_RED_LIGHT)) {
				if (!pressed) {
					lifter.cruisingHeight();
				}
				pressed = true;
			} else if (copilot.getRawButton(Wiring.COPILOT_T5)
					|| copilot.getRawButton(Wiring.COPILOT_T6)) {
				if (!pressed) {
					lifter.move(2);
				}
				pressed = true;
			} else if (copilot.getRawButton(Wiring.COPILOT_T7)
					|| copilot.getRawButton(Wiring.COPILOT_T8)) {
				if (!pressed) {
					lifter.move(3);
				}
				pressed = true;
			} else if (copilot.getRawButton(Wiring.COPILOT_T1)
					|| copilot.getRawButton(Wiring.COPILOT_T2)) {
				if (!pressed) {
					lifter.goHome();
				}
				pressed = true;
			} else {
				pressed = false;
			}
		} else {
			lifter.set(copilot.getRawAxis(1));
		}
	}

}
