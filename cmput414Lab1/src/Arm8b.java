import lejos.nxt.Button;
import lejos.nxt.BasicMotorPort;
import lejos.nxt.MotorPort;

public class Arm8b {

	// The LEFT button will save the first point
	// The RIGHT button will save the second point
	// Distance is calculated right after pressing the RIGHT button
	
	static double a1 = 144;
	static double a2 = 95;
	
	// specify starting point
	static double x1 = 100;
	static double y1 = 85;
	
	// specify angle
	static double angle = 60;
	
	// specify distance
	static double distance = 200;	
	
	static double x2;
	static double y2;
	
	static double travelledA = 0;
	static double travelledB = 0;	
		
	public static boolean insideCircle(double x, double y, double radius){
		return (x*x + y*y) < radius*radius;
	}
	
	public static boolean insideWorkspace(double x, double y){
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
	
	public static void moveTo(double xa, double ya){
		System.out.println("moving to " + Math.floor(xa*100)/100 + ", " + Math.floor(ya*100)/100);
		double theta1 = 0;
		double theta2 = 0;
		
		boolean moveit = true;
		
		double D = (xa*xa + ya*ya - a1*a1 - a2*a2)/(2*a1*a2);
		
		double theta2sol1 = Math.atan2(Math.sqrt(1-D*D), D);
		double theta2sol2 = Math.atan2(-1*Math.sqrt(1-D*D), D);
		
		double theta1sol1 = Math.atan2(ya, xa) - Math.atan2((a2*Math.sin(theta2sol1)), (a1+a2*Math.cos(theta2sol1)));
		double theta1sol2 = Math.atan2(ya, xa) - Math.atan2((a2*Math.sin(theta2sol2)), (a1+a2*Math.cos(theta2sol2)));
		
		
		//System.out.println("t1s1: " + Math.floor(theta1sol1 * 180/Math.PI * 100) / 100);
		//System.out.println("t2s1: " + Math.floor(theta2sol1 * 180/Math.PI * 100) / 100);
		//System.out.println("t1s2: " + Math.floor(theta1sol2 * 180/Math.PI * 100) / 100);
		//System.out.println("t2s2: " + Math.floor(theta2sol2 * 180/Math.PI * 100) / 100);
		
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
		
		if (!insideWorkspace(xa, ya) && moveit){
			System.out.println("out of workspace!");
			moveit = false;
		}
		
		// "virtually" go back to origin
		double t1origin = -1 *(travelledA * (Math.PI / 180));
		double t2origin = -1 *(travelledB * (Math.PI / 180));
		
		theta1 += t1origin;
		theta2 += t2origin;
		
		if (theta2 > Math.PI) theta2 = -1*(2*Math.PI - theta2);
		if (theta2 < -Math.PI) theta2 = 2*Math.PI + theta2;
		
		System.out.println("t1: " + Math.floor((theta1*180/Math.PI)*100)/100);
		System.out.println("t2: " + Math.floor((theta2*180/Math.PI)*100)/100);
		
		if (moveit){
			if (theta1 > 0) {
				MotorPort.A.controlMotor(70, BasicMotorPort.FORWARD);
			} else {
				MotorPort.A.controlMotor(70, BasicMotorPort.BACKWARD);
			}
			
			if (theta2 > 0){		
				MotorPort.B.controlMotor(70, BasicMotorPort.FORWARD);
			} else {
				MotorPort.B.controlMotor(70, BasicMotorPort.BACKWARD);
			}
		}
		
		boolean doneA = false;
		boolean doneB = false;
		
		MotorPort.A.resetTachoCount();
		MotorPort.B.resetTachoCount();
		
		long delayTimer = System.currentTimeMillis();
		
		while(moveit){	
			/*if (System.currentTimeMillis() - delayTimer < 50){
				continue;
			} else {
				delayTimer = System.currentTimeMillis();
			}*/
			
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
		
		travelledA += MotorPort.A.getTachoCount();
		travelledB += MotorPort.B.getTachoCount();
	}
		
	public static void main(String[] args) {
		//calculate second point 
		double dx = distance*Math.cos(angle*Math.PI/180);
		double dy = distance*Math.sin(angle*Math.PI/180);
		
		x2 = x1 + dx;
		y2 = y1 + dy;
		
		// move to first point
		System.out.println("initial move:");
		moveTo(x1, y1);
		
		Button.waitForPress();
		
		double vecX = x2 - x1;
		double vecY = y2 - y1;
		double vecLength = Math.sqrt(vecX*vecX + vecY*vecY);
		int numPoints = (int) (vecLength / 10);
		
		for (int i = 0; i < numPoints; i++){
			double diffX = vecX * (i+1)/numPoints;
			double diffY = vecY * (i+1)/numPoints;
			
			double targetX = x1 + diffX;
			double targetY = y1 + diffY;
			
			moveTo(targetX, targetY);
		}		
				
		//start motors
	    Button.waitForPress();

	}

}