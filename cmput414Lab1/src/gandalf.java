import lejos.nxt.Button;
import lejos.nxt.BasicMotorPort;
import lejos.nxt.MotorPort;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.util.Delay;
import lejos.nxt.LightSensor;

public class gandalf {
	static double P = 56 * Math.PI;
	static double Pprime = 115 * Math.PI;
	static double distPerTick = P / 360;
	static double ticksPerRotation =  Pprime / distPerTick;
	static double radiansPerTick = (2 * Math.PI) / ticksPerRotation;
	
	public static void main(String[] args) {
		
		System.out.println("Hello !");
		
		LightSensor light1 = new LightSensor(SensorPort.S1);
		LightSensor light2 = new LightSensor(SensorPort.S4);
		
		int minThreshold = 300;
	    String state = "explore";
	    
	    int lightFound = 0;
	    double totalHeading = 0;
	    
		while (true) {
	        // draw to LCD
	        LCD.drawInt(light1.getNormalizedLightValue(), 4, 0, 1);
	        LCD.drawInt(light2.getNormalizedLightValue(), 4, 0, 2);
	        //LCD.drawInt(light1.getLightValue(), 4, 0, 0);
	        //LCD.drawInt(SensorPort.S1.readRawValue(), 4, 0, 2);
	        //LCD.drawInt(SensorPort.S1.readValue(), 4, 0, 3);

	        // get light value
	        int lightRec1 = light1.getNormalizedLightValue();
	        int lightRec2 = light2.getNormalizedLightValue();
	        //System.out.println("state: " + state);
	        if (state.equals("explore")){
	    		MotorPort.A.controlMotor(60, BasicMotorPort.BACKWARD);
	            MotorPort.C.controlMotor(60, BasicMotorPort.FORWARD);
	        	
	        	// save brightest light while exploring
		        if (lightRec1 > lightFound){
		        	lightFound = lightRec1;
		        }
		        if (lightRec2 > lightFound){
		        	lightFound = lightRec2;	        	
		        }
	        	
	        	int leftTick = MotorPort.C.getTachoCount();
	        	int rightTick = MotorPort.A.getTachoCount();
	        	MotorPort.A.resetTachoCount();
	            MotorPort.C.resetTachoCount();
	            
	        	double deltaHeading = ((rightTick - leftTick) * radiansPerTick) / 2;	
	        		
	        	totalHeading += deltaHeading;
	        	//System.out.println("heading: " + totalHeading);
	        	if (Math.abs(totalHeading) > (2*Math.PI)) {
	        		totalHeading = 0;
	        		state = "searching";
	        	}
	        } else if (state.equals("searching")){
		        
	    		MotorPort.A.controlMotor(60, BasicMotorPort.BACKWARD);
	            MotorPort.C.controlMotor(60, BasicMotorPort.FORWARD);
	            
	            if (lightFound < 300){
	            	state = "explore";
	            	continue;
	            }
	            
		        if ((Math.abs(lightRec1 - lightFound) == 50) || 
		           (Math.abs(lightRec2 - lightFound) == 50)){
		        	state = "moving";
		        }
	        } else if (state.equals("moving")){
	        	int powerA = lightRec1 -
	        }
	        

	        
	        //do a 360
	        /*
	        if ((lightRec1 > lightThreshold) && (lightRec2 > lightThreshold)){
	    		MotorPort.A.controlMotor(80, BasicMotorPort.FORWARD);
	            MotorPort.C.controlMotor(80, BasicMotorPort.FORWARD);
	        } else if ((lightRec1 - lightRec2) < 100) {
	    		MotorPort.A.controlMotor(70, BasicMotorPort.FORWARD);
	            MotorPort.C.controlMotor(70, BasicMotorPort.BACKWARD);
	        } else if ((lightRec2 - lightRec1) < 100) {
	    		MotorPort.A.controlMotor(70, BasicMotorPort.BACKWARD);
	            MotorPort.C.controlMotor(70, BasicMotorPort.FORWARD);
	        }
	        */
	        if (Button.ENTER.isPressed()){
	        	break;
	        }
	     }
	    
		//start motors

	    
	    
	    Button.waitForPress();
		/*
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
        */
        //Button.waitForPress();

	}

}