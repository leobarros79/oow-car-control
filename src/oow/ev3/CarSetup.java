package oow.ev3;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;

public class CarSetup {
	RemoteEV3 ev3 = null;
	
	HashMap<MotorPort, RMIRegulatedMotor> motorInstanceTable = new HashMap<MotorPort, RMIRegulatedMotor>();
	HashMap<MotorPort, MotorInfo> motorSetupList = new HashMap<MotorPort, MotorInfo>();
	
	public boolean connected(){
		return ev3 != null;
	}
	
	public void accelerate(MotorPort port,boolean forwards){
		if(connected()){
			try {
				if(forwards){
					motorInstanceTable.get(port).forward();
				}else{
					motorInstanceTable.get(port).backward();
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setMoveSpeed(MotorPort port, int speed){
		if(connected()){
			int ev3Speed = 0;
			
			for(Map.Entry<MotorPort, MotorInfo> entry : motorSetupList.entrySet()){
				if(entry.getValue().port == port){
					ev3Speed = (int) (((float)speed * .01) * entry.getValue().maxSpeed);
				}
			}
			
			try {
				motorInstanceTable.get(port).setSpeed(ev3Speed);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setAcceleration(MotorPort port, int acceleration){
		if(connected()){
			try {
				motorInstanceTable.get(port).setAcceleration(acceleration);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stopAccelerate(MotorPort port){
		if(connected()){
			try {
				System.out.println("stopping");
				motorInstanceTable.get(port).flt(true);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
		
	public boolean connectAndSetupMotors(String ip){
		boolean retVal = true;
		
		if(connect(ip)){
			setupMotors();
		}else{
			retVal = false;
		}
		
		return retVal;
	}
	
	public void closeConnection(){
		for(Map.Entry<MotorPort, RMIRegulatedMotor> entry : motorInstanceTable.entrySet()){
			try {
				if(entry.getValue() != null) entry.getValue().close();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean connect(String ip){
		boolean retVal = true;
		
		try{
			ev3 = new RemoteEV3(ip);
		}catch(Exception e){
			e.printStackTrace();
			retVal = false;
		}
		
		return retVal;
	}
	
	private void setupMotors(){
		for(Map.Entry<MotorPort, MotorInfo> entry : motorSetupList.entrySet()){
			motorInstanceTable.put(entry.getValue().port, ev3.createRegulatedMotor(entry.getValue().port.name(),entry.getValue().size.name().charAt(0)));
		}
		
		for(Map.Entry<MotorPort, MotorInfo> entry : motorSetupList.entrySet()){
			setAcceleration(entry.getValue().port,entry.getValue().acceleration);
			setMoveSpeed(entry.getValue().port, entry.getValue().speed);
		}
	}
	
	public void addMotor(MotorInfo motorInfo){
		motorSetupList.put(motorInfo.port, motorInfo);
	}
	
}
