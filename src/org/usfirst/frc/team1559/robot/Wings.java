package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.Servo;

public class Wings {

	Servo left;
	Servo right;

	public Wings() {
		left = new Servo(2);
		right = new Servo(3);
	}

	public void latch() {
		left.setAngle(60);
		right.setAngle(60);
	}

	public void release() {
		left.setAngle(0);
		right.setAngle(0);
	}
}