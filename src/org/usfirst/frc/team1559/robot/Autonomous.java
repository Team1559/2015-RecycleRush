package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Timer;

public class Autonomous {

	BCDSwitch bcd;
	int step;
	boolean once;
	int counter;
	Gatherer gather;
	Wings wing;
	Lifter lifter;
	IRSensor irSensor;
	MaxSonar sonar;
	Arduino arduino;
	MecanumDrive md;
	
	public Autonomous(int[] ports, Gatherer g, Wings w, Lifter l, IRSensor ir, MaxSonar sonar, Arduino arduino, MecanumDrive md){
		
		bcd = new BCDSwitch(ports);
		step = 0;
		once = true;
		wing = w;
		gather = g;
		lifter = l;
		irSensor = ir;
		this.sonar = sonar;
		this.arduino = arduino;
		this.md = md;
		counter = 0;
	
	}
	
	public int getBCDValue(){
		return bcd.read();
	}
	
	public void startAutonomous(){
		if(getBCDValue() == 0){
			routine0();
		} else if(getBCDValue() == 1){
			routine1();
		} else if(getBCDValue() == 2){
			routine2();
		} else if(getBCDValue() == 3){
			routine3();
		} else if(getBCDValue() == 4){
			routine4();
		} else if(getBCDValue() == 5){
			routine5();
		} else if(getBCDValue() == 6){
			routine6();
		} else if(getBCDValue() == 7){
			routine7();
		} else if(getBCDValue() == 8){
			routine8();
		} else if(getBCDValue() == 9){
			routine9();
		}
	}
	
	/*
	 * ==========ROUTINE 0==========
	 * 
	 * Pick up one tote, and run into the auto zone (get in the zone!)
	 * 
	 * ROBOT PLACEMENT
	 * 
	 * Start with the tote placed a little outside the frame, so the crate can be gathered.
	 * The Lifter should be above home.
	 * 
	 * MAKE SURE THE SONAR IS FACING THE WALL
	 * 
	 * =============================
	 */
	public void routine0(){
		switch (step) {
		case 0:
			wing.release();
			if (irSensor.hasTote()) {
				step++;
			} else {
				gather.gatherIn();
			}
			break;
		case 1:
			if (once) {
				gather.stopGather();
				lifter.goHome();/* You're drunk. */
				once = false;
			}

			if (lifter.bottomLimit()) {
				lifter.setHome();
				Timer.delay(.25);
				lifter.move(1);
				step++;
			}
			break;
		case 2:
			if (sonar.getInches() <= 120) {
				md.drivePID(1, -.25, 0);
				System.out.println("TRYING TO MOVE!!!! "
						+ sonar.getInches());
			} else {
				step++;
			}
			break;
		case 3:
			md.drivePID(0, 0, 0);
			// lifter.goHome();
			// wing.latch();
			arduino.writeSequence(2);
			break;
		}
		
	}
	
	
	/*
	 *
	 * ==========ROUTINE 1==========
	 * 
	 * Just drive forward until the robot is in the auto zone, no totes/recycling bins
	 * 
	 * ROBOT Placement:
	 * 
	 * Point it so the sensor is facing the wall
	 * 
	 * =============================
	 * 
	 */
	public void routine1(){
		
		switch(step){
		
		case 0:
			if (sonar.getInches() <= 120) {
				md.drivePID(1, -.25, 0);
				System.out.println("TRYING TO MOVE!!!! "
						+ sonar.getInches());
			} else {
				md.drive(0, 0, 0);
			}
		break;
		case 1:
			arduino.writeSequence(2);
		break;
		
		}
		
		
		
	}
	
	/*
	 *
	 * ==========ROUTINE 2==========
	 * 
	 * 3 tote autonomous
	 * 
	 * ROBOT PLACEMENT:
	 * set it up like routine 0, just around the 1 tote
	 * 
	 * =============================
	 * 
	 */
	public void routine2(){
		
		switch (step) {
		case 0:
			wing.release();
			if (irSensor.hasTote()) {
				step++;
			} else {
				gather.gatherIn();
			}
			break;
		case 1:
			if (once) {
				gather.stopGather();
				lifter.goHome();/* You're drunk. */
				once = false;
			}

			if (lifter.bottomLimit()) {
				lifter.setHome();
				Timer.delay(.25);
				lifter.move(1);
				step++;
			}
			break;
		case 2:
			if (sonar.getInches() <= 120) {
				md.drivePID(1, -.25, 0);
				System.out.println("TRYING TO MOVE!!!! "
						+ sonar.getInches());
			} else {
				step++;
			}
			break;
		case 3:
			md.drivePID(0, 0, 0);
			// lifter.goHome();
			// wing.latch();
			arduino.writeSequence(2);
			break;
		}
		
	}
	
