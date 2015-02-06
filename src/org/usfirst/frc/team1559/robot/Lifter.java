package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.CANJaguar;

public class Lifter extends CANJaguar
{
	int toteHeight = 0;
	ControlMode m_controlMode;
	double homePos;
	final int UP = 0, DOWN = 1;
	
	public Lifter(int deviceNumber)
	{
		super(deviceNumber);
		setPercentMode(CANJaguar.kQuadEncoder, 360);
		configNeutralMode(CANJaguar.NeutralMode.Brake);
		enableControl();
		configLimitMode(LimitMode.SoftPositionLimits);
	}
	
	public void setToteHeight(int numTotes) // 1 TOTE = 1'
	{
		double tgtHeight = numTotes * 25;
		if (getPosition() > tgtHeight){
			configForwardLimit(tgtHeight);
			move(DOWN);
			System.out.println("Going down");
		}
		else{
			configReverseLimit(tgtHeight);
			move(UP);
			System.out.println("Going up");
		}
	}
	
	public void liftCan(double height) // 1 CAN = 2'5"
	{
		configForwardLimit(getPosition() + (height * 25) + 11);
		move(UP);
	}
	
	public void goHome() // Call in periodic
	{
//		if (getForwardLimitOK()){
			move(DOWN);
//		}
//		else{
//		    stop();
//			setHome();
			enableControl();
//		}
	}
	
	public void stop() {
		set(0);
	}
	
	public void move(int direction) {
		switch(direction) {
		case UP:
			set(-.7);
			break;
		case DOWN:
			set(.7);
			break;
		}
	}
	
	public void setHome() {
		homePos = getPosition();
	}
	
	public double getHome() {
		return homePos;
	}
}