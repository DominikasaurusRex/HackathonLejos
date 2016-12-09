import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;

import lejos.robotics.navigation.DifferentialPilot;

import lejos.nxt.TouchSensor;
import lejos.nxt.LightSensor;
import lejos.nxt.UltrasonicSensor;

public class MainClass {

	DifferentialPilot controllUnit;
	TouchSensor emergencyButton = new TouchSensor(SensorPort.S1);
	LightSensor lightSensor = new LightSensor(SensorPort.S2);
	UltrasonicSensor ultraSensor = new UltrasonicSensor(SensorPort.S3);

	int distanceBasedOnUnltrasonic;
	boolean mainLoopCondition;
	boolean greifarmOffen = true;

	int noResultOnUltrasonic = 255;
	int whiteTapeLightReading = 30;
	float diameterOfWheel = 2.25f;

	public void mainLoop() {
		while (mainLoopCondition) {
			bewegenMitLinienScan(5);
			distanceBasedOnUnltrasonic = ultraSensor.getDistance();
			
			if(drehenMitSuchfunktion(30) < noResultOnUltrasonic){
				System.out.println(drehenMitSuchfunktion(5));
				gefundenesObjektAnsteuerungUndGreifen();
			}else if(drehenMitSuchfunktion(-60) < noResultOnUltrasonic){
				gefundenesObjektAnsteuerungUndGreifen();
			}else if(drehenMitSuchfunktion(30) < noResultOnUltrasonic){
				gefundenesObjektAnsteuerungUndGreifen();
			}
			
			
			if (emergencyButton.isPressed()) {
				controllUnit.quickStop();
				mainLoopCondition = false;
			}
		}

		System.out.println("Distance: " + controllUnit.getMovement().getDistanceTraveled());

		Button.waitForAnyPress();

	}

	public static void main(String[] args) {
		MainClass traveler = new MainClass();
		traveler.controllUnit = new DifferentialPilot(2.25f, 5.5f, Motor.A, Motor.B);
		traveler.mainLoopCondition = true;
		traveler.mainLoop();

		// TODO: waypoint base
	}

	public float drehenMitSuchfunktion(double grad) {
		double deg;
		if (grad > 0) {
			deg = 5.0;
		} else {
			deg = -5.0;
		}

		for (int i = 0; i < grad;) {
			controllUnit.rotate(deg);
			distanceBasedOnUnltrasonic = ultraSensor.getDistance();
			if (distanceBasedOnUnltrasonic < noResultOnUltrasonic) {
				
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				if(distanceBasedOnUnltrasonic < noResultOnUltrasonic){
					return distanceBasedOnUnltrasonic / diameterOfWheel;
				}
			}
			i += 5;
		}
		return 255;
	}

	public boolean gefundenesObjektAnsteuerungUndGreifen() {
		controllUnit.travel(distanceBasedOnUnltrasonic, true);
		while (controllUnit.isMoving()) {
			if (lightSensor.readValue() >= whiteTapeLightReading) {
				return false;
			}
		}
		schliesseGreifarm();
		System.exit(0);
		return true;
	}

	public void schliesseGreifarm() {
		if (greifarmOffen) {
			Motor.C.rotate(-90);
			greifarmOffen = false;
		}
	}

	public void offneGreifarm() {
		if (!greifarmOffen) {
			Motor.C.rotate(90);
			greifarmOffen = true;
		}
	}

	public void bewegenMitLinienScan(int distanceToTravel) {
		controllUnit.travel(distanceToTravel, true);
		while (controllUnit.isMoving()) {
			if (lightSensor.readValue() >= whiteTapeLightReading) {
				controllUnit.travel(-15 / diameterOfWheel);
				drehenMitSuchfunktion(120);
			}
			
			distanceBasedOnUnltrasonic = ultraSensor.getDistance();
			if (distanceBasedOnUnltrasonic != noResultOnUltrasonic) {
				if(gefundenesObjektAnsteuerungUndGreifen()){
					//
					/*TODO: 
					mainLoopCondition = false;
					REturn to base
					*///
					
				}
			}
		}
	}
	
	
}