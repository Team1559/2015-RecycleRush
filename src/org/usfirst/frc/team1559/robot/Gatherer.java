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
	
	
	public void gatherIn(){
		
		rightGather.set(-Wiring.GATHER_IN_SPEED);
		leftGather.set(Wiring.GATHER_IN_SPEED);
		in.set(true);
		out.set(false);
		
	}
	
	public void gatherOut(){
		
		rightGather.set(Wiring.GATHER_OUT_SPEED);
		leftGather.set(-Wiring.GATHER_OUT_SPEED);
		in.set(true);
		out.set(false);
		
	}
	
	public void stopGather(){
		
		rightGather.set(0);
		leftGather.set(0);
		in.set(false);
		out.set(true);
		
	}
	
	public void rotateLeft(){
		in.set(true);
		out.set(false);
		rightGather.set(Wiring.GATHER_ROTATE_SPEED);
		leftGather.set(Wiring.GATHER_ROTATE_SPEED);
	}
	
	public void rotateRight(){
		in.set(true);
		out.set(false);
		rightGather.set(-Wiring.GATHER_ROTATE_SPEED);
		leftGather.set(-Wiring.GATHER_ROTATE_SPEED);
	}
	
	public void half(){
		in.set(true);
		out.set(false);
	}
	
}
