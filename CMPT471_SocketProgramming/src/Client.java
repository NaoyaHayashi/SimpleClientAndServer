import java.io.*;
import java.util.Scanner;
import java.net.Socket;


/**
	* Client class requests TCP/IP connection to the Server. <br>
	* If the connection is built successfully, this client sends a message "This is CMPT471!!". <br>
	* The client expects that the server responses to the message and will wait for the reply message. <br>
	* If the reply is received by the client, the reply message from the server will be displayed on the console. <br>
	* The client will terminate after showing the message content.
	* Note: Before you run this program, Serve.java must be running; otherwise, the client can't connect to the server.

 	* @author Naoya Hayashi
 	* <dt><b>Student No.:</b><dd>
 	* 301233985
 	* @date November 2nd, 2015
 	* @version 1.0
 	*/
public class Client {
	/**
	 * Main method for a client to build TCP/IP connection with a server. <br>
	 * @param args args[0] for IP address of a server to connect, and args[1] for port
	 * <dt><b>Precondition:</b><dd>
	 * Command-line input for IP address and port number must be valid.
	 * Especially, the given IP address must exist and reachable. 
	 * Also, the given port number must not be the reserved numbers and exceeds 65536.
	 */
	public static void main(String[] args) throws IOException {
		String serverIP;
		// tentatively assigned, this port number will not be used.
		// The port number given by the user input will be used instead.
		// The purpose of this value assignment is just to squelch the syntax warning. 
		int port = 8888; 
		
		
		
		// Check if the number of arguments is valid or not
		if(args.length != 2){
			System.out.println("The given arguments are invalid!");
			System.out.println("Provide just two arguments: valid Server IP Address and Port");
			System.out.println("The program ends...");
			System.exit(1);
		}	
			
		// Get command-line arguments
		serverIP = args[0];
		// Check if the given input for port is a valid number; otherwise, terminates the program.
		try{
			port = Integer.parseInt(args[1]);
		}
		catch(NumberFormatException e){
			System.out.println("The argument can't be converted into an integer!!");
			System.out.println("The program ends...");
			System.exit(1);
		}
		// Open a socket with given arguments
		Socket socket = new Socket(serverIP, port);
		
		// Get streams
		InputStream instream = socket.getInputStream();
		OutputStream outstream = socket.getOutputStream();
		
		// Turn streams into a scanner and a writer
		Scanner input = new Scanner(instream);
		PrintWriter output = new PrintWriter(outstream);

		// Send a message to the server
		// "\\EOF" is a terminator to tell the server that it's the end of the message.
		// The sever will know no more incoming messages, so it can proceed.
		String EOF_token = "\\EOF";
		String data = "This is CMPT471!!" + " " + EOF_token + "\r";
		output.print(data);
		output.flush();
		
		// Read the server's response if anything
		while(input.hasNext()){
			String incomingData = input.nextLine();
			System.out.print("The server's response: ");
			System.out.println(incomingData);
		}
		
		// Close the inputstream and socket/connection
		input.close(); 
		socket.close();
		System.exit(0);
	}

}
