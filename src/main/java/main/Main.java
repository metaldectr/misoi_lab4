package main;

import gui.MainFrame;

import javax.swing.*;

/**
 * Created by romario on 12/16/14.
 */
public class Main {
	public static void main (String [] args) {
		System.out.println("Lab4");
		MainFrame mainFrame = new MainFrame();
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
		System.out.println("end lab4");
	}
}
