import java.net.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.*;

public class Rojahn_ClientServer {

	/*
	*** Command line arguments ***
	Server: java Rojahn_ClientServer playerName server port
	Client: java Rojahn_ClientServer playerName client address port
	*/
	public static void main(String[] args) throws IOException, InterruptedException {
		
		// Get the name of the player
		String playerName = args[0];
		
		// Print some info about RPS
		System.out.println("\nROCK-PAPER-SCISSORS (RPS)  by Spencer Rojahn");
		System.out.println("\nROCK - R\nPAPER - P\nSCISSORS - S");
		System.out.println("\nGAME RESULTS: ROCK beats SCISSORS, SCISSORS beats PAPER, PAPER beats ROCK");
		
		
		// If the program is run on server side
		if (args[1].equals("server")) {
			
			// Get the specified port number and create a server socket
			int port = Integer.parseInt(args[2]);	
			ServerSocket serverSock = new ServerSocket(port);
			
			// Block until a connection is made to the server and create the socket
			System.out.println("\nWaiting for an opponent...");
			Socket sock = serverSock.accept();
			
			// Create a server output and input stream
			DataOutputStream serverOut = new DataOutputStream(sock.getOutputStream());
			DataInputStream serverIn = new DataInputStream(sock.getInputStream());
			Scanner scan = new Scanner(System.in);
			
			// Send and receive player names
			serverOut.writeUTF(playerName);
			String clientName = serverIn.readUTF();
			
			// Notified that a player has connected and do "handshake"
			System.out.println("\nA player (client) named " + clientName +" has connected.");
			serverOut.writeUTF("Hi "+clientName+", it\'s nice to meet you!");
			
			// Receive "handshake" back from client
			String clientResponse = serverIn.readUTF();
			System.out.println(clientName +": "+clientResponse);
			
			// Make a move
			System.out.print("\nChoose a move (R for rock, P for paper, or S for scissors): ");
			String serverMove = scan.next();
			
			// Send move to opponent and wait for them to make a move
			serverOut.writeUTF(serverMove);
			System.out.println("\nGood choice! " + clientName+" is choosing a move now. Please wait...");
			
			// Notified when opponent makes move
			String clientMove = serverIn.readUTF();
			System.out.println(clientName+" has chosen a move.");
		
			// Determine the winner
			String winner = determineWinner(clientMove, serverMove);
			
			// Count down to results
			resultsCountDown();
			
			// Print out the results of the game
			if (winner.equalsIgnoreCase("client"))  {
				System.out.println("\n\nYour opponent, " +clientName+", has played " +clientMove.toUpperCase() +".");
				System.out.println("You have LOST :(. "+clientName+" has WON the duel. "+clientMove.toUpperCase()+  " beats " +serverMove.toUpperCase()+".");
			} else if (winner.equalsIgnoreCase("server"))  {
				System.out.println("\n\nYour opponent, " +clientName+", has played " +clientMove.toUpperCase() +".");
				System.out.println("You have WON the duel!! "+serverMove.toUpperCase()+  " beats " +clientMove.toUpperCase()+".");
			} else {
				System.out.println("\n\nYour opponent, " +clientName+", has played " +clientMove.toUpperCase() +".");
				System.out.println("It is a TIE! Both players played " +serverMove.toUpperCase()+".");
			}
			System.out.println();
				
			// Close sockets and scanner
			sock.close();
			serverSock.close();
			scan.close();
			
		// If the program is run on client side
		} else if (args[1].equals("client")) {
			
			// Get the specified port number and address to create a socket
			int port = Integer.parseInt(args[3]);	 
			Socket sockClient = new Socket(args[2], port);
			
			// Create a client output and input stream
			DataOutputStream clientOut = new DataOutputStream(sockClient.getOutputStream());	
			DataInputStream clientIn = new DataInputStream(sockClient.getInputStream());
			Scanner scan = new Scanner(System.in);
			
			// Send and receive player names
			clientOut.writeUTF(playerName);
			String serverName = clientIn.readUTF();
			
			// Notified that you have established a connection with the other player and do "handshake"
			System.out.println("\nYou have established a connection with a player (server) named " + serverName+ ".");
			clientOut.writeUTF("Hi "+serverName +", it\'s nice to meet you!");
			
			// Receive "handshake" back from server
			String serverPrompt = clientIn.readUTF();
			System.out.println(serverName+": " +serverPrompt);
			
			// Wait while the other player (server) selects a move
			System.out.println("\n" + serverName+" is choosing a move. Please wait...");
			
			// Notified when the other player has chosen a move
			String serverMove = clientIn.readUTF();
			System.out.println(serverName+" has chosen a move.");
		
			// Now it is the clients turn to make a move
			System.out.print("\nYour turn. Choose a move (R for rock, P for paper, or S for scissors): ");
			String clientMove = scan.next();
			
			// Send the client move to the other player (server)
			clientOut.writeUTF(clientMove);
			
			// Determine who has won
			String winner = determineWinner(clientMove, serverMove);
			
			// Dramatic count down
			resultsCountDown();
			
			// Print out the resutls of the duel
			if (winner.equalsIgnoreCase("client"))  {
				System.out.println("\n\nYour opponent, " +serverName+", has played " +serverMove.toUpperCase() +".");
				System.out.println("You have WON the duel!! "+clientMove.toUpperCase()+  " beats " +serverMove.toUpperCase()+".");
			} else if (winner.equalsIgnoreCase("server"))  {
				System.out.println("\n\nYour opponent, " +serverName+", has played " +serverMove.toUpperCase() +".");
				System.out.println("You have LOST :(. "+serverName+" has WON the duel! "+serverMove.toUpperCase()+  " beats " +clientMove.toUpperCase()+".");
			} else {
				System.out.println("\n\nYour opponent, " +serverName+", has played " +serverMove.toUpperCase() +".");
				System.out.println("It is a TIE! Both players played " +serverMove.toUpperCase()+".");
			}
			System.out.println();
			
			// Close socket and scanner
			sockClient.close();
			scan.close();
					
		} 
	}
	
	// Helper function to determine who has won the duel based on the moves by each player
	public static String determineWinner(String clientMove, String serverMove) {
		if (clientMove.equalsIgnoreCase(serverMove)) {
			return "tie";
		} else if ((clientMove.equalsIgnoreCase("R") && serverMove.equalsIgnoreCase("S")) || (clientMove.equalsIgnoreCase("S") && serverMove.equalsIgnoreCase("P")) || (clientMove.equalsIgnoreCase("P") && serverMove.equalsIgnoreCase("R"))) {
			return "client";
		} else {
			return "server";
		}
	}
	
	// Helper function to do the dramatic countdown
	public static void resultsCountDown() throws InterruptedException {
		System.out.print("\nRESULTS in ");
		for (int i=3; i > 0; i--) {
			System.out.print(i+" ... ");
			TimeUnit.SECONDS.sleep(1);
		}
	}
	
	
}

