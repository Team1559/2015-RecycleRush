package org.usfirst.frc.team1559.robot;

public class PixyException extends Exception {
	public PixyException(String message) {
		super(message);
		message = "Checksum didn't work";
	}

}
