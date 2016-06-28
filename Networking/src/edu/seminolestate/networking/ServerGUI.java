package edu.seminolestate.networking;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import javax.swing.*;

public class ServerGUI extends JFrame implements ActionListener
{
	//Instance Variables
	private String x;
	
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
	public ServerGUI(String title, boolean visible){
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
		txtServerLog.append("Transferred files will be in " + path.toString() + " unless you change this destination.\n");
		
		//Render JFrame	
		setVisible(visible); //<== Must be applied after UI is initialized to render components
	}
	
	private void initUserInterface(){
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
		txtServerLog = new JTextArea("Server waiting for connections.\n", 15, 48);
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
	
	private void createServerSocket(){
		
		//Step 1: Create a ServerSocket
		try {
			ServerSocket server = new ServerSocket(80, 3);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		//Step 2: Wait for connection
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		//If exit button is clicked
		if(source == btnExit){
			System.exit(0);
		}
		
		//If help button is clicked
		if(source == btnHelp){
			
		}
		
		//If destination button is clicked
		if(source == btnDestination){
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
		if(path != null && Files.exists(path)){
			//Gather directory information
			txtServerLog.append("Transferred files will now be in " + path.toString() + ".\n");
		}
	}
	
	private Path getCurrentDirectoryPath(){
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
		if(result == JFileChooser.CANCEL_OPTION){
			return null;
		}
		
		//Return path representing the selected directory
		return fileChooser.getSelectedFile().toPath();
	}
}
