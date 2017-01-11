import java.io.*;

/*
 * Name:		James Walker Holland
 * Course:		COP2805 Advanced Java Programming
 * Instructor:	Professor Jeho Park
 * File:		Message.java
 * Description:	Internet chat client and server application.
 * 				Server communicates with multiple clients. Each 
 * 				client communicates with the server IP and port
 * 				number specified at launch.
 * Team:		None
 * Notes:		See Client.java
 */
@SuppressWarnings("serial")
public class Message implements Serializable {

	private String message;
	
	// Setter
	Message(String message) {
		this.message = message;
	}
	
	// Getter
	String getMessage() {
		return message;
	}
}

