package lab6ex1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
	
	/**
	 * Client socket to connect
	 */
	private Socket aSocket;
	/**
	 * Server's own socket
	 */
	private ServerSocket serverSocket;
	/**
	 * Output stream to write to
	 */
	private PrintWriter socketOut;
	/**
	 * Input stream to read from
	 */
	private BufferedReader socketIn;
	
	/**
	 * Constructor for server, port is set at 8989
	 */
	public Server () {
		try {
			serverSocket = new ServerSocket (8099);
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Communicate with client, read response and send messages
	 */
	public void palindrome () {
		String line = null;
		String restr = null;
		
		while (true) {
			try {
				line = socketIn.readLine();
				
				if (line.equals("QUIT")) {
					line = "Good Bye!";
					socketOut.println(line);
					break;
				}
				
				restr = checkPalindrome(line);
				
				socketOut.println(restr);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
	}
	
	/**
	 * Check if a string is a palindrome, reading from either side is the same
	 * @param line
	 * @return
	 */
	private String checkPalindrome(String line) {
		String restr = "";
		String check = "";
		char[] characters = line.toCharArray();
		
		for(int i = characters.length - 1; i >= 0; i--) {
			check += characters[i];
		}
		
		if(line.equals(check))
			restr = line + " is a Palindrome";
		else
			restr = line + " is not a Palindrome";		
		
		return restr;
	}
	
	/**
	 * Run the Server
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main (String [] args) throws IOException{
		
		Server myServer = new Server ();
		
		try {
			myServer.aSocket = myServer.serverSocket.accept();
			System.out.println("My Server: Connection accepted by the server!");
			
			myServer.socketIn = new BufferedReader (new InputStreamReader(myServer.aSocket.getInputStream()));
			myServer.socketOut = new PrintWriter (myServer.aSocket.getOutputStream(), true);
			
			System.out.println("My Server: Server is now running!");
			myServer.palindrome();
			
			System.out.println("My Server: Closing connection to server!");
			myServer.socketIn.close();
			myServer.socketOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}

}
