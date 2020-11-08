# ensf607_lab6
ENSF 607 - Software Design and Architecture I - Lab 6

<h1>Exercise 1: A Very Simple Client-Server Program</h1><br>
The client connects to the server running on port 8099 of the “localhost”. The main job of the server that you need to implement is to read a message from socket, examine it for palindrome and send an appropriate message to back to the client. A string is called palindrome if it spells the same word from both ends. As an example, the word “radar” is palindrome. You can assume that user inputs are always lower case and contain only alphanumeric characters, and no punctuation. You don’t need to do any error checking
for these requirements.

<h1>Exercise 2: Date-time server</h1><br>
A simple client that connects to a date server. The date server class is given to you. he server runs on port 9090 of your local machine and waits for a client and when a client connects to the server, client can select to receive DATE or TIME. Depending on the client’s selection, server sends the response to the client. 

<h1>Exercise 3: Serialization and Deserialization of Java Objects</h1><br>
The program reads the text file called someSongs.txt, and creates a binary file called mySongs.ser. The created output file is expected to contain objects of music records, which is a binary file, and if you try to open it with a text editor, you will find it almost unreadable. Then, another program will de-serialize the objects from mySongs.ser and displays them on the screen. Use allSongs.ser to also check if de-serialize worked.

<h1>Exercise 4: Tic-Tac-Toe with a Thread pool</h1><br>
Modify the first version of the Tic-Tac-Toe game in a way that it can support multiple games simultaneously. Each game will haveits own thread and the communication between players is managed via sockets. The client side must run in two different terminal-windows and a game should not start before two players are available (must be monitored by the server). The server-side must use a threadpool to start a new game for every two clients that connect.

<h1>Exercise 5: Tic-Tac-Toe with GUI</h1><br>
Create GUI (similar to your assignment in ENSF 593) for the clients that allows players to use their mouse to mark the board with an X or O. Each game is to be played by 2 clients; i.e. OPlayer, and XPlayer. Each client must have their own GUI. This is because each client runs on a machine.
Here are the minimal functionalities of the component of your client window/frame must have:
   • A presentation of the board
   • A text area that displays the game messages for the communication or information exchange.
   • A text box that players can enter their name.
