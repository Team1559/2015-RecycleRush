package org.usfirst.frc.team1559.robot;


import edu.wpi.first.wpilibj.DigitalOutput;

public class SonarStereo {
	private Sonar left;
	private Sonar right;
	private DigitalOutput pulse;
	private int counter;


	/**
	 * 
	 * @param pulseChannel
	 *            Digital output to trigger sonar chain
	 * @param sonarLeft
	 *            the analog channel of the left sonar
	 * @param sonarRight
	 *            the analog channel of the right sonar
	 */
	public SonarStereo(int pulseChannel, int sonarLeft, int sonarRight) {
		
		pulse = new DigitalOutput(pulseChannel);
		left = new Sonar(sonarLeft);
		right = new Sonar(sonarRight);
		counter = 0;
	}

	/**
	 * Called periodically (assumes at least 20mS)
	 */
	public void periodic() {
		final int START_PULSE = 0;
		final int END_PULSE = START_PULSE + 1;
		final int LEFT_SAMPLE = END_PULSE + 5;
		final int RIGHT_SAMPLE = LEFT_SAMPLE + 5;

		double lefty;
		double righty;

		switch (counter++) {

		case START_PULSE:
			pulse.set(true);
			break;

		case END_PULSE:
			pulse.set(false);
			break;

		case LEFT_SAMPLE:
			lefty = left.getFeet();
			System.out.println("l: " + lefty);
			break;

		case RIGHT_SAMPLE:
			righty = right.getFeet();
			System.out.println("r: " + righty);
			counter = 0;
			break;
		}
	}

	public void stop() {
		counter = 0;
		pulse.set(false);
	}
}