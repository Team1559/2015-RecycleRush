package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.CANJaguar;

public class Lifter extends CANJaguar
{
	int toteHeight = 0;
	ControlMode m_controlMode;
	double homePos;
	final int UP = 1, DOWN = 2;
	boolean hardLimit = false;
	int state = 0;
	double tgtHeight, tgtHeight1, tgtHeight2, tgtHeight3;
	
	
	public Lifter(int deviceNumber)
	{
		super(deviceNumber);
		setPercentMode(CANJaguar.kQuadEncoder, 360); //Use voltage and encoder
		configNeutralMode(CANJaguar.NeutralMode.Brake);
		enableControl();
		configLimitMode(LimitMode.SoftPositionLimits);
	}
	
	public void moveDown(int toteLevel) // 1 TOTE = 1'
	{
		hardLimit = false;
		tgtHeight = (toteLevel * 25) + getHome();
			move(DOWN);
			configSoftPositionLimits(100, tgtHeight); //ISSUES
			System.out.println("Going down");
		
	}
	public void moveUp(int toteLevel){
		tgtHeight = toteLevel * 25 + getHome();
		configForwardLimit(tgtHeight);
		move(UP);
		System.out.println("Going up");
		System.out.println(tgtHeight);
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
		tgtHeight1 = 25 + getHome();
		tgtHeight2 = 50 + getHome();
		tgtHeight3 = 75 + getHome();
	}
	
	public double getHome() {
		return homePos;
	}
	public double getRelative(){
		return getPosition() - getHome();
	}
public void newLifterControls(){
    	
    	switch(state){
    	case 0: //At Home
    		setHome();
    		break;
    	case 1: //Go Home
    		goHome();
    		break;
    	case 2: //Go up to 1
    		moveUp(1);
    		break;
    	case 3: //Go down to 1
    		moveDown(1);
    		break;
    	case 4: //At 1
    		break;
    	case 5: //Go up to 2
    		moveUp(2);
    		break;
    	case 6: //Go down to 2
    		moveDown(2);
    		break;
    	case 7: //Go to 3
    		
    	}
}
}