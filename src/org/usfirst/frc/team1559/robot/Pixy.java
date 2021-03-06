package org.usfirst.frc.team1559.robot;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;

//Warning: if the pixy is plugged in through mini usb, this code WILL NOT WORK b/c the pixy is smart and detects where it should send data
public class Pixy extends Thread{
	private SerialPort pixy;
	private Port port = Port.kMXP;
	private PixyPacket pkt;
	private byte[] b1;
	private int state = 0;
	private byte holder;
	private boolean flag;
	private int queueIn;
	private int queueOut;
	private PixyPacket[] queue;
	private int Checksum = 0;
	private int index = 0;
	private int Sig = 0;
	private int packetCounter = 0;
	private int bCount = 0;
	private Robot rob;
	
	private final int QUEUE_SIZE = 1;

	public Pixy(Robot r) {
		pixy = new SerialPort(115200, port);
		pixy.setReadBufferSize(1);
		pkt = new PixyPacket();
		b1 = new byte[1];
		flag = false;
		queueIn = queueOut = 0;
		queue = new PixyPacket[QUEUE_SIZE];
//		start();
		rob = r;
	}

	// This method parses raw data from the pixy into readable integers
	public int cvt(byte upper, byte lower) {
		return (((int) upper & 0xff) << 8) | ((int) lower & 0xff);
	}

	public void pixyReset() {
		pixy.reset();
	}
	public void setFlag(boolean value){
		flag = value;
	}
	public void run(){
		while (rob.isAutonomous()){
			process();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
				
			}
		}
		System.out.println("Ding dong the witch is dead");
	}

	public void process() {
		// This method gathers data, then parses that data, and assigns the ints to global variables
		
			System.out.println("The thread is running");
			while (true){
			try {
				bCount = pixy.getBytesReceived();
				if (bCount == 0){
					return;
				}
				b1 = pixy.read(1);
			} catch (RuntimeException r) {
				System.out.println(r.getMessage());
				bCount = 0;
			}
			
			if (bCount > 0) {
				// if (b1 != null){
//				System.out.println("s:" + state);
				switch (state) {
				case 0:
//					System.out.printf("1 %02x %d\n", b1[0], b1[0]);
					if (b1[0] == 85) {
						state++;
					}
					break;
				case 1:
//					System.out.printf("2 %02x %d\n", b1[0], b1[0]);
					if (b1[0] == -86) {
						state++;
					} else {
//						System.out.println("Start over");
						state = 0;
					}
					break;
				case 2:
					holder = b1[0];
					if (85 == b1[0]) {
						state++;
					} else {
						state = 5;
					}
					break;
				case 3:
					if (-86 == b1[0]) {
						state++;
					} else {
						Checksum = cvt(b1[0], holder);
						state = 6;
					}
					break;
				case 4:
					holder = b1[0];
					state++;
					break;
				case 5:
					Checksum = cvt(b1[0], holder);
					state++;
					break;
				case 6:
					holder = b1[0];
					state++;
					break;
				case 7:
					switch (index) {
					case 0:
						Sig = cvt(b1[0], holder);
						index++;
						state = 6;
						break;
					case 1:
						pkt.X = cvt(b1[0], holder);
						index++;
						state = 6;
						break;
					case 2:
						pkt.Y = cvt(b1[0], holder);
						index++;
						state = 6;
						break;
					case 3:
						pkt.Width = cvt(b1[0], holder);
						index++;
						state = 6;
						break;
					case 4:
						pkt.Height = cvt(b1[0], holder);
						int tempChecksum = pkt.X + pkt.Y + pkt.Width + pkt.Height + Sig;
						if (Checksum == tempChecksum) {
//							System.out.printf("%d %d %d \n", pkt.X, pkt.Y, ++packetCounter);
//							if (flag)
							{
								int tIn = queueIn + 1;
								if (tIn == QUEUE_SIZE){
									tIn = 0;
								}
								if (tIn != queueOut){
									queue[tIn] = pkt;
									queueIn = tIn;
								}
							}
						} else {
//							System.out.println("Bad checksum" + Checksum + ", " + tempChecksum);
						}
						state = 0;
						index = 0;
						
						break;
					}// switch
				}// switch
			}// if
			}
	}//parsePackets method
	
	public PixyPacket getPacket(){
		PixyPacket pkt = null;
		if (queueIn != queueOut){
			pkt = queue[queueOut++];
			if (queueOut >= QUEUE_SIZE){
				queueOut = 0;
			}
		}
		return pkt;
	}
}//class