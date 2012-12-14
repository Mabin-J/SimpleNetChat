package info.mabin.android.simplechat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import android.os.AsyncTask;
import android.util.Log;

public class NetTCPClient extends NetTCP{
	String serverIpAddr = "";
	
	public NetTCPClient(String serverIpAddr, int serverPort, Chat chatActivity){
		this.serverIpAddr = serverIpAddr;
		this.serverPort = serverPort;
		this.chatActivity = chatActivity;
		Log.i("Net", "Create");
	}
	
	@Override
	protected void connect(){
		try{
			socket = new Socket(serverIpAddr, serverPort);
			out = new ObjectOutputStream( socket.getOutputStream() );
			in = new ObjectInputStream( socket.getInputStream() );
			
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