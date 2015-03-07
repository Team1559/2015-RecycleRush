package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.CANJaguar;

public class Lifter extends CANJaguar
{
	int toteHeight = 0;
	ControlMode m_controlMode;
	double homePos;
	final int UP = 1, DOWN = 2;
	int state = 0;
	double tgtHeight, tgtHeight1, tgtHeight2, tgtHeight3;
	boolean movingDown;
	boolean movingUp;
	boolean f = false; // print once
	
	
	public Lifter(int deviceNumber)
	{
		super(deviceNumber);
		setPercentMode(CANJaguar.kQuadEncoder, Wiring.LIFTER_ENCODER_TICKS_PER_INCH); //Use voltage and encoder
		configNeutralMode(CANJaguar.NeutralMode.Brake);
		enableControl();
	}
	
	public void moveToPosition(double rotations){
		
		
		
	}
	
	public void moveDown(int toteLevel) // 1 TOTE = 1'
	{
		tgtHeight = (toteLevel * Wiring.TOTE_HEIGHT) + getHome();
		move(DOWN);
		System.out.println("Going down");
		System.out.println(tgtHeight);
		movingDown = true;
		
	}
	public void moveUp(int toteLevel){
		tgtHeight = toteLevel * Wiring.TOTE_HEIGHT + getHome();
		move(UP);
		System.out.println("Going up");
		System.out.println(tgtHeight);
		movingUp = true;
	}
	
	public void moveElevator(int toteLevel){
		tgtHeight = (toteLevel * Wiring.TOTE_HEIGHT) + getHome();
		System.out.println(tgtHeight);
		if(tgtHeight > getPosition()){
			move(DOWN);
			movingDown = true;
		} else if(tgtHeight < getPosition()){
			move(UP);
			movingUp = true;
		}
		
	}
	
	public void goHome() // Call in periodic
	{
		move(DOWN);
	}
	
	public void stop() {
		set(0);
		movingDown = false;
		movingUp = false;
	}
	
	public void move(int direction) {
		switch(direction) {
		case UP:
			set(1);
			break;
		case DOWN:
			set(-1);
			break;
		}
	}
	
	public void setHome() {
		homePos = getPosition();
		Wiring.GATHERER_HEIGHT = getHome()+25;
		if(!f){
			System.out.println("Set home to " + homePos);
			f = true;
		}
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