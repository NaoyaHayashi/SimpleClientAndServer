import java.io.*;
import java.util.Scanner;
import java.net.Socket;
import java.net.ServerSocket;

/**
 	* Server class waits TCP/IP connection from the Client. <br>
 	* The server will report if the connection from the client is successfully built. <br>
 	* Then, it will check the message is the expected one.  <br>
 	* The expected message is "This is CMPT471!!". <br>
 	* If the message matches to the expected one, it will reply a message to the client; otherwise, ignore. <br>
 	* The server will close the connection after sending the reply.
	
	* @author Naoya Hayashi
	* <dt><b>Student No.:</b><dd>
	* 301233985
	* @date November 2nd, 2015
	* @version 1.0
	*/
public class Server {

	/**
	 * Main method for the server.
	 * @param args args[0] for port
	 * <dt><b>Precondition:</b><dd>
	 * the given port number must not be the reserved numbers and exceed 65536.
	 */
	public static void main(String[] args) throws IOException {
		// Check if the number of input is 1 or not.
		// If it is not 1, the server will not launch.
		if(args.length != 1){
			System.out.println("Invalid argument!! Provide just a port number.");
			System.out.println("The program ends...");
			System.exit(1);
		}
		int port = 8888; // temporary value assigned to suppress the syntax warning
		// If the port is not number, it will end.
		try{
			port = Integer.parseInt(args[0]);
		}
		catch(NumberFormatException e){
			System.out.println("The argument can't be converted into an integer!!");
			System.out.println("The program ends...");
			System.exit(1);
		}
		
		// Create a server socket
		ServerSocket serverSocket = new ServerSocket(port);
		try{
			while(true){
				System.out.println("Waiting for clients to connect . . . ");
				// The server accepts connections if anything.
				Socket socket = serverSocket.accept();
				System.out.println("Client connected.");
				// Get streams
				InputStream instream = socket.getInputStream();
				OutputStream outstream = socket.getOutputStream();
				// Turn streams into a scanner and a writer
				Scanner input = new Scanner(instream);
				PrintWriter output = new PrintWriter(outstream);
				String str = "";
				
				boolean firstLoop = true;
				// Terminator String
				// If the server recognizes this terminator, it knows no more messages from client.
				String EOF_token = "\\EOF";
				while(input.hasNext()){
					// Get the client message word by word.
					String nextStr = input.next();
					if(nextStr.equals(EOF_token)){
						break;
					}
					// If the loop is executed first time, it won't concatenate with a whitespace, " ". 
					if(firstLoop){
						str = nextStr;
						firstLoop = false;
					}
					else{
						str = str + " " + nextStr;
					}
				}
				// Display the message sent by the client.
				System.out.print("The message sent by the Client: ");
				System.out.println(str);
				// Checking if the connection is made from the client sending the string "This is CMPT471!!"./
				// If not, server ignores the message and close the socket.
				if(str.equals("This is CMPT471!!")){
					// sending reply meesage to the client
					output.write("Hi CMPT471 student!");
					output.flush();
				}
				// close the input stream and socket connected to the client
				input.close();
				socket.close();
			}
		}
		finally{
			// close the server socket
			// Program normally won't reach here but this is necessary to suppress syntax warning.
			serverSocket.close();
			System.exit(0);
		}
	}

}
