package info.mabin.android.simplechat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import android.os.AsyncTask;
import android.util.Log;

public class NetUDPClient extends NetUDP{
	String serverIpAddr = "";
	
	public NetUDPClient(String serverIpAddr, int serverPort, Chat chatActivity){
		this.serverIpAddr = serverIpAddr;
		this.serverPort = serverPort;
		this.chatActivity = chatActivity;
		Log.i("Net", "Create");
	}
	
	@Override
	protected void connect(){
		try{
			
			socket = new DatagramSocket(serverPort);
			partnerIPaddress = (new InetSocketAddress(serverIpAddr, serverPort)).getAddress();
			partnerPort = serverPort;
			buffer[0] = 0;
			buffer[1] = 0;
			outPacket = new DatagramPacket(buffer, buffer.length, partnerIPaddress, partnerPort);
			socket.send(outPacket);
			
						
			Log.i("Still"," mySocket()... ");
		} catch (Exception e){
			e.printStackTrace();	
		}
	}
	
	@Override
	protected String messageDecorator(String message) {
		return "Server: " + message;
	}
}