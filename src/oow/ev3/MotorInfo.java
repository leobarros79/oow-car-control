package oow.ev3;

public class MotorInfo {
	MotorPort port;
	MotorSize size;
	int speed;
	int acceleration;
	int maxSpeed;

	public MotorInfo(MotorPort port,MotorSize size,int speed,int acceleration){
		this.port = port;
		this.size = size;
		this.speed = speed;
		this.acceleration = acceleration;
		
		maxSpeed = size == MotorSize.L ? 740 : 600;
	}
}
