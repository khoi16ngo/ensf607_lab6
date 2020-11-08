

import java.io.*;

/**
 * Provides fields and methods to create a player in game, tic-tac-toe.
 * 
 * @author Khoi
 * @version 1.0
 * @since October 1, 2020
 *
 */

public class Player implements Constants{
	
	/**
	 * Player's name
	 */
	private String name;
	/**
	 * The board they will play on
	 */
	private Board myBoard;
	/**
	 * The mark they will use, either X or O
	 */
	private char mark;
	
	/**
	 * Constructs a player to participate in game.
	 * @param name name of player
	 * @param mark symbol they will use
	 */
	public Player(String name, char mark) {
		this.name = name;
		this.mark = mark;
	}
	
	/**
	 * Player makes a move by inserting mark into spot
	 * @throws IOException
	 */
	public void makeMove(int row, int col) {
		myBoard.addMark(row, col, mark);
	}
		
	/**
	 * Set board the player will play on
	 * @param theBoard, the board game is played on
	 */
	public void setBoard(Board theBoard) {
		myBoard = theBoard;
	}
		
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return name + " playing as " + mark;
	}
	
}
