package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Encoder;

public class Pedometer {

	Encoder x;
	Encoder y;
	double wheelDiameter = 2.75;
	double ticksPerRev = 100;
	double distPerPulse = Math.PI* wheelDiameter / ticksPerRev; //mechanically set???
	
	public Pedometer(){
		x = new Encoder(Wiring.ENCODER_xA, Wiring.ENCODER_xB);
		y = new Encoder(Wiring.ENCODER_yA, Wiring.ENCODER_yB);
		x.setDistancePerPulse(distPerPulse); //gotta love me some doubles
		y.setDistancePerPulse(distPerPulse);
	}
	
	public double getX(){
		return x.getDistance();
	}
	
	public double getY(){
		return y.getDistance();
	}
	
	public double[] getBoth(){
		double[] d = new double[2];
		d[0] = x.getDistance();
		d[1] = y.getDistance();
		return d;
	}
	
	public void reset(){
		x.reset();
		y.reset();	
	}	
}