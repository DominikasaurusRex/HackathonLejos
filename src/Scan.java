import lejos.nxt.LightSensor;
import lejos.nxt.UltrasonicSensor;

public class Scan extends Thread {

	//TouchSensor emergencyButton;
	LightSensor lightSensor;
	UltrasonicSensor ultraSensor;

	int distanceBasedOnUnltrasonic;
	int lightValue;
	boolean buttonPress;
	boolean mainLoopCondition = true;

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
			System.out.println(lightValue);

			if (distanceBasedOnUnltrasonic <= noResultOnUltrasonic) {
				System.out.println("object gefunden");
				MainClass.objectgefunden = true;
				MainClass.distanceOnUltra = distanceBasedOnUnltrasonic;
				MainClass.changedVariables = true;
			} else {
				if (MainClass.objectgefunden = true) {
					MainClass.objectgefunden = false;
					MainClass.changedVariables = true;
				}
				MainClass.objectgefunden = false;
			}

			if (lightValue >= whiteTapeLightReading) {
				System.out.println("tape gefunden");
				MainClass.linegefunden = true;
				MainClass.changedVariables = true;
			} else {
				MainClass.linegefunden = false;
			}
		}
	}
}
