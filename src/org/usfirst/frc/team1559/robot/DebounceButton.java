package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Joystick;

public class DebounceButton {
	Joystick joy;
	int button;
	boolean checkedOnce = false;

	/**
	 *  Debounces a button
	 *  
	 * @param Joy
	 * @param Button
	 */
	public DebounceButton(Joystick Joy, int Button) {
		joy = Joy;
		button = Button;
	}

	public boolean get() {
		if (joy.getRawButton(button)) {
			if (!checkedOnce) {
				checkedOnce = true;
				return true;
			}
		} else {
			checkedOnce = false;
		}
		return false;
	}
	
	public boolean getRelease() {
		if (!joy.getRawButton(button)) {
			if (!checkedOnce) {
				checkedOnce = true;
				return true;
			}
		} else {
			checkedOnce = false;
		}
		return false;
	}

}
