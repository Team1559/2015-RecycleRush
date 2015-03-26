package org.usfirst.frc.team1559.robot;
public class Wiring{
	
	/*
	 * Controls
	 */
	public static final int JOYSTICK_1 = 0;
	public static final int JOYSTICK_2 = 1;
	
	public static final int GATHER_IN_BUT = 2;
	public static final int GATHER_OUT_BUT = 3;
	public static final int WINGS_BUT = 3;
	public static final int RESET_GYRO_BUT = 6;
	
	public static final int COPILOT_T1 = 5;
	public static final int COPILOT_T2 = 6;
	public static final int COPILOT_T3 = 7;
	public static final int COPILOT_T4 = 8;
	public static final int COPILOT_T5 = 9;
	public static final int COPILOT_T6 = 10;
	public static final int COPILOT_T7 = 11;
	public static final int COPILOT_T8 = 12;
	public static final int COPILOT_RED_LIGHT = 2;
	public static final int COPILOT_TRIGGER = 1;
	public static final int PILOT_ROTATE_RIGHT = 5;
	public static final int PILOT_ROTATE_LEFT = 4;
	public static final int COPILOT_AUTO_1_TOTE = 3;
	
	/*
	 * Drive system
	 */
	public static final int LEFT_FRONT_MOTOR_ID = 9;
	public static final int LEFT_REAR_MOTOR_ID = 6;
	public static final int RIGHT_FRONT_MOTOR_ID = 8;
	public static final int RIGHT_REAR_MOTOR_ID = 7;
	
		//CONSTANTS: Drive System
		public static final double AUTO_SPEED = 1;
		public static final double MAX_DRIVE_SPEED = .685;
		public static final int PRIMARY_BUFFER_LENGTH = 10;
		public static final int SECONDARY_BUFFER_LENGTH = 6;
	
	/*
	 * Lifter
	 */
	public static final int LIFTER_ENCODER_TICKS_PER_INCH = 360;
	public static final int LIFTER_JAGUAR_VALUE = 15;
	
		//CONSTANTS: Lifter
		public static final int ZERO = 0;
		public static final int ELEVATOR_UP_SPEED = 1;
		public static final int ELEVATOR_DOWN_SPEED = -1;
		public static final double CRUISING_HEIGHT = 13.5;
		public static final double TOTE_HEIGHT = 32.0;
		public static final double CAN_HEIGHT = 42;
	
	/*
	 * Gatherer
	 */
	public static final int LEFT_GATHER_MOTOR = 4;
	public static final int RIGHT_GATHER_MOTOR = 5;
	
		//CONSTANTS: Gatherer
		public static final double GATHERER_HEIGHT = 25; 
		public static final int GATHER_ARMS_IN = 0;
		public static final int GATHER_ARMS_OUT = 1;
		public static final double GATHER_IN_SPEED = .8;
		public static final double GATHER_OUT_SPEED = .5;
		public static final double GATHER_ROTATE_OUT_SPEED = .25;
		public static final double GATHER_ROTATE_IN_SPEED = .6;

	
	/*
	 * Other stuff
	 */
	public static final int GYRO_ANALOG_ID = 0;
	public static final int SONAR_ANALOG_ID = 1;
	public static final int[] BCD_PORTS = {6, 7, 8, 9};
	public static final int IR_SENSOR = 2;
	public static final int RIGHT_TOTE_DETECTOR_SONAR = 3;
	public static final int LEFT_TOTE_DETECTOR_SONAR = 4;
	public static final double GATHER_RANGE = 22;

	/*
	 * Pixy Things
	 */
	public static final int PIXY_HALF_BAND = 3;

	//Kyle's box
	public static final int BOX_HOME = 5;
	public static final int BOX_CRUISING = 2;
	public static final int BOX_LVL1 = 3;
	public static final int BOX_LVL2 = 4;
	public static final int BOX_LVL3 = 1;
	public static final int BOX_EASY_BUTTON = 6;
	public static final int BOX_OVERRIDE_AXIS = 0;
}