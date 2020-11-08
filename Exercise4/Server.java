import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
	/**
	 * The server socket
	 */
	private ServerSocket serverSocket;
	
	/**
	 * Constructor to build a server
	 * @param port Port number of server socket
	 */
	public Server (int port) {
		try {
			serverSocket = new ServerSocket (port);
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Run the server, to start a game two clients must be accepted, throw two clients to a game thread
	 * @param args
	 * @throws IOException
	 */
	public static void main (String [] args) throws IOException{
		
		Server myServer = new Server(8989);
		
		System.out.println(">My Server: Server started.");
		
		while(true) {
			Socket client1 = null, client2 = null;
			
			try {
				//wait for first player to join
				client1 = myServer.serverSocket.accept();
				System.out.println(">My Server: New client is connected.");
				
				BufferedReader socketIn1 = new BufferedReader (new InputStreamReader(client1.getInputStream()));
				PrintWriter socketOut1 = new PrintWriter (client1.getOutputStream(), true);
	
				System.out.println(">My Server: Need another client to play.");
				
				//wait for second player to join
				client2 = myServer.serverSocket.accept();
				System.out.println(">My Server: New client is connected.");
				
				BufferedReader socketIn2 = new BufferedReader (new InputStreamReader(client2.getInputStream()));
				PrintWriter socketOut2 = new PrintWriter (client2.getOutputStream(), true);
					
				System.out.println(">My Server: Two players connected. Starting Game.");
				
				//start game when two clients connected
				Runnable game = new GameHandler(client1, socketIn1, socketOut1, client2, socketIn2, socketOut2);
				Thread t = new Thread(game);
				t.start();
				
				System.out.println(">My Server: Game started. Waiting for new players...");
				
			} catch (IOException e) {
				client1.close();
				client2.close();
				e.printStackTrace();
			} 		
		}
	}
	
}
