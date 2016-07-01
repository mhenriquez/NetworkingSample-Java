package edu.seminolestate.networking;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import javax.swing.*;

public class ServerGUI extends JFrame implements ActionListener
{
	//Networking Objects
	private ServerSocket server;
	private Socket connection;
	
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
	//JPanels
	private JPanel pnlNorth;
	private JPanel pnlSouth; 
	
	//JButtons
	private JButton btnDestination;
	private JButton btnHelp;
	private JButton btnExit;
	
	//JScrollPane
	private JScrollPane scrollPane;
	
	//JTextArea
	private JTextArea txtServerLog;
	
	//JFileChooser
	private JFileChooser fileChooser;
	
	//Constructor
	public ServerGUI(String title, boolean visible) {
		super(title);
		
		//Set size and lock it
		setSize(600, 350);
		setResizable(false);
		
		//Center window to screen
		setLocationRelativeTo(null);
		
		//Initialize UI
		initUserInterface();
		
		//Set default directory path
		Path path = getCurrentDirectoryPath();
		txtServerLog.append("Transferred files will be in: " + path.toString() + " (unless you change this destination).\n");
		
		//Render JFrame	
		setVisible(visible); //<== Must be applied after UI is initialized to render components
	}
	
	private void initUserInterface() {
		
		//Instantiate new border layout for JFrame
		setLayout(new BorderLayout());
		
		//Instantiate destination button
		btnDestination = new JButton("Destination");
		btnDestination.setPreferredSize(new Dimension(100, 40));
		btnDestination.addActionListener(this);
		
		//Instantiate help button
		btnHelp = new JButton("Help");
		btnHelp.setPreferredSize(new Dimension(100, 40));
		//btnHelp.addActionListener(this);
		
		//Instantiate exit button
		btnExit = new JButton("Exit");
		btnExit.setPreferredSize(new Dimension(100, 40));
		btnExit.addActionListener(this);
		
		//Instantiate scroll pane and text area
		txtServerLog = new JTextArea("Waiting for connection.\n", 15, 48);
		scrollPane = new JScrollPane(txtServerLog, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//Instantiate and add components to north panel
		pnlNorth = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		pnlNorth.add(scrollPane);
		
		//Instantiate and add components to south panel
		pnlSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		pnlSouth.add(btnDestination);
		pnlSouth.add(btnHelp);
		pnlSouth.add(btnExit);
		
		//Add JPanel elements to JFrame
		add(pnlNorth, BorderLayout.NORTH);
		add(pnlSouth, BorderLayout.SOUTH);
		
	}
	
	public void runServer() {
		//
		try {
			//Step 1: Create a ServerSocket object
			server = new ServerSocket(8000); //Set server to use port 21
			
			while(true) {
				try {
					//Step 2: Listen for client requests
					connection = server.accept();
					//Log connection
					txtServerLog.append("Connected to client: " + connection.getInetAddress().getHostName() + "\n");
					
					//Step 3: Get the input stream object (write)
					output = new ObjectOutputStream(connection.getOutputStream()); //Set up output stream for objects
					output.flush();
					
					//Step 4: Get the output stream object (read)
					input = new ObjectInputStream(connection.getInputStream()); //Set up input stream for objects
					
					//Step 5: Process connection with client
					output.writeObject("Server>>> Connection successful");
					output.flush();
					
				}
				catch (EOFException ex) {
					displayMessage("");
				}
				finally {
					//Step 6: Close the socket connection
					closeConnection();
				}
			}
			
		} 
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private void closeConnection() {
		try {
			output.close();
			input.close();
			connection.close();
		} 
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private void displayMessage(final String message){
		SwingUtilities.invokeLater(
			new Runnable() {
				public void run(){
					
				}
			}
		);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		//If exit button is clicked
		if(source == btnExit) {
			//Close the connection
			//closeConnection(); //gives error
			//
			System.exit(0);
		}
		
		//If help button is clicked
		if(source == btnHelp) {
			
		}
		
		//If destination button is clicked
		if(source == btnDestination) {
			try {
				analyzePath();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public void analyzePath() throws IOException {
		//Get path to user-selected directory
		Path path = getSelectedDirectoryPath();
		
		//If path exists, display info
		if(path != null && Files.exists(path)) {
			//Gather directory information
			txtServerLog.append("Files will now be transferred to: " + path.toString() + ".\n");
		}
	}
	
	private Path getCurrentDirectoryPath() {
		//Instantiate new file chooser
		fileChooser = new JFileChooser();
		
		//Return path representing the default directory
		return fileChooser.getCurrentDirectory().toPath();
	}

	private Path getSelectedDirectoryPath() {
		//Instantiate new file chooser
		fileChooser = new JFileChooser();
		
		//Configure dialog allowing selection of a directory only
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		//Open dialog window showing directory
		int result = fileChooser.showOpenDialog(this);
		
		//If user clicks cancel button on dialog window
		if(result == JFileChooser.CANCEL_OPTION) {
			return null;
		}
		
		//Return path representing the selected directory
		return fileChooser.getSelectedFile().toPath();
	}
}
