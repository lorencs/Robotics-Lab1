import lejos.nxt.Button;
import lejos.nxt.BasicMotorPort;
import lejos.nxt.MotorPort;
import lejos.nxt.LCD;
import lejos.util.Delay;

public class deadrec {
	static double P = 56 * Math.PI;
	static double Pprime = 115 * Math.PI;
	static double distPerTick = P / 360;
	static double ticksPerRotation =  Pprime / distPerTick;
	static double radiansPerTick = (2 * Math.PI) / ticksPerRotation;
	
	public static void main(String[] args) {
		int[][] command = {
			      { 80, 60, 1},
			      {-80, 80, 1},
			      {	80, 80, 1}
			    };		
		
		double totalHeading = 0;
		double X = 0;
		double Y = 0;
		 
		for (int i = 0; i < 3; i++){
			int powerA = command[i][0];
			int powerB = command[i][1];
			int timeInterval = command[i][2];
			
			long intervalTimer = System.currentTimeMillis();
			
			//start motors
			MotorPort.A.controlMotor(powerA, BasicMotorPort.FORWARD);
	        MotorPort.C.controlMotor(powerB, BasicMotorPort.FORWARD);
			
	        MotorPort.A.resetTachoCount();
	        MotorPort.C.resetTachoCount();
	        
			while(true){
				int leftTick = MotorPort.A.getTachoCount();
				int rightTick = MotorPort.C.getTachoCount();
				MotorPort.A.resetTachoCount();
		        MotorPort.C.resetTachoCount();
		        
				double deltaDistance = (leftTick + rightTick) / 2 * distPerTick;
				double deltaHeading = ((rightTick - leftTick) * radiansPerTick) / 2;	
				
				totalHeading += deltaHeading;
				
				double deltaX = deltaDistance * Math.cos(totalHeading);
				double deltaY = deltaDistance * Math.sin(totalHeading);
				
				X += deltaX;
				Y += deltaY;
				
				if (System.currentTimeMillis() - intervalTimer > timeInterval*1000){
					break;
				}
			}
		}
		
		
		MotorPort.A.controlMotor(100, BasicMotorPort.STOP);
        MotorPort.C.controlMotor(100, BasicMotorPort.STOP);
		   
        System.out.println("Heading: " + Math.floor((totalHeading * 180/Math.PI)* 100) / 100);
        
        Button.waitForPress();

	}

}