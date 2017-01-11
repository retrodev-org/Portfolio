import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
 * Name:		James Walker Holland
 * Course:		COP2805 Advanced Java Programming
 * Instructor:	Professor Jeho Park
 * File:		ServerGUI.java
 * Description:	Internet chat client and server application.
 * 				Server communicates with multiple clients. Each 
 * 				client communicates with the server IP and port
 * 				number specified at launch.
 * Team:		None
 * Notes:		See Client.java
 */

@SuppressWarnings("serial")
public class ServerGUI extends JFrame implements ActionListener, WindowListener {
	
	private JButton stopStart;
	private JTextArea chatDisplay;
	private JTextField portNumberField;
	private Server server;

	public static final int ROWS = 80;
	public static final int COLS = 40;
	public static final Dimension FRAME_SIZE = new Dimension(600,400);

	// Constructor
	ServerGUI(int port) {
		super("Chat Server");
		server = null;

		// mainFrame > userPanel
		JPanel userPanel = new JPanel();
		userPanel.add(new JLabel("Port number: "));
		portNumberField = new JTextField("  " + port);
		userPanel.add(portNumberField);
		
		// mainFrame > userPanel > stopStart button
		stopStart = new JButton("Start Server");
		stopStart.addActionListener(this);
		userPanel.add(stopStart);
		add(userPanel, BorderLayout.SOUTH);
		
		// mainFrame > centerPanel
		JPanel centerPanel = new JPanel(new GridLayout(1,1));
		
		// mainFrame > centerPanel > chatDisplay
		chatDisplay = new JTextArea(ServerGUI.ROWS, ServerGUI.COLS);
		chatDisplay.setEditable(false);
		appendChatDisplay("Welcome!\n");
		centerPanel.add(new JScrollPane(chatDisplay));	
		add(centerPanel);
		
		// Listen for mouse click on frame
		addWindowListener(this);
		setSize(ServerGUI.FRAME_SIZE);
		setVisible(true);
	}		

	// Append message to chatDisplay
	void appendChatDisplay(String message) {
		chatDisplay.append(message);
	}
	
	// Start or stop when clicked
	public void actionPerformed(ActionEvent e) {
		if(server != null) {
			server.stop();
			server = null;
			portNumberField.setEditable(true);
			stopStart.setText("Start");
			return;
		}
		
      	// Start server	
		int port;
		try {
			port = Integer.parseInt(portNumberField.getText().trim());
		}
		catch(Exception er) {
			appendChatDisplay("Invalid port number");
			return;
		}

		// Create & start new Server
		server = new Server(port, this);
		new ServerRunning().start();
		stopStart.setText("Stop");
		portNumberField.setEditable(false);
	}
	
	// entry point to start the Server
	public static void main(String[] arg) {
		// start server default port
		new ServerGUI(Server.DEFAULT_PORT);
	}

	// Close connection if user clicks X
	public void windowClosing(WindowEvent e) {
		if(server != null) {
			try {
				server.stop(); // Ask server to close connection
			}
			catch(Exception eClose) {
			}
			server = null;
		}
		
		dispose();
		System.exit(0);
	}

	// Ignore other WindowListener methods
	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}

	class ServerRunning extends Thread {
		public void run() {
			server.start(); // should execute until if fails
			// the server failed
			stopStart.setText("Start");
			portNumberField.setEditable(true);
			appendChatDisplay("Server stopped.\n");
			server = null;
		}
	}

}

