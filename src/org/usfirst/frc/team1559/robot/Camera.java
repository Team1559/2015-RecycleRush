package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.USBCamera;

public class Camera {
	CameraServer server;
	USBCamera cam;
	public Camera(String name,int width,int height,int compressionLevel){
        server = CameraServer.getInstance();
        cam = new USBCamera(name);
        cam.setSize(width, height);
        server.setQuality(compressionLevel);
        server.startAutomaticCapture(cam);
	}
}
