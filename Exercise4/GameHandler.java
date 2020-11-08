import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class GameHandler implements Runnable{
	
	/**
	 * Thread game that is playing
	 */
	private Game theGame;
	/**
	 * Client 1, X player, socket
	 */
	private Socket client1;
	/**
	 * Client 1, X player, read inputstream
	 */
	private BufferedReader socketIn1;
	/**
	 * Client 1, X player, write outputstream
	 */
	private PrintWriter socketOut1;
	/**
	 * Client 2, O player, socket
	 */
	private Socket client2;
	/**
	 * Client 2, O player, read inputstream
	 */
	private BufferedReader socketIn2;
	/**
	 * Client 2, O player, write outputstream
	 */
	private PrintWriter socketOut2;
	/**
	 * Array of messages to send to Client 1, X player
	 */
	private ArrayList<String> sendMessagesX = new ArrayList<String>();
	/**
	 * Array of messages to send to Client 2, O player
	 */
	private ArrayList<String> sendMessagesO = new ArrayList<String>();
	
	/**
	 * Constructor for game handler
	 * @param client1 Client 1 socket
	 * @param socketIn1 Client 1 inputstream
	 * @param socketOut1 Client 1 outputstream
	 * @param client2 Client 2 socket
	 * @param socketIn2 Client 2 inputstream
	 * @param socketOut2 Client 2 outputstream
	 */
	public GameHandler(Socket client1, BufferedReader socketIn1, PrintWriter socketOut1, Socket client2, BufferedReader socketIn2, PrintWriter socketOut2) {
		this.client1 = client1;
		this.socketIn1 = socketIn1;
		this.socketOut1 = socketOut1;
		
		this.client2 = client2;
		this.socketIn2 = socketIn2;
		this.socketOut2 = socketOut2;
	}
	
	/**
	 * At the start of the game, introduce game and prompt players for names
	 * Start the game
	 */
	private void startGame() {
		try {
			//Start a new game
			theGame = new Game();
			
			//Prompt X player, first client, name
			sendMessagesX.add(">Welcome to TIC-TAC-TOE!!");
			sendMessagesX.add(">You are X Player. Enter name:");
			
			
			//Prompt player, first client, name
			sendMessagesO.add(">Welcome to TIC-TAC-TOE!!");
			sendMessagesO.add(">You are O Player. Enter name:");
			
			//send messages
			sendMessages();
			
			//Get X player, first client, name
			String reply = socketIn1.readLine();		
			//Set X player
			theGame.setPlayer(reply, 'X');
			
			//Get O player, second client, name
			reply = socketIn2.readLine();		
			//Set O player
			theGame.setPlayer(reply, 'O');
						
			//Start game
			theGame.startGame();
						
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	
	/**
	 * Get game board as messages and put into arrays that will be sent to the players
	 */
	private void getBoard() {
		//Get board
		String boardDisplay = theGame.getBoard().display();
		String[] strArr = boardDisplay.split("\n");
		
		//Put into messages
		for(String str: strArr) {
			sendMessagesX.add(str);
			sendMessagesO.add(str);
		}
		
	}
	
	/**
	 * Get who's turns as messages and put into arrays
	 * @return String that tells who's turn it is
	 */
	private String[] getTurn() {
		//Get board and put into messages
		getBoard();
		
		//Get turn from board
		String[] restr = theGame.getTurn().clone();
		sendMessagesX.add(restr[0]);
		sendMessagesO.add(restr[1]);
		
		return restr;
	}
	
	/**
	 * Send everything in the arrays to designated player
	 */
	private void sendMessages() {
		sendMessagesX.add(">END OF GAME OUTPUT");
		sendMessagesO.add(">END OF GAME OUTPUT");
		
		for(String msg: sendMessagesX) {
			socketOut1.println(msg);
		}
			
		for(String msg: sendMessagesO) {
			socketOut2.println(msg);
		}
			
		//Reset sendMessages
		sendMessagesX.clear();
		sendMessagesO.clear();
	}
	
	/**
	 * Run the game
	 */
	@Override
	public void run() {		
		String c1Response = "abcd";
		String c2Response = "abcd";
		String[] restr = new String[2];
		
		//start the game
		startGame();
		
		//Ask for moves from clients
		while(true) {						
			//Check if response is a valid move, keep prompting till user makes valid move
			while(!theGame.checkMove(c1Response, c2Response)) {
				//Prompt user again
				restr = getTurn().clone();
				sendMessages();
				try {
					//Get clients move, from client 1
					c1Response = socketIn1.readLine();

					//Get clients move, from client 2
					c2Response = socketIn2.readLine();
				
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				//Check if player have terminated
				if(c1Response == null || c2Response == null)
					break;
				//Check if response was 'QUIT'
				else if(c1Response.equals("QUIT") || c2Response.equals("QUIT")) 
	            {  
					sendMessagesX.add(">Player Disconnected. Closing Connection.");
					sendMessagesO.add(">Player Disconnected. Closing Connection.");
					//Send messages to players
					sendMessages();
	                break; 
	            } 
			}
			
			//Check if player have terminated
			if(c1Response == null || c2Response == null)
				break;
			//Check if player have quit
			else if(c1Response.equals("QUIT") || c2Response.equals("QUIT"))
                break; 
			//Check if game is over
			else if(restr[0].contains("Game over")) 
				break;
			
			//Add clients move to board, if any
			theGame.makeTurn(c1Response, c2Response);			
		}
		
		try {
			client1.close();
			socketIn1.close();
			socketOut1.close();
			client2.close();
			socketIn2.close();
			socketOut2.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
