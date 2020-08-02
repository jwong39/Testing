import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;

public class Client extends Thread{
	// User input for port
	int inputPort;
	// Stores Game Info
	theInfo gameInfo = new theInfo();
	
	String inputAddress;
	Socket socketClient;
	
	ObjectOutputStream out;
	ObjectInputStream in;
	
	
	private Consumer<Serializable> callback;
	
	Client(Consumer<Serializable> call){
	
		callback = call;
		// Initialize too I guess
		this.gameInfo = new theInfo();
	}
	
	public void run() {
		
		try {
		socketClient= new Socket(inputAddress,inputPort);
	    out = new ObjectOutputStream(socketClient.getOutputStream());
	    in = new ObjectInputStream(socketClient.getInputStream());
	    socketClient.setTcpNoDelay(true);
		}
		catch(Exception e) {}
		
		while(true) {
			 
			try {
			// Read my object
			gameInfo = (theInfo)in.readObject();
			// Grabs message out of my object
			String newMessage = gameInfo.message;
			// Outputs message onto javaFX
			callback.accept(newMessage);
			}
			catch(Exception e) {}
		}
	
    }
	// try
	public void send(int data, String total) {
		
		try {
			// Updates a temporary gameInfo then sends it.
			// data or total is a integer do this
			int transTotal = Integer.parseInt(total);
			gameInfo.Guess = data;
			gameInfo.GuessTotal = transTotal;
			// Had to have a copy constructor to send
			out.writeObject(new theInfo(gameInfo));
			//else nah
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
