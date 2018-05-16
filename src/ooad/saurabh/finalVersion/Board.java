package ooad.saurabh.finalVersion;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This panel displays a 160-by-160 checkerboard pattern with
 * a 2-pixel black border.  It is assumed that the size of the
 * canvas is set to exactly 164-by-164 pixels.  This class does
 * the work of letting the users play checkers, and it displays
 * the checkerboard.
 */

public class Board extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JButton endButton;     // Button that a player can use to end the game by resigning.
	private JButton newGameButton; // Button for starting a new game.
	private JButton nextButton;    // Button for simulating next move
	private JLabel  message;       // Label for displaying messages to the user.
	
	protected JButton getNextButton() {
		return nextButton;
	}

	protected JButton getEndButton() {
		return endButton;
	}

	protected JButton getNewGameButton() {
		return newGameButton;
	}

	protected JLabel getMessage() {
		return message;
	}
	   
    private CheckersData board; /* The data for the checkers board is kept here.
    							   This board is also responsible for generating   
    							   lists of legal moves. */
  
	private boolean gameInProgress; // Is a game currently in progress?
	
	/* The next three int variables are valid only when the game is in progress. */	
	private int currentPlayer;   /*Whose turn is it now?  The possible values
	                              are CheckersData.RED and CheckersData.BLACK. */
	
	private int selectedRow, selectedCol;  /* If the current player has selected a piece to
					                          move, these give the row and column containing that piece.  
					                          If no piece is yet selected, then selectedRow is -1. */
	
	private CheckersMove[] legalMoves;  // An array containing the legal moves for the current player.	                            	
	
	private ReadFromFile inputMoves; //Object of class ReadFromFile
	private String[] boardFromToValues; // An array containing Strings('fromRow-fromCol:toRow-toCol')
	private String[] movesFromFile; // An array containing the original inputMoves from given file.
	private int counter = 0; // Used as pointer to loop over array boardFromToValues.
	private int loopPtr = 0; // Used as pointer to acces strings inside a string array.
		
	/**
     * Constructor. Create the buttons and lable.  Listens for clicks on the buttons.
     * Create the board and start the first game.
     */
    protected Board() {
       setBackground(Color.BLACK);
       
       endButton = new JButton("End");
       endButton.addActionListener(this);
       
       newGameButton = new JButton("New Game");
       newGameButton.addActionListener(this);
       
       nextButton = new JButton("Next");
       nextButton.addActionListener(this); 
       
       message = new JLabel("",JLabel.CENTER);
       message.setFont(new  Font("Serif", Font.BOLD, 14));
       message.setForeground(Color.green);
       
       board = new CheckersData();     
       doNewGame();
    }//end of constructor
        
    /**
     * Start a new game
     */
    private void doNewGame() {
       if (gameInProgress == true) {
         // Extra Check for boolean gameInProgess. Ideally this loop should never get executed.
    	 // If it does get executed, then there is some problem with launching the game.  
          message.setText("Finish the current game first!");
          return;
       }
       board.setUpGame();// Set up the pieces.
       currentPlayer = CheckersData.RED;   // RED moves first.
       legalMoves    = board.getLegalMoves(CheckersData.RED);  // Get RED's legal moves.
       selectedRow   = -1;   // RED has not yet selected a piece to move.
       message.setText("Red:  Make your move.");
       gameInProgress = true;
       newGameButton.setEnabled(false);
       endButton.setEnabled(true);
       inputMoves        = new ReadFromFile();
       boardFromToValues = inputMoves.readInput();
       counter           = boardFromToValues.length;  //counter set for number of moves in the input file. 
       movesFromFile     = inputMoves.boardCoordinates(inputMoves.getMovesFromFile());
       loopPtr           = 0; // Used as pointer to acces strings inside a string array.
       repaint();
    }//end of method doNewGame
    
    /**
     * Respond to user's click on one of the three buttons.
     */
    @Override
    public void actionPerformed(ActionEvent evt) {
       Object src = evt.getSource();
       if (src == newGameButton) {
          doNewGame();
       }
       else if (src == endButton) {
          doEnd();
       }
       else if (src == nextButton) {
    	  doSimulateMove(); 
       }
    }//end of method actionPerformed()
    
    /**
     * Simulates the move from the input File when 'Next' button is clicked.
     */
    private void doSimulateMove() {
    	if(gameInProgress == false) {
    		message.setText("Click \"New Game\" to start a new game.");
    		return;
    	} else { 			
    		 int row, col;
    		 boolean moveValidation = false;   			
    		 if(counter > 0) {
    			String[] tempBoardFromToValues = boardFromToValues[loopPtr].split(":"); 
    			int fromRow = inputMoves.getCoordinates(tempBoardFromToValues[0], 0); 
    			int fromCol = inputMoves.getCoordinates(tempBoardFromToValues[0], 1); 
    			int toRow   = inputMoves.getCoordinates(tempBoardFromToValues[1], 0); 
    			int toCol   = inputMoves.getCoordinates(tempBoardFromToValues[1], 1); 
    			
    			//Checking wether the given move is valid in order to be simulated.
    			for (int i = 0; i < legalMoves.length; i++) {
		           if (legalMoves[i].getFromRow() == fromRow && legalMoves[i].getFromCol() == fromCol
		                 && legalMoves[i].getToRow() == toRow && legalMoves[i].getToCol() == toCol) {
		                 moveValidation = true;
		           } 
		        }
    			  
    			if(moveValidation) { 
        			for(String ptr : tempBoardFromToValues) { 
        				row = inputMoves.getCoordinates(ptr, 0);        				
        				col = inputMoves.getCoordinates(ptr, 1);        				       				
    		            if (col >= 0 && col < 8 && row >= 0 && row < 8) {
    		            	doClickSquare(row,col);
    		            }
        			}
    			} else { 
    				JOptionPane.showMessageDialog(null, movesFromFile[loopPtr] + " is an Invalid Move!!");
    			}
    			
     			loopPtr = loopPtr + 1;     			
     			counter = counter - 1;     			     			
    		 } else {
    			 //If you reach here, it means the moves in the input file are finished.
    			 JOptionPane.showMessageDialog(null, "End of Input Moves from File");
    			 System.exit(0);
    		 }
    	}
    }//end of method doSimulateMove()
 	        
    /**
     * This is called by doSimulateMove() when a player clicks on the
     * next button.
     */
   private void doClickSquare(int row, int col) {     
       /*
        * When called the first time, the below for-loop sets row and column
        * to selectedRow and selectedCol which will be assigned to fromRow and
        * fromCol values.   
        */
       for (int i = 0; i < legalMoves.length; i++) {
          if (legalMoves[i].getFromRow() == row && legalMoves[i].getFromCol() == col) {
             selectedRow = row;
             selectedCol = col;
             if (currentPlayer == CheckersData.RED)
                message.setText("RED:  Make your move.");
             else
                message.setText("BLACK:  Make your move.");
             repaint();
             return;
          }
       }
       /* If no piece has been selected to be moved, the user must first
        select a piece.  Show an error message and return. */     
       if (selectedRow < 0) {
          message.setText("Click the piece you want to move.");
          return;
       }
       
       /*
        * Calling doMakeMove method which will make the specified move, by passing it the 
        * valid fromRow,fromCol and toRow,toCol.  
        */
       for (int i = 0; i < legalMoves.length; i++) { //testing purposes
          if (legalMoves[i].getFromRow() == selectedRow && legalMoves[i].getFromCol() == selectedCol
                && legalMoves[i].getToRow() == row && legalMoves[i].getToCol() == col) {
             doMakeMove(legalMoves[i]);
             return;
          }
       }    
    }//end of method doClickSquare()
    
    /**
     * This is called when the current player has chosen the specified
     * move.  Make the move, and then either end or continue the game
     * appropriately.
     */
    private void doMakeMove(CheckersMove move) {     
       board.makeMove(move);      
       /* If the move was a jump, it's possible that the player has another
        jump.  Check for legal jumps starting from the square that the player
        just moved to.  If there are any, the player must jump.  The same
        player continues moving.
        */       
       if (move.isJump()) {
          legalMoves = board.getLegalJumpsFrom(currentPlayer,move.getToRow(),move.getToCol());
          if (legalMoves != null) {
             if (currentPlayer == CheckersData.RED) {
                message.setText("RED:  You must continue jumping.");
             }
             else {
                message.setText("BLACK:  You must continue jumping.");
             }
             //testing purposes
             selectedRow = move.getToRow();  // Since only one piece can be moved, select it.
             selectedCol = move.getToCol();
             repaint();
             return;
          }
       }     
       /* The current player's turn is ended, so change to the other player.
        Get that player's legal moves.  If the player has no legal moves,
        then the game ends. */    
       if (currentPlayer == CheckersData.RED) {
          currentPlayer = CheckersData.BLACK;
          legalMoves = board.getLegalMoves(currentPlayer);
          if (legalMoves == null) {
             gameOver("BLACK has no moves.  RED wins.");
          }
          else if (legalMoves[0].isJump()) {
             message.setText("BLACK:  Make your move.  You must jump.");
          }
          else {
             message.setText("BLACK:  Make your move.");
          }
       }
       else {
          currentPlayer = CheckersData.RED;
          legalMoves = board.getLegalMoves(currentPlayer);
          if (legalMoves == null) {
             gameOver("RED has no moves.  BLACK wins.");
          }
          else if (legalMoves[0].isJump()) {
             message.setText("RED:  Make your move.  You must jump.");
          }
          else {
             message.setText("RED:  Make your move.");
          }
       }     
       /* Set selectedRow = -1 to record that the player has not yet selected
        a piece to move. */      
       selectedRow = -1;     
       
       /* If all legal moves use the same piece, then select that piece automatically.*/
       if (legalMoves != null) {
          boolean sameStartSquare = true;
          for (int i = 1; i < legalMoves.length; i++) {
             if (legalMoves[i].getFromRow() != legalMoves[0].getFromRow()
                   || legalMoves[i].getFromCol() != legalMoves[0].getFromCol()) {
                sameStartSquare = false;
                break;
             }
          }
          if (sameStartSquare) {
             selectedRow = legalMoves[0].getFromRow();
             selectedCol = legalMoves[0].getFromCol();
          }
       }      
       /* Make sure the board is redrawn in its new state. */      
       repaint();       
    }//end of method doMakeMove();
    
    /**
     * Draw  checkerboard pattern in gray and lightGray.  Draw the
     * checkers.  If a game is in progress, hilight the legal moves.
     */
    //This method has to be public since we cannot reduce the visibility of an inherited method of JComponent 
    public void paintComponent(Graphics g) {      
       /* Draw a two-pixel black border around the edges of the canvas. */      
       g.setColor(Color.black);
       g.drawRect(0,0,getSize().width-1,getSize().height-1);
       g.drawRect(1,1,getSize().width-3,getSize().height-3);
       
       /* Draw the squares of the checkerboard and the checkers. */     
       for (int row = 0; row < 8; row++) {
          for (int col = 0; col < 8; col++) {
             if ( row % 2 == col % 2 )
                g.setColor(Color.LIGHT_GRAY);
             else
                g.setColor(Color.GRAY);
             g.fillRect(2 + col*20, 2 + row*20, 20, 20);
             switch (board.pieceAt(row,col)) {
             case CheckersData.RED:
                g.setColor(Color.RED);
                g.fillOval(4 + col*20, 4 + row*20, 15, 15);
                break;
             case CheckersData.BLACK:
                g.setColor(Color.BLACK);
                g.fillOval(4 + col*20, 4 + row*20, 15, 15);
                break;
             case CheckersData.RED_KING:
                g.setColor(Color.RED);
                g.fillOval(4 + col*20, 4 + row*20, 15, 15);
                g.setColor(Color.WHITE);
                g.drawString("K", 7 + col*20, 16 + row*20);
                break;
             case CheckersData.BLACK_KING:
                g.setColor(Color.BLACK);
                g.fillOval(4 + col*20, 4 + row*20, 15, 15);
                g.setColor(Color.WHITE);
                g.drawString("K", 7 + col*20, 16 + row*20);
                break;
             }
          }
       }       
       /* If a game is in progress, hilight the legal moves. Note that legalMoves
        is never null while a game is in progress. */          
       if (gameInProgress) {
             /* First, draw a 2-pixel cyan border around the pieces that can be moved. */
          g.setColor(Color.cyan);
          for (int i = 0; i < legalMoves.length; i++) {
             g.drawRect(2 + legalMoves[i].getFromCol()*20, 2 + legalMoves[i].getFromRow()*20, 19, 19);
             g.drawRect(3 + legalMoves[i].getFromCol()*20, 3 + legalMoves[i].getFromRow()*20, 17, 17);
          }
             /* If a piece is selected for moving (i.e. if selectedRow >= 0), then
              draw a 2-pixel white border around that piece and draw green borders 
              around each square that that piece can be moved to. */
          if (selectedRow >= 0) {
             g.setColor(Color.white);
             g.drawRect(2 + selectedCol*20, 2 + selectedRow*20, 19, 19);
             g.drawRect(3 + selectedCol*20, 3 + selectedRow*20, 17, 17);
             g.setColor(Color.green);
             for (int i = 0; i < legalMoves.length; i++) {
                if (legalMoves[i].getFromCol() == selectedCol && legalMoves[i].getFromRow() == selectedRow) {
                   g.drawRect(2 + legalMoves[i].getToCol()*20, 2 + legalMoves[i].getToRow()*20, 19, 19);
                   g.drawRect(3 + legalMoves[i].getToCol()*20, 3 + legalMoves[i].getToRow()*20, 17, 17);
                }
             }
          }
       }
    }//end of method paintComponent()

    /**
     * Current player resigns.  Game ends.  Opponent wins.
     */
    private void doEnd() {
       if (gameInProgress == false) {
          message.setText("There is no game in progress!");
          return;
       }
       if (currentPlayer == CheckersData.RED)
          gameOver("RED resigns.  BLACK wins.");
       else
          gameOver("BLACK resigns.  RED wins.");
    }//end of method doEnd()
    
    /**
     * The game ends.  The parameter, str, is displayed as a message
     * to the user.  The states of the buttons are adjusted so players
     * can start a new game.  This method is called when the game
     * ends at any point in this class.
     */
    private void gameOver(String str) {
       message.setText(str);
       newGameButton.setEnabled(true);
       endButton.setEnabled(false);
       gameInProgress = false;
    }//end of method gameOver()

}//end of class Board
