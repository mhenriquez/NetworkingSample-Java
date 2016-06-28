package edu.seminolestate.networking;

import java.awt.event.*;
import javax.swing.*;

public class ClientGUI extends JFrame implements ActionListener
{
	//Instance Variables
	private String x;
	
	//JPanels
	private JPanel pnlNorth;
	private JPanel pnlSouth;
	
	//JButtons
	private JButton btnButton;
	
	public ClientGUI(String title, boolean visible){
		super(title);
		
		//Set size and lock it
		setSize(600, 370);
		setResizable(false);
		
		//Center window to screen
		setLocationRelativeTo(null);
		
		//Initialize UI
		initUserInterface();
		
		//Render JFrame	
		setVisible(visible); //<== Must be applied after UI is initialized to render components
	}
	
	private void initUserInterface(){
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
