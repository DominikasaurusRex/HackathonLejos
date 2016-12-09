import lejos.nxt.LightSensor;
import lejos.nxt.UltrasonicSensor;

public class Scan extends Thread {

	//TouchSensor emergencyButton;
	LightSensor lightSensor;
	UltrasonicSensor ultraSensor;

	int distanceBasedOnUnltrasonic;
	int lightValue;
	boolean buttonPress;
	boolean mainLoopCondition;

	int noResultOnUltrasonic = 255;
	int whiteTapeLightReading = 30;

	public Scan(LightSensor light, UltrasonicSensor ult) {
		//emergencyButton = touch;
		lightSensor = light;
		ultraSensor = ult;
	}

	public void run() {
		while (mainLoopCondition) {
			distanceBasedOnUnltrasonic = ultraSensor.getDistance();
			lightValue = lightSensor.readValue();


			if (distanceBasedOnUnltrasonic <= noResultOnUltrasonic) {
				System.out.println("object gefunden");
				MainClass.objectgefunden = true;
				MainClass.distanceOnUltra = distanceBasedOnUnltrasonic;
				MainClass.changedVariables();
			} else {
				if (MainClass.objectgefunden = true) {
					MainClass.objectgefunden = false;
					MainClass.changedVariables();
				}
				MainClass.objectgefunden = false;
			}

			if (lightValue >= whiteTapeLightReading) {
				MainClass.linegefunden = true;
				MainClass.changedVariables();
			} else {
				MainClass.linegefunden = false;
			}
		}
	}
}
