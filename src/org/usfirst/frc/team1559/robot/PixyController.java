package org.usfirst.frc.team1559.robot;
//NOTE: Pixy is offset 3 in to the right

public class PixyController {
	Pixy pixy;
	
    final double ratio = 109/80;
	
	public PixyController(Pixy p){
		pixy = new Pixy();
	}
	
	public PixyDriveValues autoCenter(PixyPacket pkt){
		double errorX = 0;
		double errorY = 0;
		double objRatio = 0;
		
		PixyDriveValues drivePkt = new PixyDriveValues();
		
		if (pkt != null){
			System.out.println(pkt.X + " " + pkt.Y);
			try{
				objRatio = pkt.Height/pkt.Width;
			} catch (Exception e){
				System.out.println("Dividing by zero");
				objRatio = 0;
			}
//			if ((ratio-.1) <= (objRatio) && (ratio+.1) >= (objRatio)){
				if (pkt.X < 150 || pkt.X > 170){
					errorX = pkt.X - 160;
					errorX = errorX/160;
					if(-0.3 < errorX && errorX < 0){
						errorX = -0.3;
					} else if (0 < errorX && errorX < 0.3){
						errorX = 0.3;
					}
				}
				else{
					drivePkt.centeredX = true;
					errorX = 0;
				}
				
				if (pkt.Width < 320){
					errorY = 320 - pkt.Width;
					errorY = errorY/320;
					if (errorY < 0.3){
						errorY = 0.3;
					}
				}
				else{
					System.out.println("Tote fills screen");
					errorY = 0;
					drivePkt.centeredY = true;
				}
//			}
//			else if((objRatio) > (ratio+0.1)){
//				error = 0;
//			}
//		    else{
//				error = 0;
//			}
		}
			
		drivePkt.driveX = errorX;
		drivePkt.driveY = errorY;
		return drivePkt;
	}
}