package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {

	SonarStereo ssonar = new SonarStereo(9, 0, 1);

	public void robotInit() {

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
	}

	public void testPeriodic() {

	}

	public void disabledInit() {
		ssonar.stop();
	}

	public void disabledPeriodic() {

	}

}
