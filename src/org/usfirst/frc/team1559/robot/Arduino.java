package org.usfirst.frc.team1559.robot;
import edu.wpi.first.wpilibj.I2C;

public class Arduino {
	I2C arduino;
	byte[] data;
	int address;
	int value1 = 0;
	int value2 = 0;
	int value3 = 0;

	public Arduino (int address_){
		arduino = new I2C(I2C.Port.kMXP,address_);
		address_ = address;
		data = new byte[3];
	}
	public void Write(int val1, int val2, int val3){
		data[0] = (byte) val1;
		data[1] = (byte) val2;
		data[2] = (byte) val3;
		arduino.writeBulk(data);
	}
	public void writeSequence(int sequence){
		value1 = sequence;
		Write(value1,value2,value3);
	}
	public void writeElevatorPos(int elevatorPos){
		value2 = elevatorPos;
		Write(value1,value2,value3);
	}
	public void writeAlliance(int alliance){
		value3 = alliance;
		Write(value1,value2,value3);
	}
	public void update(){
		Write(value1,value2,value3);
	}
}
