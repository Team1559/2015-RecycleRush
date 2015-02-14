package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;

public class Robot extends IterativeRobot {

	SonarStereo ssonar = new SonarStereo(1, 2, 3);
	Talon leftFront, rightFront, leftBack, rightBack;
	SonarMovement move;
        Gyro gyro;
        Joystick joy;
        int a;
        double lft;
        double rgt;
        int decision;
        
	public void robotInit() {
		
		leftFront = new Talon(9);
		rightFront = new Talon(8);
		leftBack = new Talon(6);
		rightBack = new Talon(7);
		gyro = new Gyro (1);
		move = new SonarMovement(leftFront, rightFront, leftBack, rightBack, ssonar, gyro);
	}

	public void autonomousInit() {
              
	}

	public void autonomousPeriodic() {
            ssonar.periodic();
            if(!move.decisionMade)
                decision = move.decide();
            move.react(decision);
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
            move.disable();
	}

}