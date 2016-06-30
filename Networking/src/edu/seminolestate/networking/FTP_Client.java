package edu.seminolestate.networking;

import javax.swing.JFrame;

/**
 * 
 * @author Moises Henriquez
 * @date July 3, 2016
 *
 */
public class FTP_Client {

	public static void main(String[] args) {
		//
		ClientGUI window = new ClientGUI("FTP Client", true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.runClient();
	}

}
