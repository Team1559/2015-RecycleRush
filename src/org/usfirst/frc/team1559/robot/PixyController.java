package org.usfirst.frc.team1559.robot;
//NOTE: Pixy is offset 3 in to the right

public class PixyController {
	Pixy pixy;
	PixyPacket pkt;
	double error;
	double objRatio = 0;
	public PixyController(Pixy p){
		pixy = new Pixy();
		pkt = new PixyPacket();
	}
	public double autoCenter(){
		pkt = null;
		try{
			pkt = pixy.readPacket(1);
		} catch (PixyException e){
			e.printStackTrace();
		}
		if (pkt != null){
				if (pkt.Y < 100 || pkt.Y > 110){
					error = 50-pkt.Y;
					error = error/120;
					error = error/2;
				}
				else{
					error = 0;
				}
		}
		else{
			error = error - 0.05;
		}
		return error;
	}
}