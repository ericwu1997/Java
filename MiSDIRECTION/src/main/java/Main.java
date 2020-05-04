package main.java;

import main.java.control.*;
import main.java.frame.GameFrame;
import main.java.gui.GUIAccess;

/**
 * 
 */

/**
 * @author Mike
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		/*
		 * DisplayBoard b = new DisplayBoard();
		 * 
		 * b.printBoard();
		 * 
		 * b.getTileAt(4, 3).reveal();
		 * 
		 * b.getTileAt(6, 12).reveal();
		 * 
		 * b.getTileAt(1, 8).reveal();
		 * 
		 * b.getTileAt(15, 4).reveal();
		 * 
		 * b.getTileAt(7, 15).reveal();
		 * 
		 * b.getTileAt(1, 3).reveal();
		 * 
		 * 
		 * System.out.println("\nrevealing some tiles\n");
		 * 
		 * b.printBoard();
		 * 
		 * System.out.println("\nrevealing ALL tiles\n"); b.revealAll();
		 * 
		 * b.printBoard();
		 * 
		 * System.out.println();
		 */

		GameController gc = new GameController();
		GameFrame gf = gc.getFrame();
		GUIAccess access = new GUIAccess(gc, gf);
		gc.getBoard().printBoard();
	}

}
