package clientserverpackage;
import java.io.*;
import java.net.*;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Client {
	
	static PriorityQueue<String> packetQ = new PriorityQueue<String>();
    public static void main(String[] args) throws IOException {
        
    	Scanner keyboard = new Scanner(System.in);
    	System.out.println("Enter the host name");
        String hostName = keyboard.nextLine();//192.168.1.129
    	System.out.println("Enter the port number");
        int portNumber = Integer.parseInt(keyboard.nextLine());
        keyboard.close();

        try (
            Socket echoSocket = new Socket(hostName, portNumber);
        	PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            ) 
        {
        	System.out.println("Connected to socket - host="+hostName+" and port="+portNumber);
        	out.println("Start sending");
        	//echoSocket.setSoTimeout(5000);
        	while(true)
			{
        		
        		String serverMessage = in.readLine();
        		if(serverMessage != null) 
        		{
        			if(serverMessage.equals("YOU_HAVE_RECEIVED_ALL_PACKETS"))
        				break;
        			//echo first two characters which is id
					out.println(serverMessage.substring(0, 2));
					// first two string digits automatically will prioritize rest of string based on ASCII?
					if(serverMessage.matches("\\d\\d\\w+"))
						packetQ.add(serverMessage);
        		}
			}
        	//read all packets after all have been received, leaving out ids
        	while(!packetQ.isEmpty()) 
        	{
        		System.out.print(packetQ.poll().substring(2)+" ");
        	}
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } 
    }
}