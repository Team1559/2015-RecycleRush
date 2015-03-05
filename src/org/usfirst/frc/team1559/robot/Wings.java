package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;

public class Wings {

//	Servo left;
//	Servo right;
	Solenoid rightWing;
	Solenoid leftWing; //communists

	public Wings() {
//		left = new Servo(3);
//		right = new Servo(2);
		
		rightWing = new Solenoid(2);
		leftWing = new Solenoid(3);
	}

	public void latch() {
//		left.setAngle(0);
//		right.setAngle(105);
		rightWing.set(true);
		leftWing.set(true);
	}

	public void release() {
//		left.setAngle(60);
//		right.setAngle(35);
		rightWing.set(false);
		leftWing.set(false);
	}
}