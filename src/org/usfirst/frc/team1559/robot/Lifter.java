package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.CANJaguar;

public class Lifter implements Runnable
{
	double encoderPosition;
	double targetPosition;
	CANJaguar motor;
	boolean movingDown;
	boolean movingUp;
	boolean notMoving;
	int currentLevel;
	double homePosition;
	
	public Lifter(){
		encoderPosition = 0.0;
		targetPosition = 0.0;
		motor = new CANJaguar(Wiring.LIFTER_JAGUAR_VALUE);
		movingDown = false;
		movingUp = false;
		notMoving = true;
		currentLevel = 0;
		homePosition = 0; //just cuz - MAKE SURE YOU RESET IT!
	}

	/*
	 * This method is to set the elevator in motion
	 */
	public void move(int desiredLevel){ //1 2 or 3
		
		targetPosition = homePosition + (desiredLevel * Wiring.TOTE_HEIGHT);
		
		if(desiredLevel > currentLevel){
			motor.set(Wiring.ELEVATOR_UP_SPEED);
			movingUp = true;
			movingDown = false;
			notMoving = false;
		} else if(desiredLevel < currentLevel){
			motor.set(Wiring.ELEVATOR_DOWN_SPEED);
			movingUp = false;
			movingDown = true;
			notMoving =false;
		}
	}
	
	public void set(double input){
		motor.set(input);
	}
	
	public double getEncoderPosition(){
		return motor.getPosition();
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
		homePosition = motor.getPosition();
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
		
		//check to see when the values are correct, so we can stop checking iteratively
		if(!notMoving){
		
			if(movingUp){
				if(encoderPosition >= targetPosition){
					stop();
				}
			} else if(movingDown) {
				if(encoderPosition <= targetPosition){
					stop();
				}
			}
			
		}
		
		if(bottomLimit()){
			setHome();
		}
		
	}
	
}