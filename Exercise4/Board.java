/**
 * Provides methods and fields to create a board for the game, tic-tac-toe. 
 * Methods include building board, displaying the board, putting marker on spots,
 * and checking for a winner or a tie. 
 * 
 * @author Khoi
 * @version 1.0
 * @since October 1, 2020
 *
 */

public class Board implements Constants {
	
	/**
	 * The board to play game on
	 */
	private char theBoard[][];
	
	/**
	 * The count of how many marks are on board
	 */
	private int markCount;

	/**
	 * Constructs board, the board is a 3 x 3 square
	 */
	public Board() {
		markCount = 0;
		theBoard = new char[3][];
		for (int i = 0; i < 3; i++) {
			theBoard[i] = new char[3];
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		}
	}
	
	/**
	 * Returns the mark at a given spot
	 * @param row row number
	 * @param col column number
	 * @return char mark at the spot given
	 */
	public char getMark(int row, int col) {
		return theBoard[row][col];
	}
	
	/**
	 * Checks to see if board is filled with marks
	 * @return true if all 9 spots filled or when markCount = 9, otherwise false
	 */
	public boolean isFull() {
		return markCount == 9;
	}
	
	/**
	 * Checks to see if X won
	 * @return true if X won, otherwise false
	 */
	public boolean xWins() {
		if (checkWinner(LETTER_X) == 1)
			return true;
		else
			return false;
	}
	
	/**
	 * Checks to see if O won
	 * @return true if O won, otherwise false
	 */
	public boolean oWins() {
		if (checkWinner(LETTER_O) == 1)
			return true;
		else
			return false;
	}
	
	/**
	 * Display board visually to console
	 */
	public String display() {
		String restr = "";
		
		restr += displayColumnHeaders();
		restr += addHyphens();
		for (int row = 0; row < 3; row++) {
			restr += addSpaces();
			restr += "    row " + row + ' ';
			for (int col = 0; col < 3; col++)
				restr += "|  " + getMark(row, col) + "  ";
			restr += "|\n";
			restr += addSpaces();
			restr += addHyphens();
		}
		
		return restr;
	}
	
	/**
	 * Put mark into a given spot and increase markCount by one
	 * @param row row number of spot
	 * @param col column number of spot
	 * @param mark mark to put in spot
	 */
	public void addMark(int row, int col, char mark) {
		
		theBoard[row][col] = mark;
		markCount++;
	}
	
	/**
	 * Reset board by clearing all marks on board and reset markCount to 0
	 */
	public void clear() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		markCount = 0;
	}
	
	/**
	 * Check if there is a winner by check each row, column, and diagonal
	 * @param mark mark to check if winner
	 * @return 1 if there is a winner, otherwise returns 0
	 */
	int checkWinner(char mark) {
		int row, col;
		int result = 0;

		for (row = 0; result == 0 && row < 3; row++) {
			int row_result = 1;
			for (col = 0; row_result == 1 && col < 3; col++)
				if (theBoard[row][col] != mark)
					row_result = 0;
			if (row_result != 0)
				result = 1;
		}

		
		for (col = 0; result == 0 && col < 3; col++) {
			int col_result = 1;
			for (row = 0; col_result != 0 && row < 3; row++)
				if (theBoard[row][col] != mark)
					col_result = 0;
			if (col_result != 0)
				result = 1;
		}

		if (result == 0) {
			int diag1Result = 1;
			for (row = 0; diag1Result != 0 && row < 3; row++)
				if (theBoard[row][row] != mark)
					diag1Result = 0;
			if (diag1Result != 0)
				result = 1;
		}
		if (result == 0) {
			int diag2Result = 1;
			for (row = 0; diag2Result != 0 && row < 3; row++)
				if (theBoard[row][3 - 1 - row] != mark)
					diag2Result = 0;
			if (diag2Result != 0)
				result = 1;
		}
		return result;
	}
	
	/**
	 * Displays column headers visually to console
	 */
	String displayColumnHeaders() {
		String restr = "";
		restr +="          ";
		for (int j = 0; j < 3; j++)
			restr += "|col " + j;
		restr += "\n";
		
		return restr;
	}
	
	/**
	 * Displays division between spots visually to console
	 */
	String addHyphens() {
		String restr = "";
	
		restr += "          ";
		for (int j = 0; j < 3; j++)
			restr += "+-----";
		restr += "+\n";
		
		return restr;
	}
	
	/**
	 * Displays spots (spaces) visually to console
	 */
	String addSpaces() {
		String restr = "";
		
		restr += "          ";
		for (int j = 0; j < 3; j++)
			restr += "|     ";
		restr += "|\n";
		
		return restr;
	}
}
