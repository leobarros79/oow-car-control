package oow.ev3;



public  class CarAction extends MovementAction{

	public CarAction(CarSetup ev3, boolean clockwiseDirection, MotorInfo motorInfo) {
		super(ev3,clockwiseDirection,motorInfo);
	}

	@Override
	public void downAction() {
		carSetup.accelerate(motorInfo.port, clockwiseDirection);
	}

	@Override
	public void upAction() {
		carSetup.stopAccelerate(motorInfo.port);
	}

	@Override
	public void changeSpeed(int newSpeed) {
		carSetup.setMoveSpeed(motorInfo.port, newSpeed);
		if(isCurrentlyPressed){
			downAction();
		}
	}

}
