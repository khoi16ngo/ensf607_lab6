

import java.io.IOException;

/**
 * Provides fields and methods for a referee in game, tic-tac-toe.
 * 
 * @author Khoi
 * @version 1.0
 * @since October 1, 2020
 *
 */

public class Referee implements Constants{
	/**
	 * The X player
	 */
	private Player xPlayer;
	
	/**
	 * The O player
	 */
	private Player oPlayer;
	
	/**
	 * The board ref will operate
	 */
	private Board board;
	
	/**
	 * Keep track of who's turn it is
	 */
	private int count;
	
	/**
	 * Referee will start the game.
	 * Start the game count to 1, keeps track of who's turn it is
	 * Odd count number is X player turn, even count number is O player turn
	 * @throws IOException
	 */
	public void runTheGame() throws IOException {		
		count = 1;
	}
	
	/**
	 * Set ref's board
	 * @param board board to ref
	 */
	public void setBoard(Board board) {
		this.board = board;
	}
	
	/**
	 * Set O player
	 * @param oPlayer the O player
	 */
	public void setoPlayer(Player oPlayer) {
		this.oPlayer = oPlayer;
	}
	
	/**
	 * Set X player
	 * @param xPlayer the x Player
	 */
	public void setxPlayer(Player xPlayer) {
		this.xPlayer = xPlayer;
	}
	
	/**
	 * Get whos turn it is
	 * @return String array to send to the individual users
	 */
	public String[] getMove() {
		String[] restr = checkWinner().clone();
		
		if(restr[0].contains("NONE")) {
			if(count%2 == 1) {
				restr[0] = xPlayer + " turn!!";
				restr[1] = xPlayer + " turn, please wait!!";
			}
			else {
				restr[0] = oPlayer + " turn, please wait!!";
				restr[1] = oPlayer + " turn!!";
			}
		}
					
		return restr;
	}
	
	/**
	 * check to see if there is a winner
	 * @return String array to send to the individual users
	 */
	private String[] checkWinner() {
		String[] restr = new String[2];
	
		if(board.oWins()) {
			restr[0] = "Game over. The winner is" + oPlayer;
			restr[1] = "Game over. The winner is" + oPlayer;
		}
		else if(board.xWins()) {
			restr[0] = "Game over. The winner is" + xPlayer;
			restr[1] = "Game over. The winner is" + xPlayer;
		}
		else if(board.isFull()) {
			restr[0] = "Game over. There is no winner which means it is a tie";
			restr[1] = "Game over. There is no winner which means it is a tie";
		}
		else {
			restr[0] = "NONE";
			restr[1] = "NONE";
		}
		
		return restr;
	}
	
	/**
	 * Verify if move give was valid
	 * @param row Row number
	 * @param col Column number
	 * @return True if no mark in space, false if there is a mark
	 */
	public boolean verifyMove(int row, int col) {
		if(board.getMark(row, col) == SPACE_CHAR)
			return true;
		else
			return false;
	}
	
	/**
	 * Insert mark into given spot
	 * @param row Row number
	 * @param col Column number
	 * @param mark Player's mark to put in spot
	 */
	public void makeMove(int row, int col, char mark) {
		if(mark == LETTER_X)
			xPlayer.makeMove(row, col);
		else if(mark == LETTER_O)
			oPlayer.makeMove(row, col);
		
		count++;
	}
}
