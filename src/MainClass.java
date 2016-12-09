import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class MainClass {
	
	static Scan scanthread;
	static Move movethread;
	static DifferentialPilot controllUnit;
	static boolean changedVariables = false;
	
	static boolean emergencyBreak = false;
	static boolean objectgefunden = false;
	static boolean linegefunden = false;
	
	static float distanceOnUltra = 0;
	
	//static TouchSensor emergencyButton = new TouchSensor(SensorPort.S1);
	static LightSensor lightSensor = new LightSensor(SensorPort.S2);
	static UltrasonicSensor ultraSensor = new UltrasonicSensor(SensorPort.S3);

	int distanceBasedOnUnltrasonic;
	boolean mainLoopCondition;
	boolean greifarmOffen = true;

	int noResultOnUltrasonic = 255;
	int whiteTapeLightReading = 30;
	static float diameterOfWheel = 2.25f;

	public static void main(String[] args) {
		controllUnit = new DifferentialPilot(2.25f, 5.5f, Motor.A, Motor.B);
		scanthread = new Scan(lightSensor, ultraSensor);
		movethread = new Move(controllUnit);
	
		
		scanthread.run();
		movethread.run();
	}
	
	public static void changedVariables(){
		System.out.println("ChangedVariables!");
		
		movethread.interrupt();
		movethread = new Move(controllUnit);
		movethread.run();
	}
}