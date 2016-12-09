import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
 
import lejos.robotics.navigation.DifferentialPilot;
 
import lejos.nxt.TouchSensor;
import lejos.nxt.LightSensor;
import lejos.nxt.UltrasonicSensor;
 
public class MainClass {
 
    DifferentialPilot pilot;
    TouchSensor bump = new TouchSensor(SensorPort.S1);
    LightSensor light = new LightSensor(SensorPort.S2);
    UltrasonicSensor ultra = new UltrasonicSensor(SensorPort.S3);
   
    public void go(){
        pilot.travel(20000,true);
        while(pilot.isMoving()){
            //System.out.println("Light Value: "+light.readValue());
            int dis = ultra.getDistance();
            if(dis < 255){
                pilot.travel(dis,true);
                //TODO greifen, return to base
            }
            if(light.readValue() > 30){
                pilot.travel(-15);
                pilot.rotate(120.0);
                this.go();
            }  
            if (bump.isPressed()) pilot.stop();
        }
       
        System.out.println("Distance: "+pilot.getMovement().getDistanceTraveled());
       
        Button.waitForAnyPress();
    }
   
    public static void main(String[] args) {
               
        //LightSensor light = new LightSensor(SensorPort.S2);
        //System.out.println("Light Value: "+light.readNormalizedValue());
        //Button.waitForAnyPress();
       
        MainClass traveler = new MainClass();
        traveler.pilot = new DifferentialPilot(2.25f, 5.5f, Motor.A, Motor.B);
        traveler.go();
    }
   
    public int drehen(double grad){ 
    	grad = grad/5.0;
    	double deg;
    	if(grad > 0){
    		deg = 5.0;
    	}else{
    		deg = -5.0;
    	}
    	
    	for(int i = 0; i < grad; i++){
    		int dist = ultra.getDistance();
    		if(dist < 255) {
    			return dist;
    		}else{
    			pilot.rotate(deg);
    		}
    	}
    	return 255;
    }
}