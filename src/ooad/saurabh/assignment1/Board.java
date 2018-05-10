package ooad.saurabh.assignment1;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Board {
	private static final int rows = 8;
	private static final int columns = 8;
	private static final Color whiteColor = Color.WHITE;
	private static final Color blackColor = Color.BLACK;
	
	//Constructor
	public Board() {
		initializeBoard();
	}
	
	//method that initializes the board
	public static void initializeBoard() {
		JFrame checkersBoard = new JFrame();
		checkersBoard.setSize(720, 720);
		checkersBoard.setTitle("Checkers Board");
		checkersBoard.setBackground(Color.CYAN);//new line added
		checkersBoard.setLocationRelativeTo(null);//new line added
		
		Container pane = checkersBoard.getContentPane();
		pane.setLayout(new GridLayout(rows, columns));
		
		Color temp;
		
		for(int i = 0; i < rows; i++) {
			if(i % 2 == 0) {
				temp = whiteColor;
			} else {
				temp = blackColor;
			}
			for(int j = 0; j < columns; j++) {
				JPanel panel = new JPanel();
				panel.setBackground(temp);
				if(temp.equals(whiteColor)) {
					temp = blackColor;
				} else {
					temp = whiteColor;
				}
				pane.add(panel);
			}
		}
		//checkersBoard.pack();
		checkersBoard.setVisible(true);
		checkersBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}//end of class board
