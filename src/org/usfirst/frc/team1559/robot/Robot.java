package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;

public class Robot extends IterativeRobot {

	SonarStereo ssonar = new SonarStereo(1, 2, 3);
	Victor left;
        Victor right;
	SonarMovement move;
        Gyro gy;
        Joystick joy;
        int a;
        double lft;
        double rgt;
        int decision;
        
	public void robotInit() {
		left = new Victor(2);
		right = new Victor(3);
                gy = new Gyro (1);
                move = new SonarMovement(left, right, ssonar, gy);
                joy = new Joystick(1);
                a = 1;
	}

	public void autonomousInit() {
              
	}

	public void autonomousPeriodic() {
            ssonar.periodic();
            if(!move.decisionMade)
                decision = move.decide();
            move.react(decision);
        }
        

	public void teleopInit() {
            double lft;
            double rgt;
            if (a==1) {
                System.out.println("teleopPeriodic");
                a++;
            }
        }

	public void teleopPeriodic() {
//            ssonar.periodic();
            
            if (joy.getX() > 0.0) {
                lft = joy.getY() - joy.getX();
                rgt = Math.max(joy.getY(), joy.getX());
            } else {
                lft = Math.max(joy.getY(), -joy.getX());
                rgt = joy.getY() + joy.getX();
            if (joy.getX() > 0.0) {
                lft = -Math.max(-joy.getY(), joy.getX());
                rgt = joy.getY() + joy.getX();
            } else {
                lft = joy.getY() - joy.getX();
                rgt = -Math.max(-joy.getY(), -joy.getX());
            }
        }
        left.set(-lft);
        right.set(rgt);
        
        ssonar.periodic();
}
	public void testPeriodic() {

	}

	public void disabledInit() {
            ssonar.stop();   
	}

	public void disabledPeriodic() {
            move.disable();
	}

}