	/*
	 *
	 * ==========ROUTINE 3==========
	 * 
	 * Just grab the recycling can, and run
	 * 
	 * Robot placement: put the recycling can in the robot...it's not hard
	 * 
	 * TEST WHAT THE BEST POSITION FOR THE LIFTER IS!
	 * 
	 * =============================
	 * 
	 */
	public void routine3(){
		
		switch(step){
		
		case 0:
			lifter.move(1);
			if(lifter.notMoving){
				step++;
			}
		break;
		case 1:
			if (sonar.getInches() <= 120) {
				md.drivePID(1, -.25, 0);
				System.out.println("TRYING TO MOVE!!!! "
						+ sonar.getInches());
			} else {
				step++;
			}
		break;
		case 2:
			md.drivePID(0, 0, 0);
			arduino.writeSequence(2);
		break;
		}
		
	}
	
	/*
	 *
	 * ==========ROUTINE 4==========
	 * 
	 * Grab a can, put it on a tote, and take it away
	 * 
	 * ROBOT PLACEMENT: place the robot such that the robot is about to pick up the can
	 * 
	 * =============================
	 * 
	 */
	public void routine4(){
		
		switch(step){
		
		case 0: //get the recycle bin
			wing.latch();
			lifter.move(2);
			if(lifter.notMoving){
				step++;
			}
		break;
		case 1: //move back a little
			if(counter < 26){
				md.drivePID(0, -1, 0);
				counter ++;
			} else {
				step++;
			}
		break;
		case 2:
			double currentDist = sonar.getInches();
			double desiredDist = currentDist + 30;
			
			if(sonar.getInches() < desiredDist){
				md.drivePID(1, 0, 0);
			} else {
				md.drivePID(0, 0, 0);
				step++;
			}
			
		break;		
		case 3: //do a turn
			
			double orig = md.g.getAngle();
			double desired = orig - 90;

			if(md.g.getAngle() >= desired){
				md.drivePID(0, 0, desired);
			} else {
				step++;
			}
			
		break;
		case 4:
			
			//make the pixy center the tote...when it's done, it returns to here!
			if(!irSensor.hasTote()){
				gather.gatherIn();	
			} else {
				step++;
			}
			
		break;
		case 5:
			
			lifter.goHome();
			if(lifter.bottomLimit()){
				step++;
			}	
		break;
		case 6: //grab the tote!
			
			lifter.move(1);
			
		break;
		case 7:
			if (sonar.getInches() <= 120) {
				md.drivePID(1, -.25, 0);
				System.out.println("TRYING TO MOVE!!!! "
						+ sonar.getInches());
			} else {
				step++;
			}
		break;
		case 8:
			md.drivePID(0, 0, 0);
			arduino.writeSequence(2);
		break;
		}
		
	}
		

	
	/*
	 *
	 * ==========ROUTINE 5==========
	 * 
	 * Just grab the barrel and run
	 * 
	 * =============================
	 * 
	 */
	public void routine5(){
		
		switch(step){
		
		case 0:
			wing.latch();
		break;
		case 1:
			lifter.move(2);
		break;
		case 2:
			if (sonar.getInches() <= 120) {
				md.drivePID(1, -.25, 0);
				System.out.println("TRYING TO MOVE!!!! "
						+ sonar.getInches());
			} else {
				step++;
			}
		break;
		case 3:
			md.drivePID(0, 0, 0);
			arduino.writeSequence(2);
		break;
		}
		
	}
	
	/*
	 *
	 * ==========ROUTINE 6==========
	 * 
	 * 
	 * 
	 * =============================
	 * 
	 */
	public void routine6(){
		
	}
	
	/*
	 *
	 * ==========ROUTINE 7==========
	 * 
	 * 
	 * 
	 * =============================
	 * 
	 */
	public void routine7(){
		
	}
	
	/*
	 *
	 * ==========ROUTINE 8==========
	 * 
	 * 
	 * 
	 * =============================
	 * 
	 */
	public void routine8(){
		
	}
	
	/*
	 *
	 * ==========ROUTINE 9==========
	 * 
	 * 
	 * 
	 * =============================
	 * 
	 */
	public void routine9(){
		
	}

}
