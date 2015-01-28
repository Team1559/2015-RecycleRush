package org.usfirst.frc.team1559.robot;
import edu.wpi.first.wpilibj.DigitalOutput;

public class Arduino {
	DigitalOutput commPort1;
	DigitalOutput commPort2;
	DigitalOutput commPort3;

	public Arduino (int port1,int port2,int port3){
		commPort1 = new DigitalOutput(port1);
		commPort2 = new DigitalOutput(port2);
		commPort3 = new DigitalOutput(port3);
	}
	public void Write(int val){
		if (val < 8){
			commPort3.set((val & 1 << 2) != 0);
			commPort2.set((val & 1 << 1) != 0);
			commPort1.set((val & 1 << 0) != 0);
		}
		else {
			System.out.println("Lolz, passed in value greater than 7");
		}
	}
}
