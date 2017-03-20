package oow.ev3;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;


public abstract class MovementAction extends AbstractAction{
	protected CarSetup carSetup;
	protected boolean clockwiseDirection;
	protected MotorInfo motorInfo;
	
	protected boolean isCurrentlyPressed = false;
	
	public MovementAction(CarSetup carSetup, boolean clockwiseDirection, MotorInfo motorInfo){
		this.carSetup = carSetup;
		this.clockwiseDirection = clockwiseDirection;
		this.motorInfo = motorInfo;
		
		carSetup.addMotor(motorInfo);
	}
	
	public abstract void downAction();	
	public abstract void upAction();
	public void changeSpeed(int newSpeed){};
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getID() == KeyEvent.KEY_PRESSED){
			if(!isCurrentlyPressed){
				isCurrentlyPressed = true;
				downAction();
			}
		}else if(e.getID() == KeyEvent.KEY_RELEASED){
			isCurrentlyPressed = false;
			upAction();
		}
	}
}
