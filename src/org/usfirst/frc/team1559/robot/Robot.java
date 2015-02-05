package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	BCDSwitch automode;

	public void robotInit() {
		automode = new BCDSwitch(Wiring.BCD_SWITCH_1_WIRE, Wiring.BCD_SWITCH_2_WIRE, Wiring.BCD_SWITCH_4_WIRE, Wiring.BCD_SWITCH_8_WIRE);
	}

	public void autonomousInit() {
		System.out.println("Automode is: " + automode.read());

	}

	public void autonomousPeriodic() {

	}

}
