package edu.seminolestate.networking;

import javax.swing.*;

/**
 * 
 * @author Moises Henriquez
 * @date July 3, 2016
 *
 */
public class Server {

	public static void main(String[] args) {
		//
		ServerGUI window = new ServerGUI("FTP Server", true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
