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
				
				if (System.currentTimeMillis() - intervalTimer > timeInterval*1000){
					break;
				}
			}
		}
		
		
		MotorPort.A.controlMotor(100, BasicMotorPort.STOP);
        MotorPort.C.controlMotor(100, BasicMotorPort.STOP);
		
        /*MotorPort.A.resetTachoCount();
        MotorPort.C.resetTachoCount();
        double distToMove = Pprime/4;
        double leftTick = MotorPort.A.getTachoCount();
		double rightTick = MotorPort.C.getTachoCount();
		double deltaDistance = ((Math.abs(leftTick) + Math.abs(rightTick)) / 2) * distPerTick;
		//double deltaHeading = ((rightTick - leftTick) * radiansPerTick) / 2;
		System.out.println("Hey there!");
		System.out.println(distToMove);
		while(deltaDistance < distToMove) {
			leftTick = MotorPort.A.getTachoCount();
			rightTick = MotorPort.C.getTachoCount();
			deltaDistance = ((Math.abs(leftTick) + Math.abs(rightTick)) / 2) * distPerTick;
			//double deltaHeading = ((rightTick - leftTick) * radiansPerTick) / 2;	
			//LCD.drawInt(deltaDistance, 7, 0);
			String dd = Double.toString(deltaDistance);
			System.out.println(dd);
			//LCD.drawString(dd, 7, 0);
		}*/
 
        
        Button.waitForPress();

	}

}