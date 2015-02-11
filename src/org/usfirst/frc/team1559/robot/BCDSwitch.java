package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 * BCDSwitch 
 * 
 * Reads integers from a BCD Switch 
 * 
 * @author aidan
 *
 */
public class BCDSwitch {
	DigitalInput di1;
	DigitalInput di2;
	DigitalInput di4;
	DigitalInput di8;

	/**
	 * 
	 * @param in1 Digital input for 1 wire
	 * @param in2 Digital input for 2 wire
	 * @param in4 Digital input for 4 wire
	 * @param in8 Digital input for 8 wire
	 */
	public BCDSwitch(int in1, int in2, int in4, int in8) {
		di1 = new DigitalInput(in1);
		di2 = new DigitalInput(in2);
		di4 = new DigitalInput(in4);
		di8 = new DigitalInput(in8);
	}
	
	/**
	 * 
	 * @return The switch's value
	 */
	public int read() {
		int val = 0;
		if (!di1.get()) {
			val += 1;
		}
		if (!di2.get()) {
			val += 2;
		}
		if (!di4.get()) {
			val += 4;
		}
		if (!di8.get()) {
			val += 8;
		}

		return val;
	}
}
