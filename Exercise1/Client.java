import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	/**
	 * Output stream to write to
	 */
	private PrintWriter socketOut;
	/**
	 * Server socket to connect to
	 */
	private Socket palinSocket;
	/**
	 * Input stream to get user input
	 */
	private BufferedReader stdIn;
	/**
	 * Input stream to read from
	 */
	private BufferedReader socketIn;
	
	/**
	 * Constructor for client
	 * @param serverName Server name to connect to
	 * @param portNumber Port number of server to connect to
	 */
	public Client(String serverName, int portNumber) {
		try {
			palinSocket = new Socket(serverName, portNumber);
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			socketIn = new BufferedReader(new InputStreamReader(
					palinSocket.getInputStream()));
			socketOut = new PrintWriter((palinSocket.getOutputStream()), true);
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}
	
	/**
	 * Communicate with socket, get response and send messages
	 */
	public void communicate()  {

		String line = "";
		String response = "";
		boolean running = true;
		while (running) {
			try {
				System.out.println("please enter a word: ");
				line = stdIn.readLine();
				System.out.println(line);
				socketOut.println(line);
				response = socketIn.readLine();
				System.out.println(response);
				
				if (line.equals("QUIT")){
					running = false;
				}
				
			} catch (IOException e) {
				System.out.println("Sending error: " + e.getMessage());
			}
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
	 * Run the client
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException  {
		Client aClient = new Client("localhost", 8099);
		aClient.communicate();
	}
}