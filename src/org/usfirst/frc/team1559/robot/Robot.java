package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Talon;


public class Robot extends IterativeRobot {
	
	SonarStereo ssonar = new SonarStereo(1, 0, 1);
//	Talon leftFront, rightFront, leftBack, rightBack;
//	SonarMovement move;
//	Encoder pedometerX;
//	Encoder pedometerY;
//  int decision;
	
	public void robotInit() {
		
//		leftFront = new Talon(9);
//		rightFront = new Talon(8);
//		leftBack = new Talon(6);
//		rightBack = new Talon(7);
//		pedometerX = new Encoder(0, 1);
//		pedometerY = new Encoder(2, 3);
//		move = new SonarMovement(leftFront, rightFront, leftBack, rightBack, ssonar, pedometerX, pedometerY);
	}

	public void autonomousInit() {
		
	}

	public void autonomousPeriodic() {
		//move.avoid();
    }
        

	public void teleopInit() {
         
    }

	public void teleopPeriodic() {
		ssonar.periodic();
	}
	
	public void testPeriodic() {

	}

	public void disabledInit() {
		ssonar.stop();   
	}

	public void disabledPeriodic() {
        //move.disable();
	}
}