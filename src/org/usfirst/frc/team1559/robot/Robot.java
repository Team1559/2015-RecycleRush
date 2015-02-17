
package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

//testing test test
public class Robot extends IterativeRobot {
 
	Joystick joy;
    Arduino eyeCandy;
	
    public void robotInit() {
        eyeCandy = new Arduino(4);
        joy = new Joystick(0);
    }


    public void autonomousInit(){
    	
    }
    
    public void autonomousPeriodic() {
    	    	
    }
    
    public void teleopInit(){
    	if(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue){
    		eyeCandy.writeAlliance(1);
    	}
    	else {
    		eyeCandy.writeAlliance(0);
    	}
    }


    public void teleopPeriodic() {
    	eyeCandy.writeSequence(5);
        eyeCandy.writeElevatorPos((int)((joy.getRawAxis(1)+1)*60));
        System.out.println((int)((joy.getRawAxis(1)+1)*60));
    }
    

    public void testPeriodic() {
    
    }
    public void disabledPeriodic(){
    	eyeCandy.writeSequence(4);
    }
    
    public void arduinoControls(){

        if (joy.getRawButton(1)){
            eyeCandy.writeSequence(1);
        }
        else if (joy.getRawButton(2)){
            eyeCandy.writeSequence(2);
        }
        else if (joy.getRawButton(3)){
            eyeCandy.writeSequence(3);
        }
        else if (joy.getRawButton(4)){
            eyeCandy.writeSequence(4);
        }
        else if(joy.getRawButton(5)){
            eyeCandy.writeSequence(5);
        }
        else if(joy.getRawButton(6)){
            eyeCandy.writeSequence(6);
        }
        else {
            eyeCandy.writeSequence(0);
        }

    }
}


