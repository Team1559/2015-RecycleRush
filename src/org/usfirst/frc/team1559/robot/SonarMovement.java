package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Talon;

public class SonarMovement {
    
    Talon leftFront, rightFront, leftBack, rightBack;
    final double MAXDISTANCE = 4;
    final int NO = 0, LEFT = 1, RIGHT = 2;
    SonarStereo sonarStereo;
    MecanumDrive drive;

    Gyro gyro;
    int turnStage;
    int timer;
    final int TIME_LIMIT = 75;
    boolean decisionMade = false;
    
    public SonarMovement(Talon leftFront, Talon rightFront, Talon leftBack, Talon rightBack, SonarStereo sonarStereo, Gyro gyro) {
    	drive = new MecanumDrive(leftFront, leftBack, rightFront, rightBack);
        this.gyro = gyro;
        this.sonarStereo = sonarStereo;
        turnStage = 0;
        timer = 0;
    }

    public double gyroReading() {
        return gyro.getAngle();
    }

    public boolean isDetecting() {
        return sonarStereo.right.getFeet() < MAXDISTANCE || sonarStereo.left.getFeet() < MAXDISTANCE;
    }
    
    public int decide() {
        if (isDetecting()) {
            if (sonarStereo.right.getFeet() < sonarStereo.left.getFeet()) {
                System.out.println("Left!");
                decisionMade = true;
                return LEFT;
            } else {
                System.out.println("Right!");
                decisionMade = true;
                return RIGHT;
            }
        } else {
            System.out.println("No decision");
            return NO;
        }
    }
    
    public void react(int decision) {
        switch (decision) {
        case NO:
            moveForward();
            break;
        case LEFT:
            leftSequence();
            break;
        case RIGHT:
            rightSequence();
            break;
        default:
            moveForward();
            break;
        }
    }
    
    public void leftSequence() {
        System.out.println(gyroReading());
        switch(turnStage) {
            case 0:
                System.out.println("Stage 0: Setup");
                gyro.reset();
                turnStage = 1;
                break;
            case 1:
                System.out.println("Stage 1: Turn outward"); // Gyro-based
                turnLeft();
                if(gyro.getAngle() <= -45)
                    turnStage = 2;
                break;
            case 2:
                System.out.println("Stage 2: Forward"); // Time-based
                timer++;
                if(timer <= TIME_LIMIT) {
                    moveForward();
                } else {
                    turnStage = 3;
                    timer = 0;
                }
                break;
            case 3:
                System.out.println("Stage 3: Turn back"); // Gyro-based
                turnRight();
                if(gyro.getAngle() >= 45)
                    turnStage = 4;
                break;
            case 4:
                System.out.println("Stage 4: Forward again"); // Time-based
                timer++;
                if(timer <= TIME_LIMIT) {
                    moveForward();
                } else {
                    turnStage = 5;
                    timer = 0;
                }
                break;
            case 5:
                System.out.println("Stage 5: Re-adjust"); // Gyro-based
                turnLeft();
                if(gyro.getAngle() <= 0)
                    turnStage = 6;
                break;
            case 6:
                System.out.println("Stage 6: Preparing for another obstacle");
                timer = 0;
                decisionMade = false;
                gyro.reset();
                turnStage = 0;
            default:
                break;
        }
    }
    
    public void rightSequence() {
        System.out.println(gyroReading());
        switch(turnStage) {
            case 0:
                System.out.println("Stage 0: Setup");
                gyro.reset();
                turnStage = 1;
                break;
            case 1:
                System.out.println("Stage 1: Turn outward"); // Gyro-based
                turnRight();
                if(gyro.getAngle() >= 45)
                    turnStage = 2;
                break;
            case 2:
                System.out.println("Stage 2: Forward"); // Time-based
                timer++;
                if(timer <= TIME_LIMIT) {
                    moveForward();
                } else {
                    turnStage = 3;
                    timer = 0;
                }
                break;
            case 3:
                System.out.println("Stage 3: Turn back"); // Gyro-based
                turnLeft();
                if(gyro.getAngle() <= -45)
                    turnStage = 4;
                break;
            case 4:
                System.out.println("Stage 4: Forward again"); // Time-based
                timer++;
                if(timer <= TIME_LIMIT) {
                    moveForward();
                } else {
                    turnStage = 5;
                    timer = 0;
                }
                break;
            case 5:
                System.out.println("Stage 5: Re-adjust"); // Gyro-based
                turnRight();
                if(gyro.getAngle() >= 0)
                    turnStage = 6;
                break;
            case 6:
                System.out.println("Stage 6: Preparing for another obstacle");
                timer = 0;
                decisionMade = false;
                gyro.reset();
                turnStage = 0;
            default:
                break;
        }
    }
    
    public void moveForward() { //actually moving sideways
    	drive.drive(.25, .0, 0, 0);            
    }

    public void turnLeft() { //actually going forwawrdsish
        drive.drive(0, .25, 0, 0);
    }
    
    public void turnRight() { //actually going backwardsish
        drive.drive(-.25, 0, 0, 0);
    }
    
//    public void setLeft(double speed) {
//        leftMotor.set(speed);
//    }
//    
//    public void setRight(double speed) {
//        rightMotor.set(-speed);
//    }
    
    public void disable() {
        gyro.reset();
        turnStage = 0;
        timer = 0;
        decisionMade = false;
    }
    
    public double getDistance() { // Gets lesser distance
        if(sonarStereo.left.getFeet() > sonarStereo.right.getFeet()) {
            return sonarStereo.right.getFeet();
        } else {
            return sonarStereo.left.getFeet();
        }
    }
}