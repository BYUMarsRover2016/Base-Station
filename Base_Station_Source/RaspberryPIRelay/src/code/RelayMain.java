package code;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class RelayMain {
	protected static final int PORT = 4444;
    protected static DatagramSocket socket = null;
    protected static CommonData com = null;
    protected static InetAddress wiznetAddress = null;
    protected static InetAddress returnAddress = null;
    protected static byte[] buf = new byte[6];
    static boolean quit = false;
    
    public static void main(String[] args)
    {
    	try
    	{
    		socket = new DatagramSocket(PORT);
    		wiznetAddress = InetAddress.getByName("192.168.10.193");
	        //DatagramPacket packet = new DatagramPacket(buf, buf.length);
	        
	        System.out.println("Relay Started");
	        
    		while(!quit)	// while we haven't been told to stop
	        {
    			DatagramPacket packet = new DatagramPacket(buf, buf.length);
	        	socket.receive(packet);
	  // figure out how to not check for returnAddress each time but not just set it once and forget it
	        	if(packet.getAddress() == wiznetAddress)
	        	{
		        	packet = new DatagramPacket(buf, buf.length ,wiznetAddress, PORT);
	        	}
	        	else
	        	{
	        		if(returnAddress == null)
	        			returnAddress = packet.getAddress();
	        		packet = new DatagramPacket(buf, buf.length ,returnAddress, PORT);
	        	}
	        	socket.send(packet);
	        	
	        	
	        }
    	}
    	catch (IOException e)
    	{
    		//if (!(e.getMessage().equals("socket closed")))	// we want to ignore socket closed exceptions, we cause this on purpose when the thread is interrupted to force it to stop waiting to receive a packet
    		{
        		System.err.println("Some sort of exception in RelayMain, Uh oh.");
        		e.printStackTrace();
    		}
        }
    }
}
