package org.usfirst.frc.team1559.robot;
//NOTE: Pixy is offset 3 in to the right

public class PixyController {
	Pixy pixy;
	PixyPacket pkt;
	double error;
	public final double ratio = 109/80;
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
//			if ((ratio-.1) <= (objRatio) && (ratio+.1) >= (objRatio)){
				if (pkt.X < 70 || pkt.X > 90){
					error = 80-pkt.X;
					error = error/160;
				}
				else{
					error = 0;
				}
//			}
//			else if((objRatio) > (ratio+0.1)){
//				error = 0;
//			}
//		    else{
//				error = 0;
//			}
		}
		else{
			error = error - 0.05;
		}
		return error;
	}
}