package edu.seminolestate.networking;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.*;

public class ClientGUI extends JFrame implements ActionListener
{
	//Networking Objects
	private Socket client;
	
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
	//JPanels
	private JPanel pnlNorth;
	private JPanel pnlSouth;
	
	//JButtons
	private JButton btnConnect;
	private JButton btnDisconnect;
	private JButton btnSelectFile;
	private JButton btnTransferFile;
	private JButton btnHelp;
	private JButton btnExit;
	
	//JScrollPane
	private JScrollPane scrollPane;
	
	//JTextArea
	private JTextArea txtClientLog;
	
	public ClientGUI(String title, boolean visible){
		super(title);
		
		//Set size and lock it
		setSize(600, 350);
		setResizable(false);
		
		//Center window to screen
		setLocationRelativeTo(null);
		
		//Initialize UI
		initUserInterface();
		
		//Render JFrame	
		setVisible(visible); //<== Must be applied after UI is initialized to render components
	}
	
	private void initUserInterface(){
		//Instantiate new border layout for JFrame
		setLayout(new BorderLayout());
		
		//Instantiate connect button
		btnConnect = new JButton("Connect");
		btnConnect.setPreferredSize(new Dimension(100, 40));
		btnConnect.addActionListener(this);
		
		//Instantiate disconnect button
		btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setPreferredSize(new Dimension(100, 40));
		//btnDisconnect.addActionListener(this);
		
		//Instantiate help button
		btnHelp = new JButton("Help");
		btnHelp.setPreferredSize(new Dimension(100, 40));
		//btnHelp.addActionListener(this);
		
		//Instantiate exit button
		btnExit = new JButton("Exit");
		btnExit.setPreferredSize(new Dimension(100, 40));
		//btnExit.addActionListener(this);
		
		//Instantiate scroll pane and text area
		txtClientLog = new JTextArea("", 15, 48);
		scrollPane = new JScrollPane(txtClientLog);
		
		//Instantiate and add components to north panel
		pnlNorth = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		pnlNorth.add(scrollPane);
		
		//Instantiate and add components to south panel
		pnlSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		pnlSouth.add(btnConnect);
		pnlSouth.add(btnDisconnect);
		
		//Add JPanel elements to JFrame
		add(pnlNorth, BorderLayout.NORTH);
		add(pnlSouth, BorderLayout.SOUTH);
	}
	
	public void runClient(){
		//Try connecting to server
		try {
			//Step 1: Create a socket object
			client = new Socket(InetAddress.getByName("127.0.0.1"), 8000);
			
			//Step 2: Get the output stream object (write)
			output = new ObjectOutputStream(client.getOutputStream());
			
			//Step 3: Get the input stream object (read)
			input = new ObjectInputStream(client.getInputStream());
			
			//Read server message
			try {
				txtClientLog.append((String) input.readObject() + "\n");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		catch (IOException ex) {
			displayMessage("Cannot connect to server: " + ex.getMessage() + "\n");
		}
	}
	
	private void displayMessage(final String message){
		SwingUtilities.invokeLater(
			new Runnable() {
				public void run(){
					txtClientLog.append(message);
				}
			}
		);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//window.runClient();
		Object source = e.getSource();
		
		//If exit button is clicked
		if(source == btnConnect) {
			runClient();
		}
		
	}
}
