package clientserverpackage;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.net.*;
import java.io.*;

public abstract class Server {
	
	ArrayList<String> packets;
	public  void setPackets(String[] arr) {
		packets = new ArrayList<>(Arrays.asList(arr));
	}
	
	boolean packetProbability;
	public abstract void setProbability();
	
	public void performTCPServerProtocol() {
    	
    	Scanner keyboard = new Scanner(System.in);
    	System.out.println("Enter port number");
        int portNumber = Integer.parseInt(keyboard.nextLine());
        keyboard.close();
        
        try (
            ServerSocket serverSocket =
                new ServerSocket(portNumber);
            Socket clientSocket = serverSocket.accept();   
        	
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);                   
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        		
        ) {
        	System.out.println("Server started on port "+ portNumber);
        	out.println("Sending");
        	//check that there are still non-received packets
        	while (!packets.stream().allMatch(x -> x == null))
            {   
        		
        		String inputLine = in.readLine();
        		if(inputLine.matches("\\d\\d.+"))
        		{
	        			//serverSocket.setSoTimeout(3000);
	        		//can parse 01 into 1?
	            	int receivedPacketNumber = Integer.parseInt(inputLine);//contains first 2 digits of packet id echoed by client
	            	//set received packets to null.
	            	packets.set(receivedPacketNumber, null);
	            	//go through all packets as long as there are non null ones - maybe needs optimization
		            for (int i = 0; i < packets.size(); i++) 
		            {   
		            	String packetText = packets.get(i);
		            	if(packetText != null) 
		            	{
		            		//send packets. use index in arrayList as "numerical" String id. Append "0" to id if single digit
		            		String packetId = i >= 10 ? ""+i : "0"+i;
		            		String fullPacket = packetId+packetText;
		            		if(packetProbability) 
			                {
			                	out.println(fullPacket);
			                }
		            	}
		            }
        		}
            }
        	//inform client that all packets were received by client
        	out.println("YOU_HAVE_RECEIVED_ALL_PACKETS");
            
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}







