import lejos.nxt.Button;
import lejos.nxt.BasicMotorPort;
import lejos.nxt.MotorPort;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.util.Delay;
import lejos.nxt.LightSensor;

public class Arm4 {
	
	// static double P = 56 * Math.PI;
	// static double Pprime = 115 * Math.PI;
	// static double distPerTick = P / 360;
	// static double ticksPerRotation =  Pprime / distPerTick;
	// static double radiansPerTick = (2 * Math.PI) / ticksPerRotation;
	static double theta1Rad;
	static double theta2Rad;
	static double theta1Deg;
	static double theta2Deg;
	
	static double L1 = 144;
	static double L2 = 95;
	
	static double xe;
	static double ye;
	
	public static void main(String[] args) {
		// SPECIFY ARGUMENT
		theta1Deg = -50;
		theta2Deg = -50;
		theta1Rad = theta1Deg * Math.PI / 180;
		theta2Rad = theta2Deg * Math.PI / 180;
		
		//////////////
		
		boolean doneA = false;
		boolean doneB = false;
		
		if (theta1Rad > 0) {
			MotorPort.A.controlMotor(62, BasicMotorPort.FORWARD);
		} else {
			MotorPort.A.controlMotor(62, BasicMotorPort.BACKWARD);
		}
		
		if (theta2Rad > 0){		
			MotorPort.B.controlMotor(62, BasicMotorPort.FORWARD);
		} else {
			MotorPort.B.controlMotor(62, BasicMotorPort.BACKWARD);
		}
		
		MotorPort.A.resetTachoCount();
		MotorPort.B.resetTachoCount();
		
		while(true){
			System.out.println("Motor A: " + MotorPort.A.getTachoCount());
			System.out.println("Motor B: " + MotorPort.B.getTachoCount());
			double tA = MotorPort.A.getTachoCount() * (Math.PI / 180); // in radians
			double tB = MotorPort.B.getTachoCount() * (Math.PI / 180); // in radians
			
			if (Math.abs(MotorPort.A.getTachoCount()) >= Math.abs(theta1Deg)){
				doneA = true;
				MotorPort.A.controlMotor(100, BasicMotorPort.STOP);
			}
			
			if (Math.abs(MotorPort.B.getTachoCount()) >= Math.abs(theta2Deg)){
				doneB = true;
				MotorPort.B.controlMotor(100, BasicMotorPort.STOP);
			}
			
			//if (theta2 == 0) doneB = true;
			//if (theta1 == 0) doneA = true;
			
			xe = L1 * Math.cos(theta1Rad) + L2 * Math.cos(theta1Rad + theta2Rad);
			ye = L1 * Math.sin(theta1Rad) + L2 * Math.sin(theta1Rad + theta2Rad);
			
			
			if (doneA && doneB){
				//System.out.println("theta1deg: " + Math.floor(theta1Deg* 100) / 100);
				//System.out.println("theta2deg: " + Math.floor(theta2Deg* 100) / 100);
				System.out.println("xe: " + Math.floor(xe* 100) / 100);
				System.out.println("ye: " + Math.floor(ye* 100) / 100);
				break;
			}
			
			if (Button.ENTER.isPressed()){
	        	break;
	        }
		}
		//start motors
	    Button.waitForPress();

	}

}