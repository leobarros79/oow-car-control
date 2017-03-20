package oow.gui;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.Action;
import javax.swing.JFrame;

import oow.ev3.CarAction;
import oow.ev3.CarSetup;
import oow.ev3.MotorInfo;
import oow.ev3.MotorPort;
import oow.ev3.MotorSize;


public class GuiFrame extends JFrame {

	private HashMap<Integer,Action> keyActions;
	protected CarSetup carSetup;

	public GuiFrame(String ip) {
		setupFrame(ip);
		setupEv3();
		setupHotkeys();
	}

	private void setupEv3() {
		carSetup = new CarSetup();
	}

	private void setupHotkeys() {
		setupKeyHashMap_tracked_vehicle();

		KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		kfm.addKeyEventDispatcher( new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				int key = e.getKeyCode();
				if (keyActions.containsKey(key)){
					keyActions.get(key).actionPerformed(new ActionEvent(e.getSource(), e.getID(), null ));
					return true;
				}else if (key == 0){
					return false;
				}else{
					return false;
				}
			}
		});
	}

	private void setupKeyHashMap_tracked_vehicle() {
		keyActions = new HashMap<Integer, Action>();

		MotorInfo ma = new MotorInfo(MotorPort.B, MotorSize.L, 100, 300);
		MotorInfo mb = new MotorInfo(MotorPort.A, MotorSize.L, 100, 300);

		keyActions.put(KeyEvent.VK_W, new CarAction(carSetup, false, ma));
		keyActions.put(KeyEvent.VK_E, new CarAction(carSetup, false, mb));

		keyActions.put(KeyEvent.VK_S, new CarAction(carSetup, true, ma));
		keyActions.put(KeyEvent.VK_D, new CarAction(carSetup, true, mb));
	}

	private void setupFrame(String ip) {

		setResizable(false);
		this.setSize(232, 210);
		this.setTitle("OOW - Remote Control Demo");
		this.setContentPane(new GuiPanel(ip));


		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					System.out.println("closing");
					carSetup.closeConnection();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}
}