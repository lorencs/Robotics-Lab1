import lejos.nxt.Button;
import lejos.nxt.BasicMotorPort;
import lejos.nxt.MotorPort;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.util.Delay;
import lejos.nxt.LightSensor;

public class Arm5 {

	// The LEFT button will save the first point
	// The RIGHT button will save the second point
	// Distance is calculated right after pressing the RIGHT button
		
	static double L1 = 144;
	static double L2 = 95;
	
	static double x1;
	static double y1;
	static double x2;
	static double y2;
	
	public static void main(String[] args) {
		boolean part1 = true;
		
		double tA; // in radians
		double tB; // in radians
		
		while(true){
			if (Button.LEFT.isPressed() && part1){
				tA = MotorPort.A.getTachoCount() * (Math.PI / 180); // in radians
				tB = MotorPort.B.getTachoCount() * (Math.PI / 180); // in radians
				x1 = L1 * Math.cos(tA) + L2 * Math.cos(tA + tB);
				y1 = L1 * Math.sin(tA) + L2 * Math.sin(tA + tB);
				part1 = false;
				System.out.println("Saved point 1 !");
				continue;
			}
			if (!part1 && Button.RIGHT.isPressed()){
				tA = MotorPort.A.getTachoCount() * (Math.PI / 180); // in radians
				tB = MotorPort.B.getTachoCount() * (Math.PI / 180); // in radians
				x2 = L1 * Math.cos(tA) + L2 * Math.cos(tA + tB);
				y2 = L1 * Math.sin(tA) + L2 * Math.sin(tA + tB);
				System.out.println("Saved point 2 !");
				break;
			}
			
		}
		double distance = Math.sqrt( (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)  );
		System.out.println("Distance: " + Math.floor(distance* 100) / 100);
		
		//start motors
	    Button.waitForPress();

	}

}