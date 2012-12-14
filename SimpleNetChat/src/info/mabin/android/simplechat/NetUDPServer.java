package info.mabin.android.simplechat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import android.os.AsyncTask;
import android.util.Log;

public class NetUDPServer extends NetUDP{
	
	public NetUDPServer(int serverPort, Chat chatActivity){
		this.serverPort = serverPort;
		this.chatActivity = chatActivity;
		Log.i("Net", "Create");
	}
	
	@Override
	protected void connect() throws IOException{
		socket = new DatagramSocket(serverPort);
		inPacket = new DatagramPacket(buffer, buffer.length);

		socket.receive(inPacket);
		
		partnerIPaddress = inPacket.getAddress();
		partnerPort = inPacket.getPort(); 
	
		buffer[0] = 19;
		buffer[1] = 0;
		
		outPacket = new DatagramPacket(buffer, buffer.length, partnerIPaddress, partnerPort);
		socket.send(outPacket);
		
		Log.i("Still"," mySocket()... ");
	}
	
	@Override
	protected String messageDecorator(String message) {
		return "Client: " + message;
	}
}