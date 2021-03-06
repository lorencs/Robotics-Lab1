import lejos.nxt.Button;
import lejos.nxt.BasicMotorPort;
import lejos.nxt.MotorPort;

public class Arm6 {

	// The LEFT button will save the first point
	// The RIGHT button will save the second point
	// Distance is calculated right after pressing the RIGHT button
	
	static double theta1;
	static double theta2;
	
	static double a1 = 144;
	static double a2 = 95;
	
	
	// SPECIFY ARGUEMNT HERE/////
	static double x = 94;      //
	static double y = -64;    //
	/////////////////////////////
	
	public static boolean insideCircle(double x, double y, double radius){
		return (x*x + y*y) < radius*radius;
	}
	
	public static boolean insideWorkspace(){
		// if outside of bigger circle then false
		if (!insideCircle(x,y, 240)){
			System.out.println("not in big");
			return false;
		}
		// if insider smaller circle then false
		if (insideCircle(x,y,50)){
			System.out.println("in small");
			return false;
		}
		
		//if negative x, then false
		if (x < 0) return false;
		return true;
	}
	
	public static void moveTo(double x, double y){
		//theta1Rad = theta1Deg * Math.PI / 180;
		//theta2Rad = theta2Deg * Math.PI / 180;
		boolean moveit = true;
		
		double D = (x*x + y*y - a1*a1 - a2*a2)/(2*a1*a2);
		
		/*double theta2sol1 = Math.atan(Math.sqrt(1-D*D)/D);
		double theta2sol2 = Math.atan(-1*Math.sqrt(1-D*D)/D);
		
		double theta1sol1 = Math.atan(y/x) - Math.atan((a2*Math.sin(theta2sol1))/(a1+a2*Math.cos(theta2sol1)));
		double theta1sol2 = Math.atan(y/x) - Math.atan((a2*Math.sin(theta2sol2))/(a1+a2*Math.cos(theta2sol2)));
		*/
		
		double theta2sol1 = Math.atan2(Math.sqrt(1-D*D), D);
		double theta2sol2 = Math.atan2(-1*Math.sqrt(1-D*D), D);
		
		double theta1sol1 = Math.atan2(y, x) - Math.atan2((a2*Math.sin(theta2sol1)), (a1+a2*Math.cos(theta2sol1)));
		double theta1sol2 = Math.atan2(y, x) - Math.atan2((a2*Math.sin(theta2sol2)), (a1+a2*Math.cos(theta2sol2)));
		
		
		System.out.println("t1s1: " + Math.floor(theta1sol1 * 180/Math.PI * 100) / 100);
		System.out.println("t2s1: " + Math.floor(theta2sol1 * 180/Math.PI * 100) / 100);
		System.out.println("t1s2: " + Math.floor(theta1sol2 * 180/Math.PI * 100) / 100);
		System.out.println("t2s2: " + Math.floor(theta2sol2 * 180/Math.PI * 100) / 100);
		
		//decide which solution to use
		if (Math.abs(theta1sol1) < Math.PI/2){
			theta1 = theta1sol1;
			theta2 = theta2sol1;
		} else if (Math.abs(theta1sol2) < Math.PI/2){
			theta1 = theta1sol2;
			theta2 = theta2sol2;
		} else {
			System.out.println("out of workspace!");
			moveit = false;
		}
		
		if (!insideWorkspace() && moveit){
			System.out.println("out of workspace!");
			moveit = false;
		}
		
		if (moveit){
			if (theta1 > 0) {
				MotorPort.A.controlMotor(62, BasicMotorPort.FORWARD);
			} else {
				MotorPort.A.controlMotor(62, BasicMotorPort.BACKWARD);
			}
			
			if (theta2 > 0){		
				MotorPort.B.controlMotor(62, BasicMotorPort.FORWARD);
			} else {
				MotorPort.B.controlMotor(62, BasicMotorPort.BACKWARD);
			}
		}
		
		boolean doneA = false;
		boolean doneB = false;
		
		while(moveit){			
			if (Math.abs(MotorPort.A.getTachoCount()*Math.PI/180) >= Math.abs(theta1)){
				doneA = true;
				MotorPort.A.controlMotor(100, BasicMotorPort.STOP);
			}
			
			if (Math.abs(MotorPort.B.getTachoCount()*Math.PI/180) >= Math.abs(theta2)){
				doneB = true;
				MotorPort.B.controlMotor(100, BasicMotorPort.STOP);
			}
			
			if (doneA && doneB){
				break;
			}
		}
	}
	
	public static void main(String[] args) {
		moveTo(x,y);
		
		//start motors
	    Button.waitForPress();

	}

}