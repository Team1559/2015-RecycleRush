package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Victor;

public class Robot extends IterativeRobot {

	SonarStereo ssonar = new SonarStereo(9, 0, 1);
	Victor left, right;
	SonarMovement move = new SonarMovement(left, right, ssonar);

	public void robotInit() {
		left = new Victor(1);
		right = new Victor(2);
	}

	public void autonomousInit() {

	}

	public void autonomousPeriodic() {
		ssonar.periodic();
	}

	public void teleopInit() {

	}

	public void teleopPeriodic() {
		ssonar.periodic();
		move.stopOneSonar();
	}

	public void testPeriodic() {

	}

	public void disabledInit() {
		ssonar.stop();
	}

	public void disabledPeriodic() {

	}

}
