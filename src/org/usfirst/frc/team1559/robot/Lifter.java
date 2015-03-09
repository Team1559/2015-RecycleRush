package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lifter implements Runnable
{
	
	//Thread Safety!
	double targetPosition;
	CANJaguar motor;
	volatile boolean movingDown;
	volatile boolean movingUp;
	volatile boolean notMoving;
	volatile double currentLevel;
	double homePosition;
	
	public Lifter(){
		targetPosition = 0.0;
		motor = new CANJaguar(Wiring.LIFTER_JAGUAR_VALUE);
		movingDown = false;
		movingUp = false;
		notMoving = true;
		currentLevel = 0;
		homePosition = 0; //just 'cuz - MAKE SURE YOU RESET IT!
	}

	/*
	 * This method is to set the elevator in motion
	 */
	public void move(int desiredLevel){ //1 2 or 3
		targetPosition = homePosition + (desiredLevel * Wiring.TOTE_HEIGHT);
		
		if(targetPosition > currentLevel){
			motor.set(Wiring.ELEVATOR_UP_SPEED);
			movingUp = true;
			movingDown = false;
			notMoving = false;
		} else if(targetPosition < currentLevel){
			motor.set(Wiring.ELEVATOR_DOWN_SPEED);
			movingUp = false;
			movingDown = true;
			notMoving = false;
		}
	}
	
	public void set(double input){
		//this could break a couple of things if triggered while we're going to a level, so I made it safer
		motor.set(input);
		//safety First!
		notMoving = true;
		movingUp = false;
		movingDown = false;
		
	}
	
	public double getEncoderPosition(){
		return motor.getPosition();
	}
	
	//a method to get a position relative to home, for the gatherers
	public double getRelativePosition(){
		return currentLevel - homePosition;
	}
	
	public void goHome(){ //you're drunk
		motor.set(Wiring.ELEVATOR_DOWN_SPEED);
	}
	
	public boolean bottomLimit(){
		return !motor.getReverseLimitOK();
	}
	
	public boolean topLimit(){
		return !motor.getForwardLimitOK();
	}
	
	public void setHome(){
		homePosition = getEncoderPosition();
	}
	
	public void stop(){
		motor.set(Wiring.ZERO); //lolz
		notMoving = true;
		movingUp = false;
		movingDown = false;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 * 
	 * This is to catch when the lifter is above (or below) the desired position
	 * 
	 */
	public void run() { //here you go, Jeremy. The thread you wanted. You're welcome
		//you forgot to update current level!
		
		while(true){ //this is a little necessary
			currentLevel = (int) (getEncoderPosition());//Shouldn't this just be the relative encoder position?
			SmartDashboard.putNumber("CURRENT LEVEL", currentLevel);
			SmartDashboard.putNumber("ENCODER VALUE", getEncoderPosition());
			SmartDashboard.putNumber("TARGET", targetPosition);
			SmartDashboard.putNumber("HOME POSITION", homePosition);
			//check to see when the values are correct, so we can stop checking iteratively
			if(!notMoving){
			
				if(movingUp){
					if(getEncoderPosition() >= targetPosition){
						stop();
					}
				} else if(movingDown) {
					if(getEncoderPosition() <= targetPosition){
						stop();
					}
				}
				
			}
			
			if(bottomLimit()){
				setHome();
			}
			try {
				//Thread.sleep(250); We might miss stuff if we only check every .25 seconds
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
