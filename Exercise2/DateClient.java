import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class DateClient {
	/**
	 * Ouput stream to write to
	 */
	private PrintWriter socketOut;
	/**
	 * Server socket
	 */
	private Socket palinSocket;
	/**
	 * Input stream to read user input
	 */
	private BufferedReader stdIn;
	/**
	 * Input stream to read from server
	 */
	private BufferedReader socketIn;

	/**
	 * Constructor of client
	 * @param serverName Server name connecting to
	 * @param portNumber Server port number to connect ot
	 */
	public DateClient(String serverName, int portNumber) {
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
	 * Communicate with server, read response and send messages
	 */
	public void communicate()  {

		String line = "";
		String response = "";
		boolean running = true;
		while (running) {
			try {
				System.out.println("Please select an option (DATE/TIME)");
				line = stdIn.readLine();
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
		DateClient aClient = new DateClient("localhost", 9090);
		aClient.communicate();
	}
}