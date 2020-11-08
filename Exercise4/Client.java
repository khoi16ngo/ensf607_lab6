import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Client {
	/**
	 * Output stream to write to
	 */
	private PrintWriter socketOut;
	/**
	 * Server socket
	 */
	private Socket serverSocket;
	/**
	 * Input stream to read from
	 */
	private BufferedReader socketIn;
	/**
	 * Input stream to read from user input
	 */
	private BufferedReader stdIn;
	/**
	 * Sending message string
	 */
	private String sendMessage = "";
	/**
	 * Received message string
	 */
	private String receiveMessage = "";
	/**
	 * Array of receiving messages
	 */
	private ArrayList<String> messages =  new ArrayList<String>();
	
	/**
	 * Constructor of client
	 * @param serverName Server name to connect to
	 * @param portNumber Server port number to connect to
	 */
	public Client(String serverName, int portNumber) {
		try {
			//Connect to server socket  
			serverSocket = new Socket(serverName, portNumber);

			//Open streams on scoket, pass socket I/O handles
			socketOut = new PrintWriter((serverSocket.getOutputStream()), true);
			socketIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			
			//User input
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}
	
	/**
	 * At the beginning of the game, prompt for user name
	 * Set-up game
	 */
	private void startGame() {
		//Start of game
		try {
			while(!receiveMessage.contains("END")){
				receiveMessage = socketIn.readLine();		
				System.out.println(receiveMessage);
			}
			//Get the name of client/player
			String line = stdIn.readLine();
			sendMessage = line;
			//Send to game
			socketOut.println(sendMessage);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Communicate with server, get response, display output to user, and send messages
	 */
	public void communicate() {
		boolean running = true;
		
		startGame();
		
		//Make moves for game
		while (running) {
			try {
				receiveMessage = "";
				
				//Get response from game till end
				while(!receiveMessage.contains("END")){
					receiveMessage = socketIn.readLine();
					System.out.println(receiveMessage);
					messages.add(receiveMessage);					
				}

				
				//wait for other player
				if(checkResponse("wait")) {
					System.out.println(">Other player's turn, please wait for them to make a turn.");
					System.out.println(">Type 's' to continue or 'QUIT' to quit game.");
					sendMessage = stdIn.readLine();
					socketOut.println(sendMessage);
				}
				
				//user turn
				else if(checkResponse("turn")){
					//Get move from client, must be "row column" format
					//Also row and column number must be within 0-2
					while(true) {
						//Prompt user
						System.out.println(">Enter Row and Column Number (seperated by space):\n>(Type 'QUIT' to quit game)");
						sendMessage = stdIn.readLine();
						
						//check to see if line is valid
						if(checkNumber(sendMessage)) {
							//send to game
							socketOut.println(sendMessage);
							break;
						}
						else {
							System.out.println(">Invalid Response!");
						}
					}
				}
		
				//Quit game
				else if (checkResponse("Disconnected")){
					while(!receiveMessage.contains("Disconnected")){
						receiveMessage = socketIn.readLine();
						System.out.println(receiveMessage);
						messages.add(receiveMessage);					
					}
					running = false;
				}
				
				//End of game
				else if(checkResponse("Game over")) {
					running = false;
				}
				
			} catch (IOException e) {
				System.out.println(">Sending error: " + e.getMessage());
			}
			//Reset received messages array
			messages.clear();
		}

		try {
			stdIn.close();
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			System.out.println("Closing error: " + e.getMessage());
		}
	}
	
	/**
	 * Check to see if received messages contains word
	 * @param word Word to see if contained
	 * @return True if word is in the received messages array, otherwise false
	 */
	private boolean checkResponse(String word) {
		for(String msg: messages)
			if(msg.contains(word))
				return true;
		
		return false;
	}
	
	/**
	 * Check to see if string is row-column pair, are numbers, and within board range
	 * @param line
	 * @return
	 */
	private boolean checkNumber(String line) {
		String[] val = line.split(" ");
		
		try{
			int row = Integer.parseInt(val[0]);
			int col = Integer.parseInt(val[1]);
			
			if(row>2 || col>2 || row<0 || col<0)
				return false;
			
		}catch(NumberFormatException e) {
			return false;
		}
		
		return true;
	}

	/**
	 * Run the client
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		//Connect to server, local host and port 8989
		Client thisClient = new Client("localhost", 8989);
		
		//Communicate with server
		thisClient.communicate();
	}
}
