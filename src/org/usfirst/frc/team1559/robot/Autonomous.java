package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	Pixy p;
	PixyController pc;
	PixyPacket pkt;
	double desired;
	double orig;
	Ramp rampx;
	Ramp rampy;
	
	public Autonomous(int[] ports, Gatherer g, Wings w, Lifter l, IRSensor ir, MaxSonar sonar, Arduino arduino, MecanumDrive md, Pixy p, PixyController pc){
		
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
		this.p = p;
		this.pc = pc;
		counter = 0;
		pkt = new PixyPacket();
		rampx = new Ramp();
		rampy = new Ramp();
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
	 * TESTED WORKING
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
			if (sonar.getInches() <= 135) {
				md.drivePID(1, -.25, 0);
				System.out.println("TRYING TO MOVE!!!! "
						+ sonar.getInches());
			} else {
				step++;
			}
		break;
		case 3:
			lifter.goHome();
			step++;
		break;
		case 4:
			md.drivePID(0, 0, 0);
			// lifter.goHome();
			// wing.latch();
			arduino.writeSequence(2);
			break;
		}
		
	}
	
	
	/*
	 * TESTED WORKING
	 * ==========ROUTINE 1==========
	 * 
	 * Just drive sideways until the robot is in the auto zone, no totes/recycling bins
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
			if (sonar.getInches() <= 135) {
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
	 * TESTED WORKING
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
			step++;
		break;
		case 1:
			if(lifter.notMoving){
				step++;
			}
		break;
		case 2:
			if (sonar.getInches() <= 135) {
				md.drivePID(1, -.25, 0);
				System.out.println("TRYING TO MOVE!!!! "
						+ sonar.getInches());
			} else {
				step++;
			}
		break;
		case 3:
			lifter.goHome();
			step++;
		break;
		case 4:
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
			PixyDriveValues pdv = new PixyDriveValues();
			pdv = pc.autoCenter(pkt);
			md.drivePID(pdv.driveX, pdv.driveY, md.g.getAngle());
			//make the pixy center the tote...when it's done, it returns to here!
			step++;
		break;
		case 5:
			if(!irSensor.hasTote()){
				gather.gatherIn();	
			} else {
				step++;
			}
			
		break;
		case 6:
			
			lifter.goHome();
			if(lifter.bottomLimit()){
				step++;
			}	
		break;
		case 7: //grab the tote!
			
			lifter.move(1);
			
		break;
		case 8:
			if (sonar.getInches() <= 120) {
				md.drivePID(1, -.25, 0);
				System.out.println("TRYING TO MOVE!!!! "
						+ sonar.getInches());
			} else {
				step++;
			}
		break;
		case 9:
			md.drivePID(0, 0, 0);
			arduino.writeSequence(2);
		break;
		}
		
	}
		

	
	/*
	 * TESTED WORKING!
	 * ==========ROUTINE 5==========
	 * 		  
	 * [TOTE](B)R
	 * 		       |
	 * ___________/
	 * 
	 * Grab the !tote, and put it on a tote
	 * 
	 * =============================
	 * 
	 */
	public void routine5(){

		switch(step){
		
		case 0:
			lifter.move(2);
			step++;
		break;
		case 1:
			if(lifter.notMoving){
				step++;
				counter = 0;
			}
		break;
		case 2:
			if(counter <= 15){
				md.drivePIDToteCenter(0, -.75, 0);
				counter++;
			} else {
				step++;
				md.drive(0, rampy.rampMotorValue(0), 0);
				counter = 0;
			}
		break;
		case 3:
			if(!irSensor.hasTote()){
				gather.gatherIn();
			} else {
				if(counter <= 15){
					counter++;
				} else {
					step++;
					gather.stopGather();
				}
			}
		break;
		case 4:
			lifter.goHome();
			step++;
		break;
		case 5:
			if(lifter.bottomLimit()){
				lifter.cruisingHeight();
				step++;
			}
		break;
		case 6:
			if (sonar.getInches() <= 135) {
				md.drivePID(rampx.rampMotorValue(1), rampy.rampMotorValue(-.25), 0);
				System.out.println("TRYING TO MOVE!!!! "
						+ sonar.getInches());
			} else {
				step++;
			}
		break;
		case 7:
			md.drivePID(rampx.rampMotorValue(0), rampy.rampMotorValue(0), 0);
			lifter.goHome();
			wing.release();
			arduino.writeSequence(2);
		break;
		}
		
	}
	
	/*
	 * TESTED WORKING!
	 * ==========ROUTINE 6==========
	 * 
	 * Grab the barrell on the opposite side as routine 3;
	 * This shuold be opposite the routine 0 configuration
	 * 
	 * =============================
	 * 
	 */
	public void routine6(){
		switch(step){
		
		case 0:
			lifter.move(1);
			step++;
		break;
		case 1:
			if(lifter.notMoving){
				counter = 0;
				step++;
			}
		break;
		case 2:
			if(counter <= 50){
				md.drivePID(-1, 0, 0);
				counter++;
			} else {
				step++;
			}
		break;
		case 3:			
                md.g.reset();
		        orig = md.g.getAngle();
		        desired = orig + 180;
		        step++;
		break;
		case 4:			
			if(md.g.getAngle() <= desired){
				md.drivePIDToteCenter(0, 0, 1);
				SmartDashboard.putNumber("Gyro Angle", md.g.getAngle());
			} else {
				md.drivePIDToteCenter(0, 0, 0);
				step++;
			}
		break;
		case 5:
			if (sonar.getInches() <= 135) {
				md.drivePID(1, -.25, 0);
				System.out.println("TRYING TO MOVE!!!! "
						+ sonar.getInches());
			} else {
				step++;
			}
		break;
		case 6:
			lifter.goHome();
			step++;
		break;
		case 7:
			md.drivePID(0, 0, 0);
			arduino.writeSequence(2);
		break;
		}
	}
	
	/*
	 *
	 * ==========ROUTINE 7==========
	 *   --
	 *  R(B)[TOTE]   |
	 *   --         /
	 *   __________/
	 *   
	 *   Grabs the !tote, puts it on the tote, does a 180, and moves into the auto zone!
	 * =============================
	 * 
	 */
	public void routine7(){
		switch(step){
		
		case 0:
			lifter.move(2);
			step++;
		break;
		case 1:
			if(lifter.notMoving){
				step++;
				counter = 0;
			}
		break;
		case 2:
			if(counter <= 15){
				md.drivePIDToteCenter(0, -.75, 0);
				counter++;
			} else {
				step++;
				md.drive(0, 0, 0);
				counter = 0;
			}
		break;
		case 3:
			if(!irSensor.hasTote()){
				gather.gatherIn();
			} else {
				if(counter <= 15){
					counter++;
				} else {
					step++;
					gather.stopGather();
				}
			}
		break;
		case 4:
			lifter.goHome();
			step++;
		break;
		case 5:
			if(lifter.bottomLimit()){
				lifter.cruisingHeight();
				step++;
				md.g.reset();
				orig = md.g.getAngle();
		        desired = orig + 180;
			}
		break;
		case 6:
			if(md.g.getAngle() <= desired){
				md.drivePIDToteCenter(0, 0, 1);
				SmartDashboard.putNumber("Gyro Angle", md.g.getAngle());
			} else {
				md.drivePIDToteCenter(0, 0, 0);
				step++;
			}
		break;
		case 7:
			if (sonar.getInches() <= 135) {
				md.drivePID(1, -.25, 0);
				System.out.println("TRYING TO MOVE!!!! "
						+ sonar.getInches());
			} else {
				step++;
			}
		break;
		case 8:
			lifter.goHome();
			step++;
		break;
		case 9:
			md.drivePID(0, 0, 0);
			arduino.writeSequence(2);
		break;
		
		}
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
