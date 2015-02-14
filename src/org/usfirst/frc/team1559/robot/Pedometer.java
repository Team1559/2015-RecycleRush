package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.Encoder;

public class Pedometer {

	Encoder x;
	Encoder y;
	
	public Pedometer(){
		x = new Encoder(0, 1);
		y = new Encoder(2, 3);
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
