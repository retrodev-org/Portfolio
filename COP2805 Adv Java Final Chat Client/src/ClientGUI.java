import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
 * Name:		James Walker Holland
 * Course:		COP2805 Advanced Java Programming
 * Instructor:	Professor Jeho Park
 * File:		ClientGUI.java
 * Description:	Internet chat client and server application.
 * 				Server communicates with multiple clients. Each 
 * 				client communicates with the server IP and port
 * 				number specified at launch.
 * Team:		None
 * Notes:		See Client.java
 */
@SuppressWarnings("serial")
public class ClientGUI extends JFrame implements ActionListener {

	// will first hold "Username:", later on "Enter message"
	private JLabel myLabel;
	private JTextField textField;
	private JTextField serverEntered, portEntered;
	private JButton login;
	private JTextArea chatDisplayArea;
	private boolean connected;
	private Client client;
	private int defaultPort = Server.DEFAULT_PORT;
	private String defaultServer = Server.DEFAULT_SERVER;

	// Constructor connection receiving a socket number
	ClientGUI(String host, int port) {

		super("Chat Client");
		defaultPort = port;
		defaultServer = host;
		
		// mainFrame > northPanel
		JPanel northPanel = new JPanel(new GridLayout(3,1));
		
		// mainFrame > northPanel > serverAndPort
		JPanel serverAndPort = new JPanel(new GridLayout(1,5, 1, 3));
		serverEntered = new JTextField(host);
		portEntered = new JTextField("" + port);
		portEntered.setHorizontalAlignment(SwingConstants.RIGHT);
		serverAndPort.add(new JLabel("Server Address:  "));
		serverAndPort.add(serverEntered);
		serverAndPort.add(new JLabel("Port Number:  "));
		serverAndPort.add(portEntered);
		serverAndPort.add(new JLabel(""));
		northPanel.add(serverAndPort);

		// mainFrame > northPanel > myLabel (username and then text entry)
		myLabel = new JLabel("Enter your username below:", SwingConstants.CENTER);
		northPanel.add(myLabel);
		textField = new JTextField("Anonymous");
		textField.setBackground(Color.WHITE);
		northPanel.add(textField);
		add(northPanel, BorderLayout.NORTH);

		// mainFrame > centerPanel
		JPanel centerPanel = new JPanel(new GridLayout(1,1));
		
		// mainFrame > centerPanel > chatDisplayArea
		chatDisplayArea = new JTextArea("Welcome!\n", ServerGUI.ROWS, ServerGUI.COLS);
		centerPanel.add(new JScrollPane(chatDisplayArea));
		chatDisplayArea.setEditable(false);
		add(centerPanel, BorderLayout.CENTER);

		// mainFrame > southPanel
		JPanel southPanel = new JPanel();
		
		// mainFrame > southPanel > login button
		login = new JButton("Login");
		login.addActionListener(this);
		southPanel.add(login);
		add(southPanel, BorderLayout.SOUTH);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(ServerGUI.FRAME_SIZE);
		setVisible(true);
		textField.requestFocus();

	}

	// Called by client to append text to TextArea 
	void append(String message) {
		chatDisplayArea.append(message);
		chatDisplayArea.setCaretPosition(chatDisplayArea.getText().length() - 1);
	}
	
	// If connection fails
	void connectionFailed() {
		login.setEnabled(true);
		myLabel.setText("Enter your username below");
		textField.setText("Anonymous");
		
		// Reset port number and hostname
		portEntered.setText("" + defaultPort);
		serverEntered.setText(defaultServer);
		
		// Allow user to change them
		serverEntered.setEditable(false);
		portEntered.setEditable(false);
		
		// Don't react to <CR> after username
		textField.removeActionListener(this);
		connected = false;
	}
		
	// Button clicked
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		if(connected) {
			// Send message
			client.sendMessage(new Message(textField.getText()));				
			textField.setText("");
			return;
		} // end if(connected)

		if(object == login) {
			// Connection request
			String username = textField.getText().trim();
			if(username.length() == 0)
				return;
			String server = serverEntered.getText().trim();
			if(server.length() == 0)
				return;
			String portNumber = portEntered.getText().trim();
			if(portNumber.length() == 0)
				return;
			int port = 0;

			try {
				port = Integer.parseInt(portNumber);
			} catch(Exception en) {
				Client.display("That is an invalid port number.\n");
				return;
			}

			// Create new client GUI
			client = new Client(server, port, username, this);
			// test if we can start the Client
			if(!client.start()) 
				return;
			textField.setText("");
			myLabel.setText("Enter your message below");
			connected = true;
			
			// Disable login button
			login.setEnabled(false);
			
			// Disable the Server and Port JTextField
			serverEntered.setEditable(false);
			portEntered.setEditable(false);
			
			// Action listener for when the user enter a message
			textField.addActionListener(this);
		} // end if(object == login)
	} // end actionPerformed()

	// Start it up!
	public static void main(String[] args) {
		new ClientGUI(Server.DEFAULT_SERVER, Server.DEFAULT_PORT);
	}

}