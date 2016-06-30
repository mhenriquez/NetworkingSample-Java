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
	private JTextArea txtLog;
	
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
		//Instantiate new border layout for JFrame
		setLayout(new BorderLayout());
		
		//Instantiate connect button
		btnConnect = new JButton("Connect");
		btnConnect.setPreferredSize(new Dimension(100, 40));
		//btnConnect.addActionListener(this);
		
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
		txtLog = new JTextArea("", 15, 48);
		scrollPane = new JScrollPane(txtLog);
		
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
		//
		try {
			//Step 1: Create a socket to make connection to server
			client = new Socket(InetAddress.getByName("127.0.0.1"), 8000);
			
			//Step 2: Get the Socket's I/O streams
			output = new ObjectOutputStream(client.getOutputStream()); //Set up output stream for objects
			output.flush();
			input = new ObjectInputStream(client.getInputStream()); //Set up input stream for objects
			
			//Step 3: Process connection with server
			
			try {
				txtLog.append((String) input.readObject());
			}
			catch (ClassNotFoundException ex) {
				ex.printStackTrace();
			}
			
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
