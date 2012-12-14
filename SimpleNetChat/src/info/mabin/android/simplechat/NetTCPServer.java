package info.mabin.android.simplechat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import android.os.AsyncTask;
import android.util.Log;

public class NetTCPServer extends NetTCP{
	protected ServerSocket server;
	public NetTCPServer(int serverPort, Chat chatActivity){
		this.serverPort = serverPort;
		this.chatActivity = chatActivity;
		Log.i("Net", "Create");
	}
	
	@Override
	protected void connect() throws IOException{
			server = new ServerSocket(serverPort);
			socket = server.accept();
			out = new ObjectOutputStream( socket.getOutputStream() );
			in = new ObjectInputStream( socket.getInputStream() );
			
			out.writeInt(1999);
			out.writeObject("");
			out.flush();
			
			Log.i("Still"," mySocket()... ");
	}
	
	@Override
	protected String messageDecorator(String message) {
		return "Client: " + message;
	}
}