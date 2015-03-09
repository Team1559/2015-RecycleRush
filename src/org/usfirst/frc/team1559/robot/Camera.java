package org.usfirst.frc.team1559.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;

public class Camera {
	Image frame;
	int session;
	public Camera(String name){
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
        session = NIVision.IMAQdxOpenCamera(name,NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
	}
	public void startStream(){
		NIVision.IMAQdxStartAcquisition(session);
	}
	public void stream(){
		NIVision.IMAQdxGrab(session, frame, 1);
        CameraServer.getInstance().setImage(frame);
	}
	public void endStream(){
    	NIVision.IMAQdxStopAcquisition(session);
	}
}
