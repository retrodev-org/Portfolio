import java.net.*;
import java.io.*;
import java.util.*;

/*
 * Name:		James Walker Holland
 * Course:		COP2805 Advanced Java Programming
 * Instructor:	Professor Jeho Park
 * File:		Client.java
 * Description:	Internet chat client and server application.
 * 				Server communicates with multiple clients. Each 
 * 				client communicates with the server IP and port
 * 				number specified at launch.
 * Team:		None
 * Notes:		2014.07.14	Begin prototype
 * 				2014.07.23	Restart project with base code from class.
 * 				2014.07.28	I've lost my notes along with a somewhat complete version of the project.
 * 							I'm kicking myself for leaving my computer unattended during a storm.
 * 				2014.07.29	Unable to update JTextArea. Start GUI from scratch.
 * 				2014.07.30	I swear I'm doing the same thing I did before, but by following an example
 * 							I'm finally able to update the JTextArea!
 */
public class Client  {

	private ObjectInputStream socketInput;
	private ObjectOutputStream socketOutput;
	private Socket socket;

	// To GUI or not to GUI, that is the question
	private static ClientGUI gui;
	
	private String serverAddress;
	private static String username;
	private int port;

	// Constructor for console
	Client(String server, int port, String username) {
		this(server, port, username, null);
	}

	// Constructor for GUI
	Client(String server, int port, String username, ClientGUI gui) {
		this.serverAddress = server;
		this.port = port;
		Client.username = username;
		// save if we are in GUI mode or not
		Client.gui = gui;
	}
	
	public boolean start() {
		// Try connecting to server
		try {
			socket = new Socket(serverAddress, port);
		} catch(Exception errorConnection) {
			display("Problem connecting to server:" + serverAddress + ":" + port);
			return false;
		}
		
		String msg = "Successful connection to " + socket.getInetAddress() + ":" + socket.getPort();
		display(msg);
	
		// Create input/output streams
		try {
			socketInput  = new ObjectInputStream(socket.getInputStream());
			socketOutput = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException eIO) {
			display("Exception creating new Input/output Streams: " + eIO);
			return false;
		}

		// Create Thread to listen for the server 
		new ListenForServer().start();
		
		// Send username to server
		try {
			socketOutput.writeObject(username);
		} catch (IOException errorIO) {
			display("Exception during login : " + errorIO);
			disconnect();
			return false;
		}
		return true;
	}

	// Send message to console or GUI
	static void display(String message) {
		gui.append(message + "\n"); // Append to the ClientGUI JTextArea
	}
	
	/*
	 * To send a message to the server
	 */
	void sendMessage(Message message) {
		try {
			socketOutput.writeObject(message);
		}
		catch(IOException errorIO) {
			display("Error sending to server: " + errorIO);
		}
	}

	// Close streams and disconnect if something breaks
	private void disconnect() {
		try { 
			if(socketInput != null) socketInput.close();
		}
		catch(Exception e) {} // not much else I can do
		try {
			if(socketOutput != null) socketOutput.close();
		}
		catch(Exception e) {} // not much else I can do
        try{
			if(socket != null) socket.close();
		}
		catch(Exception e) {} // not much else I can do
		
		// Notify the GUI
		if(gui != null)
			gui.connectionFailed();
			
	}

	public static void main(String[] args) {
		// default values
		int portNumber = Server.DEFAULT_PORT;
		String serverAddress = "localhost";
		String usernameEntered = "Anonymous";

		// depending of the number of arguments provided we fall through
		switch(args.length) {
			// > console Client username portNumber serverAddress
			case 3:
				serverAddress = args[2];
			// > console Client username portNumber
			case 2:
				try {
					portNumber = Integer.parseInt(args[1]);
				}
				catch(Exception e) {
					System.out.println("Invalid port number.");
					System.out.println("Usage is: > java Client [username] [portNumber] [serverAddress]");
					return;
				}
			// > console Client username
			case 1: 
				usernameEntered = args[0];
			// > console java Client
			case 0:
				break;
			// invalid number of arguments
			default:
				System.out.println("Usage is: > java Client [username] [portNumber] {serverAddress]");
			return;
		}
		// Create the client object
		Client client = new Client(serverAddress, portNumber, usernameEntered);

		// Test connection to server
		if(!client.start())
			return;
		
		// Listen for messages from user
		Scanner input = new Scanner(System.in);
		while(true) {
			System.out.print("> ");
			String text = input.nextLine();
			if(text.equalsIgnoreCase("EXIT")) {
				client.sendMessage(new Message(username + " has logged out."));
				// Break to disconnect
				break;
			} else {
				client.sendMessage(new Message(text));
			}
		}
		
		// Disconnect when finished
		client.disconnect();
		input.close();
	}

	class ListenForServer extends Thread {
		public void run() {
			while(true) {
				try {
					String message = (String) socketInput.readObject();
					// if console mode print the message and add back the prompt
					if(gui == null) {
						System.out.println(message);
						System.out.print("> ");
					} else {
						gui.append(message + "\n");
					}
				} catch(IOException errorIO) {
					display("The server has closed the connection.\n");
					if(gui != null) 
						gui.connectionFailed();
					break;
				} catch(ClassNotFoundException errorClass) {
				}
			} // end while(true)
		} // end run()
	} // end ListenForServer
} // end Client

