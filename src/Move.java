import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class Move extends Thread {

	DifferentialPilot controllUnit;

	boolean greifarmOffen;

	public Move(DifferentialPilot cont) {
		controllUnit = cont;
	}

	public void run() {
		while (true) {
			if (MainClass.emergencyBreak) {
				controllUnit.quickStop();
				System.exit(0);
			}

			if (MainClass.objectgefunden) {
				controllUnit.travel(MainClass.distanceOnUltra / MainClass.diameterOfWheel, false);

				// todo: ausrichten
				schliesseGreifarm();
			} else if (MainClass.linegefunden) {
				controllUnit.travel(-5);
				controllUnit.rotate(120);
			} else {
				controllUnit.travel(15);
				controllUnit.rotate(30);
				controllUnit.rotate(-60);
				controllUnit.rotate(30);
			}
		}
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
}
