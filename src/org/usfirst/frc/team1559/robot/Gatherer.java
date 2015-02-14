package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;

public class Gatherer {

	Talon rightGather;
	Talon leftGather;
	Solenoid in;
	Solenoid out;
	
	public Gatherer(){
		
		rightGather = new Talon(Wiring.RIGHT_GATHER_MOTOR);
		leftGather = new Talon(Wiring.LEFT_GATHER_MOTOR);
		in = new Solenoid(Wiring.GATHER_ARMS_IN);
		out = new Solenoid(Wiring.GATHER_ARMS_OUT);
		
	}
	
}
