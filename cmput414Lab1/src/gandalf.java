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
		
		int minLightThreshold = 300;
	    String state = "explore";
	    int minSpeed = 60;
	    
	    int lightFound = 0;
	    double totalHeading = 0;
	    long timer = System.currentTimeMillis();
		while (true) {
			/*
			if (Button.RIGHT.isPressed()){
				light1.calibrateHigh();
				light2.calibrateHigh();
	        }
			if (Button.LEFT.isPressed()){
				light1.calibrateLow();
				light2.calibrateLow();
	        	break;
	        }
	        */
			if (Button.ENTER.isPressed()){
	        	break;
	        }
	        // draw to LCD
	        LCD.drawInt(light1.getNormalizedLightValue(), 4, 0, 1);
	        LCD.drawInt(light2.getNormalizedLightValue(), 4, 0, 2);

	        //LCD.drawString(state, 0,3);
	        //LCD.drawInt(light1.getLightValue(), 4, 0, 0);
	        //LCD.drawInt(SensorPort.S1.readRawValue(), 4, 0, 2);
	        //LCD.drawInt(SensorPort.S1.readValue(), 4, 0, 3);

	        // get light value
	        int lightRec1 = light1.getNormalizedLightValue();
	        int lightRec2 = light2.getNormalizedLightValue();
	        //System.out.println("state: " + state);
	        /*
	        if (state.equals("explore")){
	    		MotorPort.A.controlMotor(75, BasicMotorPort.BACKWARD);
	            MotorPort.C.controlMotor(75, BasicMotorPort.FORWARD);
	        	
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
		        
	    		MotorPort.A.controlMotor(75, BasicMotorPort.BACKWARD);
	            MotorPort.C.controlMotor(75, BasicMotorPort.FORWARD);
	            */
	            /*if (lightFound < 300){
	            	state = "explore";
	            	continue;
	            }*/
	       /*
		        if ((Math.abs(lightRec1 - lightFound) == 20) || 
		           (Math.abs(lightRec2 - lightFound) == 20)){
		        	state = "moving";
		        }
	        } else if (state.equals("moving")){
	        	if ((lightRec1 < 300) && (lightRec2 < 300)){
	        		state = "explore";
	        		continue;
	        	}
	        */
	        
	        	if ((lightRec1 > 640) || (lightRec2 > 640)){
		        	MotorPort.A.controlMotor(100, BasicMotorPort.STOP);
		            MotorPort.C.controlMotor(100, BasicMotorPort.STOP);
	        		continue;
	        	}
	        	double OldRange = (890 - minLightThreshold);  
	        	double NewRange = (100 - minSpeed);
	        	double powerA = (((lightRec1 - minLightThreshold) * NewRange) / OldRange) + minSpeed;
	        	double powerC = (((lightRec2 - minLightThreshold) * NewRange) / OldRange) + minSpeed;
	        	/*
	        	// EXPLORATION MODE:
	        	if (System.currentTimeMillis() - timer < 5000){
	        		timer = System.currentTimeMillis();
		        	if ((powerA < 60) && (powerC < 60)){
		        		MotorPort.A.controlMotor(75, BasicMotorPort.BACKWARD);
			            MotorPort.C.controlMotor(75, BasicMotorPort.FORWARD);
			            continue;
		            }
	        	}
	        	*/
		        LCD.drawInt((int)powerA, 4, 0, 3);
		        LCD.drawInt((int)powerC, 4, 0, 4);
		        
	        	MotorPort.A.controlMotor((int) powerA, BasicMotorPort.FORWARD);
	            MotorPort.C.controlMotor((int) powerC, BasicMotorPort.FORWARD);
	            

	            
	        //}
	        

	        
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
	        
	     }
	    
		//start motors

	    
	    
	    Button.waitForPress();

	}

}