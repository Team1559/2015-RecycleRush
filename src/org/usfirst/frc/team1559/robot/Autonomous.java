package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Timer;

public class Autonomous {

	BCDSwitch bcd;
	int step;
	boolean once;
	Gatherer gather;
	Wings wing;
	Lifter lifter;
	IRSensor irSensor;
	
	public Autonomous(int[] ports, Gatherer g, Wings w, Lifter l, IRSensor ir){
		
		bcd = new BCDSwitch(ports);
		step = 0;
		once = true;
		wing = w;
		gather = g;
		lifter = l;
		irSensor = ir;
	
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
		}
	}
	
	
	/*
	 *
	 * ==========ROUTINE 1==========
	 * 
	 * 
	 * 
	 * =============================
	 * 
	 */
	public void routine1(){

	}
	
	/*
	 *
	 * ==========ROUTINE 2==========
	 * 
	 * 
	 * 
	 * =============================
	 * 
	 */
	public void routine2(){
		
	}
	
	/*
	 *
	 * ==========ROUTINE 3==========
	 * 
	 * 
	 * 
	 * =============================
	 * 
	 */
	public void routine3(){
		
	}
	
	/*
	 *
	 * ==========ROUTINE 4==========
	 * 
	 * 
	 * 
	 * =============================
	 * 
	 */
	public void routine4(){
		
	}
	
	/*
	 *
	 * ==========ROUTINE 5==========
	 * 
	 * 
	 * 
	 * =============================
	 * 
	 */
	public void routine5(){
		
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
