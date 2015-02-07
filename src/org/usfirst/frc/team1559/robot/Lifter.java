package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.CANJaguar;

public class Lifter extends CANJaguar
{
	int toteHeight = 0;
	ControlMode m_controlMode;
	double homePos;
	final int UP = 1, DOWN = 2;
	boolean hardLimit = false;
	
	public Lifter(int deviceNumber)
	{
		super(deviceNumber);
		setPercentMode(CANJaguar.kQuadEncoder, 360); //Use voltage and encoder
		configNeutralMode(CANJaguar.NeutralMode.Brake);
		enableControl();
		configLimitMode(LimitMode.SoftPositionLimits);
	}
	double tgtHeight;
	public void setToteHeight(int numTotes) // 1 TOTE = 1'
	{
		hardLimit = false;
		tgtHeight = (numTotes * 25) + getHome();
		
		if (getPosition() > tgtHeight){
//			disableSoftPositionLimits(); //Do we need to clear old limits?
			
			move(DOWN);
			configSoftPositionLimits(100, tgtHeight); //ISSUES
			System.out.println("Going down");
		}
		else{
			configSoftPositionLimits(tgtHeight, 0);
			move(UP);
			System.out.println("Going up");
			System.out.println(tgtHeight);
		}
	}
	
	public void liftCan(double height) // 1 CAN = 2'5"
	{
		hardLimit = false;
		configForwardLimit(getPosition() + (height * 25) + 11);
		move(UP);
	}
	
	public void goHome() // Call in periodic
	{
		
		disableSoftPositionLimits();
		move(DOWN);
		hardLimit = true;
	}
	
	public void stop() {
		hardLimit = false;
		set(0);
	}
	
	public void move(int direction) {
		switch(direction) {
		case UP:
			set(.7);
			break;
		case DOWN:
			set(-.7);
			break;
		}
	}
	
	public void setHome() {
		homePos = getPosition();
		hardLimit = false;
	}
	
	public double getHome() {
		return homePos;
	}
	public double getRelative(){
		return getPosition() - getHome();
	}
}