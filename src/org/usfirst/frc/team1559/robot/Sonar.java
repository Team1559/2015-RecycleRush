package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.AnalogInput;

public class Sonar {

	AnalogInput sensor;
	double conversionFactor;
	double dist;

	public Sonar(int channel) {
		sensor = new AnalogInput(channel);
		conversionFactor = 9.3;
	}

	public double getInches() {
		return ((sensor.getVoltage() * conversionFactor / 12));
	}

	public double getFeet() {
		return ((sensor.getVoltage() * conversionFactor));
	}

	public double getVoltage() {
		return (sensor.getVoltage());
	}
}