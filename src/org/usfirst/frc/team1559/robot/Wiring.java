package org.usfirst.frc.team1559.robot;

public class Wiring{
	
	//driving
	public static final int JOYSTICK_1 = 0;
	public static final int LEFT_FRONT_MOTOR_ID = 9;
	public static final int LEFT_REAR_MOTOR_ID = 6;
	public static final int RIGHT_FRONT_MOTOR_ID = 8;
	public static final int RIGHT_REAR_MOTOR_ID = 7;
	public static final double STUPID_CHASSIS_CORRECTION = -.023;
	public static final double STUPID_CHASSIS_SIDEWAYS = -.05;

	//analog sensors
	public static final int GYRO_ID = 1;

	//lifter
	public static final int LIFTER_JAGUAR_VALUE = 15;

	//pixy
	public static final int PIXY_HALF_BAND = 3;
	
	//BCDSwitch
	public static final int BCD_SWITCH_1_WIRE = 10;
	public static final int BCD_SWITCH_2_WIRE = 11;
	public static final int BCD_SWITCH_4_WIRE = 12;
	public static final int BCD_SWITCH_8_WIRE = 13;
}