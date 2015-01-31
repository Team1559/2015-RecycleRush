package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.AnalogInput;

public class MaxSonar {
     AnalogInput sensor;
     double conversionFactor;
     double dist;
     public MaxSonar(int channel)
     {
         sensor = new AnalogInput(channel);
         conversionFactor = 7;
     }
     public double getInches()
     {
         return ((sensor.getVoltage()*conversionFactor*12));
     }
     public double getFeet()
     {
         return ((sensor.getVoltage()*conversionFactor));
     }
     public double getVoltage()
     {
         return (sensor.getVoltage());
     }
     public boolean isInAutoZone(){
    	 if (getInches() >= 142){
    		 return true;
    	 }
    	 else {
    		 return false;
    	 }
    	 /*Center of Auto Zone to Wall: 13ft 7 in, Extends from 10ft 6in to 16ft 8in
    	  * If Entering Auto Zone Perpendicularly, we have 1/2 inch of clearance 
    	  * Front of Crate to Back of Robot: 46.5 in
    	  * Front of Arms to Back of Robot: 40 in
    	  *Front of Chassis to back of Chassis: 31 in*/
     }
}

