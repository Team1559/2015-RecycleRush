package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class Camera {
	CameraServer server;
	USBCamera cam;
	public Camera(String name){
        server = CameraServer.getInstance();
        cam = new USBCamera(name);
        server.startAutomaticCapture(cam);
	}
}
