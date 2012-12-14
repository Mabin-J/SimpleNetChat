package info.mabin.android.simplechat;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class Chat extends Activity{
	private int mode;
	private int type;
	
	private Net connection;
	private TextView viewMessage;
	private EditText editSendMessage;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);

	}

	protected void onResume(){
		super.onResume();
		Intent i = this.getIntent();
		Bundle bundle = i.getExtras();
		
		viewMessage = (TextView) findViewById(R.id.viewMessage);
		editSendMessage = (EditText) findViewById(R.id.editSndMsg);
		
		mode = (Integer) bundle.get("mode");
		type = (Integer) bundle.get("type");
		
		Log.d("Type", type + "");
		
		if(mode == Net.MODE_SERVER){
			if(type == Net.TYPE_TCP)
				connection = new NetTCPServer((Integer) bundle.get("port"), this);
			else 
				connection = new NetUDPServer((Integer) bundle.get("port"), this);
		} else {
			if(type == Net.TYPE_TCP)
				connection = new NetTCPClient((String) bundle.get("ipaddr"), (Integer) bundle.get("port"), this);
			else 
				connection = new NetUDPClient((String) bundle.get("ipaddr"), (Integer) bundle.get("port"), this);		
		}

		connection.execute();
		
		
		
		findViewById(R.id.btnSend).setOnClickListener(onClickSend);
	}
	
	OnClickListener onClickSend = new OnClickListener(){
		@Override
		public void onClick(View v) {
			if(editSendMessage.getText().equals(""))
				return;
			
			try {
				connection.sendMessage(editSendMessage.getText().toString());
				viewMessage.append("Me: " + editSendMessage.getText().toString() + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
			editSendMessage.setText("");
		}
	};
	
	public void showMessage(String message){
		viewMessage.append(message + "\n");
	}
	
	protected void onPause(){
		connection.disconnect();
		connection.cancel(true);
		finish();
		super.onPause();
	}
}
