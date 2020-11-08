import java.io.*;

/**
 * Starts tic-tac-toe game by creating the board, referee, and players.
 * 
 * @author Khoi
 * @version 1.0
 * @since October 1, 2020
 *
 */

public class Game implements Constants {
	
	/**
	 * The board used to play
	 */
	private Board theBoard;
	
	/**
	 * The referee that will start the game
	 */
	private Referee theRef;
	
	/**
	 * Constructs a new game with a new board and referee
	 */
    public Game( ) {
        theBoard  = new Board();
        theRef = new Referee();
        theRef.setBoard(theBoard);
	}
    
   /**
    * Start the game
    * @throws IOException
    */
    public void startGame() throws IOException {
    	theRef.runTheGame();
    }
	
    /**
     * Set players for game
     * @param name
     * @param mark
     */
	public void setPlayer(String name, char mark) {
		if(mark == LETTER_X) {
			Player xPlayer = new Player(name, LETTER_X);
			theRef.setxPlayer(xPlayer);
			xPlayer.setBoard(theBoard);
		}
		else {
			Player oPlayer = new Player(name, LETTER_O);
			theRef.setoPlayer(oPlayer);
			oPlayer.setBoard(theBoard);
		}
	}
	/**
	 * Get the game board
	 * @return game board
	 */
	public Board getBoard() {
		return theBoard;
	}
	/**
	 * Get the referee of the game
	 * @return referee game
	 */
	public Referee getRef() {
		return theRef;
	}
	
	/**
	 * Get who's turn from referee
	 * @return String array that is sent to each individual player
	 */
	public String[] getTurn() {
		String[] restr = theRef.getMove().clone();
		return restr;
	}
	
	/**
	 * Innitalize a move, make sure move is valid
	 * @param c1Response Client 1 or X player move
	 * @param c2Response Client 2 or O player move
	 */
	public void makeTurn(String c1Response, String c2Response) {
		String[] c1Arr = c1Response.split(" ");
		String[] c2Arr = c2Response.split(" ");
		int row, col;
		
		if(isNumber(c1Arr[0]) && isNumber(c1Arr[1])) {
			row = Integer.parseInt(c1Arr[0]);
			col = Integer.parseInt(c1Arr[1]);
			theRef.makeMove(row, col, LETTER_X);
		}
		else if(isNumber(c2Arr[0]) && isNumber(c2Arr[1])) {
			row = Integer.parseInt(c2Arr[0]);
			col = Integer.parseInt(c2Arr[1]);
			theRef.makeMove(row, col, LETTER_O);
		}
	}
	
	/**
	 * Checks to see if a string can be converted to an integer
	 * @param str string to check
	 * @return true if string can convert to integer, otherwise false
	 */
	private static boolean isNumber(String str) {
	    if (str == null) {
	        return false;
	    }
	    try {
	        @SuppressWarnings("unused")
			double d = Integer.parseInt(str);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	/**
	 * Checking whether move was valid
	 * @param c1Response Client 1 or X player move
	 * @param c2Response Client 2 or O player move
	 * @return True if response is a row col number pair, and if spot is available, false otherwise
	 */
	public boolean checkMove(String c1Response, String c2Response) {
		String[] c1Arr = c1Response.split(" ");
		String[] c2Arr = c2Response.split(" ");
		int row, col;
		
		if(isNumber(c1Arr[0]) && isNumber(c1Arr[1])) {
			row = Integer.parseInt(c1Arr[0]);
			col = Integer.parseInt(c1Arr[1]);
			return theRef.verifyMove(row, col);
		}
		else if(isNumber(c2Arr[0]) && isNumber(c2Arr[1])) {
			row = Integer.parseInt(c2Arr[0]);
			col = Integer.parseInt(c2Arr[1]);
			return theRef.verifyMove(row, col);
		}
		else		
			return false;
	}
}

