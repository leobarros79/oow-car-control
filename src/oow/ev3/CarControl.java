package oow.ev3;

import javax.swing.JFrame;

import oow.gui.GuiFrame;

public class CarControl {

	public static void main(String[] args) {
		// set ip of EV3
		String ip = "";
		
		try {
	        ip = args[0];
	    } catch (Exception e) {
	    }
		
		JFrame guiFrame = new GuiFrame(ip);
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setLocationRelativeTo(null);
		guiFrame.setVisible(true);
		
	}

}
