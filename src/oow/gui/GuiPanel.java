package oow.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import oow.ev3.CarSetup;
import oow.ev3.MotorPort;

public class GuiPanel extends JPanel {
	
	private JButton fowardButton;
	private JButton leftButton;
	private JButton rightButton;
	private JButton backwardButton;
	private JButton btnConnect;
	private JButton lightButton;
	private JSlider powerSlider;
	private JTextArea ipTextBox;
	
	public CarSetup carSetup;
	
	public GuiPanel(String ip) {
		
		btnConnect = new JButton("Connect");
		
		powerSlider = new JSlider();
		
		ipTextBox = new JTextArea();
		fowardButton = new JButton("^");
		leftButton = new JButton("<");
		rightButton = new JButton(">");
		backwardButton = new JButton("v");
		lightButton = new JButton("Light");
		lightButton.setBackground(Color.red);
		lightButton.setEnabled(false);
		
		SetupPanel(ip);
	}
	
	public GuiPanel getOuter() {
	    return GuiPanel.this;
	}
	
	private class connectButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			GuiFrame outerFrame = (GuiFrame) SwingUtilities.getWindowAncestor(getOuter());
			
			if(outerFrame.carSetup.connectAndSetupMotors(ipTextBox.getText())){
				btnConnect.setBackground(Color.green);
				lightButton.setEnabled(true);
			}else{
				btnConnect.setBackground(Color.red);
				lightButton.setEnabled(false);
			}
		}
	}

	private class lightButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(lightButton.getBackground().equals(Color.red)){
				lightButton.setBackground(Color.green);
			}else{
				lightButton.setBackground(Color.red);
			}
		}
	}
	
	private class SliderChangeListener implements ChangeListener{
		long timeLastEventFired = System.currentTimeMillis();
		long timeBetweenCalls = 200;
		GuiFrame outerFrame;
		
		@Override
		public void stateChanged(ChangeEvent e) {
			if(outerFrame == null){
				outerFrame = (GuiFrame) SwingUtilities.getWindowAncestor(getOuter());
			}
			
			long currentTime = System.currentTimeMillis();

			if(currentTime - timeLastEventFired > timeBetweenCalls){
				timeLastEventFired = currentTime;
				
				if(outerFrame.carSetup.connected()){
					outerFrame.carSetup.setMoveSpeed(MotorPort.A,powerSlider.getValue());
					outerFrame.carSetup.setMoveSpeed(MotorPort.B,powerSlider.getValue());
				}
			}
		}
	}
	
	private void SetupPanel(String ip){
		ipTextBox.setText(ip);
		
		setLayout(null);
		setBackground(Color.DARK_GRAY);
		
		add(leftButton);
		add(fowardButton);
		add(rightButton);
		add(backwardButton);
		add(btnConnect);
		add(ipTextBox);
		add(powerSlider);
		add(lightButton);
		
		leftButton.setBounds(10, 151, 43, 23);
		fowardButton.setBounds(63, 117, 43, 23);
		backwardButton.setBounds(63, 151, 43, 23);
		rightButton.setBounds(116, 151, 43, 23);
		btnConnect.setBounds(92, 41, 89, 23);
		ipTextBox.setBounds(10, 11, 171, 22);
		powerSlider.setBounds(191, 11, 29, 163);
		lightButton.setBounds(10, 41, 81, 23);
		
		powerSlider.setOrientation(SwingConstants.VERTICAL);
		
		btnConnect.addActionListener(new connectButtonListener());
		powerSlider.addChangeListener(new SliderChangeListener());
		lightButton.addActionListener(new lightButtonListener());
	}
}
