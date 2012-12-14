package info.mabin.android.simplechat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

public abstract class Net extends AsyncTask<Void, String, Void> {
	final static int TYPE_TCP = 0;
	final static int TYPE_UDP = 1;
	
	final static int MODE_SERVER = 0;
	final static int MODE_CLIENT = 1;
	
	protected boolean disconnectRequest = false;
	
	protected List<String> messageQueue = new ArrayList<String>(10);
	
	public void sendMessage(String msg) throws IOException {
		messageQueue.add(msg);
	}
	
	public void disconnect(){
		disconnectRequest = true;
	}
}
