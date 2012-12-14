package info.mabin.android.simplechat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

public abstract class NetUDP extends Net{	
	protected final static int	KEEP_ALIVE_SEC 		= 60;			// 연결 유지를 위한 시간
	
	protected int notiId = 0;
	
	protected DatagramSocket socket;
	protected DatagramPacket inPacket, outPacket;
	protected InetAddress partnerIPaddress;
	protected int partnerPort;
	protected byte[] buffer = new byte[1024];

	protected boolean isConnected = true;
	
	protected int serverPort;
	
	protected String message = "";
	
	protected Chat chatActivity;
	
	public NetUDP(){
		buffer = new byte[1024];
	}
	
	public boolean isConnected(){
		if(socket == null)
			return false;
		
		return socket.isConnected();
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		try {
			connect();
			publishProgress("Connected");
		} catch (IOException e1) {
			e1.printStackTrace();
			isConnected = false;
			publishProgress("PortOpenFail");
		}
		
		
		
		while(isConnected){
			try {
// Scheduler?? --------------------------------------
				Thread.sleep(1000);
				
// Receive Job Start ---------------------------------------
				inPacket = new DatagramPacket(buffer, buffer.length);
				socket.receive(inPacket);
				
				int command = buffer[0];
				message = makeString(buffer);
				
				if(command == 11){
					publishProgress(messageDecorator(message));
				} else if(command == 99){
					buffer[0] = 99;
					buffer[1] = 0;
					outPacket = new DatagramPacket(buffer, buffer.length, partnerIPaddress, partnerPort);
					disconnected();
				}
				
				if(messageQueue.size() != 0){
					buffer = makeData(messageQueue.get(0));
					buffer[0] = 11;
					outPacket = new DatagramPacket(buffer, buffer.length, partnerIPaddress, partnerPort);
					socket.send(outPacket);
					messageQueue.remove(0);
				} else if(disconnectRequest){
					isConnected = false;
					buffer[0] = 99;
					buffer[1] = 0;
					outPacket = new DatagramPacket(buffer, buffer.length, partnerIPaddress, partnerPort);
					socket.send(outPacket);
					socket.receive(inPacket);
					disconnected();
				}else {
					buffer[0] = 19;
					buffer[1] = 0;
					outPacket = new DatagramPacket(buffer, buffer.length, partnerIPaddress, partnerPort);
					socket.send(outPacket);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	protected abstract void connect() throws IOException;

	private void disconnected(){
		try{
			isConnected = false;
			
			socket.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	protected void onProgressUpdate(String... params){
		chatActivity.showMessage(params[0]);
	}

	protected abstract String messageDecorator(String message);
	
	protected byte[] makeData(String data){
		byte[] result = new byte[1024];
		byte[] tmp = data.getBytes();
		result[1] = (byte)tmp.length;
		
		for(int i = 0; i < result[1]; i++){
			result[i + 2] = tmp[i];
		}
		
		return result;
	}
	
	protected String makeString(byte[] data){
		String result;
		byte[] tmp = new byte[data[1]];
		
		for(int i = 0; i < data[1]; i++){
			tmp[i] = data[i + 2];
		}
		
		result = new String(tmp);
		return result;
	}
}