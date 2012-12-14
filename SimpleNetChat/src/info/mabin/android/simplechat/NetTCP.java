package info.mabin.android.simplechat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.os.AsyncTask;
import android.util.Log;

public abstract class NetTCP extends Net{	
	protected final static int 	KEEP_ALIVE_SEC 		= 60;			// 연결 유지를 위한 시간
	
	protected int notiId = 0;
	
	protected Socket socket = null;
	protected ObjectOutputStream out = null;
	protected ObjectInputStream in = null;

	protected boolean isConnected = true;
	
	protected int serverPort;
	
	protected String message = "";
	
	protected Chat chatActivity;
	
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
				int command = in.readInt();
				message = (String) in.readObject();
				
				if(command == 1111){
					publishProgress(messageDecorator(message));
				} else if(command == 9999){
					publishProgress("Disconnected");
					disconnected();
				}
				
				if(messageQueue.size() != 0){
					out.writeInt(1111);
					out.writeObject(messageQueue.get(0));
					out.flush();
					messageQueue.remove(0);
				} else if(disconnectRequest) {
					out.writeInt(9999);
					out.writeObject("");
					out.flush();
					disconnected();
				} else {
					out.writeInt(1999);
					out.writeObject("");
					out.flush();
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
			
			in.close();
			out.close();
			socket.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	protected void onProgressUpdate(String... params){
		chatActivity.showMessage(params[0]);
	}

	protected abstract String messageDecorator(String message);
}