package info.mabin.android.simplechat;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.widget.TextView;

public class NetGetMyIp extends AsyncTask<Void, String, Void>{
	TextView viewIp;
	
	public NetGetMyIp(TextView viewIp){
		this.viewIp = viewIp;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		String myIp = "";										// 현재 IP저장을 위한 변수
		Socket socket;
		try {
			socket = new Socket("www.google.com", 80);
			myIp = socket.getLocalAddress().toString();
			publishProgress(myIp.substring(1, myIp.length()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	protected void onProgressUpdate(String... params) {				// UI변경을 위한 메소드
		viewIp.setText(params[0]);		
	}
}
