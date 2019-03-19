package clientserverpackage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.net.*;

public class Demo extends Server {
	
	public static void main(String[] args) throws IOException {
			
		String originalMessage = "One two three four five six seven eight nine ten eleven twelve thirteen fourteen fifteen "
	        	+ "sixteen seventeen eighteen nineteen twenty twenty-one twenty-two twenty-three twenty-four twenty-five.";
	      
	    //split into packets and add to array
	    String[] packetsArray = originalMessage.split(" ");//new String[25];
	    //set Server's ArrayList to a conversion of the array
	   	Demo tcpd = new Demo();
	    tcpd.setPackets(packetsArray);
	   	tcpd.setProbability();
	    tcpd.performTCPServerProtocol();
		}
	@Override
	public void setProbability()
	{
	    double probability = 0.80;    
	    double randomValue = Math.random();
	    packetProbability = randomValue <= probability;
	}
}