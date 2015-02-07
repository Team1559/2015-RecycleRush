package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Button that toggles state every press 
 * @author aoh
 *
 */
public class ToggleButton {
	boolean state = false;
	DebounceButton button;
	
	/**
	 * 
	 * @param Joy       -- the joystick
	 * @param Button    -- the button to use
	 */
	public ToggleButton(Joystick Joy, int Button) {
		button = new DebounceButton(Joy, Button);
	}
	
	/**
	 * get state and possibly change state
	 * @return
	 */
	public boolean get() {
	    if (button.get())
	    	state = !state;
	    return state;
	}
	
	public boolean getState() {
		return state;
	}
	
	public void set(boolean val) {
		state = val;
	}
}
