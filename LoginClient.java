// LoginClient.java 
// LoginClient uses an SSLSocket to transmit fake login 
// information to LoginServer. 

package lab.two.part.five.jsse;

// Java core packages
import java.io.*;

// Java extension packages
import javax.swing.*; 
import javax.net.ssl.*;

public class LoginClient {
	protected SSLSocket socket;
	
	// LoginClient constructor
	public LoginClient() {
		// open SSLSocket connection to server and send login
		try {
			
			// obtain SSLSocketFactory for creating SSLSockets
			SSLSocketFactory socketFactory = 
				( SSLSocketFactory ) SSLSocketFactory.getDefault();
			
			System.out.println("Attempting to connect to server...");
			
			
			// create SSLSocket from factory
			
			try {
				socket = ( SSLSocket ) socketFactory.createSocket("18.191.114.7", 8888);
				
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			
			
			//System.out.println(socket.getSupportedCipherSuites()[0]);
			//System.out.println(socket.getSupportedCipherSuites()[1]);
			//System.out.println(socket.getSupportedCipherSuites()[10]);
			//System.out.println(socket.getSupportedCipherSuites()[20]);
			
			System.out.println("Connection succeeded...");

			// own code
			//String[] suites = {
			//		"TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256",
			//		"TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256",
			//		"TLS_RSA_WITH_AES_128_CBC_SHA256"
			//		};
			//String[] suites = {
			//		socket.getSupportedCipherSuites()[0]
			//		};
			//socket.setEnabledCipherSuites(suites);
			
			
			System.out.println("Cipher suites enabled");
			
			// create PrintWriter for sending login to server
			
			PrintWriter output = new PrintWriter( 
					new OutputStreamWriter( socket.getOutputStream() ) );
			
			//Prompt user for user name
			//String userName = JOptionPane.showInputDialog( null, 
			//		"Enter User Name:" );
			
			// send user name to server
			output.println( "Java" );
			
			// prompt user for password
			//String password = JOptionPane.showInputDialog( null, 
			//		"Enter Password:" );
						
			// send password to server
			output.println( "Java" );
			output.flush();
			
			
			// create BufferedReader for reading server response

			BufferedReader input = new BufferedReader( 
					new InputStreamReader( socket.getInputStream () ) );
			
			String response = input.readLine();	
			
			// read response from server 
			// display response to user
			JOptionPane.showMessageDialog( null, response );
			
			// clean up streams and SSLSocket
			output.close(); 
			input.close(); 
			socket.close();
			
		} // end try 
		
		
		// handle exception communicating with server
		catch ( IOException ioException ) { 
			ioException.printStackTrace();
		}
		
		// exit application
		finally { 
			System.exit( 0 );
		} 
		
	} // end LoginClient constructor
	
	// execute application
	public static void main( String args[] ) 
	{
		new LoginClient();
		
	}
	
}