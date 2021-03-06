package ooad.saurabh.finalVersion;

/**
 * A CheckersMove object represents a move in the game of Checkers.
 * It holds the row and column of the piece that is to be moved
 * and the row and column of the square to which it is to be moved.
 * (This class makes no guarantee that the move is legal.)   
 */
class CheckersMove {
   private int fromRow, fromCol;  // Position of piece to be moved.
   private int toRow, toCol;      // Square it is to move to.
   
   public int getFromRow() {
	   return fromRow;
   }
	
	public int getFromCol() {
		return fromCol;
	}
		
	public int getToRow() {
		return toRow;
	}
		
	public int getToCol() {
		return toCol;
	}
	
	protected CheckersMove(int fromRow, int fromCol, int toRow, int toCol) {
	// Constructor :Sets the values of the instance variables.
      this.fromRow = fromRow;
      this.fromCol = fromCol;
      this.toRow   = toRow;
      this.toCol   = toCol;
    }//end of constructor
   
   protected boolean isJump() {
      // Test whether this move is a jump.  It is assumed that
      // the move is legal. In a jump, the piece moves two
      // rows. (In a regular move, it only moves one row.)
      return (fromRow - toRow == 2 || fromRow - toRow == -2); 	   
   }//end of method isJump()
   
}// end class CheckersMove.