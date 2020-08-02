import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;

public class Server{

	int numClients= 1;	
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	TheServer server;
	private Consumer<Serializable> callback;
	int inputPort;
	
	
	Server(Consumer<Serializable> call){
	
		callback = call;
		server = new TheServer();
		server.start();
	}
	
	
	public class TheServer extends Thread{
		
		public void run() {
		
			try(ServerSocket mysocket = new ServerSocket(inputPort);){
		    System.out.println("Server is waiting for a client!");
			    while(true) {
			    	// numClients the amount of users by 2
			    	if (numClients <= 2) {
						ClientThread c = new ClientThread(mysocket.accept(), numClients);
						callback.accept("client has connected to server: " + "client #" + numClients);
						clients.add(c);
						c.start();
						
						numClients++;
			    	}

					
				}
			}//end of try
				catch(Exception e) {
					callback.accept("Server socket did not launch");
				}
			}//end of while
		}
	

		class ClientThread extends Thread{
			
			Socket connection;
			// Number of clients
			int count;
			ObjectInputStream in;
			ObjectOutputStream out;
			
			theInfo gameInfo = new theInfo();
			
			ClientThread(Socket s, int count){
				this.connection = s;
				this.count = count;
			}
			//if client size is above 2 
			public void updateClients(String message) {
				for(int i = 0; i < clients.size(); i++) {
					ClientThread t = clients.get(i);
					try {
					// Updates message in my object
					// number of clients 
					gameInfo.message = message;
					// Sends my object
					 t.out.writeObject(new theInfo(gameInfo));
					}
					catch(Exception e) {}
				}
			}
			
			public void run(){
					
				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);	
				}
				catch(Exception e) {
					System.out.println("Streams not open");
				}
				
				updateClients("new client on server: client #"+count);
					
				 while(true) {
					    try {
					    	gameInfo = (theInfo) in.readObject();
					    	// I think guess is being overwritten
					    	// If player 1 answered update necessary things
					    	if (count == 1) {
					    		gameInfo.p1Guess = gameInfo.Guess;
					    		gameInfo.p1GuessTotal = gameInfo.GuessTotal;
					    		gameInfo.p1Answered = true;
						    	callback.accept("client: " + count + " played: " + gameInfo.p1Guess +
						    					"\nand guessed: " + gameInfo.p1GuessTotal);
						    	updateClients("client #"+count+" answered.");
					    	}
					    	// If player 2 answered update necessary things
					    	else if (count == 2) {
					    		gameInfo.p2Guess = gameInfo.Guess;
					    		gameInfo.p2GuessTotal = gameInfo.GuessTotal;
					    		gameInfo.p2Answered = true;
						    	callback.accept("client: " + count + " played: " + gameInfo.p2Guess +
				    					"\nand guessed: " + gameInfo.p2GuessTotal);
						    	updateClients("client #"+count+" answered.");
					        	}
					    	
					    	// Logic for scoring
					    	if (gameInfo.p1Answered && gameInfo.p2Answered) {
					    		gameInfo.sum = gameInfo.p2Guess + gameInfo.p1Guess;
					    		// above works
					    		if(gameInfo.sum == gameInfo.p1GuessTotal) {
					    			gameInfo.p1Wins++;
					    		}
					    		if(gameInfo.sum == gameInfo.p2GuessTotal) {
					    			gameInfo.p2Wins++;
					    		}
					    		// stuck here, wins are not updating
					    		
					    		// They answered so now it resets
					    		gameInfo.p1Answered = false;
					    		gameInfo.p2Answered = false;
					    		
					    		// Updates everything, this needs to be at the end
					    		updateClients("the sum of fingers: "+gameInfo.sum+
					    				"\nPlayer 1 # of wins: "+gameInfo.p1Wins+
					    				"\nPlayer 2 # of wins: "+gameInfo.p2Wins);
					    	}
					    	
					    }
					    catch(Exception e) {
					    	callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
					    	updateClients("Client #"+count+" has left the server!");
					    	clients.remove(this);
					    	break;
					    }
					}
				}//end of run
			
			
		}//end of client thread
}


	
	

	
