import java.io.*;
import java.net.*;
import java.util.*;

/*
 * Name:		James Walker Holland
 * Course:		COP2805 Advanced Java Programming
 * Instructor:	Professor Jeho Park
 * File:		Server.java
 * Description:	Internet chat client and server application.
 * 				Server communicates with multiple clients. Each 
 * 				client communicates with the server IP and port
 * 				number specified at launch.
 * Team:		None
 * Notes:		See Client.java
 */
public class Server {
	private static int identifier;
	private ArrayList<ClientThread> clientList;
	private static ServerGUI serverGUI;
	private boolean continueWorking;
	private int port;
	
	public static final int DEFAULT_PORT = 5678;
	public static final String DEFAULT_SERVER = new String("localhost");

	// Constructors
	public Server(int port) {
		this(port, null);
	}
	public Server(int port, ServerGUI serverGUI) {
		// GUI or not
		Server.serverGUI = serverGUI;
		// the port
		this.port = port;
		// ArrayList for the Client list
		clientList = new ArrayList<ClientThread>();
	}
	
	public void start() {
		continueWorking = true;
		
		// Display local address
		display("Local address:  " + setLocalAddress());
		
		// Listen for client connections
		try {
			ServerSocket serverSocket = new ServerSocket(port);

			while(continueWorking){
				display("Waiting for clients on port " + port + ".");
				
				Socket socket = serverSocket.accept();
				
				// If asked to stop
				if(!continueWorking)
					break;
				ClientThread thread = new ClientThread(socket);
				clientList.add(thread);
				thread.start();
			} // end while(continueWorking)
			
			// When finished
			try {
				serverSocket.close();
				for(int i = 0; i < clientList.size(); ++i) {
					ClientThread clientThread = clientList.get(i);
					try {
						clientThread.streamInput.close();
						clientThread.streamOutput.close();
						clientThread.socket.close();
					} catch(IOException errorIO) {
					}
				}
			}
			catch(Exception e) {
				display("Problem closing the connection.\n");
			}
		} catch (IOException errorIO) {
            display("Problem connecting to ServerSocket.\n");
		}
	}

	protected void stop() {
		continueWorking = false;
		try {
			new Socket("localhost", port);
		}
		catch(Exception error) {
		}
	}
	
	// Display event to console or GUI
	private static void display(String message) {
		if(serverGUI == null)
			System.out.println(message);
		else
			serverGUI.appendChatDisplay(message + "\n");
	}

	// Send message to clientList
	private synchronized void sendMessage(String message) {
		// display message on console or GUI
		if(serverGUI == null)
			System.out.print(message + "\n");
		else
			serverGUI.appendChatDisplay(message + "\n");
		
		// we loop in reverse order in case we would have to remove a Client
		// because it has disconnected
		for(int i = clientList.size(); --i >= 0;) {
			ClientThread clientThread = clientList.get(i);
			// try to write to the Client if it fails remove it from the list
			if(!clientThread.writeMessage(message)) {
				clientList.remove(i);
				display("Disconnected Client " + clientThread.username + " removed from list.");
			}
		}
	}

	// Log out clients via EXIT message
	synchronized void remove(int id) {
		// scan the array list for ID
		for(int i = 0; i < clientList.size(); ++i) {
			ClientThread clientThread = clientList.get(i);
			if(clientThread.id == id) {
				clientList.remove(i);
				return;
			}
		}
	}
	
	public static String setLocalAddress(){
		String localAddress;
		try
		{
			InetAddress thisIp = InetAddress.getLocalHost();
			localAddress = thisIp.getHostAddress().toString();
		} catch(Exception e){
			e.printStackTrace();
			localAddress = null;
		}
		return localAddress;
	}
	
	public static void main(String[] args) {
		int portNumber = DEFAULT_PORT;
		switch(args.length) {
			case 1:
				try {
					portNumber = Integer.parseInt(args[0]);
				}
				catch(Exception e) {
					display("Invalid port number.");
					System.out.println("Usage is: > java Server [portNumber]");
					return;
				}
			case 0:
				break;
			default:
				System.out.println("Usage is: > java Server [portNumber]");
				return;
		} // end switch(args.length)
		
		// Create server object
		Server server = new Server(portNumber);
		server.start();
	}

	class ClientThread extends Thread {
		Socket socket;
		ObjectInputStream streamInput;
		ObjectOutputStream streamOutput;
		int id;
		String username;
		Message clientMessage;

		// Constructor
		ClientThread(Socket socket) {
			// a unique id
			id = ++identifier;
			this.socket = socket;
			
			System.out.println("Thread trying to create Object Input/Output Streams");
			try {
				streamOutput = new ObjectOutputStream(socket.getOutputStream());
				streamInput  = new ObjectInputStream(socket.getInputStream());

				username = (String) streamInput.readObject();
				sendMessage(username + " has connected.");
			} catch (IOException errorIO) {
				display("Problem creating input/output streams.");
				return;
			} catch (ClassNotFoundException errorClass) {
			}
		} // end ClientThread()

		public void run() {
			// Loop until EXIT
			boolean continueWorking = true;
			while(continueWorking) {
				// read a String (which is an object)
				try {
					clientMessage = (Message) streamInput.readObject();
				}
				catch (IOException errorIO) {
					remove(id);
					sendMessage(username + " has logged out.");
					break;				
				}
				catch(ClassNotFoundException errorClass) {
					break;
				}
				// the message part of the Message
				String message = clientMessage.getMessage();

				sendMessage(username + ": " + message);
			}
			
			// When finished, remove this client from clientList
			// Inside if statement in case of premature disconnect
			if (clientList.contains(id))
				remove(id);
			close();
		}
		
		private void close() {
			try {
				if(streamOutput != null) streamOutput.close();
			} catch(Exception errorOutput) {}
			try {
				if(streamInput != null) streamInput.close();
			} catch(Exception errorInput) {};
			try {
				if(socket != null) socket.close();
			} catch (Exception errorSocket) {}
		}

		private boolean writeMessage(String message) {
			if(!socket.isConnected()) {
				close();
				return false;
			}

			// Write message to stream
			try {
				streamOutput.writeObject(message);
			} catch(IOException errorIO) {
				display("Problem delivering message to " + username);
				display(errorIO.toString());
			}
			return true;
		}
	}
}