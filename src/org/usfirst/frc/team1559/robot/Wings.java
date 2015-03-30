package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;

public class Wings {

//	Servo left;
//	Servo right;
	Solenoid up;
	Solenoid down; //communists

	public Wings() {
//		left = new Servo(3);
//		right = new Servo(2);
		
		up = new Solenoid(3);
		down = new Solenoid(2);
	}

	public void up() {
//		left.setAngle(0);
//		right.setAngle(105);
		up.set(false);
		down.set(true);
	}

	public void down() {
//		left.setAngle(60);
//		right.setAngle(35);
		up.set(true);
		down.set(false);
	}
}