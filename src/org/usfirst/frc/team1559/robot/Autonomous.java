package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.DriverStation;
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
	SonarToteDetector tinySonar;
	
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
		rampx = new Ramp();
		rampy = new Ramp();
		tinySonar = new SonarToteDetector();
	}
	
	
	public int getBCDValue(){
		return bcd.read();
	}
	
	public void startAutonomous(){ //decide which method to call!
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
	 * Grabs one tote only; Moves into the auto zone. 
	 * 
	 * 	(B)[TOTE]  R | *****START LIFTER ABOVE HOME!*****
	 * 	____________/
	 * 
	 * =============================
	 */
	public void routine0(){
		System.out.println("MODE 0!");
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
			if (sonar.getInches() <= 155) {
				md.drivePID(.75, -.1875, 0);
				System.out.println("being stupid at "
						+ sonar.getInches());
			} else {
				step++;
			}
		break;
		case 3:
			lifter.goHome();
			gather.stopGather();
			step++;
			counter = 0;
		break;
		case 4:
			md.drivePID(0, 0, 0);
			
			if(lifter.bottomLimit()){
				lifter.move(1);
				step++;
				counter = 0;
			}
		break;
		case 5:
			wing.latch();
			if(lifter.notMoving)
				step++;
		break;
		case 6:
			if(counter <= 35){
				wing.latch();
				gather.gatherOut();
				counter++;
			} else {
				step++;
			}
			
		break;
		case 7:
			arduino.writeSequence(2);
			gather.stopGather();
			wing.release();
		break;
		}
		
	}
	
	/*
	 * TESTED WORKING!
	 * ==========ROUTINE 1==========
	 * 
	 *  R [TOTE](B)  | Gather and pick up a crate, do a 180, and move into the auto zone.
	 * 				/   *****START LIFTER ABOVE HOME!*****
	 * ____________/
	 * 
	 * =============================
	 * 
	 */
	public void routine1(){
		
		switch(step){
		
		case 0:
			if(!irSensor.hasTote()){
				gather.gatherIn();
				counter = 0;
			} else {
				if(counter <= 15){
					counter++;
				} else {
					gather.stopGather();
					step++;
				}
			}
		break;
		case 1:
			step++;
		break;
		case 2:
			lifter.goHome();
			step++;
		break;
		case 3:
			if(lifter.bottomLimit()){
				step++;
			}
		break;
		case 4:
			lifter.move(1);
			step++;
		break;
		case 5:
			md.g.reset();
	        orig = md.g.getAngle();
	        desired = orig + 180;
	        step++;
		break;
		case 6:
			if(md.g.getAngle() <= desired){
				md.drivePIDToteCenter(0, 0, 1);
			} else {
				md.drivePIDToteCenter(0, 0, 0);
				step++;
			}
		break;
		case 7:
			if (sonar.getInches() <= 155) {
				md.drivePID(.75, -.1875, 0);
				System.out.println("TRYING TO MOVE!!!! "
						+ sonar.getInches());
			} else {
				md.drive(0, 0, 0);
				step++;
			}
		break;
		case 8:
			lifter.goHome();
			step++;
		break;
		case 9:
			if(lifter.bottomLimit()){
				counter = 0;
				step++;
			}
		break;
		case 10:
			if(counter <= 35){
				wing.latch();
				md.drivePIDToteCenter(0, .75, 0);
				counter++;
			} else {
				step++;
				counter = 0;
				md.drivePIDToteCenter(0, 0, 0);
			}
		break;
		case 11:
			arduino.writeSequence(2);
		break;
		}
		
	}
	
	/*
	 * 
	 * TESTED WORKING
	 * ==========ROUTINE 2==========
	 * 		 ----   |
	 * [TOTE](B)R   | Grabs the barrel, and moves into the auto Zone, place the barrel inside the robot at the start of the match
	 *       ----   |
	 *       		|
	 * ____________/
	 * =============================
	 * 
	 */
	public void routine2(){
		
		switch(step){
		
		case 0:
			gather.stopGather();
			if(once){
				lifter.goHome();
				once = false;
			}
			
			if(lifter.notMoving && !once){
				lifter.move(1);
				step++;	
			}
		break;
		case 1:
			if(lifter.notMoving){
				step++;
			}
		break;
		case 2:
			if (sonar.getInches() <= 155) {
				md.drivePID(.75, -.1875, 0);
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
			wing.latch();
		break;
		}
		
	}
	
	/*
	 * TESTED WORKING!
	 * ==========ROUTINE 3==========
	 * ---- 
	 * R(B)[TOTE]  | Grab the barrel, run into the auto zone after a 180 degree death spiral
	 * ----		   |
	 * ___________/
	 * =============================
	 * 
	 */
	public void routine3(){
		switch(step){
		
		case 0:
//			lifter.move(1);
//			step++;
			
			gather.stopGather();
			if(once){
				lifter.goHome();
				once = false;
			}
			
			if(lifter.notMoving && !once){
				lifter.move(1);
				step++;	
			}
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
			if (sonar.getInches() <= 155) {
				md.drivePID(.75, -.1875, 0);
				System.out.println("TRYING TO MOVE!!!! "
						+ sonar.getInches());
			} else {
				step++;
				once = true;
			}
		break;
		case 6:
			if(once){
				md.drivePIDToteCenter(0, 0, 0);
				lifter.goHome();
				counter = 0;
				once = false;
			} 
			
			if(lifter.bottomLimit()){
				step++;
			}
		break;
		case 7:
			if(counter <= 35){
				wing.latch();
				md.drivePIDToteCenter(0, .75, 0);
				counter++;
			} else {
				step++;
				counter = 0;

			}
		break;
		case 8:
			md.drivePID(0, 0, 0);
			arduino.writeSequence(2);
		break;
		}
	}
	
	/* ADD BACK AWAY CODE
	 * TESTED WORKING!
	 * ==========ROUTINE 4==========
	 * 		 ----
	 * [TOTE](B)R  | Grab the can, place it on a tote, and grab the stack. 
	 * 		 ----  | Move into the auto Zone
	 * ___________/
	 * 
	 * 
	 * =============================
	 * 
	 */
	public void routine4(){
		switch(step){
		
		case 0:
//			lifter.move(2);
//			step++;
			
			gather.stopGather();
			if(once){
				lifter.goHome();
				once = false;
			}
			
			if(lifter.notMoving && !once){
				lifter.move(2);
				step++;	
			}
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
			if (sonar.getInches() <= 155) {
				md.drivePID(.75, -.1875, 0);
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
	 * WORKS - FIRST TRY!!!!!! CODY IS THE BEST 5=EVER!!1!
	 * ==========ROUTINE 5==========
	 *  ----
	 *  R(B)[TOTE]   | Grab the can, place it on a tote, move into the auto zone with the stack
	 *  ----        /                   *****DOES A 180!*****
	 *   __________/
	 *   
	 * =============================
	 * 
	 */
	public void routine5(){
		switch(step){
		
		case 0:
			gather.stopGather();
			if(once){
				lifter.goHome();
				once = false;
			}
			
			if(lifter.notMoving && !once){
				lifter.move(2);
				step++;	
			}
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
			if (sonar.getInches() <= 155) {
				md.drivePID(.75, -.1875, 0);
				System.out.println("TRYING TO MOVE!!!! "
						+ sonar.getInches());
			} else {
				step++;
				once = true;
			}
		break;
		case 8:
			if(once){
				lifter.goHome();
				counter = 0;
				once = false;
			} 
			
			if(lifter.bottomLimit()){
				step++;
			}
			
		break;
		case 9:
			if(counter <= 35){
				wing.latch();
				md.drivePIDToteCenter(0, .75, 0);
				counter++;
			} else {
				step++;
				counter = 0;
				
			}
		break;
		case 10:
			md.drivePID(0, 0, 0);
			arduino.writeSequence(2);
		break;
		
		}
	}
	
	/*
	 * TESTED WORKING
	 * ==========ROUTINE 6==========
	 * 
	 * This mode simply moves the robot into the auto zone, there is no interaction with field pieces
	 * 			R	   | **The robot can be placed anywhere such that the sonar faces the wall. It will move to be ~135" from it
	 * _______________/																								(Auto Zone)
	 * 
	 * =============================
	 * 
	 */
	public void routine6(){
		
		switch(step){
		
		case 0:
			if (sonar.getInches() <= 155) {
				md.drivePID(.75, -.1875, 0);
				System.out.println("TRYING TO MOVE!!!! "
						+ sonar.getInches());
			} else {
				md.drive(0, 0, 0);
				gather.stopGather();
				step++;
			}
		break;
		case 1:
			arduino.writeSequence(2);
			lifter.goHome();
		break;
		
		}
		
	}
	
	public void driveForward(){
		md.drivePIDToteCenter(0, -.55, 0);
	}
	
	/*
	 *
	 * ==========ROUTINE 7==========
	 * 
	 * n8
	 * 
	 * =============================
	 * 
	 */
	public void routine7(){
		arduino.writeSequence(2);
	}
		
	/*
	 *
	 * ==========ROUTINE 8==========
	 * 
	 * Strictly timing
	 * 
	 * =============================
	 * 
	 */
	public void routine8(){
		gather.runMotorsIn();
		switch(step){
		
		case 0: //get can
//			lifter.liftCan();
			if(once){
				gather.stopGather();
				once = false;
			}
			wing.latch();
			if (sonar.getInches() <= 38) {
				md.drivePID(1, -.25, 0);
				System.out.println("TRYING TO MOVE!!!! "
						+ sonar.getInches());
			} else {
				md.drive(0, 0, 0);
				step++;
			}
			
		break;
		case 1:
			if(lifter.notMoving){
				driveForward();
				wing.latch();
				step++;
			}
		break;
		case 2: 
			
			driveForward();
			if(irSensor.hasTote()){
				lifter.goHome();
				wing.release();
				step++;
			} else {
				wing.latch();
			}
		break;
		case 3:
			driveForward();
			if(lifter.bottomLimit()){
				lifter.move(1);
				step++;
				counter = 0;
			}
		break;
		case 4:
			driveForward();
			if(counter <= 200){
				counter++;
			} else {
				gather.gatherIn();
			}
			if(lifter.notMoving && irSensor.hasTote()){
				gather.stopGather();
				lifter.goHome();
				step++;
			}
		break;
		case 5:
			driveForward();
			if(lifter.bottomLimit()){
				lifter.move(1);
				step++;
				counter = 0;
			}
		break;
		case 6:
			if(counter <= 200){
				counter++;
			} else {
				gather.gatherIn();
			}
			if(lifter.notMoving && irSensor.hasTote()){
				gather.stopGather();
				lifter.goHome();
				if(lifter.bottomLimit()){
					step++;
					once = true;
				}
				md.drivePIDToteCenter(0, 0, 0);
			} else {
				driveForward();
			}
		break;
		case 7:
			if(once){
				lifter.cruisingHeight();
				once = false;
				md.g.reset();
				orig = md.g.getAngle();
		        desired = orig + 180;
			}
			if(md.g.getAngle() <= desired){
				md.drivePIDToteCenter(0, 0, 1);
				SmartDashboard.putNumber("Gyro Angle", md.g.getAngle());
			} else {
				md.drivePIDToteCenter(0, 0, 0);
				step++;
			}
//			if (sonar.getInches() <= 135) {
//				md.drivePID(1, -.25, 0);
//				System.out.println("TRYING TO MOVE!!!! "
//						+ sonar.getInches());
//			} else {
//				md.drive(0, 0, 0);
//			}
		break;
		case 8:
			arduino.writeSequence(2);
			if(DriverStation.getInstance().getMatchTime() >= 14){
				md.drivePIDToteCenter(0,0,0);
				lifter.goHome();
				wing.latch();
			} else {
				md.drivePIDToteCenter(0, -1, 0);
			}
			
		break;

		}	
	}
	
	/*
	 *
	 * ==========ROUTINE 9==========
	 * 
	 * Dud	  
	 * 
	 * =============================
	 * 
	 */
	public void routine9(){
		
		

	}
}
