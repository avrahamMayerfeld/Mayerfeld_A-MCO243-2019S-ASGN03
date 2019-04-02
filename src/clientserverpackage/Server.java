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
	/*
	Explanation of protocol: 
	(The abstract Server class is implemented in a demo class which sets the probability as desired.)
	The first packet is printed out to the client and not dropped so that the process can begin. 
	There is a while loop that continues until all of the packets in the server are set to empty by the server,
	(explained further below)
	after which it sends a non-droppable message to the client that it knows the client received everything, 
	so the client can continue and print out all of the packets in order, which is accomplished by a priorityQueue which 
	sorts the packets based on ACII. Each packet gets its ArrayList index appended to it for id purposes. and a zero is 
	appended to single digit indexes. The eclient echos back the digit id as well as the first character of the packet itself 
	to confirm that it is a word character, for valid I/O purposes. The first two digits automatically prioritize the rest 
	of the packet based on ASCII. The Client verifies that the message sent is a valid packet and not a message telling the client 
	that it is not a packet. It only adds packets that have not been added to the priorityQueue, and when it finally prints everything
	tto the console, it is without the digital id of course. 
	The while loop in the server which was mentioned does the following:
	1) reads the echo from the client,
	2)sets the packet number index that was echoed in the List to "##$$$EMPTY_SLOT$##", to know it was received,
	3)
	4)

	
	
	
	*/
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
        	
        	//print out first packet so can work
        	out.println("00"+packets.get(0));
        	
        	//serverSocket.setSoTimeout(3000);
        	//check that there are still non-received packets in while loop
        	while (!packets.stream().allMatch(x -> x.equals("##$$$EMPTY_SLOT$##")))
        	{   
        		
        		String inputLine = in.readLine();
        		
       			//can parse 01 into 1
        		String idSubstring = inputLine.substring(0,2);
	           	int receivedPacketNumber = Integer.parseInt(idSubstring);//contains first 2 digits of packet id echoed by client
	           	//set received packets to Empty.
	           	packets.set(receivedPacketNumber, "##$$$EMPTY_SLOT$##");
	            //go through all packets as long as there are non empty ones - maybe needs optimization
		        for (int i = 0; i < packets.size(); i++) 
	            {   
		        	String packetText = packets.get(i);
		           	if(!packetText.equals("##$$$EMPTY_SLOT$##")) 
		           	{
		           		//send packets. use index in arrayList as "numerical" String id. Append "0" to id if single digit
		           		String packetId = i >= 10 ? ""+i : "0"+i;
		           		String fullPacket = packetId+packetText;
		           		setProbability();
		           		if(packetProbability) 
		           		{
		           			out.println(fullPacket);
			            }
		           		else
		           			out.println("00xx");
		           	}
		            else
		            	out.println("00yy");
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







