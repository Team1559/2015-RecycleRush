
package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;

//testing test test
public class Robot extends IterativeRobot {
 
	Joystick joy;
    Arduino eyeCandy;
	
    public void robotInit() {
        eyeCandy = new Arduino(0,1,2);
        joy = new Joystick(Wiring.JOYSTICK_1);
    }


    public void autonomousInit(){
    	
    }
    
    public void autonomousPeriodic() {
    	    	
    }
    
    public void teleopInit(){

    }


    public void teleopPeriodic() {
    	  if (joy.getRawButton(1)){
    		  eyeCandy.Write(1);//1 is Rave     WARNING EYE PROTECTION RECOMENDED
    	  }
    	  else if (joy.getRawButton(2)){
    		  eyeCandy.Write(2);//2 is Blue Gold Flash
    	  }
    	  else if (joy.getRawButton(3)){
    		  eyeCandy.Write(3);//3 is Synchronized RGB Fade
    	  }
    	  else if (joy.getRawButton(4)){
    		  eyeCandy.Write(4);//4 is Non-Synchronized RGB Fade
    	  }
    	  else if (joy.getRawButton(5)){
    		  eyeCandy.Write(5);//5 is Garbage, Will Probably Change
    	  }
    	  else if (joy.getRawButton(6)){
    		  eyeCandy.Write(6);//6 is Blue Gold Fade
    	  }
    	  else if (joy.getRawButton(7)){
    		  eyeCandy.Write(7);//7 is not Implemented yet...
    	  }
    	  else {
    		  eyeCandy.Write(0);//0 is Off 
    	  }
    	
    }
    

    public void testPeriodic() {
    
    }
    
    public void arduinoControls(){

        if (joy.getRawButton(1)){
            eyeCandy.Write(1);
        }
        else if (joy.getRawButton(2)){
            eyeCandy.Write(2);
        }
        else if (joy.getRawButton(3)){
            eyeCandy.Write(3);
        }
        else if (joy.getRawButton(4)){
            eyeCandy.Write(4);
        }
        else if(joy.getRawButton(5)){
            eyeCandy.Write(5);
        }
        else if(joy.getRawButton(6)){
            eyeCandy.Write(6);
        }
        else {
            eyeCandy.Write(0);
        }

    }
}